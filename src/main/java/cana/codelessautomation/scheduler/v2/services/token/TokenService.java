package cana.codelessautomation.scheduler.v2.services.token;

import cana.codelessautomation.scheduler.v2.services.config.models.ConfigKeyValueModel;
import cana.codelessautomation.scheduler.v2.services.config.models.ConfigModel;
import cana.codelessautomation.scheduler.v2.services.token.daos.ConfigTypeDao;
import cana.codelessautomation.scheduler.v2.services.token.dtos.ScopeLevel;

import java.util.List;

public interface TokenService {
    Boolean hasToken(String browserValue);

    ConfigKeyValueModel getToken(Long appId, String value, ScopeLevel scopeLevel, boolean isApplicationVariable);

    String replaceToken(Long appId, String value, ScopeLevel scopeLevel);

    ConfigKeyValueModel processToken(Long appId, String tokenName, ScopeLevel scopeLevel, boolean isApplicationVariable);

    ConfigKeyValueModel getToken(List<ConfigModel> configModels, ConfigTypeDao configType, String tokenName, boolean isApplicationVariable);

    String getTokenName(String value);
}
