
package dias_stewart_final_project.game.slots;

import java.util.Random;
import javafx.scene.image.Image;

/**
 * Represents a roll of slots.
 * @author Laura Stewart
 */
public class SlotRoll {
    
    private final Slot[] ROLL = new Slot[6];
    private int selectIndex;
    private Slot selectedSlot;
    
    /**
     * Default constructor for building a slot roll with 6 slots.
     */
    public SlotRoll() {
        
        // Build the slot roll
        for(int i = 0; i < 6; i++) {
            ROLL[i] = new Slot(i + 1);
        }
        
        // Choose a slot at random to display
        selectIndex = (int)(Math.random() * ROLL.length + 1);
        
    }
    
    /**
     * Uses a random number to select a slot from the array.
     * @return The selected slot.
     */
    public Slot spin() {
        
        // Generate the random index
        Random rand = new Random();
        selectIndex = rand.nextInt(6);
        
        // Set the selected slot to be the one from the roll at the random index
        selectedSlot = ROLL[selectIndex];
        
        return selectedSlot;
        
    }
    
    /**
     * Returns a slot array of the selected slot and the slots before and after it for displaying the results.
     * @return A Slot[] that is three slots long with the selected slot at index 1;
     */
    public Image[] getSlotsToDisplay() {
        
        // Make the result array
        Image[] toDisplay = new Image[3];
        
        // Set the middle index to be the 
        toDisplay[1] = selectedSlot.getImage();
        
        // Set the indexes before and after to be the correct slot images from the roll
        switch (selectIndex) {
            case 0:
                toDisplay[0] = ROLL[5].getImage();
                toDisplay[2] = ROLL[selectIndex + 1].getImage();
                break;
            case 5:
                toDisplay[0] = ROLL[selectIndex - 1].getImage();
                toDisplay[2] = ROLL[0].getImage();
                break;
            default:
                toDisplay[0] = ROLL[selectIndex - 1].getImage();
                toDisplay[2] = ROLL[selectIndex + 1].getImage();
                break;
        }
        
        return toDisplay;
        
    }

}
