package cana.codelessautomation.scheduler.v2.services.action.types.ui.browser.type;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import services.restclients.schedule.models.ConditionType;
import utilities.ConditionEvaluate;

import static com.codeborne.selenide.Selenide.title;
import static com.codeborne.selenide.WebDriverRunner.url;

public class BaseBrowserActionType {
    void assertUtl(ActionDetailModel scheduledActionDetailModel) throws Exception {
        var browserUrl = url();
        var conditionType = ConditionType.EQUAL;
        if (!StringUtils.isEmpty(scheduledActionDetailModel.getConditionType())) {
            conditionType = EnumUtils.getEnumIgnoreCase(ConditionType.class, scheduledActionDetailModel.getConditionType());
        }
        if (ConditionEvaluate.evaluate(browserUrl, scheduledActionDetailModel.getBrowserValue(), conditionType)) {
            return;
        }
        String message = "source=[" + browserUrl + "] express=[" + scheduledActionDetailModel.getBrowserValue() + "] conditionType=[" + scheduledActionDetailModel.getConditionType() + "]";
        throw new Exception(message);
    }

    void assertTitle(ActionDetailModel scheduledActionDetailModel) throws Exception {
        var title = title();
        var conditionType = ConditionType.EQUAL;
        if (!StringUtils.isEmpty(scheduledActionDetailModel.getConditionType())) {
            conditionType = EnumUtils.getEnumIgnoreCase(ConditionType.class, scheduledActionDetailModel.getConditionType());
        }
        if (ConditionEvaluate.evaluate(title, scheduledActionDetailModel.getBrowserValue(), conditionType)) {
            return;
        }
        String message = "source=[" + title + "] express=[" + scheduledActionDetailModel.getBrowserValue() + "] conditionType=[" + scheduledActionDetailModel.getConditionType() + "]";
        throw new Exception(message);
    }
}
