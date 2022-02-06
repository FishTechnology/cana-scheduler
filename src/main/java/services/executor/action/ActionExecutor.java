package services.executor.action;

import services.executor.dtos.SchedulerDto;
import cana.codelessautomation.scheduler.v2.services.testcase.result.models.TestCaseResultModel;
import services.restclients.schedule.models.ScheduledTestCaseModel;

public interface ActionExecutor {
    void execute(SchedulerDto schedulerDto, ScheduledTestCaseModel scheduledTestCaseModel, TestCaseResultModel testCaseResultModels) throws Exception;
}
