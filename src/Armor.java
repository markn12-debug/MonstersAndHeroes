// Used to create armor a hero can equip
// Reduces the damage the hero takes from monsters

public class Armor extends Item {
    private static final int MAX_USES = 10;

    private int damageReduction;

    public Armor(String name, int price, int level, int damageReduction) {
        super(name, price, level, MAX_USES);
        this.damageReduction = damageReduction;
    }

    public void useFromInventory(Hero hero) {
        hero.equipArmor(this);
    }

    public int getDamageReduction() { return damageReduction; }

    public String toString() {
        return super.toString() + "  Reduces damage by " + damageReduction;
    }
}
