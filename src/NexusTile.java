// Represents a nexus space at the top or bottom of the lane.
// Hero's nexus also has a marker attached to it. Entering enemy's nexus tile wins the game
public class NexusTile extends LaneTile {
    private final boolean heroSide; // whether nexus belongs to a hero or monster
    private final Market market;

    // constructor with the argument if it is on hero side or not
    public NexusTile(boolean heroSide) {
        this.heroSide = heroSide;
        this.market = heroSide ? new Market() : null;
    }

    public boolean isHeroSide() {
        return heroSide;
    }

    // only hero's nexus has a market
    public Market getMarket() {
        return market;
    }

    // win if monster's nexus
    public boolean isHeroWinTile() {
        return !heroSide;
    }

    public char getSymbol() {
        return 'N';
    }
}
