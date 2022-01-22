package cana.codelessautomation.scheduler.v2.services.action.types.ui.browser.type;

import cana.codelessautomation.scheduler.v2.services.action.models.ActionDetailModel;
import cana.codelessautomation.scheduler.v2.services.scheduler.models.ScheduledTestPlanDto;
import com.codeborne.selenide.WebDriverRunner;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import services.restclients.testcase.TestCaseModel;

import javax.enterprise.context.ApplicationScoped;
import java.net.MalformedURLException;
import java.net.URL;

@ApplicationScoped
public class SafariBrowser extends BaseBrowserActionType implements BrowserTypeAction {
    @Override
    public void execute(ScheduledTestPlanDto schedulerDto, TestCaseModel scheduledTestCaseModel, ActionDetailModel scheduledActionDetailModel) throws Exception {
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
        return BrowserType.SAFARI;
    }
}
