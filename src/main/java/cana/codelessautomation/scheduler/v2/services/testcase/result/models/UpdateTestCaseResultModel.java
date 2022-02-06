package cana.codelessautomation.scheduler.v2.services.testcase.result.models;

import lombok.Data;

@Data
public class UpdateTestCaseResultModel {
    private String status;
    private String errorMessage;
    private String totalDuration;
}