package cana.codelessautomation.scheduler.v2.services.action.types.database;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.action.models.ActionTypeDao;
import cana.codelessautomation.scheduler.v2.services.action.types.Action;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import services.restclients.testcase.TestCaseModel;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DataActionImpl implements Action {
    @Override
    public void execute(ScheduledTestPlanDto scheduledTestPlanDto, TestCaseModel testCaseModel, ActionDetailModel actionDetailModel) {

    }

    @Override
    public ActionTypeDao actionName() {
        return ActionTypeDao.DATABASE;
    }
}
