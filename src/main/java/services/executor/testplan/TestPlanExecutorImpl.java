package services.executor.testplan;

import org.apache.commons.lang3.time.StopWatch;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import services.commons.CanaSchedulerConstants;
import services.executor.dtos.SchedulerDto;
import services.executor.testcase.TestCaseExecutor;
import services.restclients.commons.ErrorMessageModel;
import services.restclients.result.testplanresult.TestPlanResultServiceRestClient;
import services.restclients.result.testplanresult.models.UpdateTestPlanResultAsCompletedModel;
import services.restclients.result.testplanresult.models.enums.TestPlanResultStatusDao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class TestPlanExecutorImpl implements TestPlanExecutor {

    @Inject
    @RestClient
    TestPlanResultServiceRestClient testPlanResultServiceRestClient;

    @Inject
    TestCaseExecutor testCaseExecutor;

    @Override
    public void execute(SchedulerDto schedulerDto) throws Exception {
        var testPlanResultModel = testPlanResultServiceRestClient.getTestPlanResultBySchIterId(schedulerDto
                .getScheduleDetail()
                .getScheduleIterationModel()
                .getId());
        var scheduleTestPlanModel = schedulerDto.getScheduleDetail().getScheduleTestPlanModel();

        if (Objects.isNull(scheduleTestPlanModel)) {
            throw new Exception("TestPlan is null for Schedule Id =" + schedulerDto.getScheduleId());
        }

        OffsetDateTime startedOn = OffsetDateTime.now();
        StopWatch stopWatch = StopWatch.createStarted();
        Boolean isErrorOccurred = false;
        String errorMessage = null;
        Collections.sort(scheduleTestPlanModel.getScheduledTestCaseModel(), (a1, a2) -> (int) (a1.getOrder() - a2.getOrder()));

        try {
            testCaseExecutor.execute(schedulerDto, scheduleTestPlanModel);
        } catch (Exception exception) {
            stopWatch.stop();
            isErrorOccurred = true;
            errorMessage = exception.getMessage();
        }
        if (!isErrorOccurred) {
            stopWatch.stop();
        }
        OffsetDateTime completedOn = OffsetDateTime.now();
        if (testPlanResultModel == null) {
            errorMessage = " not able to find TestPlanResult for ScheduleId=" + schedulerDto.getScheduleId() + " TestPlanId=" + scheduleTestPlanModel.getId();
            isErrorOccurred = true;
        }
        var updateTestPlanResultStatus = getUpdateTestPlanResultAsCompletedModel(
                isErrorOccurred,
                errorMessage,
                stopWatch.getTime(),
                startedOn,
                completedOn);
        updateTestPlanResultStatus(schedulerDto.getScheduleId(), testPlanResultModel.getId(), updateTestPlanResultStatus);
    }

    private UpdateTestPlanResultAsCompletedModel getUpdateTestPlanResultAsCompletedModel(Boolean isErrorOccurred, String errorMessage,
                                                                                         Long totalDuration,
                                                                                         OffsetDateTime startedOn,
                                                                                         OffsetDateTime completedOn) {
        UpdateTestPlanResultAsCompletedModel updateTestPlanResultAsCompletedModel = new UpdateTestPlanResultAsCompletedModel();
        if (isErrorOccurred) {
            updateTestPlanResultAsCompletedModel.setStatus(TestPlanResultStatusDao.ERROR.name());
        } else {
            updateTestPlanResultAsCompletedModel.setStatus(TestPlanResultStatusDao.COMPLETED.name());
        }
        updateTestPlanResultAsCompletedModel.setErrorMessage(errorMessage);
        updateTestPlanResultAsCompletedModel.setTotalDuration(String.valueOf(totalDuration));
        updateTestPlanResultAsCompletedModel.setModifiedBy(CanaSchedulerConstants.scheduleUser);
        updateTestPlanResultAsCompletedModel.setStartedOn(startedOn.toString());
        updateTestPlanResultAsCompletedModel.setCompletedOn(completedOn.toString());
        return updateTestPlanResultAsCompletedModel;
    }

    public List<ErrorMessageModel> updateTestPlanResultStatus(Long scheduleIterationId, Long testPlanResultId, UpdateTestPlanResultAsCompletedModel updateTestPlanResultStatus) {
        return testPlanResultServiceRestClient.updateTestPlanResultStatus(scheduleIterationId, testPlanResultId, updateTestPlanResultStatus);
    }
}
