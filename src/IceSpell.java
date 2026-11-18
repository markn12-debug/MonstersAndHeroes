// Used to create an ice spell
// Lowers the target monster's damage

public class IceSpell extends Spell {
    public IceSpell(String name, int price, int level, int damage, int manaCost) {
        super(name, price, level, damage, manaCost);
    }

    public String getEffectDescription() {
        return "lowers the target's damage";
    }

    public void applyEffect(Monster target) {
        target.reduceDamage();
    }
}
