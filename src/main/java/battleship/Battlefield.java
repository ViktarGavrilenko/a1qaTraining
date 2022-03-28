package battleship;

import aquality.selenium.core.logging.Logger;
import pageobject.MainPage;

import java.util.ArrayList;

public class Battlefield {
    private final Cell[][] field;
    private final MainPage mainPage = new MainPage();
    private Cell nextShot;

    private Ship woundedShip = new Ship();
    private final ArrayList<Ship> shipsKilled = new ArrayList<>();

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

    public int getNumberKilledShips() {
        return shipsKilled.size();
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
        Logger.getInstance().info("setCellOption");
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
            return getBetterCell();
        } else {
            return nextShot;
        }
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
        switch (ship.getTypeShip()) {
            case vertical:
                nextShot = takeCellVerticalShot(cell);
                break;
            case horizontal:
                nextShot = takeCellHorizontalShot(cell);
                break;
            default:
                nextShot = takeCellWoundedShip(cell);
                break;
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

    public Cell updateDataAfterKillShip(Ship woundedShip) {
        shipsKilled.add(woundedShip);
        Logger.getInstance().info("Number killed ships are " + shipsKilled.size());
        for (Cell cell : woundedShip.getCellsShip()) {
            Logger.getInstance().info("woundedShip x = " + cell.x + " y " + cell.y + " option " + cell.option);
        }
        Logger.getInstance().info("Killed woundedShip is from " + woundedShip.getCellsShip().size() + " cell");
        setMissAroundShip(woundedShip);
        this.woundedShip = new Ship();
        for (Ship ship : shipsKilled) {
            Logger.getInstance().info("Killed ship is from " + ship.getCellsShip().size() + " cell");
            for (Cell cell : ship.getCellsShip()) {
                Logger.getInstance().info("x = " + cell.x + " y = " + cell.y + " option " + cell.option);
            }
        }
        return getBetterCell();
    }

    public void getField() {
        for (int i = 0; i < field.length; i++) {
            StringBuilder str = new StringBuilder();
            for (int j = 0; j < field[i].length; j++) {
                switch (field[j][i].option) {
                    case empty:
                        str.append("| ");
                        break;
                    case hit:
                        str.append("|x");
                        break;
                    case miss:
                        str.append("|-");
                        break;
                }
            }
            Logger.getInstance().info(str.append("|").toString());
        }
    }

    public Cell getBetterCell() {
        Cell betterCell = new Cell();
        int numberEmptyCell;
        int numberDiagonalEmptyCell;
        int maxNumberEmptyCell = 0;
        int maxNumberDiagonalEmptyCell = 0;
        for (Cell[] cells : field) {
            for (Cell cell : cells) {
                if (cell.option.equals(CellOption.empty)) {
                    if (betterCell.x == 0 && betterCell.y == 0) {
                        betterCell = cell;
                    }
                    numberEmptyCell = getNumberEmptyCellsAround(cell, getLiveShipWithMaxNumberDecks());
                    Logger.getInstance().info("CELL x=" + cell.x + " y=" + cell.y + " numberEmptyCell= " + numberEmptyCell);
                    if (numberEmptyCell > maxNumberEmptyCell) {
                        betterCell = cell;
                        maxNumberEmptyCell = numberEmptyCell;
                        maxNumberDiagonalEmptyCell = getNumberEmptyDiagonalCell(cell);
                        Logger.getInstance().info("maxNumberDiagonalEmptyCell =" + maxNumberDiagonalEmptyCell);
                    }
                    if (numberEmptyCell == maxNumberEmptyCell) {
                        numberDiagonalEmptyCell = getNumberEmptyDiagonalCell(cell);
                        Logger.getInstance().info("maxNumberDiagonalEmptyCell =" + maxNumberDiagonalEmptyCell);
                        if (numberDiagonalEmptyCell > maxNumberDiagonalEmptyCell) {
                            betterCell = cell;
                            maxNumberEmptyCell = numberEmptyCell;
                            maxNumberDiagonalEmptyCell = numberDiagonalEmptyCell;
                        }
                    }
                }
            }
        }
        Logger.getInstance().info("BetterCell x = " + betterCell.x + " y = " + betterCell.y);
        return betterCell;
    }

    public int getNumberEmptyCellsOnLeft(Cell cell, int maxLength) {
        int count = 0;
        for (int i = 1; i < maxLength; i++) {
            if (cell.x - i >= 0 && field[cell.x - i][cell.y].option.equals(CellOption.empty)) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    public int getNumberEmptyCellsOnRight(Cell cell, int maxLength) {
        int count = 0;
        for (int i = 1; i < maxLength; i++) {
            if (cell.x + i < 10 && field[cell.x + i][cell.y].option.equals(CellOption.empty)) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    public int getNumberEmptyCellsOnDown(Cell cell, int maxLength) {
        int count = 0;
        for (int i = 1; i < maxLength; i++) {
            if (cell.y + i < 10 && field[cell.x][cell.y + i].option.equals(CellOption.empty)) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    public int getNumberEmptyCellsOnUp(Cell cell, int maxLength) {
        int count = 0;
        for (int i = 1; i < maxLength; i++) {
            if (cell.y - i >= 0 && field[cell.x][cell.y - i].option.equals(CellOption.empty)) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    public int getNumberEmptyCellsAround(Cell cell, int maxLength) {
        return getNumberEmptyCellsOnLeft(cell, maxLength) + getNumberEmptyCellsOnRight(cell, maxLength) +
                getNumberEmptyCellsOnDown(cell, maxLength) + getNumberEmptyCellsOnUp(cell, maxLength);
    }

    public int getNumberEmptyDiagonalCell(Cell cell) {
        int count = 0;
        if (cell.x - 1 >= 0 && cell.y - 1 >= 0 && field[cell.x - 1][cell.y - 1].option.equals(CellOption.empty)) {
            count++;
        }
        if (cell.x + 1 < field.length && cell.y - 1 >= 0 && field[cell.x + 1][cell.y - 1].option.equals(CellOption.empty)) {
            count++;
        }
        if (cell.x + 1 < field.length && cell.y + 1 < field[0].length && field[cell.x + 1][cell.y + 1].option.equals(CellOption.empty)) {
            count++;
        }
        if (cell.x - 1 >= 0 && cell.y + 1 < field[0].length && field[cell.x - 1][cell.y + 1].option.equals(CellOption.empty)) {
            count++;
        }
        return count;
    }

    public int getLiveShipWithMaxNumberDecks() {
        int numberFourDeck = 0;
        int numberThreeDeck = 0;

        for (Ship ship : shipsKilled) {
            switch (ship.getCellsShip().size()) {
                case 4:
                    numberFourDeck++;
                    break;
                case 3:
                    numberThreeDeck++;
                    break;
            }
        }

        if (numberFourDeck == 1) {
            if (numberThreeDeck == 2) {
                return 2;
            } else {
                return 3;
            }
        } else {
            return 4;
        }
    }
}