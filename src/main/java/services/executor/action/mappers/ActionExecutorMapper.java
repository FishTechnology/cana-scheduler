package services.executor.action.mappers;

import cana.codelessautomation.scheduler.v2.services.action.result.models.UpdateActionResultModel;

import java.time.OffsetDateTime;

public interface ActionExecutorMapper {
    UpdateActionResultModel mapUpdateActionResultModel(long duration, String errorMessage, OffsetDateTime startedOn, OffsetDateTime completedOn);
}
