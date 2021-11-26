package services.restclients.schedule.models;

import lombok.Data;
import services.restclients.action.ActionDetailModel;

import java.util.List;

@Data
public class
ScheduledActionDetailModel extends ActionDetailModel {
    private List<ScheduledActionOptionModel> scheduledActionOptions;
}
