package cana.codelessautomation.scheduler.v2.services.action.types.ui.utilities;

import cana.codelessautomation.scheduler.v2.services.action.types.ui.dtos.ControlTypeAndIdDto;
import com.codeborne.selenide.SelenideElement;

public interface UIActionUtility {
    ControlTypeAndIdDto getIdAndValue(String controlKey);

    SelenideElement getUIControl(Long applicationId, ControlTypeAndIdDto controlTypeAndIdDto);
}
