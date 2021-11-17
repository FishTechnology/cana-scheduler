package services.restclients.result.testplanresult.models;

import lombok.Data;

@Data
public class UpdateTestPlanResultAsCompletedModel {
    private String status;
    private String errorMessage;
    private String totalDuration;
    private String modifiedBy;
    private String startedOn;
    private String completedOn;
}
