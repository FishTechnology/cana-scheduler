package cana.codelessautomation.scheduler.v2.services.action.types.ui;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import services.restclients.testcase.TestCaseModel;

public interface UIAction {
    void execute(ScheduledTestPlanDto schedulerDto, TestCaseModel scheduledTestCaseModel, ActionDetailModel scheduledActionDetailModel) throws Exception;
    UIActionType actionName();
}
