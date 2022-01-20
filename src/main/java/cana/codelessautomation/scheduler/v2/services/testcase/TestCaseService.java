package cana.codelessautomation.scheduler.v2.services.testcase;

import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;

public interface TestCaseService {
    void executeTestCase(ScheduledTestPlanDto scheduledTestPlanDto);
}
