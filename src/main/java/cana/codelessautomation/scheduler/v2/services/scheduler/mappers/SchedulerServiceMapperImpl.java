package cana.codelessautomation.scheduler.v2.services.scheduler.mappers;

import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import services.restclients.schedule.models.ScheduleModel;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SchedulerServiceMapperImpl implements SchedulerServiceMapper {
    @Override
    public ScheduledTestPlanDto mapScheduleTestPlanDto(ScheduleModel scheduleModel) {
        ScheduledTestPlanDto scheduledTestPlanDto = new ScheduledTestPlanDto();
        scheduledTestPlanDto.setScheduleModel(scheduleModel);
        return scheduledTestPlanDto;
    }
}
