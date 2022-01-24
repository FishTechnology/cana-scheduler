package cana.codelessautomation.scheduler.v2.services.scheduler.mappers;

import cana.codelessautomation.scheduler.v2.services.config.models.ConfigModel;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import cana.codelessautomation.scheduler.v2.services.system.models.SystemConfigModel;
import org.apache.commons.lang3.StringUtils;
import services.restclients.schedule.models.ScheduleModel;
import services.restclients.schedule.models.UpdateScheduleStatusModel;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class SchedulerServiceMapperImpl implements SchedulerServiceMapper {
    @Override
    public ScheduledTestPlanDto mapScheduleTestPlanDto(ScheduleModel scheduleModel, List<SystemConfigModel> systemConfigs, List<ConfigModel> configsDetails) {
        ScheduledTestPlanDto scheduledTestPlanDto = new ScheduledTestPlanDto();
        scheduledTestPlanDto.setScheduleDetail(scheduleModel);
        scheduledTestPlanDto.setSystemConfigs(systemConfigs);
        scheduledTestPlanDto.setApplicationConfigs(configsDetails);
        return scheduledTestPlanDto;
    }

    @Override
    public UpdateScheduleStatusModel mapUpdateScheduleStatusModel(ScheduleStatusDao scheduleStatusDao, String errorMessage, String totalDuration) {
        var updateScheduleStatusModel = new UpdateScheduleStatusModel();
        updateScheduleStatusModel.setStatus(scheduleStatusDao.name());
        if (StringUtils.isNotEmpty(errorMessage)) {
            updateScheduleStatusModel.setErrorMessage(errorMessage);
        }

        if (StringUtils.isNotEmpty(totalDuration)) {
            updateScheduleStatusModel.setTotalDuration(totalDuration);
        }

        return updateScheduleStatusModel;
    }
}
