// Used to create a player in the games
// Stores player's name and number of wins

public class Player {
    private String name;
    private int wins;
    private Marker marker;

    public Player(String name) {
        this.name = name;
        marker = null;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public Marker getMarker() {
        return marker;
    }

    public String getName() {
        return name;
    }

    public int getWins() {
        return wins;
    }

    public void addWin() {
        wins++;
    }
}
