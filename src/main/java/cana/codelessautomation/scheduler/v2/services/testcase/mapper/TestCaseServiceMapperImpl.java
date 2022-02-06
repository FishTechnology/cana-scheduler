package cana.codelessautomation.scheduler.v2.services.testcase.mapper;

import cana.codelessautomation.scheduler.v2.services.testcase.result.dtos.TestCaseResultStatusDao;
import cana.codelessautomation.scheduler.v2.services.testcase.result.models.UpdateTestCaseResultModel;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TestCaseServiceMapperImpl implements TestCaseServiceMapper {
    @Override
    public UpdateTestCaseResultModel mapUpdateTestCaseResultModel(TestCaseResultStatusDao testCaseResultStatusDao, long duration, String errorMessage) {
        var updateTestCaseResultModel = new UpdateTestCaseResultModel();
        updateTestCaseResultModel.setStatus(testCaseResultStatusDao.name());
        if (testCaseResultStatusDao == TestCaseResultStatusDao.ERROR || testCaseResultStatusDao == TestCaseResultStatusDao.COMPLETED) {
            updateTestCaseResultModel.setTotalDuration(String.valueOf(duration));
            updateTestCaseResultModel.setErrorMessage(errorMessage);
        }
        return updateTestCaseResultModel;
    }
}
