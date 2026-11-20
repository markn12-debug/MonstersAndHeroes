import java.util.HashMap;
import java.util.Map;
import java.util.Random;

// Legends of Valor world map: 8x8 grid with 3 lanes
// Each lane is 2 tiles wide, and lanes are separated by a wall of inaccessible tiles
// Top row is monsters' nexuses, bottom row - heroes' nexuses
// Map randomly adds different kind of lane tiles, tracks locations of combatants and enforces movement rules
public class ValorMap extends Grid<Tile> {
    // constants for better readability of code
    public static final int SIZE = 8;
    public static final int LANES = 3;
    private static final int LANE_WIDTH = 2;
    private static final int LANE_STRIDE = LANE_WIDTH + 1;  // lane width combined with the wall


    private static final double OBSTACLE_CHANCE = 0.1;
    private static final Random RANDOM = new Random();


    private final Map<Combatant, int[]> positions = new HashMap<>();    // [row, col] of every combatant on the map
    private final Map<Combatant, String> labels = new HashMap<>();  // label of each combatant used when displaying the map

    // keeps track of home lanes for each hero (defines nexus to teleport to)
    private final Map<Hero, Integer> homeLanes = new HashMap<>();

    // constructor that creates an 8x8 grid and fills tiles with terrain
    public ValorMap() {
        super(SIZE);
        do {
            generate();
        } while (!hasEveryTerrain());   // generate a map that has every terrain
    }

    // accessor for positions
    public int[] getPositions(Combatant combatant) {
        int[] pos = positions.get(combatant);
        return (pos == null) ? null : new int[]{pos[0], pos[1]};
    }

