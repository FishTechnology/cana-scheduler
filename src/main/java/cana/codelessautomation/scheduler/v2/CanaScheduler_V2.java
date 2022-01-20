package cana.codelessautomation.scheduler.v2;

import cana.codelessautomation.scheduler.v2.services.scheduler.SchedulerService;
import io.quarkus.scheduler.Scheduled;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CanaScheduler_V2 {
    @Inject
    SchedulerService schedulerService;

    @Scheduled(every = "1m")
    void executeTestPlan() {
        schedulerService.executeSchedule();
    }
}
