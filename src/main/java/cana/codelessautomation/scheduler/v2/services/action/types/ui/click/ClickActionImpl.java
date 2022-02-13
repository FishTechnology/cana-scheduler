package cana.codelessautomation.scheduler.v2.services.action.types.ui.click;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.UIAction;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.UIActionType;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.options.UIOption;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.utilities.UIActionUtility;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import services.restclients.testcase.TestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ClickActionImpl implements UIAction {


    @Inject
    UIOption uiOption;

    @Inject
    UIActionUtility uiActionUtility;

    @Override
    public void execute(ScheduledTestPlanDto schedulerDto, TestCaseModel scheduledTestCaseModel, ActionDetailModel scheduledActionDetailModel) throws Exception {
        var controlTypeAndIdDto = uiActionUtility.getIdAndValue(scheduledActionDetailModel.getKey());
        var webElement = uiActionUtility.getUIControl(schedulerDto.getScheduleDetail().getApplicationId(), controlTypeAndIdDto);

        if (CollectionUtils.isNotEmpty(scheduledActionDetailModel.getActionOptionModels())) {
            uiOption.execute(schedulerDto, scheduledTestCaseModel, scheduledActionDetailModel, scheduledActionDetailModel.getActionOptionModels(), webElement);
        }
        if (BooleanUtils.isFalse(scheduledActionDetailModel.getIsAssertVerification())) {
            webElement.click();
        }
    }

    @Override
    public UIActionType actionName() {
        return UIActionType.CLICK;
    }
}
