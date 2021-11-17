package services.executor.action;

import services.executor.dtos.SchedulerDto;
import services.restclients.schedule.models.ScheduledTestCaseModel;

public interface ActionExecutor {
    void execute(SchedulerDto schedulerDto, ScheduledTestCaseModel scheduledTestCaseModel);
}
