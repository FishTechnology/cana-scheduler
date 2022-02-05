package cana.codelessautomation.scheduler.v2.services.action.types.ui.options.types;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.action.models.ActionOptionModel;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import com.codeborne.selenide.SelenideElement;
import services.restclients.testcase.TestCaseModel;

public interface UIBaseOption {
    String Name();

    void execute(ScheduledTestPlanDto schedulerDto, TestCaseModel scheduledTestCaseModel, ActionDetailModel scheduledActionDetailModel, ActionOptionModel actionOptionModel, SelenideElement webElement);
}