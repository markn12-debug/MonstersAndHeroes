// Cave space on the map. Increases agility of the hero standing on it
public class CaveTile extends BonusTile{
    protected String getBoostedStat() {
        return "Agility";
    }

    public char getSymbol() {
        return 'C';
    }
}