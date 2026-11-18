// Used to create a fire spell
// Lowers the target monster's defense

public class FireSpell extends Spell {
    public FireSpell(String name, int price, int level, int damage, int manaCost) {
        super(name, price, level, damage, manaCost);
    }

    public String getEffectDescription() {
        return "lowers the target's defense";
    }

    public void applyEffect(Monster target) {
        target.reduceDefense();
    }
}
