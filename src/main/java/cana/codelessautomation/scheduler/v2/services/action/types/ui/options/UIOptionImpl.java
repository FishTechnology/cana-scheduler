package cana.codelessautomation.scheduler.v2.services.action.types.ui.options;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.action.models.ActionOptionModel;
import cana.codelessautomation.scheduler.v2.services.action.result.models.ActionResultModel;
import cana.codelessautomation.scheduler.v2.services.action.result.option.models.ActionOptionResultModel;
import cana.codelessautomation.scheduler.v2.services.action.result.option.models.ActionOptionResultStatus;
import cana.codelessautomation.scheduler.v2.services.action.result.option.resetclient.ActionOptionResultRestClient;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.options.mappers.UIOptionMapper;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.options.types.UIBaseOption;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.options.types.dtos.UIOptionType;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import com.codeborne.selenide.SelenideElement;
import com.thoughtworks.xstream.converters.ConversionException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import services.restclients.testcase.TestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class UIOptionImpl implements UIOption {
    @Inject
    Instance<UIBaseOption> uiOptions;

    @Inject
    UIOptionMapper uiOptionMapper;

    @Inject
    @RestClient
    ActionOptionResultRestClient actionOptionResultRestClient;

    @Override
    public void execute(ScheduledTestPlanDto schedulerDto,
                        TestCaseModel scheduledTestCaseModel,
                        ActionDetailModel actionDetailModel,
                        List<ActionOptionModel> actionOptionModels,
                        SelenideElement webElement) throws Exception {
        var startedOn = System.nanoTime();
        var isFirstActionOption = true;

        var sortedOptions = actionOptionModels
                .stream()
                .sorted(Comparator.comparing(ActionOptionModel::getOrder))
                .collect(Collectors.toList());

        var actionOptionResultModels = actionOptionResultRestClient.getActionOptionResultsByActionResultId(schedulerDto.getActionResultModel().getId());

        if (CollectionUtils.isEmpty(actionOptionResultModels)) {
            throw new Exception("empty ActionOptionResult found for  ActionResultId=" + schedulerDto.getActionResultModel().getId());
        }

        for (ActionOptionModel actionOptionModel : sortedOptions) {
            if (!isFirstActionOption) {
                startedOn = System.nanoTime();
            }

            if (!EnumUtils.isValidEnumIgnoreCase(UIOptionType.class, actionOptionModel.getOptionType())) {
                throw new ConversionException("Option type is not found Type=" + actionOptionModel.getOptionType(), null);
            }

            var currentActionOptionResult = actionOptionResultModels
                    .stream()
                    .filter(x -> Objects.equals(x.getActionOptionId(), actionOptionModel.getId()))
                    .findFirst();

            if (currentActionOptionResult.isEmpty()) {
                throw new Exception("empty ActionOptionResult found for  ActionOptionResultId=" + actionOptionModel.getId());
            }

            for (UIBaseOption uiBaseOption : uiOptions) {
                if (StringUtils.equalsAnyIgnoreCase(uiBaseOption.Name(), actionOptionModel.getOptionType())) {
                    try {
                        uiBaseOption.execute(schedulerDto, scheduledTestCaseModel, actionDetailModel, actionOptionModel, webElement);
                    } catch (Exception exception) {
                        updateActionResult(schedulerDto.getActionResultModel(), currentActionOptionResult.get(), ActionOptionResultStatus.ERROR, startedOn, exception.getMessage());
                        throw new Exception("Error occur in processing UIActionOptions");
                    }
                    updateActionResult(schedulerDto.getActionResultModel(), currentActionOptionResult.get(), ActionOptionResultStatus.COMPLETED, startedOn, null);
                    break;
                }
            }

            isFirstActionOption = false;
        }
    }

    public void updateActionResult(ActionResultModel actionResultModel,
                                   ActionOptionResultModel actionOptionResultModel,
                                   ActionOptionResultStatus actionOptionResultStatus,
                                   long startedOn,
                                   String errorMessage) throws Exception {

        var duration = 0L;
        if (actionOptionResultStatus == ActionOptionResultStatus.COMPLETED || actionOptionResultStatus == ActionOptionResultStatus.ERROR) {
            var endedOn = System.nanoTime();
            duration = (endedOn - startedOn) / 1000000;
        }
        var updateActionOptionResultModel = uiOptionMapper.mapUpdateActionOptionResultModel(actionOptionResultStatus, duration, errorMessage);
        var actionResultMessage = actionOptionResultRestClient.updateActionOptionResult(actionResultModel.getId(), actionOptionResultModel.getId(), updateActionOptionResultModel);
        if (CollectionUtils.isNotEmpty(actionResultMessage)) {
            //TODO log error:
            throw new Exception("Error while updating ActionOptionResult");
        }
    }
}
