// Abstract class used as a parent class of any accessible tile in Legends of Valor
// Keeps track of one hero and one monster that are allowed to stand here at once
// Also has appropriate methods so that heroes/monsters can get terrain stats bonuses when entering them
public abstract class LaneTile extends Tile {
    private Hero hero;
    private Monster monster;

    public boolean isAccessible() {
        return true;    
    }

    public Hero getHero() {
        return hero;
    }

    public Monster getMonster() {
        return monster;
    }

    // hero can enter if there are no other heroes here currently
    public boolean canHeroEnter() {
        return hero == null;
    }

    // monster can enter if there are no other monsters here currently
    public boolean canMonsterEnter() {
        return monster == null;
    }

    // methods for when hero/monster gets on the tile, can trigger different methods depending o nthe terrain
    public void enter(Hero newHero) {
        hero = newHero;
        onHeroEnter(newHero);
    }

    public void enter(Monster newMonster) {
        monster = newMonster;
        onMonsterEnter(newMonster);
    }

    // hero can walk off, teleport, recall or die
    public void leave(Hero oldHero) {
        if (hero == oldHero) {
            onHeroLeave(oldHero);
            hero = null;
        }
        
    }

    public void leave(Monster oldMonster) {
        if (monster == oldMonster) {
            onMonsterLeave(oldMonster);
            monster = null;
        }
       
    }


    // Methods to be specified by subclasses
    protected void onHeroEnter(Hero hero) {}
    protected void onMonsterEnter(Monster monster) {}
    protected void onHeroLeave(Hero hero) {}
    protected void onMonsterLeave(Monster monster) {}
    

}
