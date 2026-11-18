// Used to create a game of Order and Chaos on a 6x6 board
// Handles gameplay, such as symbol selection and winning rules

public class OrderAndChaos extends TwoPlayerGame {
    private Board board;

    public OrderAndChaos(Player p1, Player p2) {
        super(p1,p2);
    }
    public void play() {
        String nextMove = "";
        char symbol;
        board = new Board(6);

        boolean gameOngoing = true;
        boolean p1Turn = true;

        System.out.println("Welcome to the game! Let's play Order and Chaos!");
        System.out.println(p1.getName() + " is Order.");
        System.out.println(p2.getName() + " is Chaos.");

        while(gameOngoing) {
            System.out.println(board);

            if(p1Turn) {
                System.out.println("Your turn, " + p1.getName());
            }
            else {
                System.out.println("Your turn, " + p2.getName());
            }

            System.out.println("Make your move.");
            nextMove = input.nextLine();
            boolean validMove = board.checkValidMove(nextMove);

            while(!validMove) {
                System.out.println("Sorry, that move is invalid! Please pick a valid box.");
                nextMove = input.nextLine();
                validMove = board.checkValidMove(nextMove);
            }

            System.out.println("Would you like to place an X or O?");
            String symbolInput = input.nextLine().toUpperCase();
            while(!symbolInput.equals("X") && !symbolInput.equals("O")) {
                System.out.println("Sorry, that choice is invalid! Please enter X or O.");
                symbolInput = input.nextLine().toUpperCase();
            }
            symbol = symbolInput.charAt(0);

            board.move(new Marker(symbol), nextMove);

            if(checkWin()) {
                System.out.println(board);
                System.out.println("Order wins! Congratulations, " + p1.getName() + "!");
                p1.addWin();
                gameOngoing = false;
            }
            else if(board.isFull()) {
                System.out.println(board);
                System.out.println("Chaos wins! Congratulations, " + p2.getName() + "!");
                p2.addWin();
                gameOngoing = false;
            }
            else {
                p1Turn = !p1Turn;
            }
        }
    }

    public boolean checkWin() {
        int size = board.getSize();
        // checks for five matching symbols horizontally
        for(int i = 0; i < size; i++) {
            for(int j = 0; j <= size - 5; j++) {

                BoardCell cell = board.getCell(i,j);
                if (!cell.isEmpty()) {
                    char symbol = cell.getMarker().getSymbol();

                    if(board.getCell(i,j+1).hasMarker(symbol) &&
                        board.getCell(i,j+2).hasMarker(symbol) &&
                        board.getCell(i,j+3).hasMarker(symbol) &&
                        board.getCell(i,j+4).hasMarker(symbol)) {

                        return true;
                    }
                }
            }
        }
        // checks for matching symbols vertically
        for(int i = 0; i <= size - 5; i++) {
            for(int j = 0; j < size; j++) {
                BoardCell cell = board.getCell(i,j);

                if(!cell.isEmpty()) {
                    char symbol = cell.getMarker().getSymbol();

                    if (board.getCell(i + 1, j).hasMarker(symbol) &&
                        board.getCell(i + 2, j).hasMarker(symbol) &&
                        board.getCell(i + 3, j).hasMarker(symbol) &&
                        board.getCell(i + 4, j).hasMarker(symbol)) {

                        return true;
                    }
                }
            }
        }
        // checks diagonal from top-left to bottom-right
        for(int i = 0; i <= size - 5; i++) {
            for(int j = 0; j <= size - 5; j++) {
                BoardCell cell = board.getCell(i,j);

                if (!cell.isEmpty()) {
                    char symbol = cell.getMarker().getSymbol();

                    if (board.getCell(i + 1, j + 1).hasMarker(symbol) &&
                        board.getCell(i + 2, j + 2).hasMarker(symbol) &&
                        board.getCell(i + 3, j + 3).hasMarker(symbol) &&
                        board.getCell(i + 4, j + 4).hasMarker(symbol)) {

                        return true;
                    }
                }

            }
        }
        // checks diagonal from top-right to bottom-left
        for(int i = 0; i <= size - 5; i++) {
            for(int j = 4; j < size; j++) {

                BoardCell cell = board.getCell(i,j);

                if (!cell.isEmpty()) {
                    char symbol = cell.getMarker().getSymbol();

                    if (board.getCell(i + 1, j - 1).hasMarker(symbol) &&
                            board.getCell(i + 2, j - 2).hasMarker(symbol) &&
                            board.getCell(i + 3, j - 3).hasMarker(symbol) &&
                            board.getCell(i + 4, j - 4).hasMarker(symbol)) {

                        return true;
                    }
                }
            }
        }

        return false;
    }
}
