
// Used to create a Super Tic-Tac-Toe game
// Handles gameplay and determines who wins / if game ends in a draw
public class SuperTicTacToe extends TwoPlayerGame {
    private SuperBoard superBoard;

    public SuperTicTacToe(Player p1, Player p2) {
        super(p1,p2);
    }

    public void play() {
        String nextMove = "";
        char game;

        p1.setMarker(new Marker('X'));
        p2.setMarker(new Marker('O'));
        superBoard = new SuperBoard();

        System.out.println("Welcome to Super Tic-Tac-Toe!");

        boolean gameOngoing = true;
        boolean p1Turn = true;
        while(gameOngoing) {
            System.out.println(superBoard);
            if(p1Turn) {
                System.out.println("Your turn, " + p1.getName());
                System.out.println("Choose a game to add your marker (A-I).");
                String gameInput = input.nextLine().trim().toUpperCase();

                boolean validGame = gameInput.length() == 1
                        && superBoard.checkValidGame(gameInput.charAt(0));

                while(!validGame) {
                    System.out.println("Sorry, that game is invalid or was already completed!");
                    System.out.println("Choose another game (A-I).");
                    gameInput = input.nextLine().trim().toUpperCase();

                    validGame = gameInput.length() == 1
                            && superBoard.checkValidGame(gameInput.charAt(0));
                }

                game = gameInput.charAt(0);

                System.out.println("Make your move.");
                nextMove = input.nextLine();
                boolean validMove = superBoard.checkValidMove(game, nextMove);

                while(!validMove) {
                    System.out.println("Sorry, that move is invalid or was already taken!");
                    System.out.println("Make your move.");
                    nextMove = input.nextLine();

                    validMove = superBoard.checkValidMove(game, nextMove);
                }

                superBoard.move(p1.getMarker(), game, nextMove);

                Board smallBoard = superBoard.getBoard(game);

                if (TicTacToeWinChecker.checkWin(smallBoard, p1)) {
                    superBoard.getWinnerBoard().move(p1.getMarker(), String.valueOf(superBoard.getGameNum(game)));
                    System.out.println(p1.getName() + " has won game " + game + "!");
                }
                else if (smallBoard.isFull()) {
                    System.out.println("Game " + game + " has ended in a draw!");
                }
                p1Turn = false;

            } else {
                System.out.println("Your turn, " + p2.getName());
                System.out.println("Choose a game to add your marker (A-I).");
                String gameInput = input.nextLine().trim().toUpperCase();

                boolean validGame = gameInput.length() == 1
                        && superBoard.checkValidGame(gameInput.charAt(0));

                while(!validGame) {
                    System.out.println("Sorry, that game is invalid or was already completed!");
                    System.out.println("Choose another game (A-I).");
                    gameInput = input.nextLine().trim().toUpperCase();

                    validGame = gameInput.length() == 1
                            && superBoard.checkValidGame(gameInput.charAt(0));
                }

                game = gameInput.charAt(0);

                System.out.println("Make your move.");
                nextMove = input.nextLine();
                boolean validMove = superBoard.checkValidMove(game, nextMove);

                while(!validMove) {
                    System.out.println("Sorry, that move is invalid!");
                    System.out.println("Make your move.");
                    nextMove = input.nextLine();

                    validMove = superBoard.checkValidMove(game, nextMove);
                }

                superBoard.move(p2.getMarker(), game, nextMove);

                Board smallBoard = superBoard.getBoard(game);

                if (TicTacToeWinChecker.checkWin(smallBoard, p2)) {
                    superBoard.getWinnerBoard().move(p2.getMarker(), String.valueOf(superBoard.getGameNum(game)));
                    System.out.println(p2.getName() + " has won game " + game + "!");
                }
                else if (smallBoard.isFull()) {
                    System.out.println("Game " + game + " has ended in a draw!");
                }

                p1Turn = true;
            }
            if(TicTacToeWinChecker.checkWin(superBoard.getWinnerBoard(),p1)) {
                System.out.println(superBoard);
                System.out.println("Congratulations, " + p1.getName() + "!");
                p1.addWin();
                gameOngoing = false;
            }
            else if(TicTacToeWinChecker.checkWin(superBoard.getWinnerBoard(),p2)) {
                System.out.println(superBoard);
                System.out.println("Congratulations, " + p2.getName() + "!");
                p2.addWin();
                gameOngoing = false;
            }
            else if (superBoard.isFull()) {
                System.out.println(superBoard);
                System.out.println("The game has ended in a draw!");
                gameOngoing = false;
            }
        }
    }

}
