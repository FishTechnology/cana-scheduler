package cana.codelessautomation.scheduler.v2.services.action.types.ui.utilities;

import cana.codelessautomation.scheduler.v2.services.action.types.ui.dtos.ControlIdTypeDto;
import cana.codelessautomation.scheduler.v2.services.action.types.ui.dtos.ControlTypeAndIdDto;
import cana.codelessautomation.scheduler.v2.services.token.TokenService;
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

    public UIActionUtilityImpl() {
        controlTypeIds.add(ControlIdTypeDto.XPATH.name());
        controlTypeIds.add(ControlIdTypeDto.ID.name());
        controlTypeIds.add(ControlIdTypeDto.CSS.name());
    }

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
        switch (controlTypeAndIdDto.getControlIdType()) {
            case ID:
                return $(By.id(controlTypeAndIdDto.getId()));
            case CSS:
                return $(By.className(controlTypeAndIdDto.getId()));
            case XPATH:
                return $(By.xpath(controlTypeAndIdDto.getId()));
        }
        return null;
    }
}
