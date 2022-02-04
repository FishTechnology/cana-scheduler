package cana.codelessautomation.scheduler.v2.services.action.types.ui.options;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.action.models.ActionOptionModel;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.options.types.UIBaseOption;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.options.types.dtos.UIOptionType;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import com.codeborne.selenide.SelenideElement;
import com.thoughtworks.xstream.converters.ConversionException;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import services.restclients.testcase.TestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UIOptionImpl implements UIOption {
    @Inject
    Instance<UIBaseOption> uiOptions;

    @Override
    public void execute(ScheduledTestPlanDto schedulerDto,
                        TestCaseModel scheduledTestCaseModel,
                        ActionDetailModel actionDetailModel,
                        List<ActionOptionModel> actionOptionModels,
                        SelenideElement webElement) throws TypeNotPresentException {
        var sortedOptions = actionOptionModels.stream().sorted(Comparator.comparing(ActionOptionModel::getOrder)).collect(Collectors.toList());

        for (ActionOptionModel actionOptionModel : sortedOptions) {
            if (!EnumUtils.isValidEnumIgnoreCase(UIOptionType.class, actionOptionModel.getOptionType())) {
                throw new ConversionException("Option type is not found Type=" + actionOptionModel.getOptionType(), null);
            }

            for (UIBaseOption uiBaseOption : uiOptions) {
                if (StringUtils.equalsAnyIgnoreCase(uiBaseOption.Name(), actionOptionModel.getOptionType())) {
                    uiBaseOption.execute(schedulerDto, scheduledTestCaseModel, actionDetailModel, actionOptionModel, webElement);
                    break;
                }
            }
        }
    }
}
