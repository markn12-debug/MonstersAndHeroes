// Acts as a parant class for BushTile, CaveTile and KoulouTile
// A hero standing on a bonus tile gets a bonus to one of their stats, which gets removed when they leave

public abstract class BonusTile extends LaneTile {
    private static final double BONUS = 0.1; // fixed bonus of 10% for a stat

    // keep track of how much was given to later take away the correct amount when hero leaves
    private int bonusGiven = 0;

    // Can be either "Strength", "Dexterity" or "Agility"
    protected abstract String getBoostedStat();

    // give the bonus when entering
    protected void onHeroEnter(Hero hero) {
        bonusGiven = (int) (hero.getStat(getBoostedStat()) * BONUS);
        hero.boostStat(getBoostedStat(), bonusGiven);
        System.out.println(hero.getName() + " gets " + bonusGiven + " " + getBoostedStat() + ".");
    }

    // take away the bonus when leaving
    protected void onHeroLeave(Hero hero) {
        hero.boostStat(getBoostedStat(), -bonusGiven);
        bonusGiven = 0;
    }
}
