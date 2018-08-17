
package dias_stewart_final_project.game;

/**
 * An interface to be implemented by any casino game.
 * @author Laura Stewart
 */
public interface Playable {
   
    /**
     * Starts or resets a game by setting all game variables to their defaults.
     */
    void start();

    /**
     * Calculates and returns the amount of money won by the player.
     * @return The amount of money won in a game.
     */
    double moneyWon();
    
}
