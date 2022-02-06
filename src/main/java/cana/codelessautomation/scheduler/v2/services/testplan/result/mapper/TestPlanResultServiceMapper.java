package cana.codelessautomation.scheduler.v2.services.testplan.result.mapper;

import cana.codelessautomation.scheduler.v2.services.testplan.result.dtos.TestPlanResultStatusDao;
import cana.codelessautomation.scheduler.v2.services.testplan.result.models.UpdateTestPlanResultAsCompletedModel;

public interface TestPlanResultServiceMapper {
    UpdateTestPlanResultAsCompletedModel mapStartUpdateTestPlanResultAsCompletedModel();

    UpdateTestPlanResultAsCompletedModel mapEndUpdateTestPlanResultAsCompletedModel(TestPlanResultStatusDao testPlanResultStatusDao, long duration, String errorMessage);
}
