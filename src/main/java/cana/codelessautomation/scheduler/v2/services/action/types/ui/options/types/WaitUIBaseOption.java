package cana.codelessautomation.scheduler.v2.services.action.types.ui.options.types;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.action.models.ActionOptionModel;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.options.types.dtos.UIOptionType;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import com.codeborne.selenide.SelenideElement;
import services.restclients.testcase.TestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.TimeUnit;


@ApplicationScoped
public class WaitUIBaseOption implements UIBaseOption {
    @Override
    public String Name() {
        return UIOptionType.Wait.name();
    }

    @Override
    public void execute(ScheduledTestPlanDto schedulerDto, TestCaseModel scheduledTestCaseModel, ActionDetailModel scheduledActionDetailModel, ActionOptionModel actionOptionModel, SelenideElement webElement) {
        try {
            TimeUnit.MILLISECONDS.sleep(actionOptionModel.getWaitDuration());
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}
