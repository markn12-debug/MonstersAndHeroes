import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Class that is used to carry out actions heroes can take during their turns:
// attack, cast a spell, change equipment or use a potion. The caller decides mosnters to targt.
// Each action returns true if it uses up a hero's turn and false otherwise
public class HeroActions {
    private static final Random RANDOM = new Random();

    private final Scanner input;

    public HeroActions(Scanner input) {
        this.input = input;
    }

    public boolean attack(Hero hero, List<Monster> targets) {
        Monster target = chooseTarget(targets);
        if (target == null) {
            return false;
        }
        if (RANDOM.nextDouble() < target.getDodgeChance()) {
            System.out.println(target.getName() + " dodged " + hero.getName() + "'s attack!");
            return true;
        }
        int dealt = target.receiveAttack(hero.getAttackDamage());
        System.out.println(hero.getName() + " hit " + target.getName() + " for " + dealt + " damage.");
        reportIfFainted(target);
        return true;
    }

    public  boolean castSpell(Hero hero, List<Monster> targets) {
        if (targets.isEmpty()) {
            System.out.println("There is no monster in range.");
            return false;
        }
        List<Item> spells = new ArrayList<>();
        for (Item item : hero.getInventory()) {
            if (item instanceof Spell) {
                spells.add(item);
            }
        }
        if (spells.isEmpty()) {
            System.out.println(hero.getName() + " has no spells.");
            return false;
        }

        Spell spell = (Spell) pickItem(spells, "Spell to cast");
        if (spell == null) {
            return false;
        }
        if (!hero.hasManaFor(spell)) {
            System.out.println("Not enough mana! " + spell.getName() + " needs "
                    + spell.getManaCost() + " MP.");
            return false;
        }
        Monster target = chooseTarget(targets);
        if (target == null) {
            return false;
        }

        hero.spendMana(spell.getManaCost());
        spell.recordUse();
        if (!spell.isUsable()) {
            hero.removeItem(spell);
        }

        if (RANDOM.nextDouble() < target.getDodgeChance()) {
            System.out.println(target.getName() + " dodged " + spell.getName() + "!");
            return true;
        }
        int dealt = target.receiveAttack(hero.getSpellDamage(spell));
        spell.applyEffect(target);
        System.out.println(hero.getName() + " cast " + spell.getName() + " on " + target.getName()
                + " for " + dealt + " damage. It " + spell.getEffectDescription() + "!");
        reportIfFainted(target);
        return true;
    }

    public boolean changeEquipment(Hero hero) {
        List<Item> gear = new ArrayList<>();
        for (Item item : hero.getInventory()) {
            if (item instanceof Weapon || item instanceof Armor) {
                gear.add(item);
            }
        }
        if (gear.isEmpty()) {
            System.out.println(hero.getName() + " has no weapons or armor.");
            return false;
        }
        Item item = pickItem(gear, "Item to equip");
        if (item == null) {
            return false;
        }
        item.useFromInventory(hero);
        return true;
    }

    public boolean drinkPotion(Hero hero) {
        List<Item> potions = new ArrayList<>();
        for (Item item : hero.getInventory()) {
            if (item instanceof Potion) {
                potions.add(item);
            }
        }
        if (potions.isEmpty()) {
            System.out.println(hero.getName() + " has no potions.");
            return false;
        }
        Item potion = pickItem(potions, "Potion to drink");
        if (potion == null) {
            return false;
        }
        potion.useFromInventory(hero);
        return true;
    }

    // Lets the player pick a standing monster
    // Returns null if they cancel
    private Monster chooseTarget(List<Monster> targets) {
        if (targets.isEmpty()) {
            System.out.println("There is no monster in range.");
            return null;
        }
        if (targets.size() == 1) {
            return targets.get(0);
        }
        System.out.println("Choose a target:");
        for (int i = 0; i < targets.size(); i++) {
            System.out.println((i + 1) + ") " + targets.get(i));
        }
        int choice = InputHelper.readNumber(input, "Target (0 to cancel): ", 0, targets.size());
        if (choice == 0) {
            return null;
        }
        return targets.get(choice - 1);
    }

    // Shows a numbered list and returns the chosen item, or null if cancelled.
    private Item pickItem(List<Item> items, String prompt) {
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ") " + items.get(i));
        }
        int choice = InputHelper.readNumber(input, prompt + " (0 to cancel): ", 0, items.size());
        if (choice == 0) {
            return null;
        }
        return items.get(choice - 1);
    }

    private void reportIfFainted(Monster monster) {
        if (monster.isFainted()) {
            System.out.println(monster.getName() + " has been defeated!");
        }
    }
}
