package cana.codelessautomation.scheduler.v2.services.action.types.ui.browser;

import cana.codelessautomation.scheduler.v2.services.action.types.ui.UIAction;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.UIActionType;
import services.executor.dtos.SchedulerDto;
import services.restclients.schedule.models.ScheduledActionDetailModel;
import services.restclients.schedule.models.ScheduledTestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class BrowserActionImpl implements UIAction {
    @Inject
    BrowserAction browserAction;

    @Override
    public void execute(SchedulerDto schedulerDto, ScheduledTestCaseModel scheduledTestCaseModel, ScheduledActionDetailModel scheduledActionDetailModel) throws Exception {
        browserAction.action(schedulerDto,scheduledTestCaseModel,scheduledActionDetailModel);
    }

    @Override
    public UIActionType actionName() {
        return UIActionType.BROWSER;
    }
}
