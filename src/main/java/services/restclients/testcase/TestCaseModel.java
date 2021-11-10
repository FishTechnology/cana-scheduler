package services.restclients.testcase;

import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;

@Data
public class TestCaseModel {
    @JMap
    private Long id;
    @JMap
    private Long userId;
    @JMap
    private String name;
    @JMap
    private String comments;
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
}
