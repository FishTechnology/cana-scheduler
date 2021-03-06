package cana.codelessautomation.scheduler.v2.services.testplan;

import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import cana.codelessautomation.scheduler.v2.services.testcase.TestCaseService;
import cana.codelessautomation.scheduler.v2.services.testplan.restclient.TestPlanServiceRestClient;
import cana.codelessautomation.scheduler.v2.services.testplan.result.dtos.TestPlanResultStatusDao;
import cana.codelessautomation.scheduler.v2.services.testplan.result.mapper.TestPlanResultServiceMapper;
import cana.codelessautomation.scheduler.v2.services.testplan.result.restclient.TestPlanResultRestClient;
import org.apache.commons.collections.CollectionUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import services.restclients.result.actionresult.models.enums.ActionResultStatusDao;
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

    @Inject
    @RestClient
    TestPlanResultRestClient testPlanResultRestClient;

    @Inject
    TestPlanResultServiceMapper testPlanResultServiceMapper;

    @Override
    public void executeTestPlan(ScheduledTestPlanDto scheduledTestPlanDto) throws Exception {
        var startedOn = System.nanoTime();

        var testPlanModel = (TestPlanModel) null;
        try {
            testPlanModel = testPlanServiceRestClient.getTestPlanById(scheduledTestPlanDto.getScheduleDetail().getApplicationId(), scheduledTestPlanDto.getScheduleDetail().getTestPlanId());
        } catch (Exception ex) {

        }

        if (Objects.isNull(testPlanModel)) {
            return;
        }

        scheduledTestPlanDto.setTestPlanDetail(testPlanModel);
        var scheduleIterationId = scheduledTestPlanDto
                .getScheduleDetail()
                .getScheduleIteration()
                .getId();

        try {
            var testPlanResultModel = testPlanResultRestClient.getTestPlanResultBySchIterId(scheduleIterationId);
            scheduledTestPlanDto.setTestPlanResultModel(testPlanResultModel);
        } catch (Exception exception) {
            //TODO log error:
            throw new Exception("Error while getting TestPlanResult");
        }

        updateTestPlanResult(scheduleIterationId, scheduledTestPlanDto.getTestPlanResultModel().getId(), ActionResultStatusDao.STARTED, startedOn, null);

        try {

            testCaseService.executeTestCase(scheduledTestPlanDto);
        } catch (Exception exception) {
            updateTestPlanResult(scheduleIterationId, scheduledTestPlanDto.getTestPlanResultModel().getId(), ActionResultStatusDao.ERROR, startedOn, exception.getMessage());
            throw new Exception("Error occur while processing TestPln");
        }

        updateTestPlanResult(scheduleIterationId, scheduledTestPlanDto.getTestPlanResultModel().getId(), ActionResultStatusDao.COMPLETED, startedOn, null);

    }

    public void updateTestPlanResult(Long scheduleIterationId,
                                     Long testPlanResultId,
                                     ActionResultStatusDao actionResultStatusDao,
                                     long startedOn,
                                     String errorMessage) throws Exception {

        var duration = 0L;
        if (actionResultStatusDao == ActionResultStatusDao.COMPLETED || actionResultStatusDao == ActionResultStatusDao.ERROR) {
            var endedOn = System.nanoTime();
            duration = (endedOn - startedOn) / 1000000;
        }
        var updateTestPlanResultAsCompletedModel = testPlanResultServiceMapper.mapEndUpdateTestPlanResultAsCompletedModel(TestPlanResultStatusDao.COMPLETED, duration, errorMessage);
        var errors = testPlanResultRestClient.updateTestPlanResultStatus(
                scheduleIterationId,
                testPlanResultId,
                updateTestPlanResultAsCompletedModel);
        if (CollectionUtils.isNotEmpty(errors)) {
            //TODO log error:
            throw new Exception("Error while updating TestPlan");
        }
    }
}
