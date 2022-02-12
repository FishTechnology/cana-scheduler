package cana.codelessautomation.scheduler.v2.services.action.types.ui.browser.type;

import cana.codelessautomation.scheduler.v2.commons.CanaSchedulerUtility;
import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.config.dtos.SystemVariableEnum;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import cana.codelessautomation.scheduler.v2.services.token.TokenService;
import cana.codelessautomation.scheduler.v2.services.token.dtos.ScopeLevel;
import com.codeborne.selenide.WebDriverRunner;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Rule;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import services.restclients.testcase.TestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.URL;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.open;

@ApplicationScoped
public class ChromeBrowser extends BaseBrowserActionType implements BrowserTypeAction {

    @Inject
    TokenService tokenService;

    @Rule
    public BrowserWebDriverContainer chrome = new BrowserWebDriverContainer()
            .withCapabilities(new ChromeOptions());


    @Override
    public void execute(ScheduledTestPlanDto schedulerDto,
                        TestCaseModel scheduledTestCaseModel,
                        ActionDetailModel scheduledActionDetailModel) throws Exception {


        if (scheduledActionDetailModel.getIsAssertVerification()) {
            if (StringUtils.equalsAnyIgnoreCase(scheduledActionDetailModel.getBrowserActionType(), BrowserActionTypeDao.URL.name())) {
                assertUtl(schedulerDto.getScheduleDetail().getApplicationId(), scheduledActionDetailModel);

            } else if (StringUtils.equalsAnyIgnoreCase(scheduledActionDetailModel.getBrowserActionType(), BrowserActionTypeDao.TITLE.name())) {
                assertTitle(schedulerDto.getScheduleDetail().getApplicationId(), scheduledActionDetailModel);
            }
        } else {
            if (StringUtils.equalsAnyIgnoreCase(scheduledActionDetailModel.getBrowserActionType(), BrowserActionTypeDao.NAVIGATION.name())) {
                setup(schedulerDto, scheduledActionDetailModel);
            } else if (StringUtils.equalsAnyIgnoreCase(scheduledActionDetailModel.getBrowserActionType(), BrowserActionTypeDao.CLOSE.name())) {
                tearDown();
            }
        }
    }

    @Override
    public BrowserType type() {
        return BrowserType.GOOGLE_CHROME;
    }

    private void setup(ScheduledTestPlanDto schedulerDto, ActionDetailModel scheduledActionDetailModel) throws Exception {
//        BrowserWebDriverContainer<?> chrome = new BrowserWebDriverContainer<>()
//                .withCapabilities(new ChromeOptions())
//                .withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.RECORD_FAILING, new File("./build/"));
//        FirefoxOptions firefoxOptions = new FirefoxOptions();
//        firefoxOptions.setAcceptInsecureCerts(true);
//        firefoxOptions.merge(capabilities);
        RemoteWebDriver driver = null;
        ChromeOptions chromeOptions = new ChromeOptions();
        var acceptUntrustedCertsConfig = tokenService.getToken(
                schedulerDto.getScheduleDetail().getApplicationId(),
                SystemVariableEnum.ACCEPT_UNTRUSTED_CERTS.name(),
                ScopeLevel.ACTION,
                true);
        if (!Objects.isNull(acceptUntrustedCertsConfig) && BooleanUtils.toBoolean(acceptUntrustedCertsConfig.getValue())) {
            chromeOptions.setAcceptInsecureCerts(true);
        }

        if (schedulerDto.getScheduleDetail().getScheduleIteration().getIsRecordVideoEnabled()) {
            chromeOptions.setCapability("se:recordVideo", true);
        }

        try {
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
            WebDriverRunner.setWebDriver(driver);

            if (!StringUtils.isEmpty(scheduledActionDetailModel.getBrowserValue())) {
                var tokenValue = scheduledActionDetailModel.getBrowserValue();
                if (tokenService.hasToken(scheduledActionDetailModel.getBrowserValue())) {
                    tokenValue = tokenService.replaceToken(schedulerDto.getScheduleDetail().getApplicationId(),
                            scheduledActionDetailModel.getBrowserValue(),
                            ScopeLevel.ACTION);
                }
                open(tokenValue);
            }
        } catch (Exception exception) {
            throw new Exception("Browser setup exception=" + CanaSchedulerUtility.getMessage(exception));
        }
    }

    public void tearDown() {
        WebDriverRunner.closeWebDriver();
    }
}
