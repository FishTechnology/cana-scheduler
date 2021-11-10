package services.executor.testcase;

import services.executor.dtos.SchedulerDto;
import services.restclients.schedule.models.ScheduledTestCaseModel;

public interface TestCaseExecutor {
    void execute(SchedulerDto schedulerDto, ScheduledTestCaseModel scheduledTestCaseModel);
}
