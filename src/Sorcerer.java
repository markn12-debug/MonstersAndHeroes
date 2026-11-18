// Used to create a Sorcerer hero
// Sorcerers are favored on dexterity and agility

public class Sorcerer extends Hero {
    public Sorcerer(String name, int mp, int strength, int agility, int dexterity,
                    int gold, int experience) {
        super(name, mp, strength, agility, dexterity, gold, experience);
        applyFavoredBonus();
    }

    protected void applyFavoredBonus() {
        dexterity = (int) (dexterity * (1 + FAVORED_BONUS));
        agility = (int) (agility * (1 + FAVORED_BONUS));
    }
}
