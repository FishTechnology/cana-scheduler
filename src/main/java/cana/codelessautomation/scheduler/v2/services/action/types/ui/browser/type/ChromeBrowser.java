package cana.codelessautomation.scheduler.v2.services.action.types.ui.browser.type;

import com.codeborne.selenide.WebDriverRunner;
import org.apache.commons.lang3.StringUtils;
import org.junit.Rule;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import services.commons.CanaSchedulerUtility;
import services.executor.dtos.SchedulerDto;
import services.restclients.schedule.models.ScheduledActionDetailModel;
import services.restclients.schedule.models.ScheduledTestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import java.net.URL;

import static com.codeborne.selenide.Selenide.open;

@ApplicationScoped
public class ChromeBrowser extends BaseBrowserActionType implements BrowserTypeAction {

    @Rule
    public BrowserWebDriverContainer chrome = new BrowserWebDriverContainer()
            .withCapabilities(new ChromeOptions());


    @Override
    public void execute(SchedulerDto schedulerDto,
                        ScheduledTestCaseModel scheduledTestCaseModel,
                        ScheduledActionDetailModel scheduledActionDetailModel) throws Exception {


        if (scheduledActionDetailModel.getIsAssertVerification()) {
            if (StringUtils.equalsAnyIgnoreCase(scheduledActionDetailModel.getBrowserActionType(), BrowserActionTypeDao.URL.name())) {
                assertUtl(scheduledActionDetailModel);

            } else if (StringUtils.equalsAnyIgnoreCase(scheduledActionDetailModel.getBrowserActionType(), BrowserActionTypeDao.TITLE.name())) {
                assertTitle(scheduledActionDetailModel);
            }
        } else {
            if (StringUtils.equalsAnyIgnoreCase(scheduledActionDetailModel.getBrowserActionType(), BrowserActionTypeDao.OPEN.name())) {
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

    private void setup(SchedulerDto schedulerDto, ScheduledActionDetailModel scheduledActionDetailModel) throws Exception {
//        BrowserWebDriverContainer<?> chrome = new BrowserWebDriverContainer<>()
//                .withCapabilities(new ChromeOptions())
//                .withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.RECORD_FAILING, new File("./build/"));
//        FirefoxOptions firefoxOptions = new FirefoxOptions();
//        firefoxOptions.setAcceptInsecureCerts(true);
//        firefoxOptions.merge(capabilities);
        RemoteWebDriver driver = null;
        ChromeOptions chromeOptions = new ChromeOptions();
        if (schedulerDto.getScheduleDetail().getScheduleIterationModel().getIsRecordVideoEnabled()) {
            chromeOptions.setCapability("se:recordVideo", true);
        }

        try {
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
            WebDriverRunner.setWebDriver(driver);

            if (!StringUtils.isEmpty(scheduledActionDetailModel.getBrowserValue())) {
                open(scheduledActionDetailModel.getBrowserValue());
            }
        } catch (Exception exception) {
            throw new Exception("Browser setup exception=" + CanaSchedulerUtility.getMessage(exception));
        }
    }

    public void tearDown() {
        WebDriverRunner.closeWebDriver();
    }
}
