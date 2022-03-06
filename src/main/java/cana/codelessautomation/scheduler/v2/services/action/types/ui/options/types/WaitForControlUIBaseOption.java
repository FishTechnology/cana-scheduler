package cana.codelessautomation.scheduler.v2.services.action.types.ui.options.types;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.action.models.ActionOptionModel;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.options.types.dtos.UIOptionControlTypeDao;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.options.types.dtos.UIOptionType;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.utilities.UIActionUtility;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import com.codeborne.selenide.Condition;
import com.thoughtworks.xstream.converters.ConversionException;
import org.apache.commons.lang3.EnumUtils;
import services.restclients.testcase.TestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.$;

@ApplicationScoped
public class WaitForControlUIBaseOption extends BaseUIOptionLogic implements UIBaseOption {
    @Inject
    UIActionUtility uiActionUtility;

    @Override
    public String Name() {
        return UIOptionType.CONTROL.name();
    }

    @Override
    public void execute(ScheduledTestPlanDto schedulerDto, TestCaseModel scheduledTestCaseModel, ActionDetailModel scheduledActionDetailModel, ActionOptionModel actionOptionModel) {
        if (!EnumUtils.isValidEnumIgnoreCase(UIOptionControlTypeDao.class, actionOptionModel.getOptionType())) {
            throw new ConversionException("Condition type is not found Type=" + actionOptionModel.getOptionType(), null);
        }

        var controlTypeAndIdDto = uiActionUtility.getIdAndValue(scheduledActionDetailModel.getKey());

        var elementSelector = getSelector(controlTypeAndIdDto);

        if (Objects.isNull(actionOptionModel.getDuration())) {
            actionOptionModel.setDuration(4L);
        }

        $(elementSelector).shouldBe(getSelenideCondition(EnumUtils.getEnumIgnoreCase(UIOptionControlTypeDao.class, actionOptionModel.getOptionType())), Duration.ofSeconds(actionOptionModel.getDuration()));
    }

    public Condition getSelenideCondition(UIOptionControlTypeDao conditionType) {
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
