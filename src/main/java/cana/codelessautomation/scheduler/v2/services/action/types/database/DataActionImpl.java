package cana.codelessautomation.scheduler.v2.services.action.types.database;

import cana.codelessautomation.scheduler.v2.services.action.types.Action;
import services.executor.dtos.SchedulerDto;
import services.restclients.action.ActionTypeDao;
import services.restclients.schedule.models.ScheduledActionDetailModel;
import services.restclients.schedule.models.ScheduledTestCaseModel;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DataActionImpl implements Action {
    @Override
    public void execute(SchedulerDto schedulerDto, ScheduledTestCaseModel scheduledTestCaseModel, ScheduledActionDetailModel scheduledActionDetailModel) {

    }

    @Override
    public ActionTypeDao actionName() {
        return ActionTypeDao.DATABASE;
    }
}
