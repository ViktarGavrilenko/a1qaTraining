package battleship;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import pageobject.MainPage;

import java.util.ArrayList;
import java.util.Random;

public class Battlefield {
    private final Cell[][] field;
    private final MainPage mainPage = new MainPage();
    private Cell nextShot;

    private final Ship woundedShip = new Ship();
    private final ArrayList<Ship> ships = new ArrayList<>();
    private static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");

    private static final byte fieldWidth = Byte.parseByte(TEST_DATA_FILE.getValue("/fieldWidth").toString());
    private static final byte fieldLength = Byte.parseByte(TEST_DATA_FILE.getValue("/fieldLength").toString());


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
        nextShot = null;
        field[cell.x][cell.y].option = cell.option;
        if (cell.option.equals(CellOption.hit) && woundedShip.getStatus().equals(ShipStatus.living)) {
            Logger.getInstance().info("First shot");
            woundedShip.setStatus(ShipStatus.wounded);
            woundedShip.addCellShip(cell);
            woundedShip.setTypeShip(TypeShip.single);
            setNextCell(woundedShip, cell);
            return;
        }
        if (cell.option.equals(CellOption.hit) && woundedShip.getStatus().equals(ShipStatus.wounded)) {
            Logger.getInstance().info("Wounded ship with HIT shot");
            if (woundedShip.getCellsShip().get(0).x == cell.x) {
                woundedShip.setTypeShip(TypeShip.vertical);
            } else {
                woundedShip.setTypeShip(TypeShip.horizontal);
            }
            woundedShip.addCellShip(cell);
            setNextCell(woundedShip, cell);
            return;
        }
        if (cell.option.equals(CellOption.miss) && woundedShip.getStatus().equals(ShipStatus.wounded)) {
            Logger.getInstance().info("Wounded ship with MISS shot");
            Cell lastHitCell = woundedShip.getCellsShip().get(woundedShip.getCellsShip().size() - 1);
            setNextCell(woundedShip, lastHitCell);
        }
    }

    public Cell takeNextShot() {
        if (nextShot == null) {
            return getRandomEmptyCell();
        } else {
            return nextShot;
        }
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

    public Cell takeCellWoundedShip(Cell hitCell) {
        Logger.getInstance().info("takeCellWoundedShip");
        if (takeCellLeftShot(hitCell).equals(hitCell)) {
            if (takeCellRightShot(hitCell).equals(hitCell)) {
                if (takeCellDownShot(hitCell).equals(hitCell)) {
                    if (takeCellUpShot(hitCell).equals(hitCell)) {
                        return updateDataAfterKillShip(woundedShip);
                    } else {
                        return takeCellUpShot(hitCell);
                    }
                } else {
                    return takeCellDownShot(hitCell);
                }
            } else {
                return takeCellRightShot(hitCell);
            }
        } else {
            return takeCellLeftShot(hitCell);
        }
    }

    public Cell takeCellLeftShot(Cell hitCell) {
        Logger.getInstance().info("takeCellLeftShot");
        if (hitCell.x != 0) {
            if (field[hitCell.x - 1][hitCell.y].option.equals(CellOption.empty)) {
                return field[hitCell.x - 1][hitCell.y];
            } else {
                return hitCell;
            }
        } else {
            return hitCell;
        }
    }

    public Cell takeCellRightShot(Cell hitCell) {
        Logger.getInstance().info("takeCellRightShot");
        if (hitCell.x != field.length - 1) {
            if (field[hitCell.x + 1][hitCell.y].option.equals(CellOption.empty)) {
                return field[hitCell.x + 1][hitCell.y];
            } else {
                return hitCell;
            }
        } else {
            return hitCell;
        }
    }

    public Cell takeCellDownShot(Cell hitCell) {
        Logger.getInstance().info("takeCellDownShot");
        if (hitCell.y != field[0].length - 1) {
            if (field[hitCell.x][hitCell.y + 1].option.equals(CellOption.empty)) {
                return field[hitCell.x][hitCell.y + 1];
            } else {
                return hitCell;
            }
        } else {
            return hitCell;
        }
    }

    public Cell takeCellUpShot(Cell hitCell) {
        Logger.getInstance().info("takeCellUpShot");
        if (hitCell.y != 0) {
            if (field[hitCell.x][hitCell.y - 1].option.equals(CellOption.empty)) {
                return field[hitCell.x][hitCell.y - 1];
            } else {
                return hitCell;
            }
        } else {
            return hitCell;
        }
    }

    public Cell takeCellHorizontalShot(Cell hitCell) {
        Logger.getInstance().info("takeCellHorizontalShot");
        if (takeCellLeftShot(hitCell).equals(hitCell)) {
            if (takeCellRightShot(hitCell).equals(hitCell)) {
                if (hitCell.equals(woundedShip.getCellsShip().get(0))) {
                    return updateDataAfterKillShip(woundedShip);
                } else {
                    return takeCellHorizontalShot(woundedShip.getCellsShip().get(0));
                }
            } else {
                return takeCellRightShot(hitCell);
            }
        } else {
            return takeCellLeftShot(hitCell);
        }
    }

    public Cell takeCellVerticalShot(Cell hitCell) {
        Logger.getInstance().info("takeCellVerticalShot");
        if (takeCellDownShot(hitCell).equals(hitCell)) {
            if (takeCellUpShot(hitCell).equals(hitCell)) {
                if (hitCell.equals(woundedShip.getCellsShip().get(0))) {
                    return updateDataAfterKillShip(woundedShip);
                } else {
                    return takeCellVerticalShot(woundedShip.getCellsShip().get(0));
                }
            } else {
                return takeCellUpShot(hitCell);
            }
        } else {
            return takeCellDownShot(hitCell);
        }
    }

    public void setNextCell(Ship ship, Cell cell) {
        if (ship.getTypeShip() == TypeShip.vertical) {
            nextShot = takeCellVerticalShot(cell);
        }
        if (ship.getTypeShip() == TypeShip.horizontal) {
            nextShot = takeCellHorizontalShot(cell);
        }
        if (ship.getTypeShip() == TypeShip.single) {
            nextShot = takeCellWoundedShip(cell);
        }
    }

    public void setMissAroundShip(Ship ship) {
        for (Cell cell : ship.getCellsShip()) {
            setMissCell(cell.x - 1, cell.y - 1);
            setMissCell(cell.x, cell.y - 1);
            setMissCell(cell.x + 1, cell.y - 1);
            setMissCell(cell.x + 1, cell.y);
            setMissCell(cell.x + 1, cell.y + 1);
            setMissCell(cell.x, cell.y + 1);
            setMissCell(cell.x - 1, cell.y + 1);
            setMissCell(cell.x - 1, cell.y);
        }
    }

    public void setMissCell(int x, int y) {
        if (0 <= x && x < 10) {
            if (0 <= y && y < 10) {
                if (field[x][y].option == CellOption.empty) {
                    field[x][y].option = CellOption.miss;
                }
            }
        }
    }

    public Cell updateDataAfterKillShip (Ship woundedShip) {
        ships.add(woundedShip);
        setMissAroundShip(woundedShip);
        woundedShip.setInitialState();
        for (Ship ship:ships) {
            Logger.getInstance().info("Killed ships " + ship.getCellsShip().size() + " cell");
        }
        return getRandomEmptyCell();
    }
}