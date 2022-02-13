package cana.codelessautomation.scheduler.v2.services.action.result.option.models;

import lombok.Data;

@Data
public class UpdateActionOptionResultModel {
    private String status;
    private String errorMessage;
    private Long totalDuration;
}
