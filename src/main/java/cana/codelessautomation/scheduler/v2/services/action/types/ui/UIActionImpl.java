package cana.codelessautomation.scheduler.v2.services.action.types.ui;

import cana.codelessautomation.scheduler.v2.services.action.types.Action;
import org.apache.commons.lang3.StringUtils;
import services.executor.dtos.SchedulerDto;
import services.restclients.action.ActionTypeDao;
import services.restclients.schedule.models.ScheduledActionDetailModel;
import services.restclients.schedule.models.ScheduledTestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class UIActionImpl implements Action {

    @Inject
    Instance<UIAction> uiActions;

    @Override
    public void execute(SchedulerDto schedulerDto, ScheduledTestCaseModel scheduledTestCaseModel, ScheduledActionDetailModel scheduledActionDetailModel) throws Exception {
        for (UIAction uiAction : uiActions) {
            if (StringUtils.equalsAnyIgnoreCase(uiAction.actionName().name(), scheduledActionDetailModel.getUiActionType())) {
                uiAction.execute(schedulerDto, scheduledTestCaseModel, scheduledActionDetailModel);
                return;
            }
        }
    }

    @Override
    public ActionTypeDao actionName() {
        return ActionTypeDao.UI_ACTION;
    }
}
