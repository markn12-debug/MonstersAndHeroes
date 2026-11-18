// Used to create a lightning spell
// Lowers the target monster's dodge chance

public class LightningSpell extends Spell {
    public LightningSpell(String name, int price, int level, int damage, int manaCost) {
        super(name, price, level, damage, manaCost);
    }

    public String getEffectDescription() {
        return "lowers the target's dodge chance";
    }

    public void applyEffect(Monster target) {
        target.reduceDodge();
    }
}
