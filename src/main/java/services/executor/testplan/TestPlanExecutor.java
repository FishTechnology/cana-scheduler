package services.executor.testplan;

import services.executor.dtos.SchedulerDto;

public interface TestPlanExecutor {
    void execute(SchedulerDto schedulerDto);
}
