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
    private HeroActions actions;

    public Battle(Party party, Scanner input) {
        this.party = party;
        this.input = input;
        this.actions = new HeroActions(input);
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
                turnUsed = actions.attack(hero, standingMonsters());
            } else if (choice == 2) {
               turnUsed = actions.castSpell(hero, standingMonsters());
            } else if (choice == 3) {
                turnUsed = actions.drinkPotion(hero);
            } else if (choice == 4) {
                turnUsed = actions.changeEquipment(hero);
            } else if (choice == 5) {
                showInfo();
            } else {
                playerQuit = true;
                return;
            }
        }
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

    // returns list of currently alive monsters, helper for hero actions
    private List<Monster> standingMonsters() {
        List<Monster> standing = new ArrayList<>();
        for (Monster monster: monsters) {
            if (!monster.isFainted()) {
                standing.add(monster);
            }
        }

        return standing;
    }

    private boolean allMonstersFainted() {
        for (Monster monster : monsters) {
            if (!monster.isFainted()) {
                return false;
            }
        }
        return true;
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
