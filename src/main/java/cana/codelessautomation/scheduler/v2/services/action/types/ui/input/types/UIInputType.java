package cana.codelessautomation.scheduler.v2.services.action.types.ui.input.types;

import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import com.codeborne.selenide.SelenideElement;

public interface UIInputType {
    void execute(ScheduledTestPlanDto scheduledTestPlanDto, SelenideElement selenideElement, String value);

    UIInputTypeName uiInputTypeName();
}
