package cana.codelessautomation.scheduler.v2.services.scheduler.mappers;

import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import services.restclients.schedule.models.ScheduleModel;

public interface SchedulerServiceMapper {
    ScheduledTestPlanDto mapScheduleTestPlanDto(ScheduleModel scheduleModel);
}
