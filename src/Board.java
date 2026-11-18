import java.util.HashMap;

// Used to create the board used by the turn-based games
// Stores state of each position and makes sure moves are valid
public class Board extends Grid<BoardCell> {
    private HashMap<Integer, int[]> boxes;

    public Board(int size) {
        super(size);
        boxes = new HashMap<>();

        // This fills the board with empty spaces and maps each numbered position
        // on the board to its row and column in the grid
        for(int i=0; i < size; i++) {
            for(int j=0; j < size; j++) {
                setCell(i,j, new BoardCell());
                boxes.put((i*size) + j + 1, new int[]{i, j});
            }
        }
    }

    public void move(Marker marker, String box) {
        int box_num = Integer.parseInt(box);
        int[] indices = boxes.get(box_num);
        getCell(indices[0], indices[1]).setMarker(marker);
    }

    public boolean checkValidMove(String box) {
        try {
            Integer.parseInt(box);
        } catch(NumberFormatException e) {
            return false;
        }
        int box_num = Integer.parseInt(box);
        if(box_num < 1 || box_num > getSize() * getSize()) {
            return false;
        }
        int[] indices = boxes.get(box_num);
        if(!getCell(indices[0], indices[1]).isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean isFull() {
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                if (getCell(i, j).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

}
