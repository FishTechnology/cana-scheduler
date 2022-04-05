package cana.codelessautomation.scheduler.v2.services.action.models;

import lombok.Data;

@Data
public class ActionOptionModel {
    private Long id;
    private Long actionId;
    private String optionType;
    private Long order;
    private String controlConditionType;
    private Boolean isActive;
    private String value;
    private String contentType;
    private Long duration;
    private String createdOn;
    private String modifiedOn;
    private String createdBy;
    private String modifiedBy;
}
