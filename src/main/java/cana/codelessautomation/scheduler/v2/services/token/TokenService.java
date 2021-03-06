package cana.codelessautomation.scheduler.v2.services.token;

import cana.codelessautomation.scheduler.v2.services.config.models.ConfigKeyValueModel;
import cana.codelessautomation.scheduler.v2.services.config.models.ConfigModel;
import cana.codelessautomation.scheduler.v2.services.token.daos.ConfigTypeDao;
import cana.codelessautomation.scheduler.v2.services.token.dtos.ScopeLevel;

import java.util.List;

public interface TokenService {
    Boolean hasToken(String browserValue);

    ConfigKeyValueModel getToken(Long appId, String value, Long environmentId, ScopeLevel scopeLevel, boolean isApplicationVariable);

    String replaceToken(Long appId, String value, Long environmentId, ScopeLevel scopeLevel);

    ConfigKeyValueModel processToken(Long appId, String tokenName, Long environmentId, ScopeLevel scopeLevel, boolean isApplicationVariable);

    ConfigKeyValueModel getEnvironmentToken(List<ConfigModel> configs, ConfigTypeDao environmentVariable, String tokenName, boolean isApplicationVariable, Long environmentId);

    ConfigKeyValueModel getToken(List<ConfigModel> configModels, ConfigTypeDao configType, String tokenName, boolean isApplicationVariable);

    String getTokenName(String value);
}
