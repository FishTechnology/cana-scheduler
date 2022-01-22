package cana.codelessautomation.scheduler.v2.services.system.models;

import lombok.Data;
import services.restclients.commons.ErrorMessageModel;

import java.util.List;

@Data
public class GetSystemConfigsByAppIdModel {
    private List<ErrorMessageModel> errors;
    private List<SystemConfigModel> systemConfigs;
}
