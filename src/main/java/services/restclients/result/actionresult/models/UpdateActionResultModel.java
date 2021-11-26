package services.restclients.result.actionresult.models;

import lombok.Data;

@Data
public class UpdateActionResultModel {
    private Long actionId;
    private String status;
    private String errorMessage;
    private String totalDuration;
    private String modifiedBy;
    private String startedOn;
    private String completedOn;
}
