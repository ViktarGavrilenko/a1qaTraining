import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import seabattle.battleship.Battlefield;
import seabattle.battleship.Cell;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import seabattle.pageobject.BattleshipPage;
import seabattle.pageobject.NotificationBattleship;

import static aquality.selenium.browser.AqualityServices.getBrowser;
import static org.testng.Assert.assertTrue;
import static utils.ArithmeticUtils.generateRandomIntUpToMaxWithoutZero;

public class GameBattleShipTest {
    private static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("config.json");
    private static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    private static final String DEFAULT_URL = CONFIG_FILE.getValue("/battleshipPage").toString();
    private static final Integer numberTimesRandomly =
            Integer.parseInt(TEST_DATA_FILE.getValue("/numberTimesRandomly").toString());
    private static final byte fieldWidth = Byte.parseByte(TEST_DATA_FILE.getValue("/fieldWidth").toString());
    private static final byte fieldLength = Byte.parseByte(TEST_DATA_FILE.getValue("/fieldLength").toString());
    private static final int NUMBER_SHIPS = Integer.parseInt(TEST_DATA_FILE.getValue("/numberShips").toString());

    @BeforeMethod
    protected void beforeMethod() {
        getBrowser().goTo(DEFAULT_URL);
        getBrowser().maximize();
    }

    @Test(description = "Sea battle game test")
    public void testSeaBattle() {
        BattleshipPage battleshipPage = new BattleshipPage();
        for (int i = 0; i < generateRandomIntUpToMaxWithoutZero(numberTimesRandomly); i++) {
            battleshipPage.clickRandomly();
        }
        battleshipPage.clickPlay();
        Battlefield battlefield = new Battlefield(fieldWidth, fieldLength);
        Cell cellShot;

        while (battlefield.getNumberKilledShips() != NUMBER_SHIPS
                && battleshipPage.isStatusGame(NotificationBattleship.MOVE_ON.getTextNotification())) {
            Logger.getInstance().info("----------Start shot----------------");
            cellShot = battlefield.takeNextShot();
            cellShot = battlefield.takeShot(cellShot);
            Logger.getInstance().info("CellOption x = " + cellShot.x + " y = " + cellShot.y + " is " + cellShot.option);
            battlefield.setCellOption(cellShot);
            battlefield.getField();
            Logger.getInstance().info("----------Finish shot----------------");
        }
        assertTrue(battleshipPage.isStatusGame(NotificationBattleship.WIN.getTextNotification()),
                "The game is over because '" + battleshipPage.getLastGameStatus() + "'");
    }

    @AfterMethod
    public void afterTest() {
        getBrowser().quit();
    }
}