// Used for games played by two players
// Stores the two players shared by TicTacToe, OrderAndChaos, and SuperTicTacToe

public abstract class TwoPlayerGame extends Game {
    protected Player p1;
    protected Player p2;

    public TwoPlayerGame(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
}
