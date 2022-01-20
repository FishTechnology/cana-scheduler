package cana.codelessautomation.scheduler.v2.services.scheduler;

import cana.codelessautomation.scheduler.v2.services.scheduler.mappers.SchedulerServiceMapper;
import cana.codelessautomation.scheduler.v2.services.scheduler.restclient.ScheduleServiceRestClient;
import cana.codelessautomation.scheduler.v2.services.testplan.TestPlanService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import services.restclients.schedule.models.ScheduleModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Objects;

@ApplicationScoped
public class SchedulerServiceImpl implements SchedulerService {
    @Inject
    SchedulerServiceMapper schedulerServiceMapper;

    @Inject
    TestPlanService testPlanService;

    @Inject
    @RestClient
    ScheduleServiceRestClient scheduleServiceRestClient;

    @Override
    public void executeSchedule() {
        var scheduleModel = (ScheduleModel) null;
        try {
            scheduleModel = scheduleServiceRestClient.getRunningSchedule();
        } catch (Exception ex) {

        }

        if (!Objects.isNull(scheduleModel)) {
            return;
        }

        scheduleModel = null;

        try {
            scheduleModel = scheduleServiceRestClient.getScheduleToExecute();
        } catch (Exception ex) {

        }

        if (Objects.isNull(scheduleModel)) {
            return;
        }

        var scheduledTestPlanDto = schedulerServiceMapper.mapScheduleTestPlanDto(scheduleModel);
        testPlanService.executeTestPlan(scheduledTestPlanDto);
    }
}
