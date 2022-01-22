package cana.codelessautomation.scheduler.v2.services.action.types;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.action.models.ActionTypeDao;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import services.restclients.testcase.TestCaseModel;

public interface Action {
    void execute(ScheduledTestPlanDto scheduledTestPlanDto, TestCaseModel testCaseModel, ActionDetailModel actionDetailModel) throws Exception;
    ActionTypeDao actionName();
}
