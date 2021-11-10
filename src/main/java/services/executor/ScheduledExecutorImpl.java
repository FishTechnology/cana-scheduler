package services.executor;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import services.executor.dtos.SchedulerDto;
import services.executor.testplan.TestPlanExecutor;
import services.restclients.schedule.ScheduleServiceRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ScheduledExecutorImpl implements ScheduledExecutor {

    @Inject
    @RestClient
    ScheduleServiceRestClient scheduleServiceRestClient;

    @Inject
    TestPlanExecutor testPlanExecutor;

//    @Inject
//    @RestClient
//    TestPlanServiceRestClient testPlanServiceRestClient;

    @Override
    public void executeSchedule() {
        SchedulerDto schedulerDto= new SchedulerDto();
        try {
            var scheduleDetailModel = scheduleServiceRestClient.getAllSchedules(6L);
            if (scheduleDetailModel == null || scheduleDetailModel.getScheduleModel() == null) {
                return;
            }
            schedulerDto.setScheduleDetail(scheduleDetailModel);
            testPlanExecutor.execute(schedulerDto);
        } catch (Exception ex) {

        }
    }
}
