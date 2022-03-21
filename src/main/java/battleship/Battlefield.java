package battleship;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import pageobject.MainPage;

import java.util.Random;

public class Battlefield {
    private Cell[][] field;
    private MainPage mainPage = new MainPage();

    private static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    private static final byte NUMBER_SINGLE_DESK_SHIP = Byte.parseByte(TEST_DATA_FILE.getValue("/numberSingleDeskShip").toString());
    private static final byte NUMBER_TWO_DESK_SHIP = Byte.parseByte(TEST_DATA_FILE.getValue("/numberTwoDeskShip").toString());
    private static final byte NUMBER_THREE_DESK_SHIP = Byte.parseByte(TEST_DATA_FILE.getValue("/numberThreeDeskShip").toString());
    private static final byte NUMBER_FOUR_DESK_SHIP = Byte.parseByte(TEST_DATA_FILE.getValue("/numberFourDeskShip").toString());

    private static final byte SINGLE_DESK = Byte.parseByte(TEST_DATA_FILE.getValue("/singleDesk").toString());
    private static final byte TWO_DESK = Byte.parseByte(TEST_DATA_FILE.getValue("/twoDesk").toString());
    private static final byte THREE_DESK = Byte.parseByte(TEST_DATA_FILE.getValue("/threeDesk").toString());
    private static final byte FOUR_DESK = Byte.parseByte(TEST_DATA_FILE.getValue("/fourDesk").toString());

    private static final byte fieldWidth = Byte.parseByte(TEST_DATA_FILE.getValue("/fieldWidth").toString());
    private static final byte fieldLength = Byte.parseByte(TEST_DATA_FILE.getValue("/fieldLength").toString());

    private final Ship[] singleDeskShip = new Ship[NUMBER_SINGLE_DESK_SHIP];
    private final Ship[] twoDeskShip = new Ship[NUMBER_TWO_DESK_SHIP];
    private final Ship[] threeDeskShip = new Ship[NUMBER_THREE_DESK_SHIP];
    private final Ship[] fourDeskShip = new Ship[NUMBER_FOUR_DESK_SHIP];

    public Battlefield(int x, int y) {
        field = new Cell[10][10];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                field[i][j] = new Cell();
                field[i][j].x = i;
                field[i][j].y = j;
                field[i][j].option = CellOption.empty;
            }
        }

        for (int i = 0; i < singleDeskShip.length; i++) {
            singleDeskShip[i] = new Ship(SINGLE_DESK);
        }
        for (int i = 0; i < twoDeskShip.length; i++) {
            twoDeskShip[i] = new Ship(TWO_DESK);
        }
        for (int i = 0; i < threeDeskShip.length; i++) {
            threeDeskShip[i] = new Ship(THREE_DESK);
        }
        for (int i = 0; i < fourDeskShip.length; i++) {
            fourDeskShip[i] = new Ship(FOUR_DESK);
        }

    }

    private int getNumberKilledShipsSameType(Ship[] shipIn) {
        int numberKilledShips = 0;
        for (Ship ship : shipIn) {
            if (ship.getStatus() == ShipStatus.killed) {
                numberKilledShips++;
            }
        }
        return numberKilledShips;
    }

    public int getNumberKilledShips() {
        return getNumberKilledShipsSameType(singleDeskShip) + getNumberKilledShipsSameType(twoDeskShip) +
                getNumberKilledShipsSameType(threeDeskShip) + getNumberKilledShipsSameType(fourDeskShip);
    }

    public Cell takeShot(Cell cell) {
        if (mainPage.isBattlefieldOfRivalClick()) {
            return mainPage.clickCell(cell);
        } else {
            Logger.getInstance().error("Time is over");
            return null;
        }
    }

    public void setCellOption(Cell cell) {
        field[cell.x][cell.y].option = cell.option;
    }

    public Cell getRandomEmptyCell() {
        int x, y;
        do {
            x = new Random().nextInt(fieldWidth);
            y = new Random().nextInt(fieldLength);
        }
        while (!field[x][y].option.equals(CellOption.empty));
        return field[x][y];
    }
}