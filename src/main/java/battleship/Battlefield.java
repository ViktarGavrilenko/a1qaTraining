package battleship;

import aquality.selenium.core.logging.Logger;
import pageobject.BattleshipPage;

import java.util.ArrayList;
import java.util.Objects;

public class Battlefield {
    private final Cell[][] field;
    private final BattleshipPage battleshipPage = new BattleshipPage();
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
        if (battleshipPage.isBattlefieldOfRivalClick()) {
            return battleshipPage.clickCell(cell);
        } else {
            Logger.getInstance().error("Time is over");
            return null;
        }
    }

    public void setCellOption(Cell cell) {
        Logger.getInstance().info("setCellOption x=" + cell.x + " y=" + cell.y);
        nextShot = null;
        field[cell.x][cell.y].option = cell.option;
        switch (cell.option) {
            case hit:
                switch (woundedShip.getStatus()) {
                    case wounded:
                        Logger.getInstance().info("Wounded ship with HIT shot");
                        if (woundedShip.getCellsShip().get(0).x == cell.x) {
                            woundedShip.setTypeShip(TypeShip.vertical);
                        } else {
                            woundedShip.setTypeShip(TypeShip.horizontal);
                        }
                        break;
                    case living:
                        Logger.getInstance().info("First shot");
                        woundedShip.setStatus(ShipStatus.wounded);
                        woundedShip.setTypeShip(TypeShip.single);
                        break;
                }
                woundedShip.addCellShip(cell);
                nextShot = calculateNextBestCellWoundedShip(cell);
                break;
            case miss:
                if (woundedShip.getStatus().equals(ShipStatus.wounded)) {
                    Logger.getInstance().info("Wounded ship with MISS shot");
                    Cell lastHitCell = woundedShip.getCellsShip().get(woundedShip.getCellsShip().size() - 1);
                    nextShot = calculateNextBestCellWoundedShip(lastHitCell);
                }
                break;
            case empty:
                nextShot = cell;
                break;
        }
    }

    public Cell takeNextShot() {
        return Objects.requireNonNullElseGet(nextShot, this::getBetterCell);
    }

    private void setMissAroundShip(Ship ship) {
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

    private void setMissCell(int x, int y) {
        if (0 <= x && x < 10 && 0 <= y && y < 10 && field[x][y].option == CellOption.empty) {
            field[x][y].option = CellOption.miss;
        }
    }

    private Cell updateDataAfterKillShip(Ship woundedShip) {
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

    private Cell getBetterCell() {
        Cell betterCell = new Cell();
        int numberEmptyCell;
        int numberDiagonalEmptyCell;
        int maxNumberEmptyCell = -1;
        int maxNumberDiagonalEmptyCell = 0;
        for (Cell[] cells : field) {
            for (Cell cell : cells) {
                if (cell.option.equals(CellOption.empty)) {
                    numberEmptyCell = getNumberEmptyCellsAround(cell, getLiveShipWithMaxNumberDecks());
                    if (numberEmptyCell > maxNumberEmptyCell) {
                        betterCell = cell;
                        maxNumberEmptyCell = numberEmptyCell;
                        maxNumberDiagonalEmptyCell = getNumberEmptyDiagonalCell(cell);
                    }
                    if (numberEmptyCell == maxNumberEmptyCell) {
                        numberDiagonalEmptyCell = getNumberEmptyDiagonalCell(cell);
                        if (numberDiagonalEmptyCell > maxNumberDiagonalEmptyCell) {
                            betterCell = cell;
                            maxNumberDiagonalEmptyCell = numberDiagonalEmptyCell;
                        }
                    }
                }
            }
        }
        Logger.getInstance().info("BetterCell x = " + betterCell.x + " y = " + betterCell.y);
        return betterCell;
    }

    private int getNumberEmptyCellsOnLeft(Cell cell, int maxLength) {
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

    private int getNumberEmptyCellsOnRight(Cell cell, int maxLength) {
        int count = 0;
        for (int i = 1; i < maxLength; i++) {
            if (cell.x + i < field.length && field[cell.x + i][cell.y].option.equals(CellOption.empty)) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    private int getNumberEmptyCellsOnDown(Cell cell, int maxLength) {
        int count = 0;
        for (int i = 1; i < maxLength; i++) {
            if (cell.y + i < field[0].length && field[cell.x][cell.y + i].option.equals(CellOption.empty)) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    private int getNumberEmptyCellsOnUp(Cell cell, int maxLength) {
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

    private int getNumberEmptyCellsAround(Cell cell, int maxLength) {
        return getNumberEmptyCellsOnLeft(cell, maxLength) + getNumberEmptyCellsOnRight(cell, maxLength) +
                getNumberEmptyCellsOnDown(cell, maxLength) + getNumberEmptyCellsOnUp(cell, maxLength);
    }

    private int getNumberEmptyDiagonalCell(Cell cell) {
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

    private int getLiveShipWithMaxNumberDecks() {
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

    private Cell calculateNextBestCellWoundedShip(Cell hitCell) {
        Cell bestCell = null;
        int numberEmptyCellAround;
        int maxNumberEmptyCellAround = -1;

        if (hitCell.y > 0 && !woundedShip.getTypeShip().equals(TypeShip.horizontal)) {
            Cell upCell = field[hitCell.x][hitCell.y - 1];
            if (upCell.option.equals(CellOption.empty)) {
                numberEmptyCellAround = numberEmptyCellAroundNextShot(upCell, hitCell);
                if (numberEmptyCellAround > maxNumberEmptyCellAround) {
                    maxNumberEmptyCellAround = numberEmptyCellAround;
                    bestCell = upCell;
                }
            }
        }
        if (hitCell.y < field[0].length - 1 && !woundedShip.getTypeShip().equals(TypeShip.horizontal)) {
            Cell downCell = field[hitCell.x][hitCell.y + 1];
            if (downCell.option.equals(CellOption.empty)) {
                numberEmptyCellAround = numberEmptyCellAroundNextShot(downCell, hitCell);
                if (numberEmptyCellAround > maxNumberEmptyCellAround) {
                    maxNumberEmptyCellAround = numberEmptyCellAround;
                    bestCell = downCell;
                }
            }
        }
        if (hitCell.x > 0 && !woundedShip.getTypeShip().equals(TypeShip.vertical)) {
            Cell leftCell = field[hitCell.x - 1][hitCell.y];
            if (leftCell.option.equals(CellOption.empty)) {
                numberEmptyCellAround = numberEmptyCellAroundNextShot(leftCell, hitCell);
                if (numberEmptyCellAround > maxNumberEmptyCellAround) {
                    maxNumberEmptyCellAround = numberEmptyCellAround;
                    bestCell = leftCell;
                }
            }
        }
        if (hitCell.x < field.length - 1 && !woundedShip.getTypeShip().equals(TypeShip.vertical)) {
            Cell rightCell = field[hitCell.x + 1][hitCell.y];
            if (rightCell.option.equals(CellOption.empty)) {
                numberEmptyCellAround = numberEmptyCellAroundNextShot(rightCell, hitCell);
                if (numberEmptyCellAround > maxNumberEmptyCellAround) {
                    bestCell = rightCell;
                }
            }
        }
        if (bestCell == null) {
            if (hitCell.equals(woundedShip.getCellsShip().get(0))) {
                return updateDataAfterKillShip(woundedShip);
            } else {
                return calculateNextBestCellWoundedShip(woundedShip.getCellsShip().get(0));
            }
        }
        return bestCell;
    }

    private int numberEmptyCellAroundNextShot(Cell nextCell, Cell hitCell) {
        int x = nextCell.x - hitCell.x;
        int y = nextCell.y - hitCell.y;
        int numberEmpty = 0;

        if (x + y > 0) {
            for (int i = nextCell.x - 1 + x; i <= nextCell.x + 1; i++) {
                for (int j = nextCell.y - 1 + y; j <= nextCell.y + 1; j++) {
                    if (i >= 0 && j >= 0 && i < field[0].length && j < field[0].length && field[i][j].option.equals(CellOption.empty)) {
                        numberEmpty++;
                    }
                }
            }
        } else {
            for (int i = nextCell.x - 1; i <= nextCell.x + 1 + x; i++) {
                for (int j = nextCell.y - 1; j <= nextCell.y + 1 + y; j++) {
                    if (i >= 0 && j >= 0 && i < field[0].length && j < field[0].length && field[i][j].option.equals(CellOption.empty)) {
                        numberEmpty++;
                    }
                }
            }
        }
        return numberEmpty;
    }
}