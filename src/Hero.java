import java.util.ArrayList;
import java.util.List;

// Used to create a hero controlled by the player
// Stores the hero's stats, gold, experience, inventory, and equipped weapon/armor

public abstract class Hero extends Combatant {
    private static final int HP_PER_LEVEL = 100;
    protected static final double FAVORED_BONUS = 0.05;

    private int mp;
    private int maxMp;
    private int gold;
    private int experience;
    private List<Item> inventory = new ArrayList<>();
    private Weapon weapon;
    private Armor armor;

    protected int strength;
    protected int dexterity;
    protected int agility;

    public Hero(String name, int mp, int strength, int agility, int dexterity,
                int gold, int experience) {
        super(name, 1, HP_PER_LEVEL);
        this.mp = mp;
        this.maxMp = mp;
        this.strength = strength;
        this.agility = agility;
        this.dexterity = dexterity;
        this.gold = gold;
        this.experience = experience;
    }

    protected abstract void applyFavoredBonus();

    public void levelUp() {
        increaseLevel();
        setHp(getLevel() * HP_PER_LEVEL);
        maxMp = (int) (maxMp * 1.1);
        mp = maxMp;
        strength = (int) (strength * 1.05);
        dexterity = (int) (dexterity * 1.05);
        agility = (int) (agility * 1.05);
        applyFavoredBonus();
    }

    public void boostStat(String stat, int amount) {
        if (stat.equalsIgnoreCase("Health")) {
            setHp(getHp() + amount);
        } else if (stat.equalsIgnoreCase("Mana")) {
            mp += amount;
        } else if (stat.equalsIgnoreCase("Strength")) {
            strength += amount;
        } else if (stat.equalsIgnoreCase("Dexterity")) {
            dexterity += amount;
        } else if (stat.equalsIgnoreCase("Agility")) {
            agility += amount;
        }
    }

    // return stat amount by request of its name
    public int getStat(String stat) {
        if (stat.equals("Strength")) {
            return strength;
        } else if (stat.equals("Dexterity")) {
            return dexterity;
        } else {
            return agility;
        }
    }

    public boolean spendGold(int amount) {
        if (amount > gold) {
            return false;
        }
        gold -= amount;
        return true;
    }

    public void earnGold(int amount) {
        gold += amount;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
        if (item == weapon) {
            weapon = null;
        }
        if (item == armor) {
            armor = null;
        }
    }

    public void equipWeapon(Weapon newWeapon) {
        weapon = newWeapon;
        System.out.println(getName() + " equipped " + newWeapon.getName() + ".");
    }

    public void equipArmor(Armor newArmor) {
        armor = newArmor;
        System.out.println(getName() + " put on " + newArmor.getName() + ".");
    }

    public Weapon getWeapon() { return weapon; }
    public Armor getArmor() { return armor; }

    public void displayInventory() {
        System.out.println("\n" + getName() + "'s inventory:");

        String weaponName = "none";
        if (weapon != null) {
            weaponName = weapon.getName();
        }
        String armorName = "none";
        if (armor != null) {
            armorName = armor.getName();
        }
        System.out.println("  Weapon: " + weaponName);
        System.out.println("  Armor:  " + armorName);

        if (inventory.isEmpty()) {
            System.out.println("  (no items)");
        }
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println("  " + (i + 1) + ") " + inventory.get(i));
        }
    }

    public int getAttackDamage() {
        int weaponDamage = 0;
        if (weapon != null && weapon.isUsable()) {
            weaponDamage = weapon.getDamage();
            weapon.recordUse();
        }
        return (int) ((strength + weaponDamage) * 0.05);
    }

    public int getSpellDamage(Spell spell) {
        return (int) (spell.getDamage() + (dexterity / 10000.0) * spell.getDamage());
    }

    public boolean hasManaFor(Spell spell) {
        return mp >= spell.getManaCost();
    }

    public void spendMana(int amount) {
        mp -= amount;
    }

    public double getDodgeChance() {
        return agility * 0.0002;
    }

    public int receiveAttack(int rawDamage) {
        int damage = rawDamage;
        if (armor != null && armor.isUsable()) {
            damage -= (int) (armor.getDamageReduction() * 0.02);
            armor.recordUse();
        }
        damage = Math.max(0, damage);
        takeDamage(damage);
        return damage;
    }

    public void regenerate() {
        if (isFainted()) {
            return;
        }
        int maxHp = getLevel() * HP_PER_LEVEL;
        if (getHp() < maxHp) {
            setHp(Math.min(maxHp, (int) (getHp() * 1.1)));
        }
        if (mp < maxMp) {
            mp = Math.min(maxMp, (int) (mp * 1.1));
        }
    }

    // Heroes come back fro mthe dead at their Nexus with restored HP and MP (applicable to Legends of Valor)
    public void restoreFully() {
        setHp(getLevel() * HP_PER_LEVEL);
        mp = maxMp;
    }

    public void revive() {
        setHp(getLevel() * HP_PER_LEVEL / 2);
        mp = maxMp / 2;
        System.out.println(getName() + " has been revived!");
    }

    public void gainExperience(int amount) {
        experience += amount;
        while (experience >= getLevel() * 10) {
            experience -= getLevel() * 10;
            levelUp();
            System.out.println(getName() + " reached level " + getLevel() + "!");
        }
    }

    public int getMp() { return mp; }
    public int getGold() { return gold; }

    public String toString() {
        return getName() + " (" + getClass().getSimpleName() + ")  Lvl " + getLevel()
                + "  HP " + getHp() + "  MP " + mp + "  Gold " + gold + "  XP " + experience
                + "  Str " + strength + "  Dex " + dexterity + "  Agi " + agility;
    }
}
