// Used to create a Spirit monster
// Spirits have an increased dodge chance

public class Spirit extends Monster {

    public Spirit(String name, int level, int damage, int defense, int dodge) {
        super(name, level, damage, defense, dodge);
        applyFavoredBonus();
    }

    protected void applyFavoredBonus() {
        dodge = (int) (dodge * (1 + FAVORED_BONUS));
    }

}
