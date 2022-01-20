package cana.codelessautomation.scheduler.v2.services.testcase;

import cana.codelessautomation.scheduler.v2.services.action.ActionService;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import cana.codelessautomation.scheduler.v2.services.testcase.restclient.TestCaseServiceRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import services.restclients.testcase.TestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class TestCaseServiceImpl implements TestCaseService {

    @Inject
    @RestClient
    TestCaseServiceRestClient testCaseServiceRestClient;

    @Inject
    ActionService actionService;

    @Override
    public void executeTestCase(ScheduledTestPlanDto scheduledTestPlanDto) {
        var testCaseModels = (List<TestCaseModel>) null;
        try {
            testCaseModels = testCaseServiceRestClient.getTestCaseByTestPlanId(scheduledTestPlanDto.getScheduleModel().getApplicationId(), scheduledTestPlanDto.getScheduleModel().getTestPlanId());
        } catch (Exception ex) {
            var name = ex.getClass().getSimpleName();
        }

        if (Objects.isNull(testCaseModels)) {
            return;
        }

        for (TestCaseModel testCaseModel : testCaseModels) {
            scheduledTestPlanDto.setTestCaseModel(testCaseModel);
            actionService.execute(scheduledTestPlanDto);
        }
    }
}
