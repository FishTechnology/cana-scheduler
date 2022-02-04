package cana.codelessautomation.scheduler.v2.services.action;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.action.restclient.ActionServiceRestClient;
import cana.codelessautomation.scheduler.v2.services.action.types.Action;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class ActionServiceImpl implements ActionService {

    @Inject
    Instance<Action> actionInstance;

    @Inject
    @RestClient
    ActionServiceRestClient actionServiceRestClient;

    @Override
    public void execute(ScheduledTestPlanDto scheduledTestPlanDto) {
        var actionModels = (List<ActionDetailModel>) null;
        try {
            actionModels = actionServiceRestClient.getActionsByTestCaseId(scheduledTestPlanDto.getTestCaseDetail().getId());
        } catch (Exception ex) {

        }

        if (Objects.isNull(actionModels)) {
            return;
        }

        var sortedActions = actionModels
                .stream()
                .sorted(Comparator.comparing(ActionDetailModel::getOrder))
                .collect(Collectors.toList());

        for (ActionDetailModel actionModel : sortedActions) {
            for (Action action : actionInstance) {
                if (action.actionName() == actionModel.getType()) {
                    try {
                        action.execute(scheduledTestPlanDto, scheduledTestPlanDto.getTestCaseDetail(), actionModel);
                    } catch (Exception exception) {

                    }
                }
            }
        }
    }
}
