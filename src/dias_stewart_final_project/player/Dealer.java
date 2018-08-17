
package dias_stewart_final_project.player;

import dias_stewart_final_project.game.blackjack.Card;
import dias_stewart_final_project.game.blackjack.Deck;

/**
 * Extends Player to model a Dealer who has a deck.
 * @author Laura Stewart
 */
public class Dealer extends Player {
    
    private Deck deck = new Deck();
    
    /**
     * Calls the superclass to make a default Dealer.
     */
    public Dealer() {
        
        super("Dealer");
        
    }
    
    /**
     * Takes a card from the deck to deal to a Player.
     * @return A card pulled from the deck.
     */
    public Card dealOne() {
        
        return deck.drawOne();
        
    }
    
    /**
     * Deals a card to its own hand.
     */
    public void drawCard() {
        
        hand.add(deck.drawOne());
        
    }
    
    /**
     * Resets the Dealer's deck by instantiating a new Deck.
     */
    public void resetDeck() {
        
        deck = new Deck();
        
    }

}
