package cana.codelessautomation.scheduler.v2.services.action.types.ui.key;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionKeyModel;
import com.codeborne.selenide.SelenideElement;

import java.util.List;

public interface UIActionKey {
    void execute(List<ActionKeyModel> actionKeys, SelenideElement webElement);

    CharSequence getKeyCode(ActionKeyModel actionKeyModel);
}
