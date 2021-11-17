package services.executor.testcase;

import services.executor.action.ActionExecutor;
import services.executor.dtos.SchedulerDto;
import services.restclients.schedule.models.ScheduleTestPlanModel;
import services.restclients.schedule.models.ScheduledTestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collections;

@ApplicationScoped
public class TestCaseExecutorImpl implements TestCaseExecutor {

    @Inject
    ActionExecutor actionExecutor;

    @Override
    public void execute(SchedulerDto schedulerDto, ScheduleTestPlanModel scheduleTestPlanModel) {
        for (ScheduledTestCaseModel scheduledTestCaseModel : scheduleTestPlanModel.getScheduledTestCaseModel()) {
            Collections.sort(scheduledTestCaseModel.getScheduledActionDetails(), (a1, a2) -> (int) (a1.getOrder() - a2.getOrder()));
            actionExecutor.execute(schedulerDto, scheduledTestCaseModel);
        }
    }
}
