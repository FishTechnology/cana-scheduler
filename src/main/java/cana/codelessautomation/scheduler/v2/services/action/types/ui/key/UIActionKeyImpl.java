package cana.codelessautomation.scheduler.v2.services.action.types.ui.key;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionKeyModel;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.EnumUtils;
import org.openqa.selenium.Keys;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class UIActionKeyImpl implements UIActionKey {
    /**
     * @param actionKeys
     * @param webElement
     */
    @Override
    public void execute(List<ActionKeyModel> actionKeys, SelenideElement webElement) {
        for (ActionKeyModel actionKeyModel : actionKeys) {
            webElement.sendKeys(getKeyCode(actionKeyModel));
        }
    }

    @Override
    public CharSequence getKeyCode(ActionKeyModel actionKeyModel) {
        var uiKey = EnumUtils.getEnumIgnoreCase(UIKey.class, actionKeyModel.getKey());
        Keys keys;
        switch (uiKey) {
            case NULL:
                keys = Keys.NULL;
                break;
            case CANCEL:
                break;
            case HELP:
                break;
            case BACK_SPACE:
                break;
            case TAB:
                break;
            case CLEAR:
                break;
            case RETURN:
                break;
            case ENTER:
                break;
            case SHIFT:
                break;
            case LEFT_SHIFT:
                break;
            case CONTROL:
                break;
            case LEFT_CONTROL:
                break;
            case ALT:
                break;
            case LEFT_ALT:
                break;
            case PAUSE:
                break;
            case ESCAPE:
                break;
            case SPACE:
                break;
            case PAGE_UP:
                break;
            case PAGE_DOWN:
                break;
            case END:
                break;
            case HOME:
                break;
            case LEFT:
                break;
            case ARROW_LEFT:
                break;
            case UP:
                break;
            case ARROW_UP:
                break;
            case RIGHT:
                break;
            case ARROW_RIGHT:
                break;
            case DOWN:
                break;
            case ARROW_DOWN:
                break;
            case INSERT:
                break;
            case DELETE:
                break;
            case SEMICOLON:
                break;
            case EQUALS:
                break;
            case NUMPAD0:
                break;
            case NUMPAD1:
                break;
            case NUMPAD2:
                break;
            case NUMPAD3:
                break;
            case NUMPAD4:
                break;
            case NUMPAD5:
                break;
            case NUMPAD6:
                break;
            case NUMPAD7:
                break;
            case NUMPAD8:
                break;
            case NUMPAD9:
                break;
            case MULTIPLY:
                break;
            case ADD:
                break;
            case SEPARATOR:
                break;
            case SUBTRACT:
                break;
            case DECIMAL:
                break;
            case DIVIDE:
                break;
            case F1:
                break;
            case F2:
                break;
            case F3:
                break;
            case F4:
                break;
            case F5:
                break;
            case F6:
                break;
            case F7:
                break;
            case F8:
                break;
            case F9:
                break;
            case F10:
                break;
            case F11:
                break;
            case F12:
                break;
            case META:
                break;
            case COMMAND:
                break;
            case ZENKAKU_HANKAKU:
                break;
        }
        return keys;
    }
}
