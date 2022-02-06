package cana.codelessautomation.scheduler.v2.services.testplan.result.models;

import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;

@Data
public class TestPlanResultModel {
    @JMap
    private Long id;
    @JMap
    private Long testPlanId;
    @JMap
    private Long scheduleIterationId;
    @JMap
    private String startedOn;
    @JMap
    private String completedOn;
    @JMap
    private String errorMessage;
    @JMap
    private String status;
    @JMap
    private String createdOn;
    @JMap
    private String modifiedOn;
    @JMap
    private String totalDuration;
}
