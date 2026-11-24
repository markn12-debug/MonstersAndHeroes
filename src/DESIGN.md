Design Documentation

Class Structure:

LegendsOfValor is added to the existing Game program as a fifth game. The abstract
Game class is still the parent class of every game and stores the shared input
scanner. LegendsOfValor extends Game and implements play(), which shows the instructions,
lets the player choose a difficulty and three heroes, and runs the rounds. Each round, 
every living hero takes one action, then every monster takes one action, and then the round
ends with the heroes regenerating, fainted heroes respawning, and a new wave of monsters
spawning every few rounds.

The board is handled by the ValorMap class, which extends the same generic Grid class as
WorldMap, with Tile as its cell type. ValorMap builds the 8x8 map with three lanes that
are two spaces wide, walls of inaccessible tiles between lanes, the monsters' Nexus on the
top row, and the heroes' Nexus on the bottom row. It keeps track of where every hero and
monster is standing, and it enforces the rules of the board, like movement, attack range,
teleporting, recalling, removing obstacles, and checking whether each side has reached the
other side's Nexus. LegendsOfValor decides what each hero and monster wants to do, and
ValorMap decides whether it is allowed.

Every space a hero or monster can stand on extends the abstract LaneTile class, which
extends Tile. A LaneTile holds at most one hero and at most one monster, and calls
onHeroEnter() and onHeroLeave() when a hero arrives or leaves, so each kind of space can
decide what effect it has. PlainTile has no effect. NexusTile marks the end of a lane, and
the heroes' Nexus spaces each have their own Market. ObstacleTile blocks the spae until a
hero removes it, which replaces it with a PlainTile. The abstract BonusTile class gives a
hero a 10% boost to one stat while they stand on it and takes the same amount away when
they leave. BushTile, CaveTile, and KoulouTile extend BonusTile and each choose a different
state (dexterity, agility, and strength). InaccessibleTile is shared with Monsters and
Heroes and is used for the walls.

The HeroActions class carries out the actions a hero can take in a fight, such as attacking,
casting a spell, changing equipment, and drinking a potion. Each action is given the list of
monsters the hero is allowed to target and returns whether it used up the hero's turn. In
Legends of Valor, the targets are the monsters in raneg on the map. The Battle class from
Monsters and Heroes also uses HeroActions, so the fighting logic is written once and shared
by both games.

Heroes, monsters, items, the market, and the data files are the same classes as in Monsters
and Heroes. Heroes and monsters both extend the abstract Combatant class, Warrior, Sorcerer,
and Paladin extend Hero, and Dragon, Exoskeleton, and Spirit extend Monster. Weapon, Armor,
Potion, and Spell extend Item, and FireSpell, IceSpell, and LightningSpell extend Spell. The
Party class holds the three heroes, and the DataLoader and InputHelper classes are used to
read the data files and the player's input.


Scalability:

The lane layout is calculated from the LANES and LANE_WIDTH constants in ValorMap instead
of being written as fixed column numbers, so spawning, walls, attack range, and teleport
sports all follow from those values. The map is generated randomly each game, and the
program makes sure every type of terrain appears and that both spaces of a lane in the
same row are never obstacles, so a lane can never be completely blocked.

ValorMap stores the position of every hero and monster in a map instead of separate 
variables, so any number of monsters can be on the board at once as new waves keep spawning.
The difficulty levels are stored in an array, so new difficulty levels can be added without
changing the game loop.

All heroes, monsters, and items are still read from the text files, so new ones can be added
by adding a line to the matching file. New monsters are created at the level of the strongest
hero, and if no monster exists at that level, the closest lower level is used, so the game
keeps working as heroes level up.


Extendability:

A new kind of terrain can be added by extending LaneTile, or by extending BonusTile and
choosing which stat it boosts with getBoostedStat(). A new tile can be added to randomLaneTile() 
in ValorMap so it can appear on the map. Moving onto it, leaving it, and applying its effect all 
work through LaneTile without any other changes. LaneTile also has onMonsterEnter() and 
onMonsterLeave(), so terrain that affects monsters could be added by overriding those methods.

A new hero action can be added to HeroActions, where it is available to both Legends of Valor
and Monsters and Heroes. Legends of Valor also shows that the Game class and the shared hero,
monster, and item classes can support a second role-playing game with very different rules, 
since the same classes are used for a map-based game instead of a separate battle screen.


Design Changes:

To support Legends of Valor, we moved the attack, spell, equipment, and potion code out of
the Battle class and into the new HeroActions class. Battle now uses HeroActions, so
Monsters and Heroes works the same as before, and Legends of Valor was able to reuse the
same fighting code instead of making a copy of it.

We added a second open() method to Market that takes a single Hero. In Monsters and Heroes,
the whole party stands at the market, so open() still asks which hero is shopping. In Legends
of Valor only the hero standing in the Nexus is allowed to shop, so the method opens the shop
for that hero only. Both methods share the same buy, sell, and repair code.


Design Evaluation:

Choosing an implementation:

We kept Mark's Monsters and Heroes implementation as the base for the shared classes, mainly
because it had an abstract Combatant class that both Hero and Monster extend, while the
other project keps heroes and monsters as completely separate classes.

This mattered in Legends of Valor because in the game, both stand on the same map, move one
space at a time, block each other, and are drawn on the same spaces. Because they share
Combatant, ValorMap can store every position in one Map<Combatant, int[]>, and the same
methods, such as move(), place(), can Stand(), and skipsEnemy(), work for both heroes and
monsters. Without Combatant, we would have needed two copies of each of these methods, one
for heroes and one for monsters, or separate position tracking for each side.

Overall, we feel our decisions were correct and achieved our expectations. Choosing the implementation 
with the Combatant class was important, since almost every method in ValorMap works for both heroes 
and monsters without separate versions. Moving the combat code into HeroActions also worked well, 
since both games now share it.