// Used as the parent class for everything heroes can buy
// Stores the name, price, required level, and number of uses for each item

public abstract class Item {
    private String name;
    private int price;
    private int level;
    private int maxUses;
    private int usesLeft;

    public Item(String name, int price, int level, int maxUses) {
        this.name = name;
        this.price = price;
        this.level = level;
        this.maxUses = maxUses;
        this.usesLeft = maxUses;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getLevel() { return level; }

    public int getSellPrice() {
        return price / 2;
    }

    public boolean isUsable() {
        return usesLeft > 0;
    }

    public void recordUse() {
        if (usesLeft > 0) {
            usesLeft--;
        }
    }

    public int getRepairCost() {
        return price / 2;
    }

    public void repair() {
        usesLeft = maxUses;
    }

    public abstract void useFromInventory(Hero hero);

    public String toString() {
        return name + "  (Lvl " + level + ", " + price + " gold, " + usesLeft + "/" + maxUses + " uses)";
    }
}
