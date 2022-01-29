package cana.codelessautomation.scheduler.v2.services.token;

import cana.codelessautomation.scheduler.v2.services.token.dtos.ScopeLevel;

public interface TokenService {
    Boolean hasToken(String browserValue);

    String replaceToken(Long appId, String value, ScopeLevel scopeLevel);
}
