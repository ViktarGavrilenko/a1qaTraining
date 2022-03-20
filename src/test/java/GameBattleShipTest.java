import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import battleship.Battlefield;
import battleship.CellOption;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobject.MainPage;

import java.util.Random;

import static aquality.selenium.browser.AqualityServices.getBrowser;
import static utils.ArithmeticUtils.generateRandomIntUpToMaxWithoutZero;

public class GameBattleShipTest {
    protected static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("config.json");
    protected static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    protected static final String DEFAULT_URL = CONFIG_FILE.getValue("/mainPage").toString();
    protected static final Integer numberTimesRandomly =
            Integer.parseInt(TEST_DATA_FILE.getValue("/numberTimesRandomly").toString());
    protected static final byte fieldWidth = Byte.parseByte(TEST_DATA_FILE.getValue("/fieldWidth").toString());
    protected static final byte fieldLength = Byte.parseByte(TEST_DATA_FILE.getValue("/fieldLength").toString());

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

        for (int i = 0; i < 10; i++) {
            int x = new Random().nextInt(10);
            int y = new Random().nextInt(10);
            Battlefield battlefield = new Battlefield(fieldWidth, fieldLength);

            CellOption resultShot = battlefield.takeShot(x, y);
            battlefield.setCellOption(x, y, resultShot);
            Logger.getInstance().info("CellOption x = " + x + " y = " + y + " is " + resultShot);
        }
    }

    @AfterMethod
    public void afterTest() {
//        getBrowser().quit();
    }
}
