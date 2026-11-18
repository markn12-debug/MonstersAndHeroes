// Bush space on the map. Increases dexterity of the hero standing on it
public class BushTile extends BonusTile{
    protected String getBoostedStat() {
        return "Dexterity";
    }

    public char getSymbol() {
        return 'B';
    }
}
