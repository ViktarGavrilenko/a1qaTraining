package battleship;

import java.util.Objects;

public class Cell {
    public int x;
    public int y;
    public CellOption option;

    Cell() {
        option = CellOption.empty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y && option == cell.option;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, option);
    }
}