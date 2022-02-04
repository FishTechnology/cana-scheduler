package cana.codelessautomation.scheduler.v2.services.action.models;

import lombok.Data;

@Data
public class ActionOptionModel {
    private String optionType;
    private Long waitDuration;
    private Long order;
    private String conditionType;
}
