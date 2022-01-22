package cana.codelessautomation.scheduler.v2.services.testplan;

import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import cana.codelessautomation.scheduler.v2.services.testcase.TestCaseService;
import cana.codelessautomation.scheduler.v2.services.testplan.restclient.TestPlanServiceRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import services.restclients.testplan.models.TestPlanModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Objects;

@ApplicationScoped
public class TestPlanServiceImpl implements TestPlanService {

    @Inject
    TestCaseService testCaseService;

    @Inject
    @RestClient
    TestPlanServiceRestClient testPlanServiceRestClient;

    @Override
    public void executeTestPlan(ScheduledTestPlanDto scheduledTestPlanDto) {
        var testPlanModel = (TestPlanModel) null;
        try {
            testPlanModel = testPlanServiceRestClient.getTestPlanById(scheduledTestPlanDto.getScheduleDetail().getApplicationId(), scheduledTestPlanDto.getScheduleDetail().getTestPlanId());
        } catch (Exception ex) {

        }

        if (Objects.isNull(testPlanModel)) {
            return;
        }

        scheduledTestPlanDto.setTestPlanDetail(testPlanModel);

        testCaseService.executeTestCase(scheduledTestPlanDto);
    }
}
