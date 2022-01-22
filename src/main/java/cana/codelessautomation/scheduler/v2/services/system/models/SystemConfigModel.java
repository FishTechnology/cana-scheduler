package cana.codelessautomation.scheduler.v2.services.system.models;

import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;

@Data
public class SystemConfigModel {
    @JMap
    private String id;
    @JMap
    private String key;
    @JMap
    private String value;
    @JMap
    private String userid;
    @JMap
    private String createdOn;
    @JMap
    private String modifiedOn;
    @JMap
    private String createdBy;
    @JMap
    private String modifiedBy;
    @JMap
    private Boolean isActive;
    @JMap
    private String comments;
}
