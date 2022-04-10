package cana.codelessautomation.scheduler.v2.services.action.types.ui.options.types;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.action.models.ActionOptionModel;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.options.types.dtos.UIControlConditionControlTypeDao;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.options.types.dtos.UIOptionType;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.utilities.UIActionUtility;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import com.codeborne.selenide.Condition;
import com.thoughtworks.xstream.converters.ConversionException;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.testcontainers.shaded.org.apache.commons.lang.NullArgumentException;
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
    public Exception execute(ScheduledTestPlanDto schedulerDto, TestCaseModel scheduledTestCaseModel, ActionDetailModel scheduledActionDetailModel, ActionOptionModel actionOptionModel) {
        if (StringUtils.isEmpty(actionOptionModel.getControlConditionType())) {
            throw new NullArgumentException("ControlType option is empty");
        }

        if (!EnumUtils.isValidEnumIgnoreCase(UIControlConditionControlTypeDao.class, actionOptionModel.getControlConditionType())) {
            throw new ConversionException("Control type is not found Type=" + actionOptionModel.getControlConditionType(), null);
        }

        var controlTypeAndIdDto = uiActionUtility.getIdAndValue(scheduledActionDetailModel.getKey());

        var elementSelector = getSelector(controlTypeAndIdDto);

        if (Objects.isNull(actionOptionModel.getDuration())) {
            actionOptionModel.setDuration(4L);
        }

        $(elementSelector).shouldBe(getSelenideCondition(EnumUtils.getEnumIgnoreCase(UIControlConditionControlTypeDao.class, actionOptionModel.getControlConditionType())), Duration.ofSeconds(actionOptionModel.getDuration()));
        return null;
    }

    public Condition getSelenideCondition(UIControlConditionControlTypeDao conditionType) {
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
