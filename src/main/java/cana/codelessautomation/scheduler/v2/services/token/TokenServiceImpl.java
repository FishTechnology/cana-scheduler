package cana.codelessautomation.scheduler.v2.services.token;

import cana.codelessautomation.scheduler.v2.services.config.models.ConfigKeyValueModel;
import cana.codelessautomation.scheduler.v2.services.config.models.ConfigModel;
import cana.codelessautomation.scheduler.v2.services.config.restclient.ConfigServiceRestClient;
import cana.codelessautomation.scheduler.v2.services.token.daos.ConfigTypeDao;
import cana.codelessautomation.scheduler.v2.services.token.dtos.ScopeLevel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class TokenServiceImpl implements TokenService {
    @Inject
    @RestClient
    ConfigServiceRestClient configServiceRestClient;

    @Override
    public Boolean hasToken(String browserValue) {
        return StringUtils.indexOf(browserValue, "{{") >= 0 && StringUtils.indexOf(browserValue, "}}") >= 0;
    }

    @Override
    public ConfigKeyValueModel getToken(Long appId, String value, Long environmentId, ScopeLevel scopeLevel, boolean isApplicationVariable) {
        return processToken(appId, value, environmentId, scopeLevel, isApplicationVariable);
    }

    @Override
    public String replaceToken(Long appId, String value, Long environmentId, ScopeLevel scopeLevel) {
        if (!hasToken(value)) {
            return value;
        }
        var tokenName = getTokenName(value);
        var tokenValue = processToken(appId, tokenName, environmentId, scopeLevel, false).getValue();
        return value.replace("{{" + tokenName + "}}", tokenValue);
    }

    @Override
    public ConfigKeyValueModel processToken(Long appId, String tokenName, Long environmentId, ScopeLevel scopeLevel, boolean isApplicationVariable) {
        ConfigKeyValueModel tokenValue = null;

        var configs = configServiceRestClient.getConfigsByAppId(appId);
        if (CollectionUtils.isEmpty(configs)) {
            return null;
        }
        tokenValue = getToken(configs, ConfigTypeDao.GLOBAL_VARIABLE, tokenName, isApplicationVariable);
        if (scopeLevel == ScopeLevel.GLOBAL_VARIABLE) {
            return tokenValue;
        }

        var environmentTokenValue = getEnvironmentToken(configs, ConfigTypeDao.ENVIRONMENT_VARIABLE, tokenName, isApplicationVariable, environmentId);
        if (!Objects.isNull(environmentTokenValue)) {
            tokenValue = environmentTokenValue;
        }
        if (scopeLevel == ScopeLevel.TEST_PLAN) {
            return tokenValue;
        }

        var testPlanTokenValue = getToken(configs, ConfigTypeDao.TEST_PLAN, tokenName, isApplicationVariable);
        if (!Objects.isNull(testPlanTokenValue)) {
            tokenValue = testPlanTokenValue;
        }
        if (scopeLevel == ScopeLevel.TEST_PLAN) {
            return tokenValue;
        }

        var testCaseTokenValue = getToken(configs, ConfigTypeDao.TEST_CASE, tokenName, isApplicationVariable);
        if (!Objects.isNull(testCaseTokenValue)) {
            tokenValue = testCaseTokenValue;
        }
        if (scopeLevel == ScopeLevel.TEST_CASE) {
            return tokenValue;
        }

        var actionTokenValue = getToken(configs, ConfigTypeDao.ACTION, tokenName, isApplicationVariable);
        if (!Objects.isNull(actionTokenValue)) {
            tokenValue = actionTokenValue;
        }
        return tokenValue;
    }

    @Override
    public ConfigKeyValueModel getEnvironmentToken(List<ConfigModel> configs, ConfigTypeDao configType, String tokenName, boolean isApplicationVariable, Long environmentId) {
        var configModels = configs
                .stream()
                .filter(config -> Objects.equals(config.getId(), String.valueOf(environmentId)) && StringUtils.equalsAnyIgnoreCase(config.getType(), configType.name()))
                .findFirst();

        if (configModels.isEmpty()) {
            return null;
        }

        var configKeyValues = configModels
                .get()
                .getConfigKeyValues()
                .stream()
                .filter(config -> StringUtils.equalsAnyIgnoreCase(config.getKey(), tokenName))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(configKeyValues)) {
            return null;
        }

        return configKeyValues.get(0);
    }

    @Override
    public ConfigKeyValueModel getToken(List<ConfigModel> configModels, ConfigTypeDao configType, String tokenName, boolean isApplicationVariable) {
        ConfigKeyValueModel configKeyValueModel = null;
        var globalConfigs = configModels
                .stream()
                .filter(config -> StringUtils.equalsAnyIgnoreCase(config.getType(), configType.name()))
                .collect(Collectors.toList());

        if (globalConfigs.size() > 0) {
            var globalConfig = globalConfigs.get(0);
            if (Objects.isNull(globalConfig.getConfigKeyValues())) {
                return null;
            }
            var tokenValueConfig = globalConfig
                    .getConfigKeyValues()
                    .stream()
                    .filter(config -> StringUtils.equalsAnyIgnoreCase(config.getKey(), tokenName))
                    .collect(Collectors.toList());
            if (tokenValueConfig.size() > 0) {
                if (isApplicationVariable) {
                    var applicationVariableConfig = tokenValueConfig
                            .stream()
                            .filter(config -> config.getIsApplicationVariable())
                            .collect(Collectors.toList());
                    if (applicationVariableConfig.size() > 0) {
                        configKeyValueModel = applicationVariableConfig.get(0);
                    }

                } else {
                    configKeyValueModel = tokenValueConfig.get(0);
                }
            }
        }

        return configKeyValueModel;
    }

    @Override
    public String getTokenName(String value) {
        var startIndex = StringUtils.indexOf(value, "{{");
        var partialString = StringUtils.substring(value, startIndex + 2);
        var endIndex = StringUtils.indexOf(partialString, "}}");
        return StringUtils.substring(partialString, 0, endIndex);
    }
}
