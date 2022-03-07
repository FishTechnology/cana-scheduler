package cana.codelessautomation.scheduler.v2.services.action.types.ui.shared.conditions;

import com.codeborne.selenide.impl.Html;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class CustomHtml extends Html {
    private static final Pattern REGEX_SPACES = Pattern.compile("[\\s\\n\\r ]+");
    public static CustomHtml html = new CustomHtml();

    public CustomHtml() {
    }

    public boolean startWith(String text, String subtext) {
        return StringUtils.startsWithIgnoreCase(this.reduceSpaces(text), this.reduceSpaces(subtext));
    }

    public boolean startWithCaseSensitive(String text, String subtext) {
        return StringUtils.startsWith(this.reduceSpaces(text), this.reduceSpaces(subtext));
    }

    public boolean endWith(String text, String subtext) {
        return StringUtils.endsWith(this.reduceSpaces(text), this.reduceSpaces(subtext));
    }

    public boolean endWithCaseSensitive(String text, String subtext) {
        return StringUtils.endsWithIgnoreCase(this.reduceSpaces(text), this.reduceSpaces(subtext));
    }

    String reduceSpaces(String text) {
        return REGEX_SPACES.matcher(text).replaceAll(" ").trim();
    }
}
