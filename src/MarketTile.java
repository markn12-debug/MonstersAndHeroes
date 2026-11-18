import java.util.Scanner;

// Used to create a space on the map that has a market
// Each market tile has its own market with different items

public class MarketTile extends Tile {
    private Market market = new Market();

    public Market getMarket() {
        return market;
    }
    public boolean isAccessible() {
        return true;
    }

    public char getSymbol() {
        return 'M';
    }

    public boolean onEnter(Party party, Scanner input) {
        System.out.println("You've reached a market! Press M to go inside.");
        return true;
    }
}
