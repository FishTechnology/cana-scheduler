package cana.codelessautomation.scheduler.v2.services.token;

import cana.codelessautomation.scheduler.v2.services.config.models.ConfigModel;
import cana.codelessautomation.scheduler.v2.services.config.restclient.ConfigServiceRestClient;
import cana.codelessautomation.scheduler.v2.services.token.daos.ConfigTypeDao;
import cana.codelessautomation.scheduler.v2.services.token.dtos.ScopeLevel;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TokenServiceImpl implements TokenService {
    @Inject
    @RestClient
    ConfigServiceRestClient configServiceRestClient;

    @Override
    public Boolean hasToken(String browserValue) {
        if (!(StringUtils.indexOf(browserValue, "{{") < 0 && StringUtils.indexOf(browserValue, "}}") < 0)) {
            return false;
        }
        return true;
    }

    @Override
    public String replaceToken(Long appId, String value, ScopeLevel scopeLevel) {
        if (!hasToken(value)) {
            return value;
        }
        var tokenName = getTokenName(value);
        var tokenValue = "";

        var configs = configServiceRestClient.getConfigsByAppId(appId);
        tokenValue = getTokenValue(configs, ConfigTypeDao.GLOBAL_VARIABLE, tokenName);
        if (scopeLevel == ScopeLevel.GLOBAL_VARIABLE) {
            return tokenValue;
        }

        var testPlanTokenValue = getTokenValue(configs, ConfigTypeDao.TEST_PLAN, tokenName);
        if (StringUtils.isNotEmpty(testPlanTokenValue)) {
            tokenValue = testPlanTokenValue;
        }
        if (scopeLevel == ScopeLevel.TEST_PLAN) {
            return tokenValue;
        }

        var testCaseTokenValue = getTokenValue(configs, ConfigTypeDao.TEST_CASE, tokenName);
        if (StringUtils.isNotEmpty(testCaseTokenValue)) {
            tokenValue = testCaseTokenValue;
        }
        if (scopeLevel == ScopeLevel.TEST_CASE) {
            return tokenValue;
        }

        var actionTokenValue = getTokenValue(configs, ConfigTypeDao.ACTION, tokenName);
        if (StringUtils.isNotEmpty(actionTokenValue)) {
            tokenValue = actionTokenValue;
        }
        return tokenValue;
    }

    public String getTokenValue(List<ConfigModel> configModels, ConfigTypeDao configType, String tokenName) {
        var tokenValue = "";
        var globalConfigs = configModels.stream().filter(config -> StringUtils.equalsAnyIgnoreCase(config.getType(), configType.name())).collect(Collectors.toList());

        if (globalConfigs.size() > 0) {
            var globalConfig = globalConfigs.get(0);
            var tokenValueConfig = globalConfig.getConfigKeyValues().stream().filter(config -> StringUtils.equalsAnyIgnoreCase(config.getKey(), tokenName)).collect(Collectors.toList());
            if (tokenValueConfig.size() > 0) {
                tokenValue = tokenValueConfig.get(0).getValue();
            }
        }

        return tokenValue;
    }

    public String getTokenName(String value) {
        var startIndex = StringUtils.indexOf(value, "{{");
        var partialString = StringUtils.substring(value, startIndex);
        var endIndex = StringUtils.indexOf(partialString, "}}");
        return StringUtils.substring(partialString, endIndex);
    }
}
