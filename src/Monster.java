// Used to create a monster the heroes fight
// Stores the monster's damage, defense, and dodge chance

public abstract class Monster extends Combatant {
    private static final int HP_PER_LEVEL = 100;
    protected static final double FAVORED_BONUS = 0.10;
    private static final double SPELL_EFFECT = 0.10;

    protected int damage;
    protected int defense;
    protected int dodge;

    public Monster(String name, int level, int damage, int defense, int dodge) {
        super(name, level, level * HP_PER_LEVEL);
        this.damage = damage;
        this.defense = defense;
        this.dodge = dodge;
    }

    protected abstract void applyFavoredBonus();

    public int getDamage() { return damage; }
    public int getDefense() { return defense; }

    public double getDodgeChance() {
        return dodge * 0.01;
    }

    // Scaled down from the file value so a single hit doesn't wipe out a hero
    public int getAttackDamage() {
        return (int) (damage * 0.1);
    }

    // Defense absorbs part of each hit. Every hit does at least 1 damage.
    public int receiveAttack(int rawDamage) {
        int actual = Math.max(1, rawDamage - (int) (defense * 0.05));
        takeDamage(actual);
        return actual;
    }

    public void reduceDamage() {
        damage = (int) (damage * (1 - SPELL_EFFECT));
    }

    public void reduceDefense() {
        defense = (int) (defense * (1 - SPELL_EFFECT));
    }

    public void reduceDodge() {
        dodge = (int) (dodge * (1 - SPELL_EFFECT));
    }

    public String toString() {
        return getName() + " (" + getClass().getSimpleName() + ")  Lvl " + getLevel()
                + "  HP " + getHp() + "  Damage " + damage + "  Defense " + defense
                + "  Dodge " + dodge + "%";
    }
}
