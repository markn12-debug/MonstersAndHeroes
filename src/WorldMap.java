import java.util.Random;

// Used to create the Monsters and Heroes world map
// Randomly generates the tiles, tracks where the party is, and handles movement

public class WorldMap extends Grid<Tile> {
    private static final Random RANDOM = new Random();

    private int partyRow = 0;
    private int partyCol = 0;

    public WorldMap(int size) {
        super(size);
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                setCell(r, c, randomTile());
            }
        }
        setCell(0,0, new CommonTile());
    }

    private Tile randomTile() {
        double roll = RANDOM.nextDouble();
        if (roll < 0.2) {
            return new InaccessibleTile();
        } else if (roll < 0.5) {
            return new MarketTile();
        }
        return new CommonTile();
    }

    public boolean moveParty(int rowChange, int colChange) {
        int newRow = partyRow + rowChange;
        int newCol = partyCol + colChange;
        if (!inBounds(newRow, newCol) || !getCell(newRow, newCol).isAccessible()) {
            return false;
        }
        partyRow = newRow;
        partyCol = newCol;
        return true;
    }

    public Tile getPartyTile() {
        return getCell(partyRow, partyCol);
    }

    protected String cellToString(int row, int col) {
        if (row == partyRow && col == partyCol) {
            return "P";
        }
        return super.cellToString(row, col);
    }
}
