package cana.codelessautomation.scheduler.v2.services.scheduler.models;

import cana.codelessautomation.scheduler.v2.services.config.models.ConfigModel;
import cana.codelessautomation.scheduler.v2.services.system.models.SystemConfigModel;
import cana.codelessautomation.scheduler.v2.services.testplan.result.models.TestPlanResultModel;
import lombok.Data;
import services.restclients.schedule.models.ScheduleModel;
import services.restclients.testcase.TestCaseModel;
import services.restclients.testplan.models.TestPlanModel;

import java.util.List;

@Data
public class ScheduledTestPlanDto {
    private TestPlanModel testPlanDetail;
    private TestCaseModel testCaseDetail;
    private ScheduleModel scheduleDetail;
    private List<ConfigModel> configModels;
    private List<SystemConfigModel> systemConfigs;
    private List<ConfigModel> applicationConfigs;
    private TestPlanResultModel testPlanResultModel;
}
