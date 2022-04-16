package cana.codelessautomation.scheduler.v2.services.action.types.ui.input;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.UIAction;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.UIActionType;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.input.types.UIInputType;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.input.types.UIInputTypeName;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.options.UIOption;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.utilities.UIActionUtility;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import cana.codelessautomation.scheduler.v2.services.token.TokenService;
import cana.codelessautomation.scheduler.v2.services.token.dtos.ScopeLevel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import services.restclients.testcase.TestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class InputActionImpl implements UIAction {

    @Inject
    UIOption uiOption;

    @Inject
    UIActionUtility uiActionUtility;

    @Inject
    TokenService tokenService;

    @Inject
    Instance<UIInputType> uiInputTypes;

    @Override
    public void execute(ScheduledTestPlanDto schedulerDto, TestCaseModel scheduledTestCaseModel, ActionDetailModel scheduledActionDetailModel) throws Exception {
        var tokenValue = scheduledActionDetailModel.getValue();
        var controlTypeAndIdDto = uiActionUtility.getIdAndValue(scheduledActionDetailModel.getKey());

        if (CollectionUtils.isNotEmpty(scheduledActionDetailModel.getActionOptionModels())) {
            uiOption.execute(schedulerDto, scheduledTestCaseModel, scheduledActionDetailModel, scheduledActionDetailModel.getActionOptionModels());
        }

        var webElement = uiActionUtility.getUIControl(schedulerDto.getScheduleDetail().getApplicationId(), controlTypeAndIdDto);
        if (StringUtils.isNotEmpty(scheduledActionDetailModel.getValue())) {
            if (tokenService.hasToken(tokenValue)) {
                tokenValue = tokenService.replaceToken(schedulerDto.getScheduleDetail().getApplicationId(),
                        tokenValue,
                        schedulerDto.getScheduleDetail().getEnvironmentId(),
                        ScopeLevel.ACTION);
            }
        }

        if (StringUtils.isNotEmpty(tokenValue) && BooleanUtils.isFalse(scheduledActionDetailModel.getIsAssertVerification())) {
            var uiInputTypeName = EnumUtils.getEnumIgnoreCase(UIInputTypeName.class, webElement.attr("type"), UIInputTypeName.DEFAULT);
            for (UIInputType uiInputType : uiInputTypes) {
                if (StringUtils.equalsAnyIgnoreCase(uiInputType.uiInputTypeName().name(), uiInputTypeName.name())) {
                    uiInputType.execute(schedulerDto, webElement, tokenValue);
                    return;
                }
            }
            webElement.val(tokenValue);
        }
    }

    @Override
    public UIActionType actionName() {
        return UIActionType.INPUT;
    }
}
