package cana.codelessautomation.scheduler.v2.services.action;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.action.restclient.ActionServiceRestClient;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class ActionServiceImpl implements ActionService {

    @Inject
    @RestClient
    ActionServiceRestClient actionServiceRestClient;

    @Override
    public void execute(ScheduledTestPlanDto scheduledTestPlanDto) {
        var actionModels = (List<ActionDetailModel>) null;
        try {
            actionModels = actionServiceRestClient.getActionsByTestCaseId(scheduledTestPlanDto.getTestCaseModel().getId());
        } catch (Exception ex) {
            var name = ex.getClass().getSimpleName();
        }


        if (Objects.isNull(actionModels)) {
            return;
        }

        for (ActionDetailModel actionModel : actionModels) {

        }
    }
}
