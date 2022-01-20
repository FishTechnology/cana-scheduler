package cana.codelessautomation.scheduler.v2.services.action;

import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;

public interface ActionService {
    void execute(ScheduledTestPlanDto scheduledTestPlanDto);
}
