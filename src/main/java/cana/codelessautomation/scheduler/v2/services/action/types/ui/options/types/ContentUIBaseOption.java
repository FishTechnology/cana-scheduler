package cana.codelessautomation.scheduler.v2.services.action.types.ui.options.types;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.action.models.ActionOptionModel;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.options.types.dtos.UIOptionContentTypeDao;
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

import static cana.codelessautomation.scheduler.v2.services.action.types.ui.shared.conditions.CustomCondition.endWith;
import static cana.codelessautomation.scheduler.v2.services.action.types.ui.shared.conditions.CustomCondition.startWith;
import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;

@ApplicationScoped
public class ContentUIBaseOption extends BaseUIOptionLogic implements UIBaseOption {
    @Inject
    UIActionUtility uiActionUtility;

    @Override
    public String Name() {
        return UIOptionType.CONTENT.name();
    }

    @Override
    public Exception execute(ScheduledTestPlanDto schedulerDto, TestCaseModel scheduledTestCaseModel, ActionDetailModel scheduledActionDetailModel, ActionOptionModel actionOptionModel) {
        if (!EnumUtils.isValidEnumIgnoreCase(UIOptionContentTypeDao.class, actionOptionModel.getContentType())) {
            throw new ConversionException("Text type is not found Type=" + actionOptionModel.getContentType(), null);
        }

        var controlTypeAndIdDto = uiActionUtility.getIdAndValue(scheduledActionDetailModel.getKey());

        var elementSelector = getSelector(controlTypeAndIdDto);

        if (Objects.isNull(actionOptionModel.getDuration())) {
            actionOptionModel.setDuration(4L);
        }

        var uiOptionTextTypeDao = EnumUtils.getEnumIgnoreCase(UIOptionContentTypeDao.class, actionOptionModel.getContentType());


        if (uiOptionTextTypeDao == UIOptionContentTypeDao.CONTAINS ||
                uiOptionTextTypeDao == UIOptionContentTypeDao.EQUAL ||
                uiOptionTextTypeDao == UIOptionContentTypeDao.START_WITH ||
                uiOptionTextTypeDao == UIOptionContentTypeDao.END_WITH) {
            $(elementSelector).shouldHave(getSelenideCondition(EnumUtils.getEnumIgnoreCase(UIOptionContentTypeDao.class, actionOptionModel.getContentType()), actionOptionModel), Duration.ofSeconds(actionOptionModel.getDuration()));
        } else if (uiOptionTextTypeDao == UIOptionContentTypeDao.NOT_EQUAL) {
            $(elementSelector).shouldNotHave(value(actionOptionModel.getValue()), Duration.ofSeconds(actionOptionModel.getDuration()));
        }

        return new Exception("Didn't find content type validation for contentType=" + actionOptionModel.getContentType());
    }

    public Condition getSelenideCondition(UIOptionContentTypeDao assertValueType, ActionOptionModel actionOptionModel) {
        Condition condition = null;
        switch (assertValueType) {
            case START_WITH:
                condition = startWith(actionOptionModel.getValue());
                break;
            case END_WITH:
                condition = endWith(actionOptionModel.getValue());
                break;
            case CONTAINS:
                condition = value(actionOptionModel.getValue());
                break;
            case NOT_EQUAL:
            case EQUAL:
                condition = exactValue(actionOptionModel.getValue());
                break;
        }

        return condition;
    }
}
