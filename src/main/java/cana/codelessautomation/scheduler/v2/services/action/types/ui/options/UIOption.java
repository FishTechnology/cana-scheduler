package cana.codelessautomation.scheduler.v2.services.action.types.ui.options;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.action.models.ActionOptionModel;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import com.codeborne.selenide.SelenideElement;
import services.restclients.testcase.TestCaseModel;

import java.util.List;

public interface UIOption {
    void execute(ScheduledTestPlanDto schedulerDto, TestCaseModel scheduledTestCaseModel, ActionDetailModel scheduledActionDetailModel, List<ActionOptionModel> actionOptionModel, SelenideElement webElement) throws TypeNotPresentException;
}
