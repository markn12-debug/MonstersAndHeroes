# CS611 - Assignment 4
## Legends: Monsters and Heroes

Mark Noonan
markn12@bu.edu
U43252293

## Files:
Game.java: This class handles the process of running a sequence of games
TwoPlayerGame.java: Stores the two players shared by the two-player games
TicTacToe.java: This class creates a new Tic-Tac-Toe game
OrderAndChaos.java: This class creates a new Order and Chaos game
SuperTicTacToe.java: Creates a new Super Tic-Tac-Toe game by using nine smaller TicTacToe boards
Grid.java: Creates a square grid of cells that is shared by the game boards and the world map
Board.java: This class creates a new board for a game
Player.java: This class creates a Player object and stores player data
SuperBoard.java: Manages the nine smaller boards in a Super TicTacToe game and tracks the winner of each board
BoardCell.java: Represents an individual cell on a board and stores its marker
Marker.java: Represents an X or O marker
TicTacToeWinChecker.java: Checks whether a player has won a TicTacToe board
MonstersAndHeroes.java: Creates a new Monsters and Heroes game and runs the main command loop
WorldMap.java: Creates the world map, tracks where the party is, and moves the party
Tile.java: Represents one space on the world map
CommonTile.java: Represents open ground where monsters may attack the party
MarketTile.java: Represents a space on the map that has a market
Battle.java: Runs a fight between the party and a group of monsters
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


## Notes
1. MonstersAndHeroes extends the abstract Game class, so it is added to the same
   game menu as TicTacToe, OrderAndChaos, and SuperTicTacToe
2. The shared parts of the Board class were moved into a generic Grid class, which is used
   by both the game boards and the world map
3. Heroes and monsters share an abstract Combatant class, so the battle code can treat both
   sides the same way
4. Each hero type, monster type, and spell type is its own subclass because they behave differently.
   Weapons, armor, and potions only differ in their stats, so each is a single class built from the
   data files
5. Each item decides what happens when it is chosen from the inventory: weapons and armor are
   equipped, potions are used, and spells can only be cast in battle
6. The world map is randomly generated each game, with about 20% blocked spaces, 30% markets,
   and 50% open ground. The starting space is never blocked
7. Every market stocks a different random selection of items
8. All heroes, monsters, and items are read from the text files in the data folder, so stats reset
   to their default values at the start of each new game
9. Monsters are created at the level of the highest-level hero. If no monster exists at that level,
   the closest lower level is used
10. Weapons and armor wear out with use and can be repaired at the market for half their price
11. HP and MP regenerate by 10% each round of battle without going above the hero's maximum, and fainted
    heroes are revived with half their HP and MP after a won battle
12. Invalid input is handled by asking the player again, so the game does not crash on a typo
13. Some of the formulas were adjusted to keep the game playable:
    - Hero dodge chance is agility * 0.0002 instead of agility * 0.002, which would give most
      heroes a 100% dodge chance
    - Monster attack damage is scaled to 10% of the file value so one hit does not knock out a hero
    - Monster defense absorbs 5% of its value from each hit, and armor absorbs 2% of its damage reduction value

