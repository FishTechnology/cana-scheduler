package cana.codelessautomation.scheduler.v2.services.action.types.ui.options.types;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.action.models.ActionOptionModel;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.options.types.dtos.UIOptionType;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import com.codeborne.selenide.SelenideElement;
import services.restclients.testcase.TestCaseModel;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SizeUIBaseOption implements UIBaseOption {
    @Override
    public String Name() {
        return UIOptionType.Size.name();
    }

    @Override
    public void execute(ScheduledTestPlanDto schedulerDto, TestCaseModel scheduledTestCaseModel, ActionDetailModel scheduledActionDetailModel, ActionOptionModel actionOptionModel, SelenideElement webElement) {

    }
}