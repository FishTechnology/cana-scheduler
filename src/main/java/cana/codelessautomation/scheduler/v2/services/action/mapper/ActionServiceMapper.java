package cana.codelessautomation.scheduler.v2.services.action.mapper;

import cana.codelessautomation.scheduler.v2.services.action.result.models.UpdateActionResultModel;
import services.restclients.result.actionresult.models.enums.ActionResultStatusDao;

public interface ActionServiceMapper {
    UpdateActionResultModel mapUpdateActionResultModel(ActionResultStatusDao actionResultStatusDao, long duration, String errorMessage);
}
