package services.restclients.action;

import lombok.Data;

@Data
public class ActionOptionModel {
    private String optionType;
    private Long waitDuration;
    private Long order;
}
