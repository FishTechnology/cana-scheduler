package cana.codelessautomation.scheduler.v2.services.action.types.ui;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.action.models.ActionTypeDao;
import cana.codelessautomation.scheduler.v2.services.action.types.Action;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import org.apache.commons.lang3.StringUtils;
import services.restclients.testcase.TestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class UIActionImpl implements Action {

    @Inject
    Instance<UIAction> uiActions;

    @Override
    public void execute(ScheduledTestPlanDto scheduledTestPlanDto, TestCaseModel testCaseModel, ActionDetailModel actionDetailModel) throws Exception {
        for (UIAction uiAction : uiActions) {
            if (StringUtils.equalsAnyIgnoreCase(uiAction.actionName().name(), actionDetailModel.getUiActionType())) {
                uiAction.execute(scheduledTestPlanDto, testCaseModel, actionDetailModel);
                return;
            }
        }
    }

    @Override
    public ActionTypeDao actionName() {
        return ActionTypeDao.UI_ACTION;
    }
}
