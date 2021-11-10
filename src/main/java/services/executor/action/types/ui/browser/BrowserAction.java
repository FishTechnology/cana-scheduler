package services.executor.action.types.ui.browser;

import org.apache.commons.lang3.StringUtils;
import services.executor.action.types.ui.browser.type.BrowserTypeAction;
import services.executor.dtos.SchedulerDto;
import services.restclients.schedule.models.ScheduledActionDetailModel;
import services.restclients.schedule.models.ScheduledTestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class BrowserAction {

    @Inject
    Instance<BrowserTypeAction> browserTypeActions;

    public void action(SchedulerDto schedulerDto, ScheduledTestCaseModel scheduledTestCaseModel, ScheduledActionDetailModel scheduledActionDetailModel) {
        if (StringUtils.equalsAnyIgnoreCase(scheduledActionDetailModel.getBrowserActionType(), BrowserActionTypeDao.OPEN.name())) {
            for (BrowserTypeAction browserTypeAction : browserTypeActions) {
                browserTypeAction.execute(schedulerDto, scheduledTestCaseModel, scheduledActionDetailModel);
            }
        }
    }
}
