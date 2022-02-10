package cana.codelessautomation.scheduler.v2.services.action.result.models;

import lombok.Data;

@Data
public class ActionResultModel {
    private Long testcaseResultId;
    private Long actionId;
    private Long id;
    private String duration;
    private String status;
    private String startedOn;
    private String completedOn;
    private String errorMessage;
    private Long executionOrder;
}
