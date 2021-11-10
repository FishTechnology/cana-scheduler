package services.restclients.schedule.models;

import lombok.Data;
import services.restclients.testcase.TestCaseModel;

import java.util.List;

@Data
public class ScheduledTestCaseModel extends TestCaseModel {
    private Long order;
    private List<ScheduledActionDetailModel> scheduledActionDetails;
}
