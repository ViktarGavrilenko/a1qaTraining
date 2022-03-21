package battleship;

public class Cell {
    public int x;
    public int y;
    public CellOption option;

    Cell () {
        option = CellOption.empty;
    }
}
