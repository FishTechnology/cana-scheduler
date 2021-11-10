package services.restclients.schedule.models;

import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;

import javax.persistence.Id;

@Data
public class ScheduleModel {
    @Id
    private Long id;
    @JMap
    private Long testPlanId;
    @JMap
    private Long environmentId;
    @JMap
    private Long userId;
    @JMap
    private String status;
    @JMap
    private String createdOn;
    @JMap
    private String modifiedOn;
    @JMap
    private String createdBy;
    @JMap
    private String modifiedBy;
}
