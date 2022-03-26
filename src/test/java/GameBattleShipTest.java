import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import battleship.Battlefield;
import battleship.Cell;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobject.MainPage;

import static aquality.selenium.browser.AqualityServices.getBrowser;
import static utils.ArithmeticUtils.generateRandomIntUpToMaxWithoutZero;

public class GameBattleShipTest {
    private static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("config.json");
    private static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    private static final String DEFAULT_URL = CONFIG_FILE.getValue("/mainPage").toString();
    private static final Integer numberTimesRandomly =
            Integer.parseInt(TEST_DATA_FILE.getValue("/numberTimesRandomly").toString());
    private static final byte fieldWidth = Byte.parseByte(TEST_DATA_FILE.getValue("/fieldWidth").toString());
    private static final byte fieldLength = Byte.parseByte(TEST_DATA_FILE.getValue("/fieldLength").toString());


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
        Battlefield battlefield = new Battlefield(fieldWidth, fieldLength);
        Cell cellShot;
        for (int i = 0; i < 100; i++) {
            cellShot = battlefield.takeNextShot();
            cellShot = battlefield.takeShot(cellShot);
            Logger.getInstance().info("CellOption x = " + cellShot.x + " y = " + cellShot.y + " is " + cellShot.option);
            battlefield.setCellOption(cellShot);

        }
    }

    @AfterMethod
    public void afterTest() {
//        getBrowser().quit();
    }
}
