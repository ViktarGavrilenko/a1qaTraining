package battleship;

public class Ship {
    private byte lengthShip;
    private ShipStatus status;

    public Ship(byte lengthShip) {
        this.lengthShip = lengthShip;
        status = ShipStatus.living;
    }

    public ShipStatus getStatus() {
        return status;
    }
}