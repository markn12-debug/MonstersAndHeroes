// Used to create a Warrior hero
// Warriors are favored on strength and agility

public class Warrior extends Hero {
    public Warrior(String name, int mp, int strength, int agility, int dexterity,
                   int gold, int experience) {
        super(name, mp, strength, agility, dexterity, gold, experience);
        applyFavoredBonus();
    }

    protected void applyFavoredBonus() {
        strength = (int) (strength * (1 + FAVORED_BONUS));
        agility = (int) (agility * (1 + FAVORED_BONUS));
    }
}
