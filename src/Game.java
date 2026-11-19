import java.util.Scanner;

// Used to handle any general game
// Stores the shared input scanner, defines the play method each game implements,
// and runs the game menu

public abstract class Game {
    protected Scanner input = new Scanner(System.in);

    public abstract void play();

    public static void main(String[] args) {
        runGames();
    }

    private static void runGames() {
        Scanner input = new Scanner(System.in);

        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");
        boolean gameOngoing = true;
        boolean playedTwoPlayerGame = false;

        while(gameOngoing) {
            System.out.println("Please choose a game:");
            System.out.println("1: Tic-Tac-Toe");
            System.out.println("2: Order and Chaos");
            System.out.println("3: Super Tic-Tac-Toe");
            System.out.println("4: Monsters and Heroes");
            System.out.println("5: Legends of Valor");

            String choice = input.nextLine();
            while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4") && !choice.equals("5")) {
                System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
                choice = input.nextLine();
            }

            Game game;
            if(choice.equals("1")) {
                game = new TicTacToe(p1,p2);
            }
            else if (choice.equals("2")){
                game = new OrderAndChaos(p1,p2);
            }
            else if (choice.equals("3")){
                game = new SuperTicTacToe(p1,p2);
            }
            else if (choice.equals("4")) {
                game = new MonstersAndHeroes();
            }
            else {
                game = new LegendsOfValor();
            }

            game.play();
            if (game instanceof TwoPlayerGame) {
                playedTwoPlayerGame = true;
            }

            String newGame = "";
            System.out.println("Would you like to play another game? (Y/N)");
            newGame = input.nextLine().toUpperCase();

            if(!newGame.equals("Y")) {
                gameOngoing = false;
            }
        }
        if (playedTwoPlayerGame) {
            System.out.println("Final Win Tally:");
            System.out.println("Player 1: " + p1.getWins());
            System.out.println("Player 2: " + p2.getWins());
        }
    }
}
