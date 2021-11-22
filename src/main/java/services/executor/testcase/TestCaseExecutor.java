package services.executor.testcase;

import services.executor.dtos.SchedulerDto;
import services.restclients.schedule.models.ScheduleTestPlanModel;

public interface TestCaseExecutor {
    void execute(SchedulerDto schedulerDto, ScheduleTestPlanModel scheduledTestCaseModel) throws Exception;
}
