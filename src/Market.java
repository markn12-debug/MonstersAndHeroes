import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Used to create a market where heroes can shop
// Handles buying, selling, and repairing items with each hero's own gold

public class Market {
    private static final Random RANDOM = new Random();
    private static final double STOCK_CHANCE = 0.6;

    private List<Item> stock = new ArrayList<>();

    public Market() {
        for (Item item : DataLoader.loadItems()) {
            if (RANDOM.nextDouble() < STOCK_CHANCE) {
                stock.add(item);
            }
        }
    }

    public void open(Party party, Scanner input) {
        System.out.println("\n===== WELCOME TO THE MARKET =====");
        List<Hero> heroes = party.getHeroes();

        while (true) {
            System.out.println("\nWho is shopping?");
            for (int i = 0; i < heroes.size(); i++) {
                Hero hero = heroes.get(i);
                System.out.println((i + 1) + ") " + hero.getName() + " - " + hero.getGold() + " gold");
            }
            System.out.println("0) Leave the market");

            int choice = InputHelper.readNumber(input, "Choice: ", 0, heroes.size());
            if (choice == 0) {
                System.out.println("You leave the market.");
                return;
            }
            shop(heroes.get(choice - 1), input);
        }
    }

    private void shop(Hero hero, Scanner input) {
        while (true) {
            System.out.println("\n" + hero.getName() + " (Lvl " + hero.getLevel() + ") has "
                    + hero.getGold() + " gold.");
            System.out.println("1) Buy");
            System.out.println("2) Sell");
            System.out.println("3) Repair");
            System.out.println("0) Done");

            int choice = InputHelper.readNumber(input, "Choice: ", 0, 3);
            if (choice == 1) {
                buy(hero, input);
            } else if (choice == 2) {
                sell(hero, input);
            } else if (choice == 3) {
                repair(hero, input);
            } else {
                return;
            }
        }
    }

    private void buy(Hero hero, Scanner input) {
        if (stock.isEmpty()) {
            System.out.println("This market is sold out.");
            return;
        }
        System.out.println("\nItems for sale:");
        printNumbered(stock);
        int choice = InputHelper.readNumber(input, "Item to buy (0 to cancel): ", 0, stock.size());
        if (choice == 0) {
            return;
        }

        Item item = stock.get(choice - 1);
        if (item.getLevel() > hero.getLevel()) {
            System.out.println(hero.getName() + " needs to be level " + item.getLevel() + " to buy that.");
        } else if (!hero.spendGold(item.getPrice())) {
            System.out.println(hero.getName() + " can't afford that.");
        } else {
            stock.remove(item);
            hero.addItem(item);
            System.out.println(hero.getName() + " bought " + item.getName() + ".");
        }
    }

    private void sell(Hero hero, Scanner input) {
        List<Item> inventory = hero.getInventory();
        if (inventory.isEmpty()) {
            System.out.println(hero.getName() + " has nothing to sell.");
            return;
        }
        System.out.println("\n" + hero.getName() + "'s items (sell for half price):");
        printNumbered(inventory);
        int choice = InputHelper.readNumber(input, "Item to sell (0 to cancel): ", 0, inventory.size());
        if (choice == 0) {
            return;
        }

        Item item = inventory.get(choice - 1);
        hero.removeItem(item);
        hero.earnGold(item.getSellPrice());
        stock.add(item);   // the market can sell it again later
        System.out.println(hero.getName() + " sold " + item.getName() + " for "
                + item.getSellPrice() + " gold.");
    }

    private void repair(Hero hero, Scanner input) {
        List<Item> broken = new ArrayList<>();
        for (Item item : hero.getInventory()) {
            if (!item.isUsable()) {
                broken.add(item);
            }
        }
        if (broken.isEmpty()) {
            System.out.println(hero.getName() + " has nothing that needs repairing.");
            return;
        }
        System.out.println("\nBroken items (repair costs half the price):");
        printNumbered(broken);
        int choice = InputHelper.readNumber(input, "Item to repair (0 to cancel): ", 0, broken.size());
        if (choice == 0) {
            return;
        }

        Item item = broken.get(choice - 1);
        if (!hero.spendGold(item.getRepairCost())) {
            System.out.println(hero.getName() + " can't afford the repair.");
        } else {
            item.repair();
            System.out.println(item.getName() + " is as good as new.");
        }
    }

    private void printNumbered(List<Item> items) {
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ") " + items.get(i));
        }
    }
}
