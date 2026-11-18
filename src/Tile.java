import java.util.Scanner;

// Used to represent one space on the world map
// Each type of tile decides if it can be entered, how it is drawn,
// and what happens when the party arrives

public abstract class Tile {
    public abstract boolean isAccessible();

    public boolean onEnter(Party party, Scanner input) {
        return true;
    }

    // a hero can clear obstacles
    // when he does so, the map swaps in a tile returned by remove()
    public boolean isRemovable() {
        return false;
    }

    public Tile remove() {
        return this;
    }

    public abstract char getSymbol();

    public String toString() {
        return String.valueOf(getSymbol());
    }

}
