import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObject.MainPage;

import static aquality.selenium.browser.AqualityServices.getBrowser;

public class SmartSteamTest {
    protected static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("configData.json");
    protected static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    protected static final String DEFAULT_URL = CONFIG_FILE.getValue("/mainPage").toString();

    @BeforeMethod
    protected void beforeMethod() {
        getBrowser().goTo(DEFAULT_URL);
        getBrowser().maximize();
        Logger.getInstance().info("Check if the page is loaded " + DEFAULT_URL);
    }

    @Test(description = "Test of accept cookies ")
    public void testCookies() {
        MainPage mainPage = new MainPage();
        mainPage.clickLinkCategories();
        mainPage.clickLinkActions();
    }

    @AfterMethod
    public void afterTest() {
        if (AqualityServices.isBrowserStarted()) {
            getBrowser().quit();
        }
    }
}
