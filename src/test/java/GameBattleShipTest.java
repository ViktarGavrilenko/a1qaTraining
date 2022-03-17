import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobject.MainPage;

import static aquality.selenium.browser.AqualityServices.getBrowser;
import static utils.ArithmeticUtils.generateRandomIntUpToMaxWithoutZero;

public class GameBattleShipTest {
    protected static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("config.json");
    protected static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    protected static final String DEFAULT_URL = CONFIG_FILE.getValue("/mainPage").toString();
    protected static final Integer numberTimesRandomly =
            Integer.parseInt(TEST_DATA_FILE.getValue("/numberTimesRandomly").toString());

    @BeforeMethod
    protected void beforeMethod() {
        getBrowser().goTo(DEFAULT_URL);
        getBrowser().maximize();
    }

    @Test(description = "Test of accept cookies ")
    public void testCookies() {
        MainPage mainPage = new MainPage();
        for (int i = 0; i < generateRandomIntUpToMaxWithoutZero(numberTimesRandomly); i++) {
            mainPage.clickRandomly();
        }
        mainPage.clickPlay();
        while (mainPage.isEmptyCell()) {
            mainPage.clickEmptyCell();
        }
    }

    @AfterMethod
    public void afterTest() {
        getBrowser().quit();
    }
}
