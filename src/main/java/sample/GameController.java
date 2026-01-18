package sample;

/**
 * Manages the overall game state, turns, and win conditions
 */
public class GameController {
    private Player player1;
    private Player player2;
    private int gameRound;
    private boolean shipsComplete;
    private SoundManager soundManager;
    
    public GameController(SoundManager soundManager) {
        this.soundManager = soundManager;
        initializeGame();
    }
    
    private void initializeGame() {
        player1 = new Player();
        player2 = new Player();
        gameRound = 1;
        shipsComplete = false;
    }
    
    public void resetGame() {
        player1.area.removeAllShips();
        player2.area.removeAllShips();
        player1.ResetAttackPositions();
        player2.ResetAttackPositions();
        gameRound = 1;
        shipsComplete = false;
    }
    
    public boolean isCurrentPlayerTurn(Player player) {
        return (gameRound % 2 == 1 && player == player1) || 
               (gameRound % 2 == 0 && player == player2);
    }
    
    public Player getCurrentPlayer() {
        return (gameRound % 2 == 1) ? player1 : player2;
    }
    
    public Player getOpponentPlayer() {
        return (gameRound % 2 == 1) ? player2 : player1;
    }
    
    public void nextTurn() {
        gameRound++;
    }
    
    public boolean areShipsComplete() {
        return shipsComplete;
    }
    
    public void checkShipsComplete() {
        shipsComplete = player1.area.isFleetComplete() && player2.area.isFleetComplete();
    }
    
    public boolean isGameOver() {
        return player1.area.gameOver() || player2.area.gameOver();
    }
    
    public Player getWinner() {
        if (player1.area.gameOver()) {
            return player2; // Player 2 wins
        } else if (player2.area.gameOver()) {
            return player1; // Player 1 wins
        }
        return null; // No winner yet
    }
    
    public String getWinnerName() {
        Player winner = getWinner();
        if (winner == player1) {
            return "Spieler 1";
        } else if (winner == player2) {
            return "Spieler 2";
        }
        return null;
    }
    
    public void handleFleetComplete(Player player) {
        nextTurn();
        checkShipsComplete();
    }
    
    // Getters
    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public int getGameRound() { return gameRound; }
    public SoundManager getSoundManager() { return soundManager; }
}