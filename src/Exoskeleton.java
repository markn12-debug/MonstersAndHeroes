// Used to create an Exoskeleton monster
// Exoskeletons have increased defense

public class Exoskeleton extends Monster {

    public Exoskeleton(String name, int level, int damage, int defense, int dodge) {
        super(name, level, damage, defense, dodge);
        applyFavoredBonus();
    }

    protected void applyFavoredBonus() {
        defense = (int) (defense * (1 + FAVORED_BONUS));
    }

}
