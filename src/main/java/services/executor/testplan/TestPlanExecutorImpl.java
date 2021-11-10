package services.executor.testplan;

import services.executor.dtos.SchedulerDto;
import services.executor.testcase.TestCaseExecutor;
import services.restclients.schedule.models.ScheduledTestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collections;

@ApplicationScoped
public class TestPlanExecutorImpl implements TestPlanExecutor {

    @Inject
    TestCaseExecutor testCaseExecutor;

    @Override
    public void execute(SchedulerDto schedulerDto) {
        var testPlanModel = schedulerDto.getScheduleDetail().getScheduleTestPlanModel();
        Collections.sort(testPlanModel.getScheduledTestCaseModel(), (a1, a2) -> (int) (a1.getOrder() - a2.getOrder()));
        for (ScheduledTestCaseModel scheduledTestCaseModel : testPlanModel.getScheduledTestCaseModel()) {
            testCaseExecutor.execute(schedulerDto, scheduledTestCaseModel);
        }
    }
}
