package cana.codelessautomation.scheduler.v2.services.action.types.ui.options.types;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.action.models.ActionOptionModel;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.options.types.dtos.ConditionType;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.options.types.dtos.UIOptionType;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.thoughtworks.xstream.converters.ConversionException;
import org.apache.commons.lang3.EnumUtils;
import services.restclients.testcase.TestCaseModel;

import javax.enterprise.context.ApplicationScoped;

import static com.codeborne.selenide.Selenide.$;

@ApplicationScoped
public class WaitForConditionUIBaseOption implements UIBaseOption {
    @Override
    public String Name() {
        return UIOptionType.WaitFor.name();
    }

    @Override
    public void execute(ScheduledTestPlanDto schedulerDto, TestCaseModel scheduledTestCaseModel, ActionDetailModel scheduledActionDetailModel, ActionOptionModel actionOptionModel, SelenideElement webElement) {
        if (!EnumUtils.isValidEnumIgnoreCase(ConditionType.class, actionOptionModel.getConditionType())) {
            throw new ConversionException("Condition type is not found Type=" + actionOptionModel.getConditionType(), null);
        }

        $(webElement).shouldBe(getSelenideCondition(EnumUtils.getEnumIgnoreCase(ConditionType.class, actionOptionModel.getConditionType())));
    }

    public Condition getSelenideCondition(ConditionType conditionType) {
        Condition condition = null;

        switch (conditionType) {
            case VISIBLE:
                condition = Condition.visible;
                break;
            case EXIST:
                condition = Condition.exist;
                break;
            case HIDDEN:
                condition = Condition.hidden;
                break;
            case APPEAR:
                condition = Condition.appear;
                break;
            case DISAPPEAR:
                condition = Condition.disappear;
                break;
            case READONLY:
                condition = Condition.readonly;
                break;
            case EMPTY:
                condition = Condition.empty;
                break;
            case IMAGE:
                condition = Condition.image;
                break;
            case FOCUSED:
                condition = Condition.focused;
                break;
            case ENABLED:
                condition = Condition.enabled;
                break;
            case DISABLED:
                condition = Condition.disabled;
                break;
            case SELECTED:
                condition = Condition.selected;
                break;
            case CHECKED:
                condition = Condition.checked;
                break;
        }

        return condition;
    }
}
