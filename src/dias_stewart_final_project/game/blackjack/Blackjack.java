
package dias_stewart_final_project.game.blackjack;

import dias_stewart_final_project.game.Playable;
import dias_stewart_final_project.player.Dealer;
import dias_stewart_final_project.player.Player;
import java.util.ArrayList;

/**
 * Models a game of Blackjack with one Player and a Dealer.
 * @author Laura Stewart
 */
public class Blackjack implements Playable {
    
    private Player player;
    private Dealer dealer;
    private double moneyMultiplier;
    
    private int playerTotal = 0;
    private int dealerTotal = 0;
    
    public static final double BET = 15.0;
    
    /**
     * Creates a Blackjack game with a player and a dealer.
     * @param player
     */
    public Blackjack(Player player) {
        this.player = player;
        dealer = new Dealer();
    }
    
    /**
     * Gets a copy of the player's hand.
     * @return The player's hand as an ArrayList.
     */
    public ArrayList<Card> getPlayerHand() {
        return player.getHand();
    }
    
    /**
     * Gets a copy of the dealer's hand.
     * @return The dealer's hand as an ArrayList.
     */
    public ArrayList<Card> getDealerHand() {
        return dealer.getHand();
    }
    
    /**
     * Returns the total value of the player's hand.
     * @return Value of the player's hand.
     */
    public int getPlayerTotal() {
        return playerTotal;
    }
    
    /**
     * Returns the total value of the dealer's hand.
     * @return Value of dealer's hand.
     */
    public int getDealerTotal() {
        return dealerTotal;
    }

    /**
     * Override method from Playable to start or reset the game.
     */
    @Override
    public void start() {
       
        // Reset the dealer's deck and hand
        dealer.resetDeck();
        dealer.clearHand();
        
        // Clear the player's hand
        player.clearHand();
        
        // The player places a bet (currently the default amount)
        player.bet(BET);
        
        // The dealer deals two cards to the player and dealer
        for(int i = 0; i < 2; i++) {
            player.addCard(dealer.dealOne());
        dealer.drawCard();
        }
        
        // Calculate their inital hand totals
        calcHandTotals();
        
    }
    
    /**
     * The dealer deals another card to the player and hand totals are recalculated.
     */
    public void hit() {
        player.addCard(dealer.dealOne());
        calcHandTotals();
    }
    
    /**
     * The dealer takes their turn when the player decides to stand.
     */
    public void stand() {
        
        // The dealer stands if the player went over 21 (the dealer automatically wins)
        boolean dealerStand = playerTotal > 21;
        
        // The dealer draws cards until their total is over 17
        while (!dealerStand) {
            
            if (dealer.calcTotal() < 17)
                dealer.drawCard();
            else
                dealerStand = true;
             
        }
        
        // Recalculate hand totals
        calcHandTotals();
        
    }
    
    /**
     * The player surrenders to get half their money back.
     * @return The amount of money the player receives.
     */
    public double surrender()  {
        
        moneyMultiplier = 0.5;
        
        return  moneyMultiplier * BET;
        
    }
    
    /**
     * Calculates the dealer and player hand totals.
     */
    private void calcHandTotals() {
        
        dealerTotal = dealer.calcTotal();
        playerTotal = player.calcTotal();
        
    }
    
    /**
     * Compares the player and dealer hands to decide who wins.
     * @return A String saying who won. 
     */
    public String compare() {
        
        // Set playerWon to the appropriate number for calculating the amount won and return a String to say who won
        if(playerTotal <= 21 && (dealerTotal > 21 || playerTotal > dealerTotal)) {
            
            moneyMultiplier = 2;
            return "You win!";
            
        } else if (playerTotal > 21 || (dealerTotal <=21 && dealerTotal > playerTotal)){
            
            moneyMultiplier = 0;
            return "Dealer wins.";
            
        } else {
            
            moneyMultiplier = 1;
            return "It's a tie.";
            
        }
    }

    /**
     * Calculates how much money the player has won.
     * @return The amount won.
     */
    @Override
    public double moneyWon() {
        
        return BET * moneyMultiplier;
        
    }

}
