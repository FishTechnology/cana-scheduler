package cana.codelessautomation.scheduler.v2.services.scheduler.models;

import lombok.Data;
import services.restclients.schedule.models.ScheduleModel;
import services.restclients.testcase.TestCaseModel;
import services.restclients.testplan.models.TestPlanModel;

@Data
public class ScheduledTestPlanDto {
    private TestPlanModel testPlanModel;
    private TestCaseModel testCaseModel;
    private ScheduleModel scheduleModel;
}
