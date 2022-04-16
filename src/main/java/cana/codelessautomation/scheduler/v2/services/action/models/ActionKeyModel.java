package cana.codelessautomation.scheduler.v2.services.action.models;

import lombok.Data;

@Data
public class ActionKeyModel {
    private Long id;
    private Long actionId;
    private String key;
    private String createdOn;
    private String modifiedOn;
    private String createdBy;
    private String modifiedBy;
    private Boolean isActive;
}
