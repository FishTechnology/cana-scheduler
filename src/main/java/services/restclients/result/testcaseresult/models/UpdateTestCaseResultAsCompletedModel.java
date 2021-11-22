package services.restclients.result.testcaseresult.models;

import lombok.Data;

@Data
public class UpdateTestCaseResultAsCompletedModel {
    private String status;
    private String errorMessage;
    private String totalDuration;
    private String modifiedBy;
    private String startedOn;
    private String completedOn;
}