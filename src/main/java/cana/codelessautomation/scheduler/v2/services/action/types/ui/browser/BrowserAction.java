package cana.codelessautomation.scheduler.v2.services.action.types.ui.browser;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.browser.type.BrowserTypeAction;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import org.apache.commons.lang3.StringUtils;
import services.restclients.testcase.TestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class BrowserAction {

    @Inject
    Instance<BrowserTypeAction> browserTypeActions;

    public void action(ScheduledTestPlanDto schedulerDto, TestCaseModel scheduledTestCaseModel, ActionDetailModel scheduledActionDetailModel) throws Exception {
        for (BrowserTypeAction browserTypeAction : browserTypeActions) {
            if (StringUtils.equalsAnyIgnoreCase(browserTypeAction.type().name(),
                    schedulerDto.getScheduleDetail().getScheduleIteration().getBrowserType())) {
                browserTypeAction.execute(schedulerDto, scheduledTestCaseModel, scheduledActionDetailModel);
                break;
            }
        }
    }
}
