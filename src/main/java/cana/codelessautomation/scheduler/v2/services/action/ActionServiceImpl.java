package cana.codelessautomation.scheduler.v2.services.action;

import cana.codelessautomation.scheduler.v2.services.action.mapper.ActionServiceMapper;
import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.action.restclient.ActionServiceRestClient;
import cana.codelessautomation.scheduler.v2.services.action.result.restclient.ActionResultRestClient;
import cana.codelessautomation.scheduler.v2.services.action.result.models.ActionResultModel;
import cana.codelessautomation.scheduler.v2.services.action.types.Action;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import cana.codelessautomation.scheduler.v2.services.testcase.result.models.TestCaseResultModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import services.restclients.result.actionresult.models.enums.ActionResultStatusDao;

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

    @Inject
    @RestClient
    ActionResultRestClient actionResultRestClient;

    @Inject
    ActionServiceMapper actionServiceMapper;

    @Override
    public void execute(ScheduledTestPlanDto scheduledTestPlanDto) throws Exception {
        var isFirstAction = true;
        var startedOn = System.nanoTime();

        var actionModels = (List<ActionDetailModel>) null;
        try {
            actionModels = actionServiceRestClient.getActionsByTestCaseId(scheduledTestPlanDto.getTestCaseDetail().getId());
        } catch (Exception ex) {

        }

        if (Objects.isNull(actionModels)) {
            return;
        }

        var actionResultModels = actionResultRestClient.getActionResultsByTestCaseResultId(scheduledTestPlanDto.getTestCaseResultModel().getId());
        if (CollectionUtils.isEmpty(actionResultModels)) {
            throw new Exception("didn't find action result for testCaseId testCaseId=" + scheduledTestPlanDto.getTestCaseResultModel().getId());
        }

        var sortedActions = actionModels
                .stream()
                .sorted(Comparator.comparing(ActionDetailModel::getOrder))
                .collect(Collectors.toList());

        for (ActionDetailModel actionModel : sortedActions) {

            if (!isFirstAction) {
                startedOn = System.nanoTime();
            }

            for (Action action : actionInstance) {
                if (action.actionName() == actionModel.getType()) {

                    var currentActionResult = actionResultModels
                            .stream()
                            .filter(x -> Objects.equals(x.getActionId(), actionModel.getId()))
                            .findFirst();

                    if (currentActionResult.isEmpty()) {
                        throw new Exception("didn't find action result for actionId=" + actionModel.getId());
                    }

                    scheduledTestPlanDto.setActionResultModel(currentActionResult.get());
                    updateActionResult(scheduledTestPlanDto.getTestCaseResultModel(), currentActionResult.get(), ActionResultStatusDao.STARTED, 0, null);
                    try {
                        action.execute(scheduledTestPlanDto, scheduledTestPlanDto.getTestCaseDetail(), actionModel);
                    } catch (Throwable throwable) {
                        updateActionResult(scheduledTestPlanDto.getTestCaseResultModel(), currentActionResult.get(), ActionResultStatusDao.ERROR, startedOn, throwable.getMessage());
                        if (BooleanUtils.isTrue(actionModel.getIsOptional())) {
                            continue;
                        }
                        throw throwable;
                    }

                    updateActionResult(scheduledTestPlanDto.getTestCaseResultModel(), currentActionResult.get(), ActionResultStatusDao.COMPLETED, startedOn, null);
                    isFirstAction = false;
                }
            }
        }
    }

    public void updateActionResult(TestCaseResultModel testCaseResultModel,
                                   ActionResultModel actionResultModel,
                                   ActionResultStatusDao actionResultStatusDao,
                                   long startedOn,
                                   String errorMessage) throws Exception {

        var duration = 0L;
        if (actionResultStatusDao == ActionResultStatusDao.COMPLETED || actionResultStatusDao == ActionResultStatusDao.ERROR) {
            var endedOn = System.nanoTime();
            duration = (endedOn - startedOn) / 1000000;
        }
        var updateActionResultModel = actionServiceMapper.mapUpdateActionResultModel(actionResultStatusDao, duration, errorMessage);
        var actionResultMessage = actionResultRestClient.updateActionResult(testCaseResultModel.getId(), actionResultModel.getId(), updateActionResultModel);
        if (CollectionUtils.isNotEmpty(actionResultMessage)) {
            //TODO log error:
            throw new Exception("Error while updating ActionResult as Started");
        }
    }
}