    // generate map layout
    private void generate() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (isWallColumn(col)) {
                    setCell(row, col, new InaccessibleTile());
                } else if (row == 0) {
                    setCell(row, col, new NexusTile(false));
                } else if (row == SIZE - 1) {
                    setCell(row, col, new NexusTile(true));
                } else {
                    setCell(row, col, randomLaneTile());
                }
            }
            keepLanesOpen(row);
        }
    }

    private boolean isWallColumn(int col) {
        return (col + 1) % LANE_STRIDE == 0;    // last column to fill lane stride is the wall
    }

    // generate a random lane tile: 10% obstacle, then 20% bush, 20% cave, 20% koulou and 40% plain tiles of the rest
    private Tile randomLaneTile() {
        if (RANDOM.nextDouble() < OBSTACLE_CHANCE) {
            return new ObstacleTile();
        }

        double number = RANDOM.nextDouble();
        if (number < 0.2) {
            return new BushTile();
        } else if (number < 0.4) {
            return new CaveTile();
        } else if (number < 0.6) {
            return new KoulouTile();
        } 

        return new PlainTile();
    }

    // if both columns in a lane are obstacles, remove one of them so monsters can apss through
    private void keepLanesOpen(int row) {
        for (int lane = 0; lane < LANES; lane++) {
            int leftCol = lane * LANE_STRIDE;
            if (getCell(row, leftCol) instanceof ObstacleTile && getCell(row, leftCol+1) instanceof ObstacleTile) {
                setCell(row, leftCol +1, new PlainTile());
            }
        }
    }

    // mehtod that checks if every form of terrain is present on the map
    private boolean hasEveryTerrain() {
        boolean bush = false;
        boolean cave = false;
        boolean koulou = false;
        boolean obstacle = false;


        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Tile tile = getCell(row, col);
                if (tile instanceof BushTile) bush = true;
                if (tile instanceof CaveTile) cave = true;
                if (tile instanceof KoulouTile) koulou = true;
                if (tile instanceof ObstacleTile) obstacle = true;

            }
        }


        return bush && cave && koulou && obstacle;
    }
    
    //
    // Methods for spawning and moving heroes and monsters
    //
    public boolean spawnHero(Hero hero, int lane) {
        homeLanes.put(hero, lane);
        labels.put(hero, "H" + (lane + 1));
        return place(hero, SIZE - 1, lane * LANE_STRIDE);
    }

    public boolean spawnMonster(Monster monster, int lane) {
        labels.put(monster, "M" + (lane + 1));
        return place(monster, 0, lane * LANE_STRIDE + 1);
    }

    // returns whether a combatant can be placed on a given tile
    private boolean place(Combatant combatant, int row, int col) {
        if (!canStand(combatant, row, col)) {
            return false;
        }

        enterTile(combatant, row, col);
        positions.put(combatant, new int[]{row, col});
        return true;
    }


    // chcks if a combatan cna stand on a tile (checks all avaialble rules)
    private boolean canStand(Combatant combatant, int row, int col) {
        if (!inBounds(row, col) || !(getCell(row, col) instanceof LaneTile)) {
            return false;
        }

        LaneTile laneTile = (LaneTile) getCell(row, col);
        return (combatant instanceof Hero) ? laneTile.canHeroEnter() : laneTile.canMonsterEnter();
    }


    // enters tile for combatant and calls appropriate overloaded emthod to apply any potential effects
    private void enterTile(Combatant combatant, int row, int col) {
        LaneTile laneTile = (LaneTile) getCell(row, col);
        if (combatant instanceof Hero) {
            laneTile.enter((Hero)combatant);
        } else {
            laneTile.enter((Monster)combatant);
        }
    }

    // call when monster gets killed
    public void removeMonster(Monster monster) {
        int[] pos = positions.get(monster);

        if (pos != null) {
            leaveTile(monster, pos[0], pos[1]);
        }

        labels.remove(monster);
    }

    // triggered by the recall action of hero
    public boolean recallHero(Hero hero) {
        int[] pos = positions.get(hero);
        int homeCol = homeLanes.get(hero) * LANE_STRIDE;

        if (pos != null && pos[0] == SIZE - 1 && pos[1] == homeCol) {
            return true;
        }

        if (!canStand(hero, SIZE-1, homeCol)) {
            return false;
        }

        if (pos != null) {
            leaveTile(hero, pos[0], pos[1]);
        }

        return place(hero, SIZE -1, homeCol);
    }

    // helper method to trigger effects of leaving the tile
    private void leaveTile(Combatant combatant, int row, int col) {
        LaneTile laneTile = (LaneTile) getCell(row, col);
        if (combatant instanceof Hero) {
            laneTile.leave((Hero) combatant);
        } else {
            laneTile.leave((Monster) combatant);
        }
    }


    public boolean move(Combatant combatant, int rowD, int colD) {
        int[] pos = positions.get(combatant);

        if (pos == null || Math.abs(rowD) + Math.abs(colD) != 1) {
            return false;
        }

        int newRow = pos[0] + rowD;
        int newCol = pos[1] + colD;

        if (!canStand(combatant, newRow, newCol) || skipsEnemy(combatant, pos[0], newRow, newCol)) {
            return false;
        }
        // if passed all checks, apply the move;
        leaveTile(combatant, pos[0], pos[1]);
        enterTile(combatant, newRow, newCol);
        pos[0] = newRow;
        pos[1] = newCol;
        return  true;
    }

    // rules do not allow moving to a tile if it skips past enemy o nthe way to nexus: have to check
    private boolean skipsEnemy(Combatant combatant, int fromRow, int toRow, int col) {
        int forward = (combatant instanceof Hero) ? -1 : 1;
        int leftCol = (col / LANE_STRIDE) * LANE_STRIDE; // left column of the same lane
        
        for (int row = 0; row < SIZE; row++) {
            for (int c = leftCol; col < leftCol + LANE_WIDTH; c++) {
                LaneTile laneTile = (LaneTile) getCell(row, c);
                boolean hasEnemy = (combatant instanceof Hero) ? laneTile.getMonster() != null : laneTile.getHero() != null;

                // if tile has enemy, it is in front of the mover, and move will take the combatant past the enemy, then consider move illegal
                if (hasEnemy && (row - fromRow) * forward >= 0 && (row - toRow) * forward < 0) {
                    return true;
                }
            } 
        }

        return false;
    }

    // called by hero's actio nto remove an obstacle on the map
    public boolean removeObstacle(Hero hero, int rowD, int colD) {
        int[] pos = positions.get(hero);
        // obstacle has to be in an adjacent tile
        if (pos == null || Math.abs(rowD) + Math.abs(colD) != 1) {
            return false;
        }

        int obstacleRow = pos[0] + rowD;
        int obstacleCol = pos[1] + colD;

        if (!inBounds(obstacleRow, obstacleCol) || !getCell(obstacleRow, obstacleCol).isRemovable()) {
            return  false;
        }

        setCell(obstacleRow, obstacleCol, getCell(obstacleRow, obstacleCol).remove());
        return true;
    }


    public Market getMarketForHero(Hero hero) {
        int[] pos = positions.get(hero);

        if (pos != null && getCell(pos[0], pos[1]) instanceof NexusTile) {
            return ((NexusTile)getCell(pos[0], pos[1])).getMarket();
        }

        return null;
    }

    // Methods to check for the win: when a palyer gets into enemy's nexus
    public boolean heroesWon() {
        return inEnemyNexus(true);
    }

    public boolean monstersWon() {
        return inEnemyNexus(false);
    }

    private boolean inEnemyNexus(boolean isHero) {
        int row = isHero ? 0 : SIZE-1;
        for (int col = 0; col < SIZE; col++) {
            Tile tile = getCell(row, col);
            if (tile instanceof LaneTile) {
                LaneTile laneTile = (LaneTile) tile;
                if (isHero ? laneTile.getHero() != null : laneTile.getMonster() != null) {
                    return true;
                }
            }
        }

        return false;
    }

    // toString method that prints the game map
    public String toString() {
        String border = "+";
        for (int col = 0; col < SIZE; col++) {
            border += "-------+";
        }
        border += "\n";

        String result = border;

        for (int row=0; row < SIZE; row++) {
            for (int col=0; col < SIZE; col++) {
                result += "|" + cellToString(row, col);
            }
            result += "\n" + border;
        }

        return result;
    }

    // helepr method for turning cell on a map into string representation
    protected String cellToString(int row, int col) {
        Tile tile = getCell(row, col);
        if (!(tile instanceof LaneTile)) {
            return "   " + tile.getSymbol() + "   ";
        }
        LaneTile laneTile = (LaneTile)tile;

        String hero = " ";
        String monster = " ";

        if (laneTile.getHero() != null) {
            hero = labels.get(laneTile.getHero());
        }
        if (laneTile.getMonster() != null) {
            monster = labels.get(laneTile.getMonster());
        }
        
        return hero + " " + tile.getSymbol() + " " + monster;
    }

}
