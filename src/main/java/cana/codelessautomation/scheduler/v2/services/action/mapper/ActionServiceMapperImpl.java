package cana.codelessautomation.scheduler.v2.services.action.mapper;

import cana.codelessautomation.scheduler.v2.services.action.result.models.UpdateActionResultModel;
import services.restclients.result.actionresult.models.enums.ActionResultStatusDao;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ActionServiceMapperImpl implements ActionServiceMapper {
    @Override
    public UpdateActionResultModel mapUpdateActionResultModel(ActionResultStatusDao actionResultStatusDao, long duration, String errorMessage) {
        UpdateActionResultModel updateActionResultModel = new UpdateActionResultModel();
        updateActionResultModel.setStatus(actionResultStatusDao.name());
        if(actionResultStatusDao == ActionResultStatusDao.COMPLETED || actionResultStatusDao == ActionResultStatusDao.ERROR)
        {
            updateActionResultModel.setTotalDuration(String.valueOf(duration));
            updateActionResultModel.setErrorMessage(errorMessage);
        }

        return updateActionResultModel;
    }
}
