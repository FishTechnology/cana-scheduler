package services.executor.action;

import services.executor.action.types.Action;
import services.executor.dtos.SchedulerDto;
import services.restclients.schedule.models.ScheduledActionDetailModel;
import services.restclients.schedule.models.ScheduledTestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class ActionExecutorImpl implements ActionExecutor {
    @Inject
    Instance<Action> actionInstance;

    @Override
    public void execute(SchedulerDto schedulerDto, ScheduledTestCaseModel scheduledTestCaseModel) {
        for (ScheduledActionDetailModel scheduledActionDetailModel : scheduledTestCaseModel.getScheduledActionDetails()) {
            for (Action action : actionInstance) {
                if (action.actionName() == scheduledActionDetailModel.getType()) {
                    try {
                        action.execute(schedulerDto, scheduledTestCaseModel, scheduledActionDetailModel);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
        }
    }
}
