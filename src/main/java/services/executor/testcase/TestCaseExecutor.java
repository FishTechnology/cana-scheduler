package services.executor.testcase;

import services.executor.dtos.SchedulerDto;
import services.restclients.result.testplanresult.models.TestPlanResultModel;

public interface TestCaseExecutor {
    void execute(SchedulerDto schedulerDto, TestPlanResultModel scheduledTestCaseModel) throws Exception;
}
