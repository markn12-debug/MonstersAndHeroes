import java.util.Scanner;

// Used to represent one space on the world map
// Each type of tile decides if it can be entered, how it is drawn,
// and what happens when the party arrives

public abstract class Tile {
    public abstract boolean isAccessible();

    public boolean onEnter(Party party, Scanner input) {
        return true;
    }

    public abstract char getSymbol();

    public String toString() {
        return String.valueOf(getSymbol());
    }

}
