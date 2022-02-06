package cana.codelessautomation.scheduler.v2.services.testcase.mapper;

import cana.codelessautomation.scheduler.v2.services.testcase.result.dtos.TestCaseResultStatusDao;
import cana.codelessautomation.scheduler.v2.services.testcase.result.models.UpdateTestCaseResultModel;

public interface TestCaseServiceMapper {
    UpdateTestCaseResultModel mapUpdateTestCaseResultModel(TestCaseResultStatusDao testCaseResultStatusDao, long duration, String errorMessage);
}
