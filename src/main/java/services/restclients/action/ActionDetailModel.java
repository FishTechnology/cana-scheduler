package services.restclients.action;

import lombok.Data;

import java.util.List;

@Data
public class ActionDetailModel {
    private Long id;
    private ActionTypeDao type;
    private String key;
    private String value;
    private String uiActionType;
    private String comments;
    private Long userId;
    private Long testCaseId;
    private Long order;
    private String createdOn;
    private String modifiedOn;
    private String createdBy;
    private String modifiedBy;
    private String browserActionType;
    private String browserValue;
    private Boolean isActive;
    private Boolean isAssertVerification;
    private String conditionType;
    private List<ActionOptionModel> actionOptionModels;
}
