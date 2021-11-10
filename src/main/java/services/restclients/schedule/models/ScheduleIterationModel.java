package services.restclients.schedule.models;

import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;

import javax.persistence.Id;

@Data
public class ScheduleIterationModel {
    @Id
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
}
