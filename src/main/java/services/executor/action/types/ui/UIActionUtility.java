package services.executor.action.types.ui;

import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import services.executor.action.types.ui.dtos.ControlIdTypeDto;
import services.executor.action.types.ui.dtos.ControlTypeAndIdDto;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;

@ApplicationScoped
public class UIActionUtility {
    private static List<String> controlTypeIds = new ArrayList<>();

    public static ControlTypeAndIdDto getIdAndValue(String controlKey) {
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

    public static SelenideElement getUIControl(ControlTypeAndIdDto controlTypeAndIdDto) {
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
