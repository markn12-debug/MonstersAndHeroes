// Used to create a single-use magic attack
// Costs mana to cast, and each type of spell has its own extra effect

public abstract class Spell extends Item {
    private int damage;
    private int manaCost;

    public Spell(String name, int price, int level, int damage, int manaCost) {
        super(name, price, level, 1);
        this.damage = damage;
        this.manaCost = manaCost;
    }

    public void useFromInventory(Hero hero) {
        System.out.println("Spells can only be cast during a battle.");
    }

    public int getDamage() { return damage; }
    public int getManaCost() { return manaCost; }

    public abstract String getEffectDescription();
    public abstract void applyEffect(Monster target);

    public String toString() {
        return super.toString() + "  Damage " + damage + ", Mana " + manaCost
                + ", " + getEffectDescription();
    }
}
