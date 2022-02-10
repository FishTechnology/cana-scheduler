package services.executor.action.mappers;

import cana.codelessautomation.scheduler.v2.services.action.result.models.UpdateActionResultModel;
import org.apache.commons.lang3.StringUtils;
import services.restclients.result.actionresult.models.enums.ActionResultStatusDao;

import javax.enterprise.context.ApplicationScoped;
import java.time.OffsetDateTime;

@ApplicationScoped
public class ActionExecutorMapperImpl implements ActionExecutorMapper {
    @Override
    public UpdateActionResultModel mapUpdateActionResultModel(long duration, String errorMessage, OffsetDateTime startedOn, OffsetDateTime completedOn) {
        UpdateActionResultModel updateActionResultModel = new UpdateActionResultModel();
        updateActionResultModel.setTotalDuration(String.valueOf(duration));
        updateActionResultModel.setErrorMessage(errorMessage);
        if (StringUtils.isNotEmpty(errorMessage)) {
            updateActionResultModel.setStatus(ActionResultStatusDao.ERROR.name());
        }
        updateActionResultModel.setStatus(ActionResultStatusDao.COMPLETED.name());
        return updateActionResultModel;
    }
}
