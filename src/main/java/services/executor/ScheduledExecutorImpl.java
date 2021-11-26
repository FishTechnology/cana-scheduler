package services.executor;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import services.commons.CanaSchedulerUtility;
import services.executor.dtos.SchedulerDto;
import services.executor.testplan.TestPlanExecutor;
import services.restclients.commons.ErrorMessageModel;
import services.restclients.schedule.ScheduleServiceRestClient;
import services.restclients.schedule.models.ScheduleStatusDao;
import services.restclients.schedule.models.UpdateScheduleStatusModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class ScheduledExecutorImpl implements ScheduledExecutor {

    @Inject
    @RestClient
    ScheduleServiceRestClient scheduleServiceRestClient;

    @Inject
    TestPlanExecutor testPlanExecutor;

    @Override
    public void executeSchedule() {
        SchedulerDto schedulerDto = new SchedulerDto();
        schedulerDto.setScheduleId(39L);
        String errorMessage = null;
        StopWatch stopWatch = StopWatch.createStarted();
        try {
            try {
                var scheduleDetailModel = scheduleServiceRestClient.getScheduler(schedulerDto.getScheduleId());
                if (scheduleDetailModel == null || scheduleDetailModel.getScheduleModel() == null) {
                    return;
                }
                schedulerDto.setScheduleDetail(scheduleDetailModel);

                validateSchedule(schedulerDto);

            } catch (Exception ex) {

            }

            try {
                var errors = updateScheduleStatus(schedulerDto.getScheduleId(), ScheduleStatusDao.INPROGRESS);
                if (CollectionUtils.isNotEmpty(errors)) {
                    errorMessage = CanaSchedulerUtility.getMessage(errors);
                }
            } catch (Exception exception) {
                errorMessage = CanaSchedulerUtility.getMessage(exception);
            }

            var errorMessages = scheduleServiceRestClient.copyTestPlanDetail(schedulerDto.getScheduleId());
            if (CollectionUtils.isNotEmpty(errorMessages)) {
                updateScheduleStatus(schedulerDto.getScheduleId(), ScheduleStatusDao.ERROR, CanaSchedulerUtility.getMessage(errorMessages));
                return;
            }


            if (StringUtils.isNotEmpty(errorMessage)) {
                stopWatch.stop();
                updateScheduleStatus(schedulerDto.getScheduleId(), ScheduleStatusDao.ERROR, errorMessage, stopWatch.getTime());
                return;
            }

            try {
                testPlanExecutor.execute(schedulerDto);
            } catch (Exception exception) {
                stopWatch.stop();
                updateScheduleStatus(schedulerDto.getScheduleId(), ScheduleStatusDao.ERROR, CanaSchedulerUtility.getMessage(exception), stopWatch.getTime());
            }

            if (stopWatch.isStarted()) {
                stopWatch.stop();
                updateScheduleStatus(schedulerDto.getScheduleId(), ScheduleStatusDao.COMPLETED, stopWatch.getTime());
            }
        } catch (Exception exception) {
            stopWatch.stop();
            updateScheduleStatus(schedulerDto.getScheduleId(), ScheduleStatusDao.ERROR, CanaSchedulerUtility.getMessage(exception), stopWatch.getTime());
        }
    }

    private List<ErrorMessageModel> validateSchedule(SchedulerDto schedulerDto) {
        var scheduleTestPlanModel = schedulerDto.getScheduleDetail().getScheduleTestPlanModel();
        if (Objects.isNull(scheduleTestPlanModel)) {
            return CanaSchedulerUtility.getErrorMessageModels("Schedule Test Plan Model is null for scheduleId=" + schedulerDto.getScheduleId());
        }

        if (Objects.isNull(
                schedulerDto
                        .getScheduleDetail()
                        .getScheduleIterationModel())) {
            return CanaSchedulerUtility.getErrorMessageModels("Schedule Iteration Model is null for scheduleId=" + schedulerDto.getScheduleId());
        }

        return Collections.emptyList();
    }

    private List<ErrorMessageModel> updateScheduleStatus(Long scheduleId,
                                                         ScheduleStatusDao scheduleStatusDao) {
        return updateScheduleStatus(scheduleId, scheduleStatusDao, null, 0);
    }

    private List<ErrorMessageModel> updateScheduleStatus(Long scheduleId,
                                                         ScheduleStatusDao scheduleStatusDao,
                                                         String errorMessage) {
        return updateScheduleStatus(scheduleId, scheduleStatusDao, errorMessage, 0);
    }

    private List<ErrorMessageModel> updateScheduleStatus(Long scheduleId,
                                                         ScheduleStatusDao scheduleStatusDao,
                                                         long totalDuration) {
        return updateScheduleStatus(scheduleId, scheduleStatusDao, null, totalDuration);
    }

    private List<ErrorMessageModel> updateScheduleStatus(Long scheduleId,
                                                         ScheduleStatusDao scheduleStatusDao,
                                                         String errorMessage,
                                                         long totalDuration) {
        UpdateScheduleStatusModel updateScheduleStatusModel = new UpdateScheduleStatusModel();
        updateScheduleStatusModel.setStatus(scheduleStatusDao.name());
        updateScheduleStatusModel.setErrorMessage(errorMessage);
        if (totalDuration != 0) {
            updateScheduleStatusModel.setTotalDuration(String.valueOf(totalDuration));
        }
        return scheduleServiceRestClient.updateScheduleStatus(scheduleId, updateScheduleStatusModel);
    }
}
