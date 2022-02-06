package cana.codelessautomation.scheduler.v2.services.testcase.result.models;

import lombok.Data;

@Data
public class TestCaseResultModel {
    private Long id;
    private Long testCaseId;
    private String startedOn;
    private String completedOn;
    private String duration;
    private String status;
    private String errorMessage;
    private String totalDuration;
    private Long executionOrder;
    private Long testPlanResultId;
    private String createdOn;
    private String modifiedOn;
}
