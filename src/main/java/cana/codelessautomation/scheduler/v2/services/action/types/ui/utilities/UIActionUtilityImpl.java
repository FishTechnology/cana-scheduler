package cana.codelessautomation.scheduler.v2.services.action.types.ui.utilities;

import cana.codelessautomation.scheduler.v2.services.action.types.ui.dtos.ControlIdTypeDto;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.dtos.ControlTypeAndIdDto;
import cana.codelessautomation.scheduler.v2.services.token.TokenService;
import cana.codelessautomation.scheduler.v2.services.token.dtos.ScopeLevel;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;

@ApplicationScoped
public class UIActionUtilityImpl implements UIActionUtility {

    @Inject
    TokenService tokenService;

    private List<String> controlTypeIds = new ArrayList<>();

    @Override
    public ControlTypeAndIdDto getIdAndValue(String controlKey) {
        var idAndValueDto = new ControlTypeAndIdDto();
        idAndValueDto.setControlIdType(ControlIdTypeDto.XPATH);
        for (String idType : controlTypeIds) {
            if (StringUtils.startsWithIgnoreCase(controlKey, idType + ":")) {
                idAndValueDto.setControlIdType(EnumUtils.getEnumIgnoreCase(ControlIdTypeDto.class, idType));
                idAndValueDto.setId(StringUtils.substring(controlKey, (idType + ":").length()));
                return idAndValueDto;
            }
        }
        idAndValueDto.setId(controlKey);
        return idAndValueDto;
    }

    @Override
    public SelenideElement getUIControl(Long applicationId, ControlTypeAndIdDto controlTypeAndIdDto) {
        var tokenValue = controlTypeAndIdDto.getId();
        if (this.tokenService.hasToken(controlTypeAndIdDto.getId())) {
            tokenValue = this.tokenService.replaceToken(applicationId, tokenValue, ScopeLevel.ACTION);
        }
        // var tokenConfig = tokenService.
        switch (controlTypeAndIdDto.getControlIdType()) {
            case ID:
                return $(By.id(tokenValue));
            case CSS:
                return $(By.className(tokenValue));
            case XPATH:
                return $(By.xpath(tokenValue));
        }
        return null;
    }
}
