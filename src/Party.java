import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Used to create the party of heroes traveling together
// Stores 1-3 heroes and checks if they have all fainted

public class Party {
    public static final int MAX_SIZE = 3;

    private final List<Hero> heroes = new ArrayList<>();

    public void add(Hero hero) {
        if (heroes.size() >= MAX_SIZE) {
            System.out.println("The party is full.");
            return;
        }
        heroes.add(hero);
    }

    public List<Hero> getHeroes() {
        return Collections.unmodifiableList(heroes);
    }

    public int size() {
        return heroes.size();
    }

    public void displayInfo() {
        System.out.println();
        for (Hero hero : heroes) {
            System.out.println(hero);
        }
    }

    // The highest level of any hero (monsters match this level)
    public int getHighestLevel() {
        int highest = 1;
        for (Hero hero : heroes) {
            highest = Math.max(highest, hero.getLevel());
        }
        return highest;
    }

    // This is true when every hero has fainted
    public boolean isDefeated() {
        for (Hero hero : heroes) {
            if (!hero.isFainted()) {
                return false;
            }
        }
        return true;
    }
}
