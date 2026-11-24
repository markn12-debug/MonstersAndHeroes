# CS611 - Assignment 4
## Legends of Valor

Mark Noonan
markn12@bu.edu
U43252293

Daniel Matuzka
dmatuzka@bu.edu
U69023000

## Files:
### Legends of Valor
LegendsOfValor.java: Creates a new Legends of Valor game, asks the player to select difficulty and 3 heroes to play with, runs the game rounds
ValorMap.java: Creates the specified 8x8 map for the Legends of Valor game. Map consists of 3 lanes where heroes and monsters can stand an move. The map also enforces movement, attack range and teleport rules
LaneTile.java: Abstract parent class of every walkable tile on the ValorMap. Can hold at msot one hero and at most one monster. Can apply affects to the combatants standing on the tile
PlainTile.java: Plain LaneTile with no special abilities
BonusTile.java: Abstract parent class of the terrain tiles: gives the hero extra 10% of a certain stat while he is standing there
BushTile.java: Bush bonus tile, increases the dexterity of the hero standing there
CaveTile.java: Cave bonus tile, increases the agility of the hero standing there
KoulouTile.java: Koulou bonus tile, increases the strength of the hero standing there
NexusTile.java: A Nexus space at the top or bottom row of a lane, the hero's nexus also has a market. Reaching nexus ends the game
ObstacleTile.java: A space on the map nobody can enter until a hero removes the obstacle, which turns it into a plain tile
HeroActions.java: Carries out attack actions, casting spells, changing equipment or using potion for a hero. Used by both Battle and LegendsOfValor 

### Shared with Monsters and Heroes
Game.java: This class handles the process of running a sequence of games
MonstersAndHeroes.java: Creates a new Monsters and Heroes game and runs the main command loop
WorldMap.java: Creates the world map, tracks where the party is, and moves the party
Grid.java: Creates a square grid of cells that is shared by the game boards and the world map
Tile.java: Represents one space on the world map
CommonTile.java: Represents open ground where monsters may attack the party
MarketTile.java: Represents a space on the map that has a market
InaccessibleTile.java: Represents a space nobody can enter
Battle.java: Runs a fight between the party and a group of monsters
Market.java: Stores the items a market sells and runs the buy, sell, repair menu
Party.java: Stores the group of one to three heroes
DataLoader.java: Reads the hero, monster, and item text files and creates the game objects
InputHelper.java: Reads number input from the player and asks again until it is valid
Combatant.java: Stores the name, level, and HP shared by heroes and monsters
Hero.java: Stores a hero's stats, gold, experience, inventory, and equipped weapon and armor
Warrior.java: Creates a hero that is favored on strength and agility
Sorcerer.java: Creates a hero that is favored on dexterity and agility
Paladin.java: Creates a hero that is favored on strength and dexterity
Monster.java: Stores a monster's damage, defense, and dodge chance
Dragon.java: Creates a monster with increased damage
Exoskeleton.java: Creates a monster with increased defense
Spirit.java: Creates a monster with increased dodge chance
Item.java: Stores the name, price, level, and uses shared by every item
Weapon.java: Represents a weapon that a hero can equip to deal more damage
Armor.java: Represents armor that a hero can equip to take less damage
Potion.java: Represents a single-use item that raises a hero's stats
Spell.java: Represents a single-use magic attack that costs mana
FireSpell.java: Represents a spell that lowers a monster's defense
IceSpell.java: Represents a spell that lowers a monster's damage
LightningSpell.java: Represents a spell that lowers a monster's dodge chance

### Board games from earlier assignments
TwoPlayerGame.java: Stores the two players shared by the two-player games
TicTacToe.java: This class creates a new Tic-Tac-Toe game
OrderAndChaos.java: This class creates a new Order and Chaos game
SuperTicTacToe.java: Creates a new Super Tic-Tac-Toe game by using nine smaller TicTacToe boards
Board.java: This class creates a new board for a game
SuperBoard.java: Manages the nine smaller boards in a Super TicTacToe game and tracks the winner of each board
BoardCell.java: Represents an individual cell on a board and stores its marker
Marker.java: Represents an X or O marker
TicTacToeWinChecker.java: Checks whether a player has won a TicTacToe board
Player.java: This class creates a Player object and stores player data


