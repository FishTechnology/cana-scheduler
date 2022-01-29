package services.executor.action;

import cana.codelessautomation.scheduler.v2.services.action.types.Action;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import cana.codelessautomation.scheduler.v2.commons.CanaSchedulerUtility;
import services.commons.exceptions.SchedulerException;
import services.executor.action.mappers.ActionExecutorMapper;
import services.executor.dtos.SchedulerDto;
import services.restclients.result.actionresult.ActionResultServiceRestClient;
import services.restclients.result.actionresult.models.ActionResultModel;
import services.restclients.result.actionresult.models.UpdateActionResultModel;
import services.restclients.result.testcaseresult.models.TestCaseResultModel;
import services.restclients.schedule.models.ScheduledActionDetailModel;
import services.restclients.schedule.models.ScheduledTestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ActionExecutorImpl implements ActionExecutor {
    @Inject
    Instance<Action> actionInstance;

    @Inject
    ActionExecutorMapper actionExecutorMapper;

    @Inject
    @RestClient
    ActionResultServiceRestClient actionResultServiceRestClient;

    @Override
    public void execute(SchedulerDto schedulerDto,
                        ScheduledTestCaseModel scheduledTestCaseModel,
                        TestCaseResultModel testCaseResultModel) throws Exception {

        var actionResultModels = actionResultServiceRestClient.getActionResultsByTestCaseResultId(testCaseResultModel.getId());

        for (ScheduledActionDetailModel scheduledActionDetailModel : scheduledTestCaseModel.getScheduledActionDetails()) {
            StopWatch stopWatch = StopWatch.createStarted();
            String errorMessage = null;
            OffsetDateTime startedOn = OffsetDateTime.now();

            Optional<ActionResultModel> actionResultModel = getActionResultModel(actionResultModels, scheduledActionDetailModel);

            if (!actionResultModel.isPresent()) {
                throw new SchedulerException("ActionResult is null for Action Id =" + scheduledActionDetailModel.getId());
            }

            for (Action action : actionInstance) {
                if (action.actionName() == scheduledActionDetailModel.getType()) {
                    try {
                     //   action.execute(schedulerDto, scheduledTestCaseModel, scheduledActionDetailModel);
                    } catch (Exception exception) {
                        errorMessage = CanaSchedulerUtility.getMessage(exception);
                        break;
                    }
                }
            }

            if (stopWatch.isStarted()) {
                stopWatch.stop();
            }

            OffsetDateTime completedOn = OffsetDateTime.now();
            updateActionResult(
                    testCaseResultModel,
                    actionResultModel.get(),
                    stopWatch.getTime(),
                    errorMessage,
                    startedOn,
                    completedOn);
            if (StringUtils.isNotEmpty(errorMessage)) {
                throw new SchedulerException(errorMessage);
            }
        }
    }

    public Optional<ActionResultModel> getActionResultModel(List<ActionResultModel> actionResultModels,
                                                            ScheduledActionDetailModel scheduledActionDetailModel) {
        return actionResultModels.stream().filter(x -> x.getActionId() == scheduledActionDetailModel.getId()).findFirst();

    }

    public void updateActionResult(TestCaseResultModel testCaseResultModel,
                                   ActionResultModel actionResultModel,
                                   long duration,
                                   String errorMessage,
                                   OffsetDateTime startedOn,
                                   OffsetDateTime completedOn) throws Exception {
        UpdateActionResultModel updateActionResultModel = actionExecutorMapper.mapUpdateActionResultModel(duration, errorMessage, startedOn, completedOn);
        var errorMessages = actionResultServiceRestClient.updateActionResult(
                testCaseResultModel.getId(),
                actionResultModel.getId(),
                updateActionResultModel);

        if (CollectionUtils.isNotEmpty(errorMessages)) {
            throw new Exception("Error occur while update Action result Status =" + errorMessages);
        }
    }
}
