package cana.codelessautomation.scheduler.v2.services.testcase;

import cana.codelessautomation.scheduler.v2.services.action.ActionService;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import cana.codelessautomation.scheduler.v2.services.testcase.restclient.TestCaseServiceRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import services.restclients.testcase.TestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
            testCaseModels = testCaseServiceRestClient.getTestCaseByTestPlanId(scheduledTestPlanDto.getScheduleDetail().getApplicationId(), scheduledTestPlanDto.getScheduleDetail().getTestPlanId());
        } catch (Exception ex) {

        }

        if (Objects.isNull(testCaseModels)) {
            return;
        }

        var sortedTestCases = testCaseModels
                .stream()
                .sorted(Comparator.comparing(TestCaseModel::getExecutionOrder))
                .collect(Collectors.toList());


        for (TestCaseModel testCaseModel : sortedTestCases) {
            scheduledTestPlanDto.setTestCaseDetail(testCaseModel);
            actionService.execute(scheduledTestPlanDto);
        }
    }
}
