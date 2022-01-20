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
            testPlanModel = testPlanServiceRestClient.getTestPlanById(scheduledTestPlanDto.getScheduleModel().getApplicationId(), scheduledTestPlanDto.getScheduleModel().getTestPlanId());
        } catch (Exception ex) {
            var name = ex.getClass().getSimpleName();
        }

        if (Objects.isNull(testPlanModel)) {
            return;
        }

        scheduledTestPlanDto.setTestPlanModel(testPlanModel);

        testCaseService.executeTestCase(scheduledTestPlanDto);
    }
}
