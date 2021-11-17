package services.executor.dtos;

import lombok.Data;
import services.restclients.schedule.models.ScheduleDetailModel;

@Data
public class SchedulerDto {
    private Long scheduleId;
    private ScheduleDetailModel scheduleDetail;
}
