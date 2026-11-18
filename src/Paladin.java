// Used to create a Paladin hero
// Paladins are favored on strength and dexterity

public class Paladin extends Hero {
    public Paladin(String name, int mp, int strength, int agility, int dexterity,
                   int gold, int experience) {
        super(name, mp, strength, agility, dexterity, gold, experience);
        applyFavoredBonus();
    }

    protected void applyFavoredBonus() {
        strength = (int) (strength * (1 + FAVORED_BONUS));
        dexterity = (int) (dexterity * (1 + FAVORED_BONUS));
    }
}
