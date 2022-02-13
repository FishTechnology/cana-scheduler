package cana.codelessautomation.scheduler.v2.services.action.types.ui.options.mappers;

import cana.codelessautomation.scheduler.v2.services.action.result.option.models.ActionOptionResultStatus;
import cana.codelessautomation.scheduler.v2.services.action.result.option.models.UpdateActionOptionResultModel;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UIOptionMapperImpl implements UIOptionMapper {
    @Override
    public UpdateActionOptionResultModel mapUpdateActionOptionResultModel(ActionOptionResultStatus actionOptionResultStatus, long duration, String errorMessage) {
        UpdateActionOptionResultModel updateActionOptionResultModel = new UpdateActionOptionResultModel();
        updateActionOptionResultModel.setStatus(actionOptionResultStatus.name());
        if (actionOptionResultStatus == ActionOptionResultStatus.COMPLETED || actionOptionResultStatus == ActionOptionResultStatus.ERROR) {
            updateActionOptionResultModel.setTotalDuration(duration);
            updateActionOptionResultModel.setErrorMessage(errorMessage);
        }

        return updateActionOptionResultModel;
    }
}
