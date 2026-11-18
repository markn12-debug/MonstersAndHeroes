import java.util.Random;
import java.util.Scanner;

// Used to create an open space on the map
// Monsters may attack the party when they step on this tile

public class CommonTile extends Tile {
    private static final Random RANDOM = new Random();
    private static final double BATTLE_CHANCE = 0.3;

    public boolean isAccessible() {
        return true;
    }

    public char getSymbol() {
        return ' ';
    }

    public boolean onEnter(Party party, Scanner input) {
        if (RANDOM.nextDouble() < BATTLE_CHANCE) {
            Battle battle = new Battle(party, input);
            return battle.fight();
        }
        return true;
    }
}
