package dias_stewart_final_project.player;

import dias_stewart_final_project.game.blackjack.Card;
import java.util.ArrayList;
import org.json.simple.JSONObject;

/**
 * Models a player in a casino.
 * @author Laura Stewart
 */
public class Player {

    protected String name;
    private double money;
    protected ArrayList<Card> hand = new ArrayList<>();
    private ArrayList<Player> player = new ArrayList();

    /**
     * One-arg constructor to make a player with a custom name and $100.
     * @param name The player's name.
     */
    public Player(String name) {

        this.name = name;
        money = 100.0;

    }

    /**
     * Two-arg constructor makes a player with a custom name and amount of
     * money.
     * @param name
     * @param money
     */
    public Player(String name, double money) {

        this.name = name;
        this.money = money;

    }

    /**
     * Returns the name.
     * @return The player's name as a String
     */
    public String getName() {
        return name;
    }

    /**
     * Returns how much money the player has
     * @return The amount of money
     */
    public double getMoney() {
        return money;
    }

    /**
     * Returns an ArrayList that is a copy of the cards in the player's hand.
     * @return An ArrayList copy of cards in the player's hand
     */
    public ArrayList<Card> getHand() {

        // Copy the player's hand (prevents changes from being made to the original hand)
        ArrayList<Card> handCopy = new ArrayList<>(hand);

        return handCopy;
    }

    /**
     * Adds a card to the player's hand.
     * @param newCard The card to be added.
     */
    public void addCard(Card newCard) {
        hand.add(newCard);
    }

    /**
     * Empties the player's hand.
     */
    public void clearHand() {
        hand.clear();
    }

    /**
     * Calculates the total of the player's hand, adjusting for ace values.
     * @return The total value of the cards in the hand.
     */
    public int calcTotal() {

        int total = 0;
        boolean aceCheck = false;

        // Loop through the hand to add up all the cards.
        for (Card card : hand) {
            if (card.getValue() == 1) {
                // Indicates that the total may be modified because the ace can be 1 or 11
                aceCheck = true;
            }
            total += card.getValue();
        }

        // If the ace can be an 11, add 10 to the total
        if (aceCheck && total + 10 <= 21) {
            total += 10;
        }

        return total;

    }

    /**
     * Adds to the player's money when the player wins a game.
     * @param moneyWon The amount of money won.
     * @throws IllegalArgumentException
     */
    public void win(double moneyWon) {

        // Add to money if moneyWon is positive
        if (moneyWon >= 0) {
            money += moneyWon;
        } else {
            throw new IllegalArgumentException("The amount won must be positive.");
        }

    }

    /**
     * Subtracts an amount from the player's money when they bet in a game.
     * @param amount The amount of money to be subtracted.
     */
    public void bet(double amount) {

        // Subtract from money if amount is positive
        if (amount > 0) {
            money -= amount;
        } else {
            throw new IllegalArgumentException("The bet must be positive.");
        }

    }

    /**
     * A String representation of the player
     * @return The player's name and amount of money in a String.
     */
    @Override
    public String toString() {
        return String.format("Name: %s has $%.2f", name, money);
    }

    /**
     * Returns the player as a JSONObject
     * @return the player as a JSONObject
     */
    public JSONObject toJSONObject() {
        JSONObject player = new JSONObject();
        player.put("name", name);
        player.put("money", money);

        return player;
    }

    /**
     * Makes a JSONObject to represent the player and returns it as a String for file i/o
     * @return The player as a JSONString
     */
    public String toJSONString() {

        // Make a JSONObject of the player
        JSONObject playerJSON = this.toJSONObject();

        // Return the JSONObject as a String
        return playerJSON.toJSONString();
    }

}
