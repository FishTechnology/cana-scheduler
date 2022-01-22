package cana.codelessautomation.scheduler.v2.services.action.types.ui.browser;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.UIAction;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.UIActionType;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import services.restclients.testcase.TestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class BrowserActionImpl implements UIAction {
    @Inject
    BrowserAction browserAction;

    @Override
    public void execute(ScheduledTestPlanDto schedulerDto, TestCaseModel scheduledTestCaseModel, ActionDetailModel scheduledActionDetailModel) throws Exception {
        browserAction.action(schedulerDto,scheduledTestCaseModel,scheduledActionDetailModel);
    }

    @Override
    public UIActionType actionName() {
        return UIActionType.BROWSER;
    }
}
