
package dias_stewart_final_project.game.slots;

import dias_stewart_final_project.game.Playable;
import javafx.scene.image.Image;

/**
 * Models a slot machine.
 * @author Laura Stewart
 */
public class SlotMachine implements Playable {
    
    private final SlotRoll[] SLOT_ROLLS = new SlotRoll[3];
    private int[] resultValues = new int[3];
    private Image[][] displayImages = new Image[3][];
    private boolean isMatch = false;
    
    public static final double BET = 1.0;
    
    /**
     * Default constructor for building a slot machine with three rolls.
     */
    public SlotMachine() {
        
        // Initialize each SlotRoll
        for(int i = 0; i < SLOT_ROLLS.length; i++) {
            SLOT_ROLLS[i] = new SlotRoll();
        }
        
    }
    
    /**
     * Sets the images that will be displayed.
     */
    private void setDisplayImages() {
        
        // Set which images will be displayed on each slot roll (the selected image and the images before and after it)
        for(int i = 0; i < SLOT_ROLLS.length; i++) {
            displayImages[i] = SLOT_ROLLS[i].getSlotsToDisplay();
        }
        
    }
    
    /**
     * Returns the images to be displayed, including those before and after the selected slot from the roll.
     * @return A 2D array of the images.
     */
    public Image[][] getDisplayImages() {
     
        return displayImages;
        
    }
    
    /**
     * Spins all the slot rolls and sets the array of result values.
     */
    public void spin() {
        
        // Spin each SlotRoll and store the result
        for (int i = 0; i < resultValues.length; i++) {
            resultValues[i] = SLOT_ROLLS[i].spin().getValue();
        }
        
        // Update the images to be displayed
        setDisplayImages();
        
    }
    
    /**
     * Checks if the three slot rolls match.
     */
    public void checkIsMatch() {
        
        isMatch = resultValues[0] == resultValues[1] && resultValues[0] == resultValues[2];
 
    }

    /**
     * Starts the game by spinning the slots and setting isMatch to false.
     */
    @Override
    public void start() {
        spin();
        isMatch = false;
    }

    /**
     * Calculates and returns how much the player wins.
     * @return The amount of money won.
     */
    @Override
    public double moneyWon() {
        
        // Create the multiplier for the money
        double multiplier = 0;
        
        // Check if the symbols match using their values
        checkIsMatch();
        
        // if they match, decide how much money will be won
        if (isMatch) {
            
            // Set how much is won based on the value of the first slot (will be the same as the others because they match)
            switch(resultValues[0]) {
                case 1:
                    multiplier = 2.5;
                    break;
                case 2:
                    multiplier = 5.0;
                    break;
                case 3:
                    multiplier = 10.0;
                    break;
                case 4:
                    multiplier = 20.0;
                    break;
                case 5:
                    multiplier = 40.0;
                    break;
                case 6:
                    multiplier = 80.0;
                    break;                
            }
            
        }
        
        // Calculate and return the amount won (will be 0 if the symbols did not match)
        return BET * multiplier;
        
    }

}
