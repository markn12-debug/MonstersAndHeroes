// class used to check wins in Tic-Tac-Toe games
// Used for basic Tic-Tac-Toe and small Tic-Tac-Toe games in Super Tic-Tac-Toe

public class TicTacToeWinChecker {

    public static boolean checkWin(Board board, Player player) {
        int size = board.getSize();
        char pSymbol = player.getMarker().getSymbol();
        // checks each row for a win
        for(int i=0; i < size; i++) {
            int count = 0;
            for(int j=0; j < size; j++) {
                if(board.getCell(i, j).hasMarker(pSymbol)) {
                    count++;
                }
            }

            if(count == size) {
                return true;
            }
        }

        // checks each column for a win
        for(int i=0; i < size; i++) {
            int count = 0;

            for(int j=0; j < size; j++) {
                if(board.getCell(j, i).hasMarker(pSymbol)) {
                    count++;
                }
            }
            if(count == size) {
                return true;
            }
        }
        // checks diagonal from top-left to bottom-right
        int count = 0;
        for(int i=0; i < size; i++) {
            if(board.getCell(i, i).hasMarker(pSymbol)) {
                count++;
            }
        }
        if(count == size) {
            return true;
        }
        // checks diagonal from top-right to bottom-left
        count = 0;
        for (int i = 0; i < size; i++) {
            if (board.getCell(i, size - i - 1).hasMarker(pSymbol)) {
                count++;
            }
        }
        if (count == size) {
            return true;
        }

        return false;
    }
}
