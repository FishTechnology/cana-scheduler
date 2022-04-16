package cana.codelessautomation.scheduler.v2.services.action.types.ui.input.types;

import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import com.codeborne.selenide.SelenideElement;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DefaultImpl implements UIInputType {
    /**
     * @param scheduledTestPlanDto
     * @param selenideElement
     * @param value
     */
    @Override
    public void execute(ScheduledTestPlanDto scheduledTestPlanDto, SelenideElement selenideElement, String value) {
        selenideElement.val(value);
    }

    /**
     * @return
     */
    @Override
    public UIInputTypeName uiInputTypeName() {
        return UIInputTypeName.DEFAULT;
    }
}
