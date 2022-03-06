package cana.codelessautomation.scheduler.v2.services.action.types.ui.options.types;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.action.models.ActionOptionModel;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.options.types.dtos.UIOptionTextTypeDao;
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
public class AssertUIBaseOption extends BaseUIOptionLogic implements UIBaseOption {
    @Inject
    UIActionUtility uiActionUtility;

    @Override
    public String Name() {
        return UIOptionType.TEXT.name();
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

        $(elementSelector).shouldBe(getSelenideCondition(EnumUtils.getEnumIgnoreCase(UIOptionTextTypeDao.class, actionOptionModel.getTextType())), Duration.ofSeconds(actionOptionModel.getDuration()));
    }

    public Condition getSelenideCondition(UIOptionTextTypeDao assertValueType) {
        Condition condition = null;
        switch (assertValueType) {
            case START_WITH:
                break;
            case END_WITH:
                break;
            case CONTAINS:
                break;
            case EQUAL:
                break;
            case NOT_EQUAL:
                break;
        }

        return null;
    }
}
