package services.executor.action.mappers;

import org.apache.commons.lang3.StringUtils;
import services.restclients.result.actionresult.models.UpdateActionResultModel;
import services.restclients.result.actionresult.models.enums.ActionResultStatusDao;

import javax.enterprise.context.ApplicationScoped;
import java.time.OffsetDateTime;

@ApplicationScoped
public class ActionExecutorMapperImpl implements ActionExecutorMapper {
    @Override
    public UpdateActionResultModel mapUpdateActionResultModel(long duration, String errorMessage, OffsetDateTime startedOn, OffsetDateTime completedOn) {
        UpdateActionResultModel updateActionResultModel = new UpdateActionResultModel();
        updateActionResultModel.setCompletedOn(completedOn.toString());
        updateActionResultModel.setStartedOn(startedOn.toString());
        updateActionResultModel.setTotalDuration(String.valueOf(duration));
        updateActionResultModel.setErrorMessage(errorMessage);
        if (StringUtils.isNotEmpty(errorMessage)) {
            updateActionResultModel.setStatus(ActionResultStatusDao.ERROR.name());
        }
        updateActionResultModel.setStatus(ActionResultStatusDao.COMPLETED.name());
        return updateActionResultModel;
    }
}
