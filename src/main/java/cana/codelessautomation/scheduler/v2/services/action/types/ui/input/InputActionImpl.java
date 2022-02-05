package cana.codelessautomation.scheduler.v2.services.action.types.ui.input;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.UIAction;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.UIActionType;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.options.UIOption;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.utilities.UIActionUtility;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import cana.codelessautomation.scheduler.v2.services.token.TokenService;
import cana.codelessautomation.scheduler.v2.services.token.dtos.ScopeLevel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import services.restclients.testcase.TestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class InputActionImpl implements UIAction {

    @Inject
    UIOption uiOption;

    @Inject
    UIActionUtility uiActionUtility;

    @Inject
    TokenService tokenService;

    @Override
    public void execute(ScheduledTestPlanDto schedulerDto, TestCaseModel scheduledTestCaseModel, ActionDetailModel scheduledActionDetailModel) {
        var controlTypeAndIdDto = uiActionUtility.getIdAndValue(scheduledActionDetailModel.getKey());
        var webElement = uiActionUtility.getUIControl(schedulerDto.getScheduleDetail().getApplicationId(), controlTypeAndIdDto);

        if (CollectionUtils.isNotEmpty(scheduledActionDetailModel.getActionOptionModels())) {
            uiOption.execute(schedulerDto, scheduledTestCaseModel, scheduledActionDetailModel, scheduledActionDetailModel.getActionOptionModels(), webElement);
        }

        if (StringUtils.isNotEmpty(scheduledActionDetailModel.getValue())) {
            var tokenValue = scheduledActionDetailModel.getValue();
            if (tokenService.hasToken(tokenValue)) {
                tokenValue = tokenService.replaceToken(schedulerDto.getScheduleDetail().getApplicationId(),
                        tokenValue,
                        ScopeLevel.ACTION);
            }
            webElement.val(tokenValue);
        }
    }

    @Override
    public UIActionType actionName() {
        return UIActionType.INPUT;
    }
}
