package services.executor.action.types.ui.input;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import services.executor.action.types.ui.UIAction;
import services.executor.action.types.ui.UIActionType;
import services.executor.action.types.ui.UIActionUtility;
import services.executor.dtos.SchedulerDto;
import services.restclients.action.ActionOptionModel;
import services.restclients.schedule.models.ScheduledActionDetailModel;
import services.restclients.schedule.models.ScheduledTestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import java.util.Comparator;

@ApplicationScoped
public class InputActionImpl implements UIAction {
    @Override
    public void execute(SchedulerDto schedulerDto, ScheduledTestCaseModel scheduledTestCaseModel, ScheduledActionDetailModel scheduledActionDetailModel) {
        var controlTypeAndIdDto = UIActionUtility.getIdAndValue(scheduledActionDetailModel.getValue());
        var webElement = UIActionUtility.getUIControl(controlTypeAndIdDto);

        if (CollectionUtils.isNotEmpty(scheduledActionDetailModel.getActionOptionModels())) {
            scheduledActionDetailModel
                    .getActionOptionModels()
                    .sort(new Comparator<ActionOptionModel>() {
                        @Override
                        public int compare(ActionOptionModel o1, ActionOptionModel o2) {
                            return (int) (o1.getOrder() - o2.getOrder());
                        }
                    });
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
