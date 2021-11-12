package services.executor.action.types.ui;

import services.executor.dtos.SchedulerDto;
import services.restclients.schedule.models.ScheduledActionDetailModel;
import services.restclients.schedule.models.ScheduledTestCaseModel;

public interface UIAction {
    void execute(SchedulerDto schedulerDto, ScheduledTestCaseModel scheduledTestCaseModel, ScheduledActionDetailModel scheduledActionDetailModel) throws Exception;
    UIActionType actionName();
}
