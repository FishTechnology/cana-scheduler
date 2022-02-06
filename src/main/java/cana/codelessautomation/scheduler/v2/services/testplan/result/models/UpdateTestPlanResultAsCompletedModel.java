package cana.codelessautomation.scheduler.v2.services.testplan.result.models;

import lombok.Data;

@Data
public class UpdateTestPlanResultAsCompletedModel {
    private String status;
    private String errorMessage;
    private String totalDuration;
}
