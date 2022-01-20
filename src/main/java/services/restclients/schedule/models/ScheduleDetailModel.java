package services.restclients.schedule.models;

import lombok.Data;

@Data
public class ScheduleDetailModel {
    private ScheduleModel scheduleModel;
    private ScheduleIterationModel scheduleIterationModel;
}
