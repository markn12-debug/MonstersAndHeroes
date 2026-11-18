import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Used to run a battle between the party and a group of monsters
// Handles each hero's and monster's turn and rewards the heroes after a win

public class Battle {
    private static final Random RANDOM = new Random();

    private Party party;
    private Scanner input;
    private List<Monster> monsters = new ArrayList<>();
    private boolean playerQuit = false;

    public Battle(Party party, Scanner input) {
        this.party = party;
        this.input = input;
        spawnMonsters();
    }

    private void spawnMonsters() {
        List<Monster> allMonsters = DataLoader.loadMonsters();
        int partyLevel = party.getHighestLevel();

        int level = 0;
        for (Monster monster : allMonsters) {
            if (monster.getLevel() <= partyLevel && monster.getLevel() > level) {
                level = monster.getLevel();
            }
        }

        List<Monster> candidates = new ArrayList<>();
        for (Monster monster : allMonsters) {
            if (monster.getLevel() == level) {
                candidates.add(monster);
            }
        }
        for (int i = 0; i < party.size() && !candidates.isEmpty(); i++) {
            monsters.add(candidates.remove(RANDOM.nextInt(candidates.size())));
        }
    }

    public boolean fight() {
        System.out.println("\n!!!!! MONSTERS ATTACK !!!!!");
        for (Monster monster : monsters) {
            System.out.println("  " + monster);
        }

        while (!party.isDefeated() && !allMonstersFainted()) {
            for (Hero hero : party.getHeroes()) {
                if (hero.isFainted() || allMonstersFainted()) {
                    continue;
                }
                heroTurn(hero);
                if (playerQuit) {
                    return false;
                }
            }
            for (Monster monster : monsters) {
                if (!monster.isFainted() && !party.isDefeated()) {
                    monsterTurn(monster);
                }
            }
            for (Hero hero : party.getHeroes()) {
                hero.regenerate();
            }
        }

        if (party.isDefeated()) {
            System.out.println("\nAll of your heroes have fainted. GAME OVER.");
            return false;
        }
        rewardHeroes();
        return true;
    }

    private void heroTurn(Hero hero) {
        boolean turnUsed = false;
        while (!turnUsed) {
            System.out.println("\n--- " + hero.getName() + "'s turn (HP " + hero.getHp()
                    + ", MP " + hero.getMp() + ") ---");
            System.out.println("1) Attack");
            System.out.println("2) Cast a spell");
            System.out.println("3) Drink a potion");
            System.out.println("4) Change weapon/armor");
            System.out.println("5) Show info (doesn't use your turn)");
            System.out.println("0) Quit the game");
            int choice = InputHelper.readNumber(input, "Choice: ", 0, 5);

            if (choice == 1) {
                turnUsed = attack(hero);
            } else if (choice == 2) {
                turnUsed = castSpell(hero);
            } else if (choice == 3) {
                turnUsed = drinkPotion(hero);
            } else if (choice == 4) {
                turnUsed = changeEquipment(hero);
            } else if (choice == 5) {
                showInfo();
            } else {
                playerQuit = true;
                return;
            }
        }
    }

    private boolean attack(Hero hero) {
        Monster target = chooseTarget();
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

    private boolean castSpell(Hero hero) {
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
        Monster target = chooseTarget();
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

    private boolean changeEquipment(Hero hero) {
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

    private boolean drinkPotion(Hero hero) {
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

    private void monsterTurn(Monster monster) {
        List<Hero> standing = new ArrayList<>();
        for (Hero hero : party.getHeroes()) {
            if (!hero.isFainted()) {
                standing.add(hero);
            }
        }
        Hero target = standing.get(RANDOM.nextInt(standing.size()));

        if (RANDOM.nextDouble() < target.getDodgeChance()) {
            System.out.println(target.getName() + " dodged " + monster.getName() + "'s attack!");
            return;
        }
        int dealt = target.receiveAttack(monster.getAttackDamage());
        System.out.println(monster.getName() + " hit " + target.getName() + " for " + dealt + " damage.");
        if (target.isFainted()) {
            System.out.println(target.getName() + " has fainted!");
        }
    }

    private void rewardHeroes() {
        System.out.println("\n***** VICTORY! *****");
        int monsterLevel = monsters.get(0).getLevel();
        for (Hero hero : party.getHeroes()) {
            if (hero.isFainted()) {
                hero.revive();
            } else {
                int gold = monsterLevel * 100;
                int xp = monsters.size() * 2;
                hero.earnGold(gold);
                System.out.println(hero.getName() + " earned " + gold + " gold and " + xp + " XP.");
                hero.gainExperience(xp);
            }
        }
    }

    private boolean allMonstersFainted() {
        for (Monster monster : monsters) {
            if (!monster.isFainted()) {
                return false;
            }
        }
        return true;
    }

    // Lets the player pick a standing monster
    // Returns null if they cancel
    private Monster chooseTarget() {
        List<Monster> standing = new ArrayList<>();
        for (Monster monster : monsters) {
            if (!monster.isFainted()) {
                standing.add(monster);
            }
        }
        if (standing.size() == 1) {
            return standing.get(0);
        }
        System.out.println("Choose a target:");
        for (int i = 0; i < standing.size(); i++) {
            System.out.println((i + 1) + ") " + standing.get(i));
        }
        int choice = InputHelper.readNumber(input, "Target (0 to cancel): ", 0, standing.size());
        if (choice == 0) {
            return null;
        }
        return standing.get(choice - 1);
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

    private void showInfo() {
        System.out.println("\nHEROES:");
        for (Hero hero : party.getHeroes()) {
            String weapon = "none";
            if (hero.getWeapon() != null) {
                weapon = hero.getWeapon().getName();
            }
            String armor = "none";
            if (hero.getArmor() != null) {
                armor = hero.getArmor().getName();
            }
            System.out.println("  " + hero.getName() + "  Lvl " + hero.getLevel() + "  HP " + hero.getHp()
                    + "  MP " + hero.getMp() + "  Weapon: " + weapon + "  Armor: " + armor);
        }
        System.out.println("MONSTERS:");
        for (Monster monster : monsters) {
            System.out.println("  " + monster);
        }
    }
}
