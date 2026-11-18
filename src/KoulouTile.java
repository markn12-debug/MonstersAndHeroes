// Koulou space on the map. Increases strength of the hero standing on it
public class KoulouTile extends BonusTile{
    protected String getBoostedStat() {
        return "Strength";
    }

    public char getSymbol() {
        return 'K';
    }
}