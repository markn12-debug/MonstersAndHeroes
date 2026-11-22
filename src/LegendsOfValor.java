import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Used to create a game of Legends of Valor
// Handles choosing the three heroes and the main game loop

public class LegendsOfValor extends Game {
    private static final int TEAM_SIZE = 3;
    private static final int[] SPAWN_RATES = {8, 6, 4}; // configured by difficulty chosen
    private static final Random RANDOM = new Random();

    private static final int GOLD_PER_LEVEL = 500;
    private static final int EXP_PER_LEVEL = 2;

    private Party party;
    private ValorMap map;
    private HeroActions actions;
    private List<Monster> monsters = new ArrayList<>();
    private int round = 1;
    private int spawnEvery;

    public void play() {
        printInstructions();
        spawnEvery = chooseDifficulty();
        party = chooseHeroes();
        map = new ValorMap();
        actions = new HeroActions(input);
        spawnHeroes();
        spawnMonsters();

        boolean playing = true;
        while (playing) {
            System.out.println("\n========== ROUND " + round + " ==========");
            System.out.println(map);

            playing = heroesTurn();

            if (playing && map.heroesWon()) {
                System.out.println("A hero reached the monsters' Nexus. You win!");
                playing = false;
            }

            if (playing) {
                monstersTurn();
                if (map.monstersWon()) {
                    System.out.println("A monster reached your Nexus. You lose!");
                    playing = false;
                }
            }

            if (playing) {
                endRound();
            }
        }
        System.out.println("Thanks for playing Legends of Valor!");
    }

    // Puts hero 1 in the top lane, hero 2 in the middle lane, hero 3 in the bottom lane
    private void spawnHeroes() {
        List<Hero> heroes = party.getHeroes();
        for (int lane = 0; lane < heroes.size(); lane++) {
            map.spawnHero(heroes.get(lane), lane);
        }
    }

    // Puts one new monster in each lane's Nexus
    private void spawnMonsters() {
        List<Monster> allMonsters = DataLoader.loadMonsters();
        int heroLevel = party.getHighestLevel();

        for (int lane = 0; lane < ValorMap.LANES; lane++) {
            Monster monster = pickMonster(allMonsters, heroLevel);
            if (monster == null) {
                continue;
            }
            if (map.spawnMonster(monster, lane)) {   // false if a monster is still sitting on the spawn space
                monsters.add(monster);
                allMonsters.remove(monster);         // so the same monster isn't picked twice
                System.out.println(monster.getName() + " appeared in lane " + (lane + 1) + "!");
            }
        }
    }

    // Picks a random monster with the highest level that is not above the heroes' level
    private Monster pickMonster(List<Monster> allMonsters, int heroLevel) {
        int bestLevel = 0;
        for (Monster monster : allMonsters) {
            if (monster.getLevel() <= heroLevel && monster.getLevel() > bestLevel) {
                bestLevel = monster.getLevel();
            }
        }

        List<Monster> choices = new ArrayList<>();
        for (Monster monster : allMonsters) {
            if (monster.getLevel() == bestLevel) {
                choices.add(monster);
            }
        }

        if (choices.isEmpty()) {
            return null;
        }
        return choices.get(RANDOM.nextInt(choices.size()));
    }

    // Every hero takes one action. Returns false if the player quits or a hero wins
    private boolean heroesTurn() {
        List<Hero> heroes = party.getHeroes();
        for (int i = 0; i < heroes.size(); i++) {
            if (heroes.get(i).isFainted()) {
                System.out.println("\n" + heroes.get(i).getName() + " is waiting to respawn and skips this turn.");
                continue;
            }
            boolean keepPlaying = heroTurn(heroes.get(i), i + 1);
            if (!keepPlaying || map.heroesWon()) {
                return keepPlaying;
            }
        }
        return true;
    }

