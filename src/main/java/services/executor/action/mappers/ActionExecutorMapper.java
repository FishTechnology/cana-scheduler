package services.executor.action.mappers;

import services.restclients.result.actionresult.models.UpdateActionResultModel;

import java.time.OffsetDateTime;

public interface ActionExecutorMapper {
    UpdateActionResultModel mapUpdateActionResultModel(long duration, String errorMessage, OffsetDateTime startedOn, OffsetDateTime completedOn);
}
