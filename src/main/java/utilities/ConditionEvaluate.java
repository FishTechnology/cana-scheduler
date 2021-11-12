package utilities;

import org.apache.commons.lang3.StringUtils;
import services.restclients.schedule.models.ConditionType;

public class ConditionEvaluate {
    public static Boolean evaluate(String source, String express, ConditionType conditionType) {
        switch (conditionType) {
            case EQUAL:
                return StringUtils.equalsAnyIgnoreCase(source, express);
            case CONTAINS:
                return StringUtils.containsAnyIgnoreCase(source, express);
            case END_WITH:
                return StringUtils.endsWithIgnoreCase(source, express);
            case NOT_EQUAL:
                return StringUtils.equalsIgnoreCase(source, express);
            case START_WITH:
                return StringUtils.startsWithIgnoreCase(source, express);
        }
        return false;
    }
}
