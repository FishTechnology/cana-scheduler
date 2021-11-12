package services.executor.action.types.ui.browser.type;

import com.codeborne.selenide.WebDriverRunner;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import services.executor.dtos.SchedulerDto;
import services.restclients.schedule.models.ScheduledActionDetailModel;
import services.restclients.schedule.models.ScheduledTestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import java.net.MalformedURLException;
import java.net.URL;

@ApplicationScoped
public class FirefoxBrowser extends BaseBrowserActionType implements BrowserTypeAction {
    @Override
    public void execute(SchedulerDto schedulerDto, ScheduledTestCaseModel scheduledTestCaseModel, ScheduledActionDetailModel scheduledActionDetailModel) throws Exception {
        if (StringUtils.equalsAnyIgnoreCase(scheduledActionDetailModel.getBrowserActionType(), BrowserActionTypeDao.OPEN.name())) {
            setup();
        } else if (StringUtils.equalsAnyIgnoreCase(scheduledActionDetailModel.getBrowserActionType(), BrowserActionTypeDao.CLOSE.name())) {
            tearDown();
        } else if (StringUtils.equalsAnyIgnoreCase(scheduledActionDetailModel.getBrowserActionType(), BrowserActionTypeDao.URL.name())) {
            assertUtl(scheduledActionDetailModel);

        } else if (StringUtils.equalsAnyIgnoreCase(scheduledActionDetailModel.getBrowserActionType(), BrowserActionTypeDao.TITLE.name())) {
            assertTitle(scheduledActionDetailModel);
        }
    }

    private void tearDown() {
        WebDriverRunner.closeWebDriver();
    }

    private void setup() {
        RemoteWebDriver driver = null;
        try {
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), new FirefoxOptions());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        WebDriverRunner.setWebDriver(driver);
    }

    @Override
    public BrowserType type() {
        return BrowserType.MOZILLA_FIREFOX;
    }
}
