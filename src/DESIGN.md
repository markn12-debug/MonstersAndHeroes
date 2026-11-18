Design Documentation

Class Structure:

MonstersAndHeroes is added to the existing Game program as a fourth game. The abstract Game class is
still the parent class of every game and stores the shared input scanner. MonstersAndHeroes extends Game
and implements play(), which shows the instructions, lets the player choose a party, and runs the command
loop for moving, viewing info, managing the inventory, entering markets, and quitting.

The world map is handled by the WorldMap class, which extends the generic Grid class with Tile as its cell
type. WorldMap randomly generates the map, tracks where the party is standing, and moves the party. Tile is
an abstract class, and each kind of tile is its own subclass, such as CommonTile, MarketTile, and InaccessibleTile.
Each tile decides whether the party can enter it, how it is drawn, and what happens when the party steps on it
through its own onEnter() method. A CommonTile may start a battle, and each MarketTile has its own market.

Heroes and monsters both extend an abstract Combatant parent class, which stores their name, level, and HP and
handles taking damage and fainting. The abstract Hero class adds mana, gold, experience, strength, dexterity, agility,
an inventory, and an equipped weapon and armor. Warrior, Sorcerer, and Paladin extend Hero and each override
applyFavoredBonus() to boost their two favored skills. The abstract Monster class adds damage, defense, and dodge
chance, and Dragon, Exoskeleton, and Spirit extend it to boost their favored stat. The Party class holds the group of
one to three heroes traveling together.

Items are built on an abstract Item class that stores a name, price, required level, and a limited number of uses,
and handles selling and repairing. Weapon, Armor, and Potion extend Item, and the abstract Spell class extends Item
for magic attacks that cost mana. FireSpell, IceSpell, and LightningSpell extend Spell, and each overrides
applyEffect() to lower a different monster stat. Every item also implements useFromInventory(), so weapons and armor
are equipped, potions are consumed, and spells can only be used in battle.

The Market class stores the items a market has for sale and runs the menu where each hero buys, sells, and repairs
items with their own gold. The Battle class runs a fight between the party and a group of monsters, and each round,
every standing hero takes a turn to attack, cast a spell, drink a potion, or change equipment, and then every
standing monster attacks. When the battle is won, Battle rewards the heroes with gold and experience and revives
fainted heroes.

The DataLoader class reads the hero, monster, and item text files and creates the matching objects. The InputHelper class
reads numbers from the player and keeps asking until the input is valid, which is shared by MonstersAndHeroes,
Market, and Battle.

Scalability:

The world map size is passed into the WorldMap constructor, so the same class can create maps of any size. The map is
generated randomly each game, and the program checks that the starting tile is never blocked. Because WorldMap reuses
Grid, the same storage, bounds checking, and drawing code works for maps of any size.

All heroes, monsters, and items are read from text files instead of being written into the code. New heroes, monsters,
weapons, armor, potions, or spells can be added by adding a line to the matching file, with no code changes. Each game
reads the files again, so stats always start from their default values in a new game.

The game also works for any party size from one to three heroes. Battles create one monster for each hero at the level
of the strongest hero, and if no monster exists at that level, then the closest lower level is used, so battles
continue to work as heroes keep leveling up.


Extendability:

New types can be added by extending the existing abstract classes without changing the rest of the program. A new kind
of tile can extend Tile and decide what happens in its own onEnter() method. A new hero or monster type can extend
Hero or Monster and override applyFavoredBonus(). A new spell type can extend Spell and override applyEffect(). A
new kind of item can extend Item and implement useFromInventory().

The rest of the game works with these classes through their parent types. WorldMap only calls isAccessible() on a Tile,
MonstersAndHeroes only calls onEnter() on the tile the party moves to, Market only uses methods from Item, and Battle
calls applyEffect() on a Spell and takeDamage() and isFainted() on a Combatant without needing to know which subclass
it is. This means new subclasses work with the existing map, market, and battle code automatically.

Monsters and Heroes also shows that the abstract Game class supports games that are very different from the original
board games like TicTacToe, OrderAndChaos, and SuperTicTacToe, since this is a single-player role-playing game.


Design Changes:

To support MonstersAndHeroes, I moved the shared parts of the Board class into a new generic Grid class. Grid stores
the cells, checks bounds, and draws the grid, and both Board and WorldMap extend it. Board keeps its numbered boxes
and marker moves, so the original games did not need to change, and the world map was able to reuse the same grid
code instead of creating a separate one.

I also moved the two-player parts of the Game class, such as storing the players, into a new TwoPlayerGame class.
TicTacToe, OrderAndChaos, and SuperTicTacToe now extend TwoPlayerGame, while MonstersAndHeroes extends Game
directly because it is a single-player game. This kept the Game class general enough for any kind of game.

Finally, I added the InputHelper class so that reading and checking number input is written once and shared by the
classes that need it, instead of each class having its own copy.
