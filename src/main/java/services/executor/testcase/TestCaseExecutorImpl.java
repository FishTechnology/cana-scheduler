package services.executor.testcase;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import services.commons.CanaSchedulerConstants;
import services.commons.CanaSchedulerUtility;
import services.commons.exceptions.ExceptionStatus;
import services.commons.exceptions.SchedulerException;
import services.executor.action.ActionExecutor;
import services.executor.dtos.SchedulerDto;
import services.restclients.result.testcaseresult.TestCaseResultServiceRestClient;
import services.restclients.result.testcaseresult.models.TestCaseResultModel;
import services.restclients.result.testcaseresult.models.UpdateTestCaseResultAsCompletedModel;
import services.restclients.result.testcaseresult.models.enums.TestCaseResultStatusDao;
import services.restclients.result.testplanresult.models.TestPlanResultModel;
import services.restclients.schedule.models.ScheduleTestPlanModel;
import services.restclients.schedule.models.ScheduledTestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.OffsetDateTime;
import java.util.Collections;
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
        var scheduleTestPlanModel = schedulerDto.getScheduleDetail().getScheduleTestPlanModel();
        var testCaseResultModels = testCaseResultServiceRestClient.getTestCaseResultByPlanResultId(testPlanResultModel.getId());
        if (CollectionUtils.isEmpty(testCaseResultModels)) {
            throw new Exception("TestCaseResults is null for TestPlanResult Id =" + scheduleTestPlanModel.getId());
        }

        Collections.sort(scheduleTestPlanModel.getScheduledTestCaseModel(), (a1, a2) -> (int) (a1.getOrder() - a2.getOrder()));

        for (ScheduledTestCaseModel scheduledTestCaseModel : scheduleTestPlanModel.getScheduledTestCaseModel()) {
            StopWatch stopWatch = StopWatch.createStarted();
            String errorMessage = null;
            OffsetDateTime startedOn = OffsetDateTime.now();
            TestCaseResultModel testCaseResultModel = null;
            try {
                testCaseResultModel = this.getTestCaseResultModel(scheduledTestCaseModel, testCaseResultModels);
                Collections.sort(scheduledTestCaseModel.getScheduledActionDetails(), (a1, a2) -> (int) (a1.getOrder() - a2.getOrder()));

                actionExecutor.execute(schedulerDto, scheduledTestCaseModel, testCaseResultModel);

            } catch (SchedulerException schedulerException) {
                errorMessage = "error in subsequent process";

                if (StringUtils.isNotEmpty(schedulerException.errorMessage)) {
                    errorMessage = schedulerException.errorMessage;
                }

            } catch (Exception exception) {
                errorMessage = CanaSchedulerUtility.getMessage(exception);
            }

            stopWatch.stop();
            OffsetDateTime completedOn = OffsetDateTime.now();
            updateTestCaseResultStatus(
                    scheduleTestPlanModel,
                    testCaseResultModel,
                    stopWatch.getTime(),
                    errorMessage,
                    startedOn,
                    completedOn);

            if (StringUtils.isNotEmpty(errorMessage)) {
                throw new SchedulerException("", ExceptionStatus.Error);
            }
        }
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

        UpdateTestCaseResultAsCompletedModel updateTestCaseResultAsCompletedModel = new UpdateTestCaseResultAsCompletedModel();
        updateTestCaseResultAsCompletedModel.setStatus(TestCaseResultStatusDao.COMPLETED.name());
        if (StringUtils.isNotEmpty(errorMessage)) {
            updateTestCaseResultAsCompletedModel.setStatus(TestCaseResultStatusDao.ERROR.name());
        }

        updateTestCaseResultAsCompletedModel.setModifiedBy(CanaSchedulerConstants.scheduleUser);
        updateTestCaseResultAsCompletedModel.setTotalDuration(String.valueOf(duration));
        updateTestCaseResultAsCompletedModel.setStartedOn(startedOn.toString());
        updateTestCaseResultAsCompletedModel.setCompletedOn(completedOn.toString());
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
