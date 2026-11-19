import java.util.List;

// Used to create a game of Legends of Valor
// Handles choosing the three heroes and the main game loop

public class LegendsOfValor extends Game {
    private static final int TEAM_SIZE = 3;

    private Party party;

    public void play() {
        printInstructions();
        party = chooseHeroes();

        boolean playing = true;
        while (playing) {
            System.out.print("\nEnter a command (H = help, I = info, Q = quit): ");
            String command = input.nextLine().trim().toUpperCase();

            if (command.equals("H")) {
                printInstructions();
            } else if (command.equals("I")) {
                party.displayInfo();
            } else if (command.equals("Q")) {
                playing = false;
            } else {
                System.out.println("That command isn't available yet.");
            }
        }
        System.out.println("Thanks for playing Legends of Valor!");
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
