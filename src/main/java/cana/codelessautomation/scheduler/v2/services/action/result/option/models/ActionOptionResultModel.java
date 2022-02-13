package cana.codelessautomation.scheduler.v2.services.action.result.option.models;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ActionOptionResultModel {
    private Long id;

    private Long actionResultId;

    private Long actionOptionId;

    private OffsetDateTime startedOn;

    private OffsetDateTime completedOn;

    private String status;

    private String errorMessage;

    private OffsetDateTime createdOn;

    private OffsetDateTime modifiedOn;

    private Long executionOrder;

    private Long duration;
}
