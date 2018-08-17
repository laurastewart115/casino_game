
package dias_stewart_final_project.game.slots;

import javafx.scene.image.Image;

/**
 * Represents a single symbol on a slot machine.
 * @author Laura Stewart
 */
public class Slot {
    
    private Image image;
    private int value;
    
    /**
     * One-arg constructor for making a slot with a value.
     * @param value The numeric value of the symbol.
     */
    public Slot(int value) {
    
        this.value = value;
        this.image = new Image("images/slot"+ this.value + ".png");
        
    }

    /**
     * Returns the image for this slot.
     * @return The image.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Return's this slot's value.
     * @return The numeric value from 1-6.
     */
    public int getValue() {
        return value;
    }

}
