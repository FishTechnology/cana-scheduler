package cana.codelessautomation.scheduler.v2.services.action.types.ui.input;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.action.models.ActionOptionModel;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.UIAction;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.UIActionType;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.UIActionUtility;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import services.restclients.testcase.TestCaseModel;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InputActionImpl implements UIAction {
    @Override
    public void execute(ScheduledTestPlanDto schedulerDto, TestCaseModel scheduledTestCaseModel, ActionDetailModel scheduledActionDetailModel) {
        var controlTypeAndIdDto = UIActionUtility.getIdAndValue(scheduledActionDetailModel.getValue());
        var webElement = UIActionUtility.getUIControl(controlTypeAndIdDto);

        if (CollectionUtils.isNotEmpty(scheduledActionDetailModel.getActionOptionModels())) {
            scheduledActionDetailModel
                    .getActionOptionModels();
            for (ActionOptionModel actionOptionModel : scheduledActionDetailModel.getActionOptionModels()) {

            }
        }


        if (StringUtils.isEmpty(scheduledActionDetailModel.getValue())) {
            webElement.val(scheduledActionDetailModel.getValue());
        }
    }

    @Override
    public UIActionType actionName() {
        return UIActionType.INPUT;
    }
}
