// Used to create a blocked space on the map
// The party can never enter this tile

public class InaccessibleTile extends Tile {
    public boolean isAccessible() {
        return false;
    }

    public char getSymbol() {
        return 'X';
    }
}
