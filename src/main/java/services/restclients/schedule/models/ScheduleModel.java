package services.restclients.schedule.models;

import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;

@Data
public class ScheduleModel {
    @JMap
    private Long id;
    @JMap
    private Long testPlanId;
    @JMap
    private Long environmentId;
    @JMap
    private Long applicationId;
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
    @JMap
    private ScheduleIterationModel scheduleIteration;
}
