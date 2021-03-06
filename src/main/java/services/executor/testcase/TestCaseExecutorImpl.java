package services.executor.testcase;

import cana.codelessautomation.scheduler.v2.services.testcase.result.models.TestCaseResultModel;
import cana.codelessautomation.scheduler.v2.services.testcase.result.models.UpdateTestCaseResultModel;
import cana.codelessautomation.scheduler.v2.services.testplan.result.models.TestPlanResultModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import services.executor.action.ActionExecutor;
import services.executor.dtos.SchedulerDto;
import services.restclients.result.testcaseresult.TestCaseResultServiceRestClient;
import services.restclients.result.testcaseresult.models.enums.TestCaseResultStatusDao;
import services.restclients.schedule.models.ScheduleTestPlanModel;
import services.restclients.schedule.models.ScheduledTestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.OffsetDateTime;
import java.util.List;

@ApplicationScoped
public class TestCaseExecutorImpl implements TestCaseExecutor {

    @Inject
    ActionExecutor actionExecutor;

    @Inject
    @RestClient
    TestCaseResultServiceRestClient testCaseResultServiceRestClient;

    @Override
    public void execute(SchedulerDto schedulerDto, TestPlanResultModel testPlanResultModel) throws Exception {
//        var scheduleTestPlanModel = schedulerDto.getScheduleDetail().getScheduleTestPlanModel();
//        var testCaseResultModels = testCaseResultServiceRestClient.getTestCaseResultByPlanResultId(testPlanResultModel.getId());
//        if (CollectionUtils.isEmpty(testCaseResultModels)) {
//            throw new Exception("TestCaseResults is null for TestPlanResult Id =" + scheduleTestPlanModel.getId());
//        }
//
//        Collections.sort(scheduleTestPlanModel.getScheduledTestCaseModel(), (a1, a2) -> (int) (a1.getOrder() - a2.getOrder()));
//
//        for (ScheduledTestCaseModel scheduledTestCaseModel : scheduleTestPlanModel.getScheduledTestCaseModel()) {
//            StopWatch stopWatch = StopWatch.createStarted();
//            String errorMessage = null;
//            OffsetDateTime startedOn = OffsetDateTime.now();
//            TestCaseResultModel testCaseResultModel = null;
//            try {
//                testCaseResultModel = this.getTestCaseResultModel(scheduledTestCaseModel, testCaseResultModels);
//                Collections.sort(scheduledTestCaseModel.getScheduledActionDetails(), (a1, a2) -> (int) (a1.getOrder() - a2.getOrder()));
//
//                actionExecutor.execute(schedulerDto, scheduledTestCaseModel, testCaseResultModel);
//
//            } catch (SchedulerException schedulerException) {
//                errorMessage = "error in subsequent process";
//
//                if (StringUtils.isNotEmpty(schedulerException.errorMessage)) {
//                    errorMessage = schedulerException.errorMessage;
//                }
//
//            } catch (Exception exception) {
//                errorMessage = CanaSchedulerUtility.getMessage(exception);
//            }
//
//            stopWatch.stop();
//            OffsetDateTime completedOn = OffsetDateTime.now();
//            updateTestCaseResultStatus(
//                    scheduleTestPlanModel,
//                    testCaseResultModel,
//                    stopWatch.getTime(),
//                    errorMessage,
//                    startedOn,
//                    completedOn);
//
//            if (StringUtils.isNotEmpty(errorMessage)) {
//                throw new SchedulerException("", ExceptionStatus.Error);
//            }
//        }
    }

    private TestCaseResultModel getTestCaseResultModel(
            ScheduledTestCaseModel scheduledTestCaseModel,
            List<TestCaseResultModel> testCaseResultModels) throws Exception {
        var filteredRes = testCaseResultModels
                .stream()
                .filter(x -> x.getTestCaseId() == scheduledTestCaseModel.getId())
                .findFirst();

        if (!filteredRes.isPresent()) {
            throw new Exception("TestCaseResult is null for TestCase Id =" + scheduledTestCaseModel.getId());
        }

        return filteredRes.get();
    }

    private void updateTestCaseResultStatus(ScheduleTestPlanModel scheduleTestPlanModel,
                                            TestCaseResultModel testCaseResultModel,
                                            long duration,
                                            String errorMessage,
                                            OffsetDateTime startedOn,
                                            OffsetDateTime completedOn) throws Exception {

        UpdateTestCaseResultModel updateTestCaseResultAsCompletedModel = new UpdateTestCaseResultModel();
        updateTestCaseResultAsCompletedModel.setStatus(TestCaseResultStatusDao.COMPLETED.name());
        if (StringUtils.isNotEmpty(errorMessage)) {
            updateTestCaseResultAsCompletedModel.setStatus(TestCaseResultStatusDao.ERROR.name());
        }

        updateTestCaseResultAsCompletedModel.setTotalDuration(String.valueOf(duration));
        updateTestCaseResultAsCompletedModel.setErrorMessage(errorMessage);

        var response = testCaseResultServiceRestClient.updateTestCaseResultStatus(
                scheduleTestPlanModel.getId(),
                testCaseResultModel.getId(),
                updateTestCaseResultAsCompletedModel);

        if (CollectionUtils.isNotEmpty(response)) {
            throw new Exception("Error occur while update TestCaseResult Status =" + response);
        }
    }
}
