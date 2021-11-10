package services.restclients.testplan.models;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class TestPlanModel {
    private Long id;
    private Long userId;
    private String name;
    private String comments;
    private OffsetDateTime createdOn;
    private OffsetDateTime modifiedOn;
    private String createdBy;
    private String modifiedBy;
    private String status;
}
