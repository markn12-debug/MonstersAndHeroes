// Used to create a single-use potion
// Raises one or more of a hero's stats when used

public class Potion extends Item {
    private int effectAmount;
    private String[] affectedStats;

    public Potion(String name, int price, int level, int effectAmount, String[] affectedStats) {
        super(name, price, level, 1);
        this.effectAmount = effectAmount;
        this.affectedStats = affectedStats;
    }

    public void useFromInventory(Hero hero) {
        use(hero);
        if (!isUsable()) {
            hero.removeItem(this);   // used up, so it's gone
        }
    }

    public void use(Hero hero) {
        for (String stat : affectedStats) {
            hero.boostStat(stat, effectAmount);
        }
        recordUse();
        System.out.println(hero.getName() + " drank " + getName() + "!");
    }

    public String toString() {
        return super.toString() + "  +" + effectAmount + " " + String.join("/", affectedStats);
    }
}
