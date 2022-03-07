package cana.codelessautomation.scheduler.v2.services.action.types.ui.shared.conditions;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Driver;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;

public class EndWithCustomCondition extends Condition {
    private final String expectedValue;

    public EndWithCustomCondition(String expectedValue) {
        super("value");
        this.expectedValue = expectedValue;
    }

    @Nonnull
    public CheckResult check(Driver driver, WebElement element) {
        String value = this.getValueAttribute(element);
        String actualValue = String.format("%s=\"%s\"", this.getName(), value);
        return new CheckResult(CustomHtml.html.endWith(value, this.expectedValue), actualValue);
    }

    public String toString() {
        return String.format("%s=\"%s\"", this.getName(), this.expectedValue);
    }

    private String getValueAttribute(WebElement element) {
        String attr = element.getAttribute("value");
        return attr == null ? "" : attr;
    }
}
