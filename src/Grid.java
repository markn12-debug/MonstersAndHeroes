import java.util.ArrayList;
import java.util.List;

// Used to create a square grid of cells of any type
// Shared by the game boards and the Monsters and Heroes world map

public class Grid<T> {
    private final int size;
    private final List<List<T>> cells;

    public Grid(int size) {
        this.size = size;
        this.cells = new ArrayList<>();
        for (int r=0; r < size; r++) {
            List<T> row = new ArrayList<>();
            for (int c=0; c < size; c++) {
                row.add(null);
            }
            cells.add(row);
        }
    }

    public int getSize() {
        return size;
    }

    public T getCell(int row, int col) {
        return cells.get(row).get(col);
    }

    protected final void setCell(int row, int col, T cell) {
        cells.get(row).set(col, cell);
    }

    public boolean inBounds(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    protected String cellToString(int row, int col) {
        return String.valueOf(getCell(row, col));
    }

    public String toString() {
        String border = "+";
        for (int c = 0; c < size; c++) {
            border += "--+";
        }
        border += "\n";
        String result = border;
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                result += "|" + cellToString(r, c) + " ";
            }
            result += "|\n" + border;
        }
        return result;
    }

}
