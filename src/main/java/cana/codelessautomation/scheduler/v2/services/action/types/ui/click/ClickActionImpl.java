package cana.codelessautomation.scheduler.v2.services.action.types.ui.click;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.UIAction;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.UIActionType;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import services.restclients.testcase.TestCaseModel;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClickActionImpl implements UIAction {
    @Override
    public void execute(ScheduledTestPlanDto schedulerDto, TestCaseModel scheduledTestCaseModel, ActionDetailModel scheduledActionDetailModel) {

    }

    @Override
    public UIActionType actionName() {
        return UIActionType.CLICK;
    }
}
