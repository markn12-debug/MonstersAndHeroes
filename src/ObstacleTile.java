// Represents a space with an obstacle. Cannot be entered until a hero spends a turn to remove the obstacle
public class ObstacleTile extends LaneTile {
    public boolean isAccessible() {
        return false;
    }

    public boolean isRemovable() {
        return true;
    }

    // spawn a new plain tile in place after removal
    public Tile remove() {
        return new PlainTile();
    }

    public char getSymbol() {
        return 'O';
    }
}
