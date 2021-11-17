package services.restclients.schedule.models;

import lombok.Data;

@Data
public class UpdateScheduleStatusModel {
    private String status;
    private String errorMessage;
    private String totalDuration;
}
