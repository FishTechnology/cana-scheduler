package cana.codelessautomation.scheduler.v2.services.testplan.result.mapper;

import cana.codelessautomation.scheduler.v2.services.testplan.result.dtos.TestPlanResultStatusDao;
import cana.codelessautomation.scheduler.v2.services.testplan.result.models.UpdateTestPlanResultAsCompletedModel;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TestPlanResultServiceMapperImpl implements TestPlanResultServiceMapper {

    @Override
    public UpdateTestPlanResultAsCompletedModel mapStartUpdateTestPlanResultAsCompletedModel() {
        var updateTestPlanResultAsCompletedModel = new UpdateTestPlanResultAsCompletedModel();
        updateTestPlanResultAsCompletedModel.setStatus(TestPlanResultStatusDao.STARTED.name());
        return updateTestPlanResultAsCompletedModel;
    }

    @Override
    public UpdateTestPlanResultAsCompletedModel mapEndUpdateTestPlanResultAsCompletedModel(TestPlanResultStatusDao testPlanResultStatusDao, long duration, String errorMessage) {
        var updateTestPlanResultAsCompletedModel = new UpdateTestPlanResultAsCompletedModel();
        updateTestPlanResultAsCompletedModel.setStatus(testPlanResultStatusDao.name());
        updateTestPlanResultAsCompletedModel.setTotalDuration(String.valueOf(duration));
        updateTestPlanResultAsCompletedModel.setErrorMessage(errorMessage);
        return updateTestPlanResultAsCompletedModel;
    }
}
