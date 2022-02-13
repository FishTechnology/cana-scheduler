package cana.codelessautomation.scheduler.v2.services.action.types.ui.options.mappers;

import cana.codelessautomation.scheduler.v2.services.action.result.option.models.ActionOptionResultStatus;
import cana.codelessautomation.scheduler.v2.services.action.result.option.models.UpdateActionOptionResultModel;

public interface UIOptionMapper {
    UpdateActionOptionResultModel mapUpdateActionOptionResultModel(ActionOptionResultStatus actionOptionResultStatus, long duration, String errorMessage);
}
