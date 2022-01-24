package cana.codelessautomation.scheduler.v2.services.scheduler.mappers;

import cana.codelessautomation.scheduler.v2.services.config.models.ConfigModel;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import cana.codelessautomation.scheduler.v2.services.system.models.SystemConfigModel;
import services.restclients.schedule.models.ScheduleModel;
import services.restclients.schedule.models.UpdateScheduleStatusModel;

import java.util.List;

public interface SchedulerServiceMapper {
    ScheduledTestPlanDto mapScheduleTestPlanDto(ScheduleModel scheduleModel, List<SystemConfigModel> systemConfigs, List<ConfigModel> configsDetails);

    UpdateScheduleStatusModel mapUpdateScheduleStatusModel(ScheduleStatusDao scheduleStatusDao, String errorMessage, String totalDuration);
}
