package cana.codelessautomation.scheduler.v2.services.testplan;

import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;

public interface TestPlanService {
    void executeTestPlan(ScheduledTestPlanDto scheduledTestPlanDto);
}
