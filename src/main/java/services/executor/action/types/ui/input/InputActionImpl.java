package services.executor.action.types.ui.input;

import services.executor.action.types.ui.UIAction;
import services.executor.action.types.ui.UIActionType;
import services.executor.dtos.SchedulerDto;
import services.restclients.schedule.models.ScheduledActionDetailModel;
import services.restclients.schedule.models.ScheduledTestCaseModel;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InputActionImpl  implements UIAction {
    @Override
    public void execute(SchedulerDto schedulerDto, ScheduledTestCaseModel scheduledTestCaseModel, ScheduledActionDetailModel scheduledActionDetailModel) {

    }

    @Override
    public UIActionType actionName() {
        return UIActionType.INPUT;
    }
}
