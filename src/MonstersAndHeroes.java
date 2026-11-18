import java.util.List;

// Used to create a game of Monsters and Heroes
// Handles choosing the party and the main command loop for moving, info, inventory, and markets

public class MonstersAndHeroes extends Game {
    private static final int WORLD_SIZE = 8;

    private Party party;
    private WorldMap world;

    public void play() {
        printInstructions();

        party = chooseParty();

        world = new WorldMap(WORLD_SIZE);
        showMap();

        boolean playing = true;
        while (playing) {
            System.out.print("\nEnter a command (W/A/S/D to move, I = info, E = inventory, M = market, P = map, H = help, Q = quit): ");
            String command = input.nextLine().trim().toUpperCase();

            if (command.equals("W")) {
                playing = move(-1, 0);
            } else if (command.equals("S")) {
                playing = move(1, 0);
            } else if (command.equals("A")) {
                playing = move(0, -1);
            } else if (command.equals("D")) {
                playing = move(0, 1);
            } else if (command.equals("I")) {
                party.displayInfo();
            } else if (command.equals("E")) {
                manageInventory();
            } else if (command.equals("M")) {
                enterMarket();
            } else if (command.equals("P")) {
                showMap();
            } else if (command.equals("H")) {
                printInstructions();
            } else if (command.equals("Q")) {
                playing = false;
            } else {
                System.out.println("Unknown command. Press H to see the controls.");
            }
        }
        System.out.println("Thanks for playing Monsters and Heroes!");
    }

    private boolean move(int rowChange, int colChange) {
        if (!world.moveParty(rowChange, colChange)) {
            System.out.println("You can't go that way - it's blocked or off the edge of the map.");
            return true;
        }
        showMap();
        return world.getPartyTile().onEnter(party, input);
    }

    private void enterMarket() {
        if (world.getPartyTile() instanceof MarketTile) {
            MarketTile marketTile = (MarketTile) world.getPartyTile();
            marketTile.getMarket().open(party, input);
        } else {
            System.out.println("There is no market here. Look for an M on the map.");
        }
    }

    private void showMap() {
        System.out.println();
        System.out.print(world);
        System.out.println("P = your party   M = market   X = blocked");
    }

    private Party chooseParty() {
        List<Hero> available = DataLoader.loadHeroes();

        int size = InputHelper.readNumber(input,"How many heroes do you want in your party? (1-" + Party.MAX_SIZE + "): ",
                1, Party.MAX_SIZE);

        Party newParty = new Party();
        for (int i = 1; i <= size; i++) {
            System.out.println("\nChoose hero " + i + " of " + size + ":");
            for (int j = 0; j < available.size(); j++) {
                System.out.println((j + 1) + ") " + available.get(j));
            }
            int pick = InputHelper.readNumber(input,"Hero number: ", 1, available.size());
            Hero hero = available.remove(pick - 1);
            newParty.add(hero);
            System.out.println(hero.getName() + " joins the party!");
        }
        return newParty;
    }

    private void manageInventory() {
        List<Hero> heroes = party.getHeroes();
        System.out.println("\nWhose inventory?");
        for (int i = 0; i < heroes.size(); i++) {
            System.out.println((i + 1) + ") " + heroes.get(i).getName());
        }
        System.out.println("0) Back");
        int choice = InputHelper.readNumber(input, "Choice: ", 0, heroes.size());
        if (choice == 0) {
            return;
        }

        Hero hero = heroes.get(choice - 1);
        hero.displayInventory();
        List<Item> items = hero.getInventory();
        if (items.isEmpty()) {
            return;
        }
        int pick = InputHelper.readNumber(input,
                "Item to equip/use (0 to go back): ", 0, items.size());
        if (pick != 0) {
            items.get(pick - 1).useFromInventory(hero);
        }
    }

    private void printInstructions() {
        System.out.println();
        System.out.println("==================== MONSTERS AND HEROES ====================");
        System.out.println("Lead a party of up to 3 heroes across a dangerous world.");
        System.out.println("Buy gear at markets, fight monsters, and level up your heroes.");
        System.out.println();
        System.out.println("CONTROLS (type a letter and press Enter):");
        System.out.println("  W = move up        A = move left");
        System.out.println("  S = move down      D = move right");
        System.out.println("  I = show your heroes' info");
        System.out.println("  E = inventory: equip weapons/armor or drink potions");
        System.out.println("  M = enter the market (only when standing on an M)");
        System.out.println("  P = show the map");
        System.out.println("  H = show these instructions again");
        System.out.println("  Q = quit the game");
        System.out.println();
        System.out.println("MAP:  P = your party   M = market   X = blocked   (blank) = open ground");
        System.out.println();
        System.out.println("BATTLES:");
        System.out.println("  Monsters may attack when you walk on open ground!");
        System.out.println("  On each hero's turn, pick a number to attack, cast a spell,");
        System.out.println("  drink a potion, or change gear. Win to earn gold and XP.");
        System.out.println("  If all your heroes faint, the game is over.");
        System.out.println("=============================================================");
    }
}
