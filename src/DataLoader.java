import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Used to read the hero, monster, and item text files in the data folder
// Creates the game objects so every new game starts with the default values

public class DataLoader {
    private static final String DATA_FOLDER = "data/";

    public static List<String[]> readRows(String fileName) {
        List<String[]> rows = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(new File(DATA_FOLDER + fileName))) {
            if (fileScanner.hasNextLine()) {
                fileScanner.nextLine();
            }
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    rows.add(line.split("\\s+"));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find " + DATA_FOLDER + fileName);
        }
        return rows;
    }

    public static List<Hero> loadHeroes() {
        List<Hero> heroes = new ArrayList<>();

        for (String[] row : readRows("Warriors.txt")) {
            heroes.add(new Warrior(cleanName(row[0]), toInt(row[1]), toInt(row[2]),
                    toInt(row[3]), toInt(row[4]), toInt(row[5]), toInt(row[6])));
        }
        for (String[] row : readRows("Sorcerers.txt")) {
            heroes.add(new Sorcerer(cleanName(row[0]), toInt(row[1]), toInt(row[2]),
                    toInt(row[3]), toInt(row[4]), toInt(row[5]), toInt(row[6])));
        }
        for (String[] row : readRows("Paladins.txt")) {
            heroes.add(new Paladin(cleanName(row[0]), toInt(row[1]), toInt(row[2]),
                    toInt(row[3]), toInt(row[4]), toInt(row[5]), toInt(row[6])));
        }
        return heroes;
    }

    private static String cleanName(String name) {
        return name.replace('_', ' ');
    }

    private static int toInt(String value) {
        return Integer.parseInt(value);
    }

    public static List<Item> loadItems() {
        List<Item> items = new ArrayList<>();

        for (String[] row : readRows("Weaponry.txt")) {
            items.add(new Weapon(cleanName(row[0]), toInt(row[1]), toInt(row[2]),
                    toInt(row[3]), toInt(row[4])));
        }
        for (String[] row : readRows("Armory.txt")) {
            items.add(new Armor(cleanName(row[0]), toInt(row[1]), toInt(row[2]), toInt(row[3])));
        }
        for (String[] row : readRows("Potions.txt")) {
            String[] stats = row[row.length - 1].split("/");
            items.add(new Potion(cleanName(row[0]), toInt(row[1]), toInt(row[2]),
                    toInt(row[3]), stats));
        }
        for (String[] row : readRows("FireSpells.txt")) {
            items.add(new FireSpell(cleanName(row[0]), toInt(row[1]), toInt(row[2]),
                    toInt(row[3]), toInt(row[4])));
        }
        for (String[] row : readRows("IceSpells.txt")) {
            items.add(new IceSpell(cleanName(row[0]), toInt(row[1]), toInt(row[2]),
                    toInt(row[3]), toInt(row[4])));
        }
        for (String[] row : readRows("LightningSpells.txt")) {
            items.add(new LightningSpell(cleanName(row[0]), toInt(row[1]), toInt(row[2]),
                    toInt(row[3]), toInt(row[4])));
        }
        return items;
    }

    public static List<Monster> loadMonsters() {
        List<Monster> monsters = new ArrayList<>();

        for (String[] row : readRows("Dragons.txt")) {
            monsters.add(new Dragon(cleanName(row[0]), toInt(row[1]), toInt(row[2]),
                    toInt(row[3]), toInt(row[4])));
        }
        for (String[] row : readRows("Exoskeletons.txt")) {
            monsters.add(new Exoskeleton(cleanName(row[0]), toInt(row[1]), toInt(row[2]),
                    toInt(row[3]), toInt(row[4])));
        }
        for (String[] row : readRows("Spirits.txt")) {
            monsters.add(new Spirit(cleanName(row[0]), toInt(row[1]), toInt(row[2]),
                    toInt(row[3]), toInt(row[4])));
        }
        return monsters;
    }
}
