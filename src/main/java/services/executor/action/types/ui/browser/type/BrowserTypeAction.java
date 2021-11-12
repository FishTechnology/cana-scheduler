package services.executor.action.types.ui.browser.type;

import services.executor.dtos.SchedulerDto;
import services.restclients.schedule.models.ScheduledActionDetailModel;
import services.restclients.schedule.models.ScheduledTestCaseModel;

public interface BrowserTypeAction {
    void execute(SchedulerDto schedulerDto, ScheduledTestCaseModel scheduledTestCaseModel, ScheduledActionDetailModel scheduledActionDetailModel) throws Exception;
    BrowserType type();
}
