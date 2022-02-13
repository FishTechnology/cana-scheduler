package cana.codelessautomation.scheduler.v2.services.action.models;

import lombok.Data;

@Data
public class ActionOptionModel {
    private Long id;
    private Long actionId;
    private String optionType;
    private Long waitDuration;
    private Long order;
    private String conditionType;
    private String createdOn;
    private String modifiedOn;
    private String createdBy;
    private String modifiedBy;
    private Boolean isActive;
}
