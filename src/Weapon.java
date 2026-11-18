// Used to create a weapon a hero can equip
// Adds damage to the hero's attacks

public class Weapon extends Item {

    private static final int MAX_USES = 10;

    private int damage;
    private int handsRequired;

    public Weapon(String name, int price, int level, int damage, int handsRequired) {
        super(name, price, level, MAX_USES);
        this.damage = damage;
        this.handsRequired = handsRequired;
    }

    public void useFromInventory(Hero hero) {
        hero.equipWeapon(this);
    }

    public int getDamage() { return damage; }

    public String toString() {
        return super.toString() + "  Damage " + damage + ", " + handsRequired + "-handed";
    }
}
