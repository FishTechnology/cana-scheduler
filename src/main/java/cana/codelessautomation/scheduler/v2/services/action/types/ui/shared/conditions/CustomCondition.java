package cana.codelessautomation.scheduler.v2.services.action.types.ui.shared.conditions;

import com.codeborne.selenide.Condition;

public class CustomCondition {

    public static Condition startWith(String expectedValue) {
        return new StartWithCustomCondition(expectedValue);
    }

    public static Condition endWith(String expectedValue) {
        return new EndWithCustomCondition(expectedValue);
    }
}
