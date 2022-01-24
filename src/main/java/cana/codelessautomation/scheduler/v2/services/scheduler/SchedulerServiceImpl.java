package cana.codelessautomation.scheduler.v2.services.scheduler;

import cana.codelessautomation.scheduler.v2.services.config.restclient.ConfigServiceRestClient;
import cana.codelessautomation.scheduler.v2.services.scheduler.mappers.ScheduleStatusDao;
import cana.codelessautomation.scheduler.v2.services.scheduler.mappers.SchedulerServiceMapper;
import cana.codelessautomation.scheduler.v2.services.scheduler.restclient.ScheduleServiceRestClient;
import cana.codelessautomation.scheduler.v2.services.system.models.GetSystemConfigsByAppIdModel;
import cana.codelessautomation.scheduler.v2.services.system.models.SystemConfigModel;
import cana.codelessautomation.scheduler.v2.services.system.restclient.SystemConfigRestClient;
import cana.codelessautomation.scheduler.v2.services.testplan.TestPlanService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import services.commons.CanaSchedulerUtility;
import services.restclients.commons.ErrorMessageModel;
import services.restclients.schedule.models.ScheduleModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class SchedulerServiceImpl implements SchedulerService {
    @Inject
    SchedulerServiceMapper schedulerServiceMapper;

    @Inject
    TestPlanService testPlanService;

    @Inject
    @RestClient
    ScheduleServiceRestClient scheduleServiceRestClient;

    @Inject
    @RestClient
    ConfigServiceRestClient configServiceRestClient;

    @Inject
    @RestClient
    SystemConfigRestClient systemConfigRestClient;

    @Override
    public void executeSchedule() {
        var scheduleModels = (List<ScheduleModel>) null;
        var getSystemConfigsByAppIdModel = (GetSystemConfigsByAppIdModel) null;

        try {
            getSystemConfigsByAppIdModel = systemConfigRestClient.getSystemConfigs();
        } catch (Exception exception) {

        }


        try {
            scheduleModels = scheduleServiceRestClient.getRunningSchedules();
        } catch (Exception ex) {
        }

        if (CollectionUtils.isNotEmpty(scheduleModels) && !isParallelExecutionAllowed(getSystemConfigsByAppIdModel)) {
            return;
        }

        var scheduleModel = (ScheduleModel) null;

        try {
            scheduleModel = scheduleServiceRestClient.getScheduleToExecute();
        } catch (Exception ex) {

        }

        if (Objects.isNull(scheduleModel)) {
            return;
        }

        try {
            var errorMessage = updateScheduleStatus(scheduleModel.getId(), ScheduleStatusDao.INPROGRESS, null, null);
            if (CollectionUtils.isNotEmpty(errorMessage)) {
                //TODO: log error message
                return;
            }

        } catch (Exception ex) {
            return;
        }

        var startedOn = System.nanoTime();

        var isErrorOccurred = false;
        String scheduleErrorMessage = null;
        try {
            var configsDetails = configServiceRestClient.getConfigsByAppId(scheduleModel.getApplicationId());

            var scheduledTestPlanDto = schedulerServiceMapper.mapScheduleTestPlanDto(scheduleModel, getSystemConfigsByAppIdModel.getSystemConfigs(), configsDetails);
            testPlanService.executeTestPlan(scheduledTestPlanDto);

        } catch (Exception ex) {
            isErrorOccurred = true;
            scheduleErrorMessage = CanaSchedulerUtility.getMessage(ex);
        }

        var endedOn = System.nanoTime();

        ScheduleStatusDao scheduleStatus = ScheduleStatusDao.COMPLETED;
        if (isErrorOccurred) {
            scheduleStatus = ScheduleStatusDao.ERROR;
        }

        try {
            var errorMessage = updateScheduleStatus(scheduleModel.getId(), scheduleStatus, scheduleErrorMessage, String.valueOf(endedOn - startedOn));
            if (CollectionUtils.isNotEmpty(errorMessage)) {
                //TODO: log error message
                return;
            }

        } catch (Exception ex) {
            //TODO: log error message
            return;
        }
    }

    private boolean isParallelExecutionAllowed(GetSystemConfigsByAppIdModel getSystemConfigsByAppIdModel) {
        if (CollectionUtils.isEmpty(getSystemConfigsByAppIdModel.getSystemConfigs()) || getSystemConfigsByAppIdModel.getSystemConfigs().size() == 0) {
            return false;
        }

        var parallelSystemConfig = getSystemConfigValue(getSystemConfigsByAppIdModel.getSystemConfigs(), "IS_PARALLEL_EXECUTION_ENABLE");

        if (Objects.isNull(parallelSystemConfig)) {
            return false;
        }
        if (!BooleanUtils.toBoolean(parallelSystemConfig.getValue())) {
            return false;
        }

        var parallelSystemConfigCount = getSystemConfigValue(getSystemConfigsByAppIdModel.getSystemConfigs(), "PARALLEL_EXECUTION_COUNT");
        if (Objects.isNull(parallelSystemConfigCount)) {
            return false;
        }
        Long parallelCount = Long.valueOf(parallelSystemConfigCount.getValue());
        if (parallelCount > 5) {
            return false;
        }
        return true;
    }

    private SystemConfigModel getSystemConfigValue(List<SystemConfigModel> systemConfigs, String systemConfigKey) {
        return systemConfigs.stream().filter(x -> StringUtils.equalsAnyIgnoreCase(x.getKey(), systemConfigKey)).findFirst().get();
    }

    private List<ErrorMessageModel> updateScheduleStatus(Long scheduleId, ScheduleStatusDao scheduleStatusDao, String errorMessage, String totalDuration) {
        var updateScheduleStatusModel = schedulerServiceMapper.mapUpdateScheduleStatusModel(scheduleStatusDao, errorMessage, totalDuration);
        return scheduleServiceRestClient.updateScheduleStatus(scheduleId, updateScheduleStatusModel);
    }
}
