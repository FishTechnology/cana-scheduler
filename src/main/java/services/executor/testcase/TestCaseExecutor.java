package services.executor.testcase;

import cana.codelessautomation.scheduler.v2.services.testplan.result.models.TestPlanResultModel;
import services.executor.dtos.SchedulerDto;

public interface TestCaseExecutor {
    void execute(SchedulerDto schedulerDto, TestPlanResultModel scheduledTestCaseModel) throws Exception;
}
