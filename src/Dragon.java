// Used to create a Dragon monster
// Dragons have increased damage

public class Dragon extends Monster {

    public Dragon(String name, int level, int damage, int defense, int dodge) {
        super(name, level, damage, defense, dodge);
        applyFavoredBonus();
    }

    protected void applyFavoredBonus() {
        damage = (int) (damage * (1 + FAVORED_BONUS));
    }

}