## Notes
1. LegendsOfValor extends the abstract Game class, it is option 5 in the same game menu as the other games
2. Heroes, monsters, items, markets, data files, along with the damage, defense, dodge and level-up formulas are the same as in
   Heroes and Monsters
3. The attack, cast spell, change equipment, use a potion code used to be written inside Battle. It is now moved into HeroActions
   which is used by both Battle and LegendsOfValor, so the logic exists once. Each action is given a list of potential monsters it can target and returns true if it uses up the hero's turn
4. ValorMap extends the same generic Grid class as WorldMap, with Tile being the type of the cell
5. The map is 8x8. Columns 1-2, 4-5 and 7-8 are the three lanes, with columns 3 and 6 being the walls made of inaccessible tiles.
   Top row consists of the monsters' Nexus spaces, and bottom row - of heroes' Nexus spaces.
6. Spaces inside lanes are random: 10% are obstacles, and of the remaining spaces 20% are bush, 20% cave, 20% koulou, 40% plain.
   The map is generated so that every tile type is present.
7. Both spaces of one lane in the same row cannot be obstacles. Monsters cannot remove obstacles.
8. Every walkable tile extends LaneTile, which holds at most one hero and at most one monster and calls onHeroEnter, onHeroLeave
   to apply effects when a hero enters a tile or leaves it. Bush, Cave, Koulou all extend BonusTile, which increase one stat of the hero on the tile by 10%. The bonus gets taken away when hero leaves. In our version, monsters do not receive terrain bonuses.
9. New terrain can simply be added by extending LaneTile or BonusTile and overriding getSymbol(). Making it so that monsters get
   some sort of terrain bonuses requires overriding onMonsterEnter and onMonsterLeave.
10. Obstacle spaces are replaced with plain spaces through remove(), so ValorMap never knows the specific tile type.
11. The map draws every space as 7 characters wide. The hero is on the left, the space letter in the
    middle, and the monster on the right, so a hero and a monster on the same space look like "H1 P M1".
    Spaces: N = Nexus, I = inaccessible, O = obstacle, P = plain, B = bush, C = cave, K = koulou
12. Before the game the player picks a difficulty and three heroes. The heroes start in the left space of
    the Nexus of the top, middle, and bottom lane in the order they were picked (H1, H2, H3). Monsters
    start in the right space of the Nexus of their lane (M1, M2, M3)
13. The difficulty decides how often a new wave of three monsters (one per lane) spawns: easy every
    8 rounds, medium every 6, and hard every 4. A monster is not spawned if another monster is still
    standing on that spawn space
14. Monsters are created at the level of the highest-level hero. If no monster exists at that level, the
    closest lower level is used. The same monster is not used twice in one wave
15. A round is every hero taking one action, in the order H1, H2, H3, followed by every monster acting.
    The player chooses one action per hero: move (W/A/S/D), attack (F), cast a spell (C), equip or drink a
    potion (E), teleport (T), recall (R), remove an obstacle (O), or pass (X). Buying and selling in the
    market (M), the info (I), map (P), and help (H) commands do not use up the turn. A hero cannot shop outside
    its Nexus
16. Attack and spell range is the hero's own space plus all eight neighbors (diagonals included) that are
    inside the same lane. A hero can only hit one monster per action. Attacking or casting with nothing in
    range does not use up the turn
17. A hero or monster moves one space north, south, east, or west. Nobody can move onto an inaccessible
    space or an obstacle, onto a space that has another unit of their own side, or past an enemy in the same
    lane. A hero and a monster can share a space
18. Teleport moves a hero beside or directly behind another hero in a different lane. The hero cannot land
    ahead of that hero, on another hero, or past a monster in that lane. Recall sends a hero back to the
    Nexus space they started in, and is refused if another hero is standing there
19. Each monster attacks a random hero in range if there is one. Otherwise it moves one space south, and if
    something blocks it (an obstacle or another monster), it sidesteps to the side in its lane
