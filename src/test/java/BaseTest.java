import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static aquality.selenium.browser.AqualityServices.getBrowser;

public class BaseTest extends Assert {
    protected static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("configData.json");
    protected static final ISettingsFile TEST_FILE = new JsonSettingsFile("testData.json");

    @BeforeMethod
    protected void beforeMethod() {
        getBrowser().maximize();
    }

    @AfterMethod
    public void afterMethod() {
        getBrowser().quit();
    }
}