    // Keeps asking until the hero does something that uses up the turn
    // Returns false only if the player quits
    private boolean heroTurn(Hero hero, int number) {
        while (true) {
            System.out.println("\nH" + number + " " + hero.getName() + "'s turn");
            System.out.println("W/A/S/D move | F attack | C spell | E equip/potion | T teleport | R recall | O obstacle | M market | I info | P map | H help | Q quit");
            System.out.print("Enter an action: ");
            String command = input.nextLine().trim().toUpperCase();

            if (command.equals("W")) {
                if (map.move(hero, -1, 0)) { return true; }
                System.out.println("You can't move there.");
            } else if (command.equals("A")) {
                if (map.move(hero, 0, -1)) { return true; }
                System.out.println("You can't move there.");
            } else if (command.equals("S")) {
                if (map.move(hero, 1, 0)) { return true; }
                System.out.println("You can't move there.");
            } else if (command.equals("D")) {
                if (map.move(hero, 0, 1)) { return true; }
                System.out.println("You can't move there.");
            } else if (command.equals("F")) {
                if (actions.attack(hero, map.getMonstersInRange(hero))) {
                    removeDeadMonsters();
                    return true;
                }
            } else if (command.equals("C")) {
                if (actions.castSpell(hero, map.getMonstersInRange(hero))) {
                    removeDeadMonsters();
                    return true;
                }
            } else if (command.equals("E")) {
                if (equipOrDrink(hero)) {
                    return  true;
                }
            } else if (command.equals("T")) {
                if (teleport(hero)) {
                    return  true;
                }
            } else if (command.equals("R")) {
                if (map.recallHero(hero)) { return true; }
                System.out.println("Your Nexus space is taken.");
            } else if (command.equals("O")) {
                if (removeObstacle(hero)) { return true; }
                System.out.println("There's no obstacle there.");
            } else if (command.equals("M")) {
                openMarket(hero);
            } else if (command.equals("I")) {
                party.displayInfo();
            } else if (command.equals("P")) {
                System.out.println(map);
            } else if (command.equals("H")) {
                printInstructions();
            } else if (command.equals("Q")) {
                return false;
            } else {
                System.out.println("Unknown action. Type H for help."); 
            }
        }
    }

    // Asks which direction the obstacle is in, then tries to remove it
    private boolean removeObstacle(Hero hero) {
        System.out.print("Which direction is the obstacle (W/A/S/D)? ");
        String direction = input.nextLine().trim().toUpperCase();

        if (direction.equals("W")) {
            return map.removeObstacle(hero, -1, 0);
        } else if (direction.equals("A")) {
            return map.removeObstacle(hero, 0, -1);
        } else if (direction.equals("S")) {
            return map.removeObstacle(hero, 1, 0);
        } else if (direction.equals("D")) {
            return map.removeObstacle(hero, 0, 1);
        }
        return false;
    }

    // Shopping doesn't use up the hero's turn
    private void openMarket(Hero hero) {
        Market market = map.getMarketForHero(hero);
        if (market == null) {
            System.out.println("You can only shop while standing in your Nexus.");
        } else {
            market.open(party, input);
        }
    }

    // equipping an item or using a potion uses up a turn
    private boolean equipOrDrink(Hero hero) {
        System.out.println("1) Change weapon/armor    2) Drink a potion    0) Cancel");
        int choice = InputHelper.readNumber(input, "Choice: ", 0, 2);
        if (choice == 1) {
            return actions.changeEquipment(hero);
        } else if (choice == 2) {
            return actions.drinkPotion(hero);
        }

        return false;
    }

    // ask which hero to teleport to, and to which spot beside them
    private boolean teleport(Hero hero) {
        List<Hero> options = new ArrayList<>();
        for (Hero other: party.getHeroes()) {
            if (other != hero && !map.getTeleportSpots(hero, other).isEmpty()) {
                options.add(other);
            }
        }

        if (options.isEmpty()) {
            System.out.println("There is no hero in another lane that you can teleport to");
            return false;
        }

        System.out.println("Teleport next to which hero?");
        for (int i=0; i < options.size(); i++) {
            System.out.println((i+1)+ ") " + options.get(i).getName());
        }

        int pick = InputHelper.readNumber(input, "Hero (0 to cancel): ", 0, options.size());
        if (pick == 0) {
            return false;
        }

        Hero target = options.get(pick - 1);
        int targetRow = map.getPositions(target)[0];
        List<int[]> spots = map.getTeleportSpots(hero, target);
        System.out.println("Where?");

        for(int i =0; i < spots.size(); i++) {
            String place = (spots.get(i)[0] == targetRow) ? "beside" : "behind";
            System.out.println((i+1) + ") " + place + " " + target.getName());
        }

        int spot = InputHelper.readNumber(input, "Spot (0 to cancel): ", 0, spots.size());
        if (spot == 0) {
            return false;
        }

        return map.teleportHero(hero, target, spots.get(spot-1)[0], spots.get(spot-1)[1]);
    }

    // take the killed mosnters off the map and gives heroes rewards
    private void removeDeadMonsters() {
        for (Monster monster: new ArrayList<>(monsters)) {
            if (monster.isFainted()) {
                map.removeMonster(monster);
                monsters.remove(monster);
                int gold = monster.getLevel() * GOLD_PER_LEVEL;
                int exp = monster.getLevel() * EXP_PER_LEVEL;
                System.out.println("Every hero earned " + gold + " gold and " + exp + " EXP.");
                for (Hero hero: party.getHeroes()) {
                    hero.earnGold(gold);
                    hero.gainExperience(exp);
                }
            }
        }
    }


    // a monster can either move forward or attack a hero in range
    private void monstersTurn() {
        for (Monster monster: monsters) {
            List<Hero> inRange = map.getHeroesInRange(monster);
            if (!inRange.isEmpty()) {
                monsterAttack(monster, inRange.get(RANDOM.nextInt(inRange.size())));
            } else {
                moveMonster(monster);
            }

        }
    }

    private void monsterAttack(Monster monster, Hero target) {
        if (RANDOM.nextDouble() < target.getDodgeChance()) {
            System.out.println(target.getName() + " dodged " + monster.getName() + "'s attack!");
            return; 
        }

        int dealt = target.receiveAttack(monster.getAttackDamage());
        System.out.println(monster.getName() + " hit " + target.getName() + " for " + dealt + " damage.");
        if (target.isFainted()) {
            System.out.println(target.getName() + " has fainted! They respawn in their Nexus next round.");
            map.removeHero(target);
        }
    }

    // mosnters b ydefault walk forward (S key for hero); if there is an obstacle, they move to the side first
    private void moveMonster(Monster monster) {
        if (!map.move(monster, 1, 0) && !map.move(monster, 0, 1)) {
            map.move(monster, 0, -1);
        }
    }

    // Heroes regain some HP/MP, and new monsters show up every few rounds
    private void endRound() {
        for (Hero hero : party.getHeroes()) {
            if (!hero.isFainted()) {
                hero.regenerate();
            } else if (map.recallHero(hero)) {
                hero.restoreFully();
                System.out.println(hero.getName() + " respawned in their Nexus!");
            }
        }
        round++;
        if (round % spawnEvery == 0) {
            spawnMonsters();
        }
    }

    private int chooseDifficulty() {
        System.out.println("\nChoose difficulty (how often new monsters spawn):");
        System.out.println("1) Easy   - every " + SPAWN_RATES[0] + " rounds");
        System.out.println("2) Medium - every " + SPAWN_RATES[1] + " rounds");   
        System.out.println("3) Hard   - every " + SPAWN_RATES[2] + " rounds");
        int choice = InputHelper.readNumber(input, "Difficulty: ", 1, SPAWN_RATES.length);

        return SPAWN_RATES[choice-1];
    }

    // Lets the player pick exactly three heroes, one for each lane
    private Party chooseHeroes() {
        List<Hero> available = DataLoader.loadHeroes();
        Party team = new Party();
        String[] lanes = {"top", "middle", "bottom"};

        for (int i = 0; i < TEAM_SIZE; i++) {
            System.out.println("\nChoose the hero for the " + lanes[i] + " lane:");
            for (int j = 0; j < available.size(); j++) {
                System.out.println((j + 1) + ") " + available.get(j));
            }
            int pick = InputHelper.readNumber(input, "Hero number: ", 1, available.size());
            Hero hero = available.remove(pick - 1);
            team.add(hero);
            System.out.println(hero.getName() + " will start in the " + lanes[i] + " lane!");
        }
        return team;
    }

    private void printInstructions() {
        System.out.println();
        System.out.println("==================== LEGENDS OF VALOR ====================");
        System.out.println("Lead a team of 3 heroes, one in each lane, to the monsters' Nexus");
        System.out.println("at the top of the board. If any monster reaches your Nexus at the");
        System.out.println("bottom, you lose.");
        System.out.println();
        System.out.println("Each round, every hero takes one action, then every monster");
        System.out.println("either attacks a nearby hero or moves one space down.");
        System.out.println();
        System.out.println("HERO ACTIONS (type a letter and press Enter):");
        System.out.println("  W/A/S/D = move up/left/down/right");
        System.out.println("  F = attack a monster in range");
        System.out.println("  C = cast a spell on a monster in range");
        System.out.println("  E = equip a weapon/armor or drink a potion");
        System.out.println("  T = teleport next to a hero in another lane");
        System.out.println("  R = recall to your starting Nexus space");
        System.out.println("  O = remove an obstacle next to you");
        System.out.println("  M = shop at the market (only in your Nexus, doesn't use a turn)");
        System.out.println("  I = show info    P = show the map    H = help    Q = quit");
        System.out.println();
        System.out.println("MAP:  N = Nexus   I = inaccessible   O = obstacle   P = plain");
        System.out.println("      B = bush (+dexterity)   C = cave (+agility)   K = koulou (+strength)");
        System.out.println("      H1/H2/H3 = your heroes   M1/M2/M3 = monsters");
        System.out.println("==========================================================");
    }

    

    
}
