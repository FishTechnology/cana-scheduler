package services.restclients.schedule.models;

import lombok.Data;

import java.util.List;

@Data
public class ScheduleTestPlanModel extends TestPlanModel {
    private List<ScheduledTestCaseModel> scheduledTestCaseModel;
}
