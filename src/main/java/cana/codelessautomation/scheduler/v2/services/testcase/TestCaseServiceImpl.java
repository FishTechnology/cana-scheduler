package cana.codelessautomation.scheduler.v2.services.testcase;

import cana.codelessautomation.scheduler.v2.services.action.ActionService;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import cana.codelessautomation.scheduler.v2.services.testcase.mapper.TestCaseServiceMapper;
import cana.codelessautomation.scheduler.v2.services.testcase.restclient.TestCaseServiceRestClient;
import cana.codelessautomation.scheduler.v2.services.testcase.result.dtos.TestCaseResultStatusDao;
import cana.codelessautomation.scheduler.v2.services.testcase.result.restclients.TestCaseResultRestClient;
import org.apache.commons.collections.CollectionUtils;
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

    @Inject
    @RestClient
    TestCaseResultRestClient testCaseResultRestClient;

    @Inject
    TestCaseServiceMapper testCaseServiceMapper;

    @Override
    public void executeTestCase(ScheduledTestPlanDto scheduledTestPlanDto) throws Exception {
        var startedOn = System.nanoTime();

        var testCaseModels = (List<TestCaseModel>) null;
        try {
            testCaseModels = testCaseServiceRestClient.getTestCaseByTestPlanId(scheduledTestPlanDto.getScheduleDetail().getApplicationId(), scheduledTestPlanDto.getScheduleDetail().getTestPlanId());
        } catch (Exception ex) {

        }

        var testCaseResultModels = testCaseResultRestClient.getTestCaseResultByPlanResultId(scheduledTestPlanDto.getTestPlanResultModel().getId());
        scheduledTestPlanDto.setTestCaseResultModels(testCaseResultModels);

        if (CollectionUtils.isEmpty(testCaseResultModels)) {
            throw new Exception("empty testCaseResultModels found for TestPlanResultId=" + scheduledTestPlanDto.getTestPlanResultModel().getId());
        }

        if (Objects.isNull(testCaseModels)) {
            return;
        }

        var sortedTestCases = testCaseModels.stream().sorted(Comparator.comparing(TestCaseModel::getExecutionOrder)).collect(Collectors.toList());

        for (TestCaseModel testCaseModel : sortedTestCases) {
            if (startedOn == 0) {
                startedOn = System.nanoTime();
            }

            var testCaseResultModel = testCaseResultModels
                    .stream()
                    .filter(testCaseConfig -> testCaseConfig.getTestCaseId() == testCaseModel.getId())
                    .findFirst();
            if (testCaseResultModel.isEmpty()) {
                throw new Exception("didn't find test case result for testCaseId testCaseId=" + testCaseModel.getId());
            }

            scheduledTestPlanDto.setTestCaseResultModel(testCaseResultModel.get());

            updateTestCaseResultStatus(scheduledTestPlanDto, testCaseResultModel.get().getId(), TestCaseResultStatusDao.STARTED, startedOn, null);

            scheduledTestPlanDto.setTestCaseDetail(testCaseModel);
            var testCaseResultStatus = TestCaseResultStatusDao.COMPLETED;
            String errorMessage;

            try {
                actionService.execute(scheduledTestPlanDto);

            } catch (Exception e) {
                testCaseResultStatus = TestCaseResultStatusDao.ERROR;
                errorMessage = e.getMessage();
                updateTestCaseResultStatus(scheduledTestPlanDto, testCaseResultModel.get().getId(), testCaseResultStatus, startedOn, errorMessage);
                throw e;
            }
            updateTestCaseResultStatus(scheduledTestPlanDto, testCaseResultModel.get().getId(), testCaseResultStatus, startedOn, null);
            startedOn = 0;
        }
    }

    public void updateTestCaseResultStatus(ScheduledTestPlanDto scheduledTestPlanDto, Long testCaseResultId, TestCaseResultStatusDao testCaseResultStatusDao, long startedOn, String errorMessage) throws Exception {
        var duration = 0L;
        if (testCaseResultStatusDao == TestCaseResultStatusDao.COMPLETED || testCaseResultStatusDao == TestCaseResultStatusDao.ERROR) {
            var endedOn = System.nanoTime();
            duration = (endedOn - startedOn) / 1000000;
        }

        var updateTestCaseResult = testCaseServiceMapper.mapUpdateTestCaseResultModel(testCaseResultStatusDao, duration, errorMessage);
        var errors = testCaseResultRestClient.updateTestCaseResultStatus(scheduledTestPlanDto.getTestPlanResultModel().getId(),
                testCaseResultId,
                updateTestCaseResult);
        if (CollectionUtils.isNotEmpty(errors)) {
            //TODO log error:
            throw new Exception("Error while updating TestCaseResult as Started");
        }
    }
}
