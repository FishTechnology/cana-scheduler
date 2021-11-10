package cana.codelessautomation.scheduler;

import io.quarkus.scheduler.Scheduled;
import services.executor.ScheduledExecutor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CanaScheduler {

    @Inject
    ScheduledExecutor scheduledExecutor;

   @Scheduled(every = "1m")
    void  executeTestPlan(){
       scheduledExecutor.executeSchedule();
    }
}
