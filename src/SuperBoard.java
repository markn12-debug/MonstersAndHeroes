import java.util.HashMap;

// Creates a 3x3 array of Board objects to make one "SuperBoard"
// Ensures moves are valid and keeps track of winning boards
public class SuperBoard {
    private Board[][] boards;
    private Board winnerBoard;
    private HashMap<Character, Integer> games;

    public SuperBoard() {
        boards = new Board[3][3];
        winnerBoard = new Board(3);
        games = new HashMap<>();

        games.put('A', 1);
        games.put('B', 2);
        games.put('C', 3);
        games.put('D', 4);
        games.put('E', 5);
        games.put('F', 6);
        games.put('G', 7);
        games.put('H', 8);
        games.put('I', 9);

        for(int i=0; i < 3; i++) {
            for(int j=0; j < 3; j++) {
                boards[i][j] = new Board(3);
            }
        }
    }

    public String toString() {
        String board = "";

        for(int i=0; i < 3; i++) {
            for(int j=0; j < 3; j++) {
                for(int k=0; k<3; k++) {
                    for(int l=0; l < 3; l++) {
                        board += "+--";
                    }
                    board += "+ ";
                }
                board += "\n";

                for(int k=0; k < 3; k++) {
                    for(int l=0; l < 3; l++) {
                        BoardCell cell = boards[i][k].getCell(j,l);

                        if (cell.isEmpty()) {
                            board += "|  ";
                        } else {
                            board += "|" + cell.getMarker().getSymbol() + " ";
                        }
                    }
                    board += "| ";
                }
                board += "\n";
            }

            for(int k = 0; k < 3; k++) {
                for(int l=0; l < 3; l++) {
                    board += "+--";
                }
                board += "+ ";
            }
            board += "\n";
        }

        return board;
    }

    public void move(Marker marker, char game, String box) {
        getBoard(game).move(marker, box);

    }

    public boolean checkValidMove(char game, String box) {
        return checkValidGame(game) && getBoard(game).checkValidMove(box);
    }

    public Board getBoard(char game) {
        int gameNum = games.get(game);
        int row = (gameNum - 1) / 3;
        int col = (gameNum - 1) % 3;

        return boards[row][col];
    }

    public Board getWinnerBoard() {
        return winnerBoard;
    }

    public boolean checkValidGame(char game) {
        if (!games.containsKey(game)) {
            return false;
        }

        int gameNum = games.get(game);

        return winnerBoard.checkValidMove(String.valueOf(gameNum))
                && !getBoard(game).isFull();
    }

    public int getGameNum(char game) {
        return games.get(game);
    }

    public boolean isFull() {
        for (char game : games.keySet()) {
            if (checkValidGame(game)) {
                return false;
            }
        }

        return true;
    }

}
