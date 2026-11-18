// Used to create an object representing a player's marker
// A board cell can contain a marker object
public class Marker {
    private char symbol;

    public Marker(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
