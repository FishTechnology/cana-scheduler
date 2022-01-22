package cana.codelessautomation.scheduler.v2.services.config.models;

import lombok.Data;

@Data
public class ConfigKeyValueModel {
    private String id;
    private String userId;
    private String configId;
    private String key;
    private String value;
    private String type;
    private String content;
    private String comments;
    private Boolean isActive = false;
}
