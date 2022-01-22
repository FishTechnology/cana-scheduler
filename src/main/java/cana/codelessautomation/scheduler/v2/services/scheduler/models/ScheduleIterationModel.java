package cana.codelessautomation.scheduler.v2.services.scheduler.models;

import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;

@Data
public class ScheduleIterationModel {
    @JMap
    private Long id;
    @JMap
    private Long scheduleId;
    @JMap
    private String status;
    @JMap
    private String comments;
    @JMap
    private Boolean isRecordVideoEnabled;
    @JMap
    private Boolean isDisableScreenshot;
    @JMap
    private Boolean isCaptureNetworkTraffic;
    @JMap
    private String startedOn;
    @JMap
    private String completedOn;
    @JMap
    private String createdOn;
    @JMap
    private String modifiedOn;
    @JMap
    private String createdBy;
    @JMap
    private String modifiedBy;
    @JMap
    private String browserType;
}