20. Every killed monster gives every hero 500 x monster level gold and 2 x monster level experience
21. A hero that faints is taken off the map and skips its turn. At the end of the round it respawns in its
    Nexus space with full HP and MP. If another hero is standing on that space, it waits another round.
    All other living heroes regain 10% of their HP and MP at the end of every round
22. The heroes win the moment any hero stands in the monsters' Nexus, and lose the moment any monster
    stands in the heroes' Nexus
23. Invalid input is handled by asking the player again, so the game does not crash on a typo
24. Some of the formulas from Monsters and Heroes were adjusted to keep the game playable and are unchanged:
    - Hero dodge chance is agility * 0.0002 instead of agility * 0.002
    - Monster attack damage is scaled to 10% of the file value
    - Monster defense absorbs 5% of its value from each hit, and armor absorbs 2% of its damage reduction value


## How to compile and run
1. Navigate to the project directory that contains the src and data folders
2. Run the following instructions:
   javac src/*.java -d bin
   java -cp ./bin Game
3. Choose option 5 in the menu to play Legends of Valor

## Input/Output Example
(Parts of the output were left out where marked with ... or noted)

Please choose a game:
1: Tic-Tac-Toe
2: Order and Chaos
3: Super Tic-Tac-Toe
4: Monsters and Heroes
5: Legends of Valor
5

==================== LEGENDS OF VALOR ====================
Lead a team of 3 heroes, one in each lane, to the monsters' Nexus
at the top of the board. If any monster reaches your Nexus at the
bottom, you lose.

Each round, every hero takes one action, then every monster
either attacks a nearby hero or moves one space down.

HERO ACTIONS (type a letter and press Enter):
  W/A/S/D = move up/left/down/right
  F = attack a monster in range
  C = cast a spell on a monster in range
  E = equip a weapon/armor or drink a potion
  T = teleport next to a hero in another lane
  R = recall to your starting Nexus space
  O = remove an obstacle next to you
  X = pass (do nothing this turn)
  M = shop at the market (only in your Nexus, doesn't use a turn)
  I = show info    P = show the map    H = help    Q = quit

MAP:  N = Nexus   I = inaccessible   O = obstacle   P = plain
      B = bush (+dexterity)   C = cave (+agility)   K = koulou (+strength)
      H1/H2/H3 = your heroes   M1/M2/M3 = monsters
==========================================================

Choose difficulty (how often new monsters spawn):
1) Easy   - every 8 rounds
2) Medium - every 6 rounds
3) Hard   - every 4 rounds
Difficulty: 1
Choose the hero for the top lane:
1) Gaerdal Ironhand (Warrior)  Lvl 1  HP 100  MP 100  Gold 1354  XP 7  Str 735  Dex 600  Agi 525
2) Sehanine Monnbow (Warrior)  Lvl 1  HP 100  MP 600  Gold 2500  XP 8  Str 735  Dex 500  Agi 840
...
18) Caliber Heist (Paladin)  Lvl 1  HP 100  MP 400  Gold 2500  XP 8  Str 420  Dex 420  Agi 400
Hero number: 1
Gaerdal Ironhand will start in the top lane!

Choose the hero for the middle lane:
1) Sehanine Monnbow (Warrior)  Lvl 1  HP 100  MP 600  Gold 2500  XP 8  Str 735  Dex 500  Agi 840
2) Muamman Duathall (Warrior)  Lvl 1  HP 100  MP 300  Gold 2546  XP 6  Str 945  Dex 750  Agi 525
...
17) Caliber Heist (Paladin)  Lvl 1  HP 100  MP 400  Gold 2500  XP 8  Str 420  Dex 420  Agi 400
Hero number: 1
Sehanine Monnbow will start in the middle lane!

Choose the hero for the bottom lane:
1) Muamman Duathall (Warrior)  Lvl 1  HP 100  MP 300  Gold 2546  XP 6  Str 945  Dex 750  Agi 525
2) Flandal Steelskin (Warrior)  Lvl 1  HP 100  MP 200  Gold 2500  XP 7  Str 787  Dex 700  Agi 682
...
16) Caliber Heist (Paladin)  Lvl 1  HP 100  MP 400  Gold 2500  XP 8  Str 420  Dex 420  Agi 400
Hero number: 1
Muamman Duathall will start in the bottom lane!
Blinky appeared in lane 1!
Natsunomeryu appeared in lane 2!
BigBad-Wolf appeared in lane 3!

(rounds 1-5 left out)

========== ROUND 6 ==========
+-------+-------+-------+-------+-------+-------+-------+-------+
|   N   |   N   |   I   |   N   |   N   |   I   |   N   |   N   |
+-------+-------+-------+-------+-------+-------+-------+-------+
|   B   |   P   |   I   |   P   |   O   |   I   |   B   |   P   |
+-------+-------+-------+-------+-------+-------+-------+-------+
|   K   |   B   |   I   |   P   |   P   |   I   |   P   |   P   |
+-------+-------+-------+-------+-------+-------+-------+-------+
|   B   |   K M1|   I   |   P M2|   K   |   I   |H3 K   |   P M3|
+-------+-------+-------+-------+-------+-------+-------+-------+
|H1 P   |   P   |   I   |H2 B   |   B   |   I   |   P   |   O   |
+-------+-------+-------+-------+-------+-------+-------+-------+
|   P   |   O   |   I   |   B   |   C   |   I   |   P   |   P   |
+-------+-------+-------+-------+-------+-------+-------+-------+
|   B   |   B   |   I   |   C   |   P   |   I   |   P   |   P   |
+-------+-------+-------+-------+-------+-------+-------+-------+
|   N   |   N   |   I   |   N   |   N   |   I   |   N   |   N   |
+-------+-------+-------+-------+-------+-------+-------+-------+


H1 Gaerdal Ironhand's turn (HP 16, MP 100)
W/A/S/D move | F attack | C spell | E equip/potion | T teleport | R recall | O obstacle | X pass | M market | I info | P map | H help | Q quit
Enter an action: f
Blinky dodged Gaerdal Ironhand's attack!

H2 Sehanine Monnbow's turn (HP 97, MP 600)
W/A/S/D move | F attack | C spell | E equip/potion | T teleport | R recall | O obstacle | X pass | M market | I info | P map | H help | Q quit
Enter an action: f
Sehanine Monnbow hit Natsunomeryu for 39 damage.

H3 Muamman Duathall's turn (HP 85, MP 300)
W/A/S/D move | F attack | C spell | E equip/potion | T teleport | R recall | O obstacle | X pass | M market | I info | P map | H help | Q quit
Enter an action: f
Muamman Duathall hit BigBad-Wolf for 38 damage.
Blinky hit Gaerdal Ironhand for 45 damage.
Gaerdal Ironhand has fainted! They respawn in their Nexus next round.
Natsunomeryu hit Sehanine Monnbow for 11 damage.
BigBad-Wolf hit Muamman Duathall for 15 damage.
Gaerdal Ironhand respawned in their Nexus!

========== ROUND 7 ==========
+-------+-------+-------+-------+-------+-------+-------+-------+
|   N   |   N   |   I   |   N   |   N   |   I   |   N   |   N   |
+-------+-------+-------+-------+-------+-------+-------+-------+
|   B   |   P   |   I   |   P   |   O   |   I   |   B   |   P   |
+-------+-------+-------+-------+-------+-------+-------+-------+
|   K   |   B   |   I   |   P   |   P   |   I   |   P   |   P   |
+-------+-------+-------+-------+-------+-------+-------+-------+
|   B   |   K M1|   I   |   P M2|   K   |   I   |H3 K   |   P M3|
+-------+-------+-------+-------+-------+-------+-------+-------+
|   P   |   P   |   I   |H2 B   |   B   |   I   |   P   |   O   |
+-------+-------+-------+-------+-------+-------+-------+-------+
|   P   |   O   |   I   |   B   |   C   |   I   |   P   |   P   |
+-------+-------+-------+-------+-------+-------+-------+-------+
|   B   |   B   |   I   |   C   |   P   |   I   |   P   |   P   |
+-------+-------+-------+-------+-------+-------+-------+-------+
|H1 N   |   N   |   I   |   N   |   N   |   I   |   N   |   N   |
+-------+-------+-------+-------+-------+-------+-------+-------+


H1 Gaerdal Ironhand's turn (HP 100, MP 100)
W/A/S/D move | F attack | C spell | E equip/potion | T teleport | R recall | O obstacle | X pass | M market | I info | P map | H help | Q quit
Enter an action: m

===== WELCOME TO THE MARKET =====

Gaerdal Ironhand (Lvl 1) has 854 gold.
1) Buy
2) Sell
3) Repair
0) Done
Choice: 1

Items for sale:
1) Bow  (Lvl 2, 300 gold, 10/10 uses)  Damage 500, 2-handed
2) Axe  (Lvl 5, 550 gold, 10/10 uses)  Damage 850, 1-handed
...
15) Spark Needles  (Lvl 2, 500 gold, 1/1 uses)  Damage 600, Mana 200, lowers the target's dodge chance
Item to buy (0 to cancel): 0

Gaerdal Ironhand (Lvl 1) has 854 gold.
1) Buy
2) Sell
3) Repair
0) Done
Choice: 0
You leave the market.

H1 Gaerdal Ironhand's turn (HP 100, MP 100)
W/A/S/D move | F attack | C spell | E equip/potion | T teleport | R recall | O obstacle | X pass | M market | I info | P map | H help | Q quit
Enter an action: w
Gaerdal Ironhand gets 60 Dexterity.

H2 Sehanine Monnbow's turn (HP 94, MP 600)
W/A/S/D move | F attack | C spell | E equip/potion | T teleport | R recall | O obstacle | X pass | M market | I info | P map | H help | Q quit
Enter an action: f
Sehanine Monnbow hit Natsunomeryu for 39 damage.
Natsunomeryu has been defeated!
Every hero earned 500 gold and 2 EXP.
Sehanine Monnbow reached level 2!

H3 Muamman Duathall's turn (HP 77, MP 300)
W/A/S/D move | F attack | C spell | E equip/potion | T teleport | R recall | O obstacle | X pass | M market | I info | P map | H help | Q quit
Enter an action: f
Muamman Duathall hit BigBad-Wolf for 38 damage.
BigBad-Wolf has been defeated!
Every hero earned 500 gold and 2 EXP.
Gaerdal Ironhand reached level 2!
Muamman Duathall reached level 2!
WickedWitch appeared in lane 1!
Andrealphus appeared in lane 2!
Chrysophylax appeared in lane 3!

(rounds 8-20 left out; the heroes kept pushing up their lanes)

========== ROUND 21 ==========
+-------+-------+-------+-------+-------+-------+-------+-------+
|   N   |   N   |   I   |   N M2|   N   |   I   |   N   |   N M3|
+-------+-------+-------+-------+-------+-------+-------+-------+
|   B   |   P   |   I   |   P M2|   O   |   I   |H3 B   |   P   |
+-------+-------+-------+-------+-------+-------+-------+-------+
|   K   |   B M1|   I   |   P   |   P   |   I   |   P   |   P   |
+-------+-------+-------+-------+-------+-------+-------+-------+
|H1 B   |   K   |   I   |   P   |   K   |   I   |   K   |   P   |
+-------+-------+-------+-------+-------+-------+-------+-------+
|   P   |   P   |   I   |   B   |   B   |   I   |   P   |   O   |
+-------+-------+-------+-------+-------+-------+-------+-------+
|   P   |   O   |   I   |   B   |   C   |   I   |   P   |   P   |
+-------+-------+-------+-------+-------+-------+-------+-------+
|   B   |   B   |   I   |   C   |   P   |   I   |   P   |   P   |
+-------+-------+-------+-------+-------+-------+-------+-------+
|   N   |   N   |   I   |H2 N   |   N   |   I   |   N   |   N   |
+-------+-------+-------+-------+-------+-------+-------+-------+


H1 Gaerdal Ironhand's turn (HP 39, MP 110)
W/A/S/D move | F attack | C spell | E equip/potion | T teleport | R recall | O obstacle | X pass | M market | I info | P map | H help | Q quit
Enter an action: f
Andrealphus dodged Gaerdal Ironhand's attack!

H2 Sehanine Monnbow's turn (HP 200, MP 660)
W/A/S/D move | F attack | C spell | E equip/potion | T teleport | R recall | O obstacle | X pass | M market | I info | P map | H help | Q quit
Enter an action: m

===== WELCOME TO THE MARKET =====

Sehanine Monnbow (Lvl 2) has 5650 gold.
1) Buy
2) Sell
3) Repair
0) Done
Choice: 1

Items for sale:
1) Scythe  (Lvl 6, 1000 gold, 10/10 uses)  Damage 1100, 2-handed
2) Axe  (Lvl 5, 550 gold, 10/10 uses)  Damage 850, 1-handed
...
Item to buy (0 to cancel): 7
Sehanine Monnbow bought Strength Potion.

Sehanine Monnbow (Lvl 2) has 5450 gold.
1) Buy
2) Sell
3) Repair
0) Done
Choice: 1

Items for sale:
1) Scythe  (Lvl 6, 1000 gold, 10/10 uses)  Damage 1100, 2-handed
2) Axe  (Lvl 5, 550 gold, 10/10 uses)  Damage 850, 1-handed
...
Item to buy (0 to cancel): 6
Sehanine Monnbow bought Healing Potion.

Sehanine Monnbow (Lvl 2) has 5200 gold.
1) Buy
2) Sell
3) Repair
0) Done
Choice: 0
You leave the market.

H2 Sehanine Monnbow's turn (HP 200, MP 660)
W/A/S/D move | F attack | C spell | E equip/potion | T teleport | R recall | O obstacle | X pass | M market | I info | P map | H help | Q quit
Enter an action: e
1) Change weapon/armor    2) Drink a potion    0) Cancel
Choice: 2
1) Strength Potion  (Lvl 1, 200 gold, 1/1 uses)  +75 Strength
2) Healing Potion  (Lvl 1, 250 gold, 1/1 uses)  +100 Health
Potion to drink (0 to cancel): 1
Sehanine Monnbow drank Strength Potion!

H3 Muamman Duathall's turn (HP 3, MP 330)
W/A/S/D move | F attack | C spell | E equip/potion | T teleport | R recall | O obstacle | X pass | M market | I info | P map | H help | Q quit
Enter an action: p
+-------+-------+-------+-------+-------+-------+-------+-------+
|   N   |   N   |   I   |   N M2|   N   |   I   |   N   |   N M3|
+-------+-------+-------+-------+-------+-------+-------+-------+
|   B   |   P   |   I   |   P M2|   O   |   I   |H3 B   |   P   |
+-------+-------+-------+-------+-------+-------+-------+-------+
|   K   |   B M1|   I   |   P   |   P   |   I   |   P   |   P   |
+-------+-------+-------+-------+-------+-------+-------+-------+
|H1 B   |   K   |   I   |   P   |   K   |   I   |   K   |   P   |
+-------+-------+-------+-------+-------+-------+-------+-------+
|   P   |   P   |   I   |   B   |   B   |   I   |   P   |   O   |
+-------+-------+-------+-------+-------+-------+-------+-------+
|   P   |   O   |   I   |   B   |   C   |   I   |   P   |   P   |
+-------+-------+-------+-------+-------+-------+-------+-------+
|   B   |   B   |   I   |   C   |   P   |   I   |   P   |   P   |
+-------+-------+-------+-------+-------+-------+-------+-------+
|   N   |   N   |   I   |H2 N   |   N   |   I   |   N   |   N   |
+-------+-------+-------+-------+-------+-------+-------+-------+


H3 Muamman Duathall's turn (HP 3, MP 330)
W/A/S/D move | F attack | C spell | E equip/potion | T teleport | R recall | O obstacle | X pass | M market | I info | P map | H help | Q quit
Enter an action: w
A hero reached the monsters' Nexus. You win!
Thanks for playing Legends of Valor!
Would you like to play another game? (Y/N)
N

