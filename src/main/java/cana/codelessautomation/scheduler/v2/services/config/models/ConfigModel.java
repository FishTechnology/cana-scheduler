package cana.codelessautomation.scheduler.v2.services.config.models;

import lombok.Data;

import java.util.List;

@Data
public class ConfigModel {
    private String id;
    private String name;
    private String type;
    private String userId;
    private String createdOn;
    private String modifiedOn;
    private String createdBy;
    private String modifiedBy;
    private String comments;
    private String identifier;
    private String applicationId;
    private Boolean isActive = false;
    private List<ConfigKeyValueModel> configKeyValues;
}
