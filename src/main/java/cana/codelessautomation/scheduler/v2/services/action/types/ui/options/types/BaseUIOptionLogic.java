package cana.codelessautomation.scheduler.v2.services.action.types.ui.options.types;

import cana.codelessautomation.scheduler.v2.services.action.types.ui.dtos.ControlTypeAndIdDto;
import org.openqa.selenium.By;

public class BaseUIOptionLogic {

    public By getSelector(ControlTypeAndIdDto controlTypeAndIdDto) {
        By selector = null;
        switch (controlTypeAndIdDto.getControlIdType()) {
            case ID:
                selector = By.id(controlTypeAndIdDto.getId());
                break;
            case XPATH:
                selector = By.xpath(controlTypeAndIdDto.getId());
                break;
            case CSS:
                selector = By.cssSelector(controlTypeAndIdDto.getId());
                break;
        }

        return selector;
    }
}
