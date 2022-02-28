package cana.codelessautomation.scheduler.v2.services.action.types.ui.browser.type;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.token.TokenService;
import cana.codelessautomation.scheduler.v2.services.token.dtos.ScopeLevel;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import services.restclients.schedule.models.ConditionType;
import utilities.ConditionEvaluate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static com.codeborne.selenide.Selenide.title;
import static com.codeborne.selenide.WebDriverRunner.url;

@ApplicationScoped
public class BaseBrowserActionType {

    @Inject
    TokenService tokenService;


    void assertUtl(Long applicationId,Long environmentId, ActionDetailModel scheduledActionDetailModel) throws Exception {
        var browserUrl = url();
        var actualValue = scheduledActionDetailModel.getBrowserValue();
        if (tokenService.hasToken(actualValue)) {
            actualValue = tokenService.replaceToken(applicationId, actualValue,environmentId, ScopeLevel.ACTION);
        }
        var conditionType = ConditionType.EQUAL;
        if (!StringUtils.isEmpty(scheduledActionDetailModel.getConditionType())) {
            conditionType = EnumUtils.getEnumIgnoreCase(ConditionType.class, scheduledActionDetailModel.getConditionType());
        }
        if (ConditionEvaluate.evaluate(browserUrl, actualValue, conditionType)) {
            return;
        }
        String message = "source=[" + browserUrl + "] express=[" + actualValue + "] conditionType=[" + scheduledActionDetailModel.getConditionType() + "]";
        throw new Exception(message);
    }

    void assertTitle(Long applicationId,Long environmentId, ActionDetailModel scheduledActionDetailModel) throws Exception {
        var title = title();
        var actualValue = scheduledActionDetailModel.getBrowserValue();
        if (tokenService.hasToken(actualValue)) {
            actualValue = tokenService.replaceToken(applicationId, actualValue, environmentId, ScopeLevel.ACTION);
        }
        var conditionType = ConditionType.EQUAL;
        if (!StringUtils.isEmpty(scheduledActionDetailModel.getConditionType())) {
            conditionType = EnumUtils.getEnumIgnoreCase(ConditionType.class, scheduledActionDetailModel.getConditionType());
        }
        if (ConditionEvaluate.evaluate(title, actualValue, conditionType)) {
            return;
        }
        String message = "source=[" + title + "] express=[" + actualValue + "] conditionType=[" + scheduledActionDetailModel.getConditionType() + "]";
        throw new Exception(message);
    }
}
