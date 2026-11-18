// Used as the parent class for heroes and monsters
// Stores the name, level, and HP and handles taking damage and fainting

public abstract class Combatant {

    private String name;
    private int level;
    private int hp;

    public Combatant(String name, int level, int hp) {
        this.name = name;
        this.level = level;
        this.hp = hp;
    }

    public String getName() { return name; }
    public int getLevel() { return level; }
    public int getHp() { return hp; }

    // HP never goes below 0
    public void takeDamage(int amount) {
        hp = Math.max(0, hp - amount);
    }

    public boolean isFainted() {
        return hp <= 0;
    }

    // For subclasses: healing, potions, leveling up
    protected void setHp(int newHp) {
        hp = newHp;
    }

    protected void increaseLevel() {
        level++;
    }
}
