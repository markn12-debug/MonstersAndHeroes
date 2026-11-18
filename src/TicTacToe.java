//Used to create a Tic-Tac-Toe-Game
// Handles gameplay and determines who wins on an nxn board

public class TicTacToe extends TwoPlayerGame {
    private Board board;

    public TicTacToe(Player p1, Player p2) {
        super(p1,p2);
    }

    public void play() {
        String nextMove = "";

        p1.setMarker(new Marker('X'));
        p2.setMarker(new Marker('O'));

        System.out.println("Welcome to the game! Let's play Tic Tac Toe!");
        System.out.println("What size board would you like? (3-10)");
        int size = 0;

        while (size < 3 || size > 10) {
            try {
                size = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                size = 0;
            }
            if (size < 3 || size > 10) {
                System.out.println("Invalid size. Please choose a size from 3-10.");
            }
        }
        board = new Board(size);

        boolean gameOngoing = true;
        boolean p1Turn = true;
        while (gameOngoing) {
            System.out.println(board);
            if (p1Turn) {
                System.out.println("Your turn, " + p1.getName());
                System.out.println("Make your move.");
                nextMove = input.nextLine();
                boolean validMove = board.checkValidMove(nextMove);
                while(!validMove) {
                    System.out.println("Sorry, that move is invalid or that box was already taken!");
                    System.out.println("Please choose another box.");
                    nextMove = input.nextLine();
                    validMove = board.checkValidMove(nextMove);
                }
                board.move(p1.getMarker(),nextMove);
                p1Turn = false;

            } else {
                System.out.println("Your turn, " + p2.getName());
                System.out.println("Make your move.");
                nextMove = input.nextLine();
                boolean validMove = board.checkValidMove(nextMove);
                while(!validMove) {
                    System.out.println("Sorry, that move is invalid or that box was already taken!");
                    System.out.println("Please choose another box.");
                    nextMove = input.nextLine();
                    validMove = board.checkValidMove(nextMove);
                }
                board.move(p2.getMarker(),nextMove);
                p1Turn = true;
            }
            if(TicTacToeWinChecker.checkWin(board,p1)) {
                System.out.println(board);
                System.out.println("Congratulations, " + p1.getName() + "!");
                p1.addWin();
                gameOngoing = false;
            }
            else if(TicTacToeWinChecker.checkWin(board,p2)) {
                System.out.println(board);
                System.out.println("Congratulations, " + p2.getName() + "!");
                p2.addWin();
                gameOngoing = false;
            }
            else if(board.isFull()) {
                System.out.println(board);
                System.out.println("Game ended in a Draw!");
                gameOngoing = false;
            }
        }

    }

}

