package cana.codelessautomation.scheduler.v2.services.token;

import cana.codelessautomation.scheduler.v2.services.config.models.ConfigModel;
import cana.codelessautomation.scheduler.v2.services.token.daos.ConfigTypeDao;
import cana.codelessautomation.scheduler.v2.services.token.dtos.ScopeLevel;

import java.util.List;

public interface TokenService {
    Boolean hasToken(String browserValue);

    String getTokenValue(Long appId, String value, ScopeLevel scopeLevel);

    String replaceToken(Long appId, String value, ScopeLevel scopeLevel);

    String processToken(Long appId, String tokenName, ScopeLevel scopeLevel);

    String getTokenValue(List<ConfigModel> configModels, ConfigTypeDao configType, String tokenName);

    String getTokenName(String value);
}
