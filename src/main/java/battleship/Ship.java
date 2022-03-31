package battleship;

import java.util.ArrayList;

public class Ship {
    private ShipStatus status;
    private final ArrayList<Cell> cellsShip = new ArrayList<>();
    private TypeShip typeShip;

    public Ship() {
        status = ShipStatus.living;
    }

    public ShipStatus getStatus() {
        return status;
    }

    public void setStatus(ShipStatus status) {
        this.status = status;
    }

    public void addCellShip(Cell cell) {
        cellsShip.add(cell);
    }

    public ArrayList<Cell> getCellsShip() {
        return cellsShip;
    }

    public TypeShip getTypeShip() {
        return typeShip;
    }

    public void setTypeShip(TypeShip typeShip) {
        this.typeShip = typeShip;
    }
}