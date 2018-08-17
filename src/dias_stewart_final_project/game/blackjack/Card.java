
package dias_stewart_final_project.game.blackjack;

import javafx.scene.image.Image;

/**
 * Models a single card for a game of Blackjack.
 * @author Laura Stewart
 */
public class Card {
    
    private Image face;
    private int value;
    private char suit;
    private Image back = new Image("images/b2fv.png");
    
    /**
     * Two-arg constructor for a Card with a red back.
     * @param value The card's numeric value from 1 to 13.
     * @param suit The first letter of the card's suit.
     */
    public Card(int value, char suit) {
        
        this.setValue(value);
        this.setSuit(suit);
        
        this.face = new Image("images/" + suit + value + ".png");
        
    }
    
    /**
     * Three-arg constructor for a Card with a custom back colour.
     * @param value The card's numeric value from 1 to 13
     * @param suit The first letter of the card's suit.
     * @param color The first letter of the color name for the back (red or blue).
     */
    public Card(int value, char suit, char color) {
        
        // Call the 2 argument constructor
        this(value, suit);
        
        // Set the back color to blue or red
        if(color == 'b') 
            this.back = new Image("images/b1fv.png");
        else
            this.back = new Image("images/b2fv.png");
        
    }

    /**
     * Return's the card's face.
     * @return The Image representing the Card's face.
     */
    public Image getFace() {
        return face;
    }

    /**
     * Returns the card's back Image
     * @return The Image representing the card's back.
     */
    public Image getBack() {
        return back;
    }

    /**
     * Returns the card's value
     * @return The card's value as an int
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value if it is between 1 and 13, inclusive. Values greater than 
     * 10 will be set to 10 for the purposes of Blackjack.
     * @param value 
     * @throws IllegalArgumentException
     */
    private void setValue(int value) {
        
        // Set the value or throw an exception
        if (value >= 1 && value <= 10)
            this.value = value;
        else if (value > 10 && value <= 13) 
            this.value = 10;
        else
            throw new IllegalArgumentException("Invalid value.");
        
    }

    /**
     * Returns the card's suit.
     * @return The first letter of the suit.
     */
    public char getSuit() {
        return suit;
    }

    /**
     * Sets the suit if it is equal to d, c, h, s
     * @param suit The new suit.
     * @throws IllegalArgumentException
     */
    private void setSuit(char suit) {
        
        // Set the suit if it is valid
        if (suit == 'd' || suit == 'c' || suit == 'h' || suit == 's')
            this.suit = suit;
        else
            throw new IllegalArgumentException("Invalid suit");
        
    }
    
}
