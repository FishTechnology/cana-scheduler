package services.executor.action.types;

import services.executor.dtos.SchedulerDto;
import services.restclients.action.ActionTypeDao;
import services.restclients.schedule.models.ScheduledActionDetailModel;
import services.restclients.schedule.models.ScheduledTestCaseModel;

public interface Action {
    void execute(SchedulerDto schedulerDto, ScheduledTestCaseModel scheduledTestCaseModel, ScheduledActionDetailModel scheduledActionDetailModel);
    ActionTypeDao actionName();
}
