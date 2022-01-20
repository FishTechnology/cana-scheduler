package cana.codelessautomation.scheduler.v2.services.action.types.ui.browser;

import cana.codelessautomation.scheduler.v2.services.action.types.ui.browser.type.BrowserTypeAction;
import org.apache.commons.lang3.StringUtils;
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

    public void action(SchedulerDto schedulerDto, ScheduledTestCaseModel scheduledTestCaseModel, ScheduledActionDetailModel scheduledActionDetailModel) throws Exception {
        for (BrowserTypeAction browserTypeAction : browserTypeActions) {
            if (StringUtils.equalsAnyIgnoreCase(browserTypeAction.type().name(),
                    schedulerDto.getScheduleDetail().getScheduleIterationModel().getBrowserType())) {
                browserTypeAction.execute(schedulerDto, scheduledTestCaseModel, scheduledActionDetailModel);
                break;
            }
        }
    }
}
