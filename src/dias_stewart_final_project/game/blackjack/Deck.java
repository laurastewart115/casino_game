
package dias_stewart_final_project.game.blackjack;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Models a deck of 52 cards for a game of Blackjack.
 * @author Laura Stewart
 */
public class Deck {
    
    private ArrayList<Card> deck = new ArrayList(52);
    
    private static final char[] SUITS = {'d', 'h', 'c', 's'};
    
    /**
     * Default constructor that makes a shuffled deck of 52 cards.
     */
    public Deck() {
        fillDeck();
    }
    
    /**
     * Shuffles the deck.
     */
    private void shuffle() {
        Collections.shuffle(deck);
    }
    
    /**
     * Removes one card from the deck and calls fillDeck() if it is almost empty.
     * @return The card that is removed.
     */
    public Card drawOne() {
        
        // make a new deck if it is almost empty
        if(deck.size() == 1) {
            deck.clear();
            fillDeck();
        }
        
        // return the card from the top of the deck
        return deck.remove(0);
        
    }
    
    /**
     * Fills the deck with 52 cards.
     */
    private void fillDeck() {
        
        // Loop for making all the cards from each suit
        for(int i = 0; i < SUITS.length; i++) {
            
            char suit = SUITS[i];
            
            // Make 13 cards for the current suit
            for(int j = 1; j <= 13; j++) {
            
                deck.add(new Card(j, suit));
                
            }
        }
        
        // shuffle the deck
        shuffle();
        
    }

}