## How to compile and run
1. Navigate to the project directory that contains the src and data folders
2. Run the following instructions:
   javac src/*.java -d bin
   java -cp ./bin Game

## Input/Output Example

Please choose a game:
1: Tic-Tac-Toe
2: Order and Chaos
3: Super Tic-Tac-Toe
4: Monsters and Heroes
4

==================== MONSTERS AND HEROES ====================
Lead a party of up to 3 heroes across a dangerous world.
Buy gear at markets, fight monsters, and level up your heroes.

CONTROLS (type a letter and press Enter):
W = move up        A = move left
S = move down      D = move right
I = show your heroes' info
E = inventory: equip weapons/armor or drink potions
M = enter the market (only when standing on an M)
P = show the map
H = show these instructions again
Q = quit the game

MAP:  P = your party   M = market   X = blocked   (blank) = open ground

BATTLES:
Monsters may attack when you walk on open ground!
On each hero's turn, pick a number to attack, cast a spell,
drink a potion, or change gear. Win to earn gold and XP.
If all your heroes faint, the game is over.
=============================================================
How many heroes do you want in your party? (1-3): 2

Choose hero 1 of 2:
1) Gaerdal Ironhand (Warrior)  Lvl 1  HP 100  MP 100  Gold 1354  XP 7  Str 735  Dex 600  Agi 525
2) Sehanine Monnbow (Warrior)  Lvl 1  HP 100  MP 600  Gold 2500  XP 8  Str 735  Dex 500  Agi 840
3) Muamman Duathall (Warrior)  Lvl 1  HP 100  MP 300  Gold 2546  XP 6  Str 945  Dex 750  Agi 525
4) Flandal Steelskin (Warrior)  Lvl 1  HP 100  MP 200  Gold 2500  XP 7  Str 787  Dex 700  Agi 682
5) Undefeated Yoj (Warrior)  Lvl 1  HP 100  MP 400  Gold 2500  XP 7  Str 840  Dex 700  Agi 420
6) Eunoia Cyn (Warrior)  Lvl 1  HP 100  MP 400  Gold 2500  XP 6  Str 735  Dex 600  Agi 840
7) Rillifane Rallathil (Sorcerer)  Lvl 1  HP 100  MP 1300  Gold 2500  XP 9  Str 750  Dex 525  Agi 472
8) Segojan Earthcaller (Sorcerer)  Lvl 1  HP 100  MP 900  Gold 2500  XP 5  Str 800  Dex 682  Agi 525
9) Reign Havoc (Sorcerer)  Lvl 1  HP 100  MP 800  Gold 2500  XP 8  Str 800  Dex 840  Agi 840
10) Reverie Ashels (Sorcerer)  Lvl 1  HP 100  MP 900  Gold 2500  XP 7  Str 800  Dex 420  Agi 735
11) Kalabar (Sorcerer)  Lvl 1  HP 100  MP 800  Gold 2500  XP 6  Str 850  Dex 630  Agi 420
12) Skye Soar (Sorcerer)  Lvl 1  HP 100  MP 1000  Gold 2500  XP 5  Str 700  Dex 525  Agi 420
13) Parzival (Paladin)  Lvl 1  HP 100  MP 300  Gold 2500  XP 7  Str 787  Dex 735  Agi 650
14) Sehanine Moonbow (Paladin)  Lvl 1  HP 100  MP 300  Gold 2500  XP 7  Str 787  Dex 735  Agi 700
15) Skoraeus Stonebones (Paladin)  Lvl 1  HP 100  MP 250  Gold 2500  XP 4  Str 682  Dex 367  Agi 600
16) Garl Glittergold (Paladin)  Lvl 1  HP 100  MP 100  Gold 2500  XP 5  Str 630  Dex 420  Agi 500
17) Amaryllis Astra (Paladin)  Lvl 1  HP 100  MP 500  Gold 2500  XP 5  Str 525  Dex 525  Agi 500
18) Caliber Heist (Paladin)  Lvl 1  HP 100  MP 400  Gold 2500  XP 8  Str 420  Dex 420  Agi 400
    Hero number: 1
    Gaerdal Ironhand joins the party!

Choose hero 2 of 2:
1) Sehanine Monnbow (Warrior)  Lvl 1  HP 100  MP 600  Gold 2500  XP 8  Str 735  Dex 500  Agi 840
2) Muamman Duathall (Warrior)  Lvl 1  HP 100  MP 300  Gold 2546  XP 6  Str 945  Dex 750  Agi 525
3) Flandal Steelskin (Warrior)  Lvl 1  HP 100  MP 200  Gold 2500  XP 7  Str 787  Dex 700  Agi 682
4) Undefeated Yoj (Warrior)  Lvl 1  HP 100  MP 400  Gold 2500  XP 7  Str 840  Dex 700  Agi 420
5) Eunoia Cyn (Warrior)  Lvl 1  HP 100  MP 400  Gold 2500  XP 6  Str 735  Dex 600  Agi 840
6) Rillifane Rallathil (Sorcerer)  Lvl 1  HP 100  MP 1300  Gold 2500  XP 9  Str 750  Dex 525  Agi 472
7) Segojan Earthcaller (Sorcerer)  Lvl 1  HP 100  MP 900  Gold 2500  XP 5  Str 800  Dex 682  Agi 525
8) Reign Havoc (Sorcerer)  Lvl 1  HP 100  MP 800  Gold 2500  XP 8  Str 800  Dex 840  Agi 840
9) Reverie Ashels (Sorcerer)  Lvl 1  HP 100  MP 900  Gold 2500  XP 7  Str 800  Dex 420  Agi 735
10) Kalabar (Sorcerer)  Lvl 1  HP 100  MP 800  Gold 2500  XP 6  Str 850  Dex 630  Agi 420
11) Skye Soar (Sorcerer)  Lvl 1  HP 100  MP 1000  Gold 2500  XP 5  Str 700  Dex 525  Agi 420
12) Parzival (Paladin)  Lvl 1  HP 100  MP 300  Gold 2500  XP 7  Str 787  Dex 735  Agi 650
13) Sehanine Moonbow (Paladin)  Lvl 1  HP 100  MP 300  Gold 2500  XP 7  Str 787  Dex 735  Agi 700
14) Skoraeus Stonebones (Paladin)  Lvl 1  HP 100  MP 250  Gold 2500  XP 4  Str 682  Dex 367  Agi 600
15) Garl Glittergold (Paladin)  Lvl 1  HP 100  MP 100  Gold 2500  XP 5  Str 630  Dex 420  Agi 500
16) Amaryllis Astra (Paladin)  Lvl 1  HP 100  MP 500  Gold 2500  XP 5  Str 525  Dex 525  Agi 500
17) Caliber Heist (Paladin)  Lvl 1  HP 100  MP 400  Gold 2500  XP 8  Str 420  Dex 420  Agi 400
    Hero number: 6
    Rillifane Rallathil joins the party!

+--+--+--+--+--+--+--+--+
|P |  |M |  |  |  |M |  |
+--+--+--+--+--+--+--+--+
|M |  |  |  |  |  |M |M |
+--+--+--+--+--+--+--+--+
|X |M |M |  |  |M |  |  |
+--+--+--+--+--+--+--+--+
|  |M |  |  |  |M |  |  |
+--+--+--+--+--+--+--+--+
|  |M |M |  |M |  |  |  |
+--+--+--+--+--+--+--+--+
|  |M |X |  |X |  |X |M |
+--+--+--+--+--+--+--+--+
|  |  |M |M |X |  |  |M |
+--+--+--+--+--+--+--+--+
|  |X |M |X |  |M |  |M |
+--+--+--+--+--+--+--+--+
P = your party   M = market   X = blocked

Enter a command (W/A/S/D to move, I = info, E = inventory, M = market, P = map, H = help, Q = quit): i

Gaerdal Ironhand (Warrior)  Lvl 1  HP 100  MP 100  Gold 1354  XP 7  Str 735  Dex 600  Agi 525
Rillifane Rallathil (Sorcerer)  Lvl 1  HP 100  MP 1300  Gold 2500  XP 9  Str 750  Dex 525  Agi 472

Enter a command (W/A/S/D to move, I = info, E = inventory, M = market, P = map, H = help, Q = quit): s

+--+--+--+--+--+--+--+--+
|  |  |M |  |  |  |M |  |
+--+--+--+--+--+--+--+--+
|P |  |  |  |  |  |M |M |
+--+--+--+--+--+--+--+--+
|X |M |M |  |  |M |  |  |
+--+--+--+--+--+--+--+--+
|  |M |  |  |  |M |  |  |
+--+--+--+--+--+--+--+--+
|  |M |M |  |M |  |  |  |
+--+--+--+--+--+--+--+--+
|  |M |X |  |X |  |X |M |
+--+--+--+--+--+--+--+--+
|  |  |M |M |X |  |  |M |
+--+--+--+--+--+--+--+--+
|  |X |M |X |  |M |  |M |
+--+--+--+--+--+--+--+--+
P = your party   M = market   X = blocked
You've reached a market! Press M to go inside.

Enter a command (W/A/S/D to move, I = info, E = inventory, M = market, P = map, H = help, Q = quit): m

===== WELCOME TO THE MARKET =====

Who is shopping?
1) Gaerdal Ironhand - 1354 gold
2) Rillifane Rallathil - 2500 gold
0) Leave the market
   Choice: 1

Gaerdal Ironhand (Lvl 1) has 1354 gold.
1) Buy
2) Sell
3) Repair
0) Done
   Choice: 1

Items for sale:
1) Bow  (Lvl 2, 300 gold, 10/10 uses)  Damage 500, 2-handed
2) Scythe  (Lvl 6, 1000 gold, 10/10 uses)  Damage 1100, 2-handed
3) Axe  (Lvl 5, 550 gold, 10/10 uses)  Damage 850, 1-handed
4) TSwords  (Lvl 8, 1400 gold, 10/10 uses)  Damage 1600, 2-handed
5) Dagger  (Lvl 1, 200 gold, 10/10 uses)  Damage 250, 1-handed
6) Platinum Shield  (Lvl 1, 150 gold, 10/10 uses)  Reduces damage by 200
7) Breastplate  (Lvl 3, 350 gold, 10/10 uses)  Reduces damage by 600
8) Full Body Armor  (Lvl 8, 1000 gold, 10/10 uses)  Reduces damage by 1100
9) Guardian Angel  (Lvl 10, 1000 gold, 10/10 uses)  Reduces damage by 1000
10) Healing Potion  (Lvl 1, 250 gold, 1/1 uses)  +100 Health
11) Strength Potion  (Lvl 1, 200 gold, 1/1 uses)  +75 Strength
12) Magic Potion  (Lvl 2, 350 gold, 1/1 uses)  +100 Mana
13) Luck Elixir  (Lvl 4, 500 gold, 1/1 uses)  +65 Agility
14) Mermaid Tears  (Lvl 5, 850 gold, 1/1 uses)  +100 Health/Mana/Strength/Agility
15) Ambrosia  (Lvl 8, 1000 gold, 1/1 uses)  +150 Health/Mana/Strength/Dexterity/Defense/Agility
16) Flame Tornado  (Lvl 4, 700 gold, 1/1 uses)  Damage 850, Mana 300, lowers the target's defense
17) Breath of Fire  (Lvl 1, 350 gold, 1/1 uses)  Damage 450, Mana 100, lowers the target's defense
18) Snow Cannon  (Lvl 2, 500 gold, 1/1 uses)  Damage 650, Mana 250, lowers the target's damage
19) Thunder Blast  (Lvl 4, 750 gold, 1/1 uses)  Damage 950, Mana 400, lowers the target's dodge chance
20) Spark Needles  (Lvl 2, 500 gold, 1/1 uses)  Damage 600, Mana 200, lowers the target's dodge chance
    Item to buy (0 to cancel): 5
    Gaerdal Ironhand bought Dagger.

Gaerdal Ironhand (Lvl 1) has 1154 gold.
1) Buy
2) Sell
3) Repair
0) Done
   Choice: 0

Who is shopping?
1) Gaerdal Ironhand - 1154 gold
2) Rillifane Rallathil - 2500 gold
0) Leave the market
   Choice: 2

Rillifane Rallathil (Lvl 1) has 2500 gold.
1) Buy
2) Sell
3) Repair
0) Done
   Choice: 1

Items for sale:
1) Bow  (Lvl 2, 300 gold, 10/10 uses)  Damage 500, 2-handed
2) Scythe  (Lvl 6, 1000 gold, 10/10 uses)  Damage 1100, 2-handed
3) Axe  (Lvl 5, 550 gold, 10/10 uses)  Damage 850, 1-handed
4) TSwords  (Lvl 8, 1400 gold, 10/10 uses)  Damage 1600, 2-handed
5) Platinum Shield  (Lvl 1, 150 gold, 10/10 uses)  Reduces damage by 200
6) Breastplate  (Lvl 3, 350 gold, 10/10 uses)  Reduces damage by 600
7) Full Body Armor  (Lvl 8, 1000 gold, 10/10 uses)  Reduces damage by 1100
8) Guardian Angel  (Lvl 10, 1000 gold, 10/10 uses)  Reduces damage by 1000
9) Healing Potion  (Lvl 1, 250 gold, 1/1 uses)  +100 Health
10) Strength Potion  (Lvl 1, 200 gold, 1/1 uses)  +75 Strength
11) Magic Potion  (Lvl 2, 350 gold, 1/1 uses)  +100 Mana
12) Luck Elixir  (Lvl 4, 500 gold, 1/1 uses)  +65 Agility
13) Mermaid Tears  (Lvl 5, 850 gold, 1/1 uses)  +100 Health/Mana/Strength/Agility
14) Ambrosia  (Lvl 8, 1000 gold, 1/1 uses)  +150 Health/Mana/Strength/Dexterity/Defense/Agility
15) Flame Tornado  (Lvl 4, 700 gold, 1/1 uses)  Damage 850, Mana 300, lowers the target's defense
16) Breath of Fire  (Lvl 1, 350 gold, 1/1 uses)  Damage 450, Mana 100, lowers the target's defense
17) Snow Cannon  (Lvl 2, 500 gold, 1/1 uses)  Damage 650, Mana 250, lowers the target's damage
18) Thunder Blast  (Lvl 4, 750 gold, 1/1 uses)  Damage 950, Mana 400, lowers the target's dodge chance
19) Spark Needles  (Lvl 2, 500 gold, 1/1 uses)  Damage 600, Mana 200, lowers the target's dodge chance
    Item to buy (0 to cancel): 16
    Rillifane Rallathil bought Breath of Fire.

Rillifane Rallathil (Lvl 1) has 2150 gold.
1) Buy
2) Sell
3) Repair
0) Done
   Choice: 0

Who is shopping?
1) Gaerdal Ironhand - 1154 gold
2) Rillifane Rallathil - 2150 gold
0) Leave the market
   Choice: 0
   You leave the market.

Enter a command (W/A/S/D to move, I = info, E = inventory, M = market, P = map, H = help, Q = quit): e

Whose inventory?
1) Gaerdal Ironhand
2) Rillifane Rallathil
0) Back
   Choice: 1

Gaerdal Ironhand's inventory:
Weapon: none
Armor:  none
1) Dagger  (Lvl 1, 200 gold, 10/10 uses)  Damage 250, 1-handed
   Item to equip/use (0 to go back): 1
   Gaerdal Ironhand equipped Dagger.

Enter a command (W/A/S/D to move, I = info, E = inventory, M = market, P = map, H = help, Q = quit): h

==================== MONSTERS AND HEROES ====================
Lead a party of up to 3 heroes across a dangerous world.
Buy gear at markets, fight monsters, and level up your heroes.

CONTROLS (type a letter and press Enter):
W = move up        A = move left
S = move down      D = move right
I = show your heroes' info
E = inventory: equip weapons/armor or drink potions
M = enter the market (only when standing on an M)
P = show the map
H = show these instructions again
Q = quit the game

MAP:  P = your party   M = market   X = blocked   (blank) = open ground

BATTLES:
Monsters may attack when you walk on open ground!
On each hero's turn, pick a number to attack, cast a spell,
drink a potion, or change gear. Win to earn gold and XP.
If all your heroes faint, the game is over.
=============================================================

Enter a command (W/A/S/D to move, I = info, E = inventory, M = market, P = map, H = help, Q = quit): p

+--+--+--+--+--+--+--+--+
|  |  |M |  |  |  |M |  |
+--+--+--+--+--+--+--+--+
|P |  |  |  |  |  |M |M |
+--+--+--+--+--+--+--+--+
|X |M |M |  |  |M |  |  |
+--+--+--+--+--+--+--+--+
|  |M |  |  |  |M |  |  |
+--+--+--+--+--+--+--+--+
|  |M |M |  |M |  |  |  |
+--+--+--+--+--+--+--+--+
|  |M |X |  |X |  |X |M |
+--+--+--+--+--+--+--+--+
|  |  |M |M |X |  |  |M |
+--+--+--+--+--+--+--+--+
|  |X |M |X |  |M |  |M |
+--+--+--+--+--+--+--+--+
P = your party   M = market   X = blocked

Enter a command (W/A/S/D to move, I = info, E = inventory, M = market, P = map, H = help, Q = quit): d

+--+--+--+--+--+--+--+--+
|  |  |M |  |  |  |M |  |
+--+--+--+--+--+--+--+--+
|M |P |  |  |  |  |M |M |
+--+--+--+--+--+--+--+--+
|X |M |M |  |  |M |  |  |
+--+--+--+--+--+--+--+--+
|  |M |  |  |  |M |  |  |
+--+--+--+--+--+--+--+--+
|  |M |M |  |M |  |  |  |
+--+--+--+--+--+--+--+--+
|  |M |X |  |X |  |X |M |
+--+--+--+--+--+--+--+--+
|  |  |M |M |X |  |  |M |
+--+--+--+--+--+--+--+--+
|  |X |M |X |  |M |  |M |
+--+--+--+--+--+--+--+--+
P = your party   M = market   X = blocked

Enter a command (W/A/S/D to move, I = info, E = inventory, M = market, P = map, H = help, Q = quit): d

+--+--+--+--+--+--+--+--+
|  |  |M |  |  |  |M |  |
+--+--+--+--+--+--+--+--+
|M |  |P |  |  |  |M |M |
+--+--+--+--+--+--+--+--+
|X |M |M |  |  |M |  |  |
+--+--+--+--+--+--+--+--+
|  |M |  |  |  |M |  |  |
+--+--+--+--+--+--+--+--+
|  |M |M |  |M |  |  |  |
+--+--+--+--+--+--+--+--+
|  |M |X |  |X |  |X |M |
+--+--+--+--+--+--+--+--+
|  |  |M |M |X |  |  |M |
+--+--+--+--+--+--+--+--+
|  |X |M |X |  |M |  |M |
+--+--+--+--+--+--+--+--+
P = your party   M = market   X = blocked

Enter a command (W/A/S/D to move, I = info, E = inventory, M = market, P = map, H = help, Q = quit): d

+--+--+--+--+--+--+--+--+
|  |  |M |  |  |  |M |  |
+--+--+--+--+--+--+--+--+
|M |  |  |P |  |  |M |M |
+--+--+--+--+--+--+--+--+
|X |M |M |  |  |M |  |  |
+--+--+--+--+--+--+--+--+
|  |M |  |  |  |M |  |  |
+--+--+--+--+--+--+--+--+
|  |M |M |  |M |  |  |  |
+--+--+--+--+--+--+--+--+
|  |M |X |  |X |  |X |M |
+--+--+--+--+--+--+--+--+
|  |  |M |M |X |  |  |M |
+--+--+--+--+--+--+--+--+
|  |X |M |X |  |M |  |M |
+--+--+--+--+--+--+--+--+
P = your party   M = market   X = blocked

Enter a command (W/A/S/D to move, I = info, E = inventory, M = market, P = map, H = help, Q = quit): s

+--+--+--+--+--+--+--+--+
|  |  |M |  |  |  |M |  |
+--+--+--+--+--+--+--+--+
|M |  |  |  |  |  |M |M |
+--+--+--+--+--+--+--+--+
|X |M |M |P |  |M |  |  |
+--+--+--+--+--+--+--+--+
|  |M |  |  |  |M |  |  |
+--+--+--+--+--+--+--+--+
|  |M |M |  |M |  |  |  |
+--+--+--+--+--+--+--+--+
|  |M |X |  |X |  |X |M |
+--+--+--+--+--+--+--+--+
|  |  |M |M |X |  |  |M |
+--+--+--+--+--+--+--+--+
|  |X |M |X |  |M |  |M |
+--+--+--+--+--+--+--+--+
P = your party   M = market   X = blocked

Enter a command (W/A/S/D to move, I = info, E = inventory, M = market, P = map, H = help, Q = quit): s

+--+--+--+--+--+--+--+--+
|  |  |M |  |  |  |M |  |
+--+--+--+--+--+--+--+--+
|M |  |  |  |  |  |M |M |
+--+--+--+--+--+--+--+--+
|X |M |M |  |  |M |  |  |
+--+--+--+--+--+--+--+--+
|  |M |  |P |  |M |  |  |
+--+--+--+--+--+--+--+--+
|  |M |M |  |M |  |  |  |
+--+--+--+--+--+--+--+--+
|  |M |X |  |X |  |X |M |
+--+--+--+--+--+--+--+--+
|  |  |M |M |X |  |  |M |
+--+--+--+--+--+--+--+--+
|  |X |M |X |  |M |  |M |
+--+--+--+--+--+--+--+--+
P = your party   M = market   X = blocked

!!!!! MONSTERS ATTACK !!!!!
Natsunomeryu (Dragon)  Lvl 1  HP 100  Damage 110  Defense 200  Dodge 10%
Casper (Spirit)  Lvl 1  HP 100  Damage 100  Defense 100  Dodge 55%

--- Gaerdal Ironhand's turn (HP 100, MP 100) ---
1) Attack
2) Cast a spell
3) Drink a potion
4) Change weapon/armor
5) Show info (doesn't use your turn)
0) Quit the game
   Choice: 1
   Choose a target:
1) Natsunomeryu (Dragon)  Lvl 1  HP 100  Damage 110  Defense 200  Dodge 10%
2) Casper (Spirit)  Lvl 1  HP 100  Damage 100  Defense 100  Dodge 55%
   Target (0 to cancel): 1
   Gaerdal Ironhand hit Natsunomeryu for 39 damage.

--- Rillifane Rallathil's turn (HP 100, MP 1300) ---
1) Attack
2) Cast a spell
3) Drink a potion
4) Change weapon/armor
5) Show info (doesn't use your turn)
0) Quit the game
   Choice: 2
1) Breath of Fire  (Lvl 1, 350 gold, 1/1 uses)  Damage 450, Mana 100, lowers the target's defense
   Spell to cast (0 to cancel): 1
   Choose a target:
1) Natsunomeryu (Dragon)  Lvl 1  HP 61  Damage 110  Defense 200  Dodge 10%
2) Casper (Spirit)  Lvl 1  HP 100  Damage 100  Defense 100  Dodge 55%
   Target (0 to cancel): 1
   Rillifane Rallathil cast Breath of Fire on Natsunomeryu for 463 damage. It lowers the target's defense!
   Natsunomeryu has been defeated!
   Casper hit Rillifane Rallathil for 10 damage.

--- Gaerdal Ironhand's turn (HP 100, MP 100) ---
1) Attack
2) Cast a spell
3) Drink a potion
4) Change weapon/armor
5) Show info (doesn't use your turn)
0) Quit the game
   Choice: 1
   Gaerdal Ironhand hit Casper for 44 damage.

--- Rillifane Rallathil's turn (HP 99, MP 1300) ---
1) Attack
2) Cast a spell
3) Drink a potion
4) Change weapon/armor
5) Show info (doesn't use your turn)
0) Quit the game
   Choice: 1
   Casper dodged Rillifane Rallathil's attack!
   Casper hit Rillifane Rallathil for 10 damage.

--- Gaerdal Ironhand's turn (HP 100, MP 100) ---
1) Attack
2) Cast a spell
3) Drink a potion
4) Change weapon/armor
5) Show info (doesn't use your turn)
0) Quit the game
   Choice: 1
   Gaerdal Ironhand hit Casper for 44 damage.

--- Rillifane Rallathil's turn (HP 97, MP 1300) ---
1) Attack
2) Cast a spell
3) Drink a potion
4) Change weapon/armor
5) Show info (doesn't use your turn)
0) Quit the game
   Choice: 5

HEROES:
Gaerdal Ironhand  Lvl 1  HP 100  MP 100  Weapon: Dagger  Armor: none
Rillifane Rallathil  Lvl 1  HP 97  MP 1300  Weapon: none  Armor: none
MONSTERS:
Natsunomeryu (Dragon)  Lvl 1  HP 0  Damage 110  Defense 180  Dodge 10%
Casper (Spirit)  Lvl 1  HP 12  Damage 100  Defense 100  Dodge 55%

--- Rillifane Rallathil's turn (HP 97, MP 1300) ---
1) Attack
2) Cast a spell
3) Drink a potion
4) Change weapon/armor
5) Show info (doesn't use your turn)
0) Quit the game
   Choice: 1
   Rillifane Rallathil hit Casper for 32 damage.
   Casper has been defeated!

***** VICTORY! *****
Gaerdal Ironhand earned 100 gold and 4 XP.
Gaerdal Ironhand reached level 2!
Rillifane Rallathil earned 100 gold and 4 XP.
Rillifane Rallathil reached level 2!

Enter a command (W/A/S/D to move, I = info, E = inventory, M = market, P = map, H = help, Q = quit): i

Gaerdal Ironhand (Warrior)  Lvl 2  HP 200  MP 110  Gold 1254  XP 1  Str 809  Dex 630  Agi 578
Rillifane Rallathil (Sorcerer)  Lvl 2  HP 200  MP 1430  Gold 2250  XP 3  Str 787  Dex 578  Agi 519

Enter a command (W/A/S/D to move, I = info, E = inventory, M = market, P = map, H = help, Q = quit): q
Thanks for playing Monsters and Heroes!
Would you like to play another game? (Y/N)
n

Process finished with exit code 0


