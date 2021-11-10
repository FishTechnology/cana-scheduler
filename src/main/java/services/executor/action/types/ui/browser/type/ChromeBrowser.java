package services.executor.action.types.ui.browser.type;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import services.executor.dtos.SchedulerDto;
import services.restclients.schedule.models.ScheduledActionDetailModel;
import services.restclients.schedule.models.ScheduledTestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;

@ApplicationScoped
public class ChromeBrowser implements BrowserTypeAction {

    public BrowserWebDriverContainer chrome ;


    @Override
    public void execute(SchedulerDto schedulerDto,
                        ScheduledTestCaseModel scheduledTestCaseModel,
                        ScheduledActionDetailModel scheduledActionDetailModel) {
        setup();
    }

    private void setup() {
        var chromeOptions = new ChromeOptions();
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        chrome = new BrowserWebDriverContainer()
                .withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL, new File("build"))
                .withCapabilities(desiredCapabilities);

        RemoteWebDriver driver = chrome.getWebDriver();
        WebDriverRunner.setWebDriver(driver);
    }

    public void tearDown() {
        WebDriverRunner.closeWebDriver();
    }
}
