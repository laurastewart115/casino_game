
package dias_stewart_final_project;

import dias_stewart_final_project.game.blackjack.Blackjack;
import dias_stewart_final_project.game.blackjack.Card;
import dias_stewart_final_project.game.slots.SlotMachine;
import dias_stewart_final_project.player.Player;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * The stage for playing the game
 * @author Nicholas Dias (UI and formatting) and Laura Stewart (File I/O and game logic)
 */
public class GameStage extends Stage {
    
    Player player;
    Stage menuStage;
    
    // Create universal styles and fonts
    String textStyle = "-fx-text-fill:white;";
    Font bodyFont = new Font("Arial", 16);
    Font titleFont = new Font("Arial", 24);
    
    /**
     * Creates a GameStage and passes in the Player from the main class.
     * @param player The player
     * @param menuStage The stage for the main menu (so that it can be returned to when the user quits)
     * @author Laura Stewart
     */
    public GameStage(Player player, Stage menuStage) {
        this.player = player;
        this.menuStage = menuStage;
        
        // does not allow the window to be resized
        this.setResizable(false);
    }
    
    /**
     * Displays the menu for choosing to play Blackjack or Slots.
     * @author Nicholas Dias
     */
    public void gameChoiceMenu() {
        //Set all Label, Buttons, and ImageView
        Label welcomeLbl = new Label("Welcome to the Casino");
        welcomeLbl.setFont(titleFont);
        Label nameLbl = new Label("Name: " + player.getName());
        nameLbl.setFont(bodyFont);
        Label moneyLbl = new Label(String.format("Money: $%.2f", player.getMoney()));
        moneyLbl.setFont(bodyFont);

        Button saveBtn = new Button("Save and Quit");
        Button blackjackBtn = new Button("BlackJack");
        Button slotBtn = new Button("Slots");

        ImageView blackjackIv = new ImageView(new Image("images/BlackJack.png"));
        ImageView slotIv = new ImageView(new Image("images/Slots.png"));

        //formating and aligning
        HBox hBox1 = new HBox(welcomeLbl);
        hBox1.setAlignment(Pos.CENTER);
        blackjackIv.setPreserveRatio(true);
        blackjackIv.setFitWidth(250);
        slotIv.setPreserveRatio(true);
        slotIv.setFitWidth(250);
        HBox hBox2 = new HBox(blackjackIv, slotIv);
        hBox2.setAlignment(Pos.CENTER);
        hBox2.setSpacing(50);
        HBox hBox3 = new HBox(blackjackBtn, slotBtn);
        hBox3.setAlignment(Pos.CENTER);
        hBox3.setSpacing(250);
        HBox hBox4 = new HBox(nameLbl, moneyLbl);
        hBox4.setAlignment(Pos.CENTER);
        hBox4.setSpacing(150);

        VBox vBox1 = new VBox(saveBtn, hBox1, hBox2, hBox3, hBox4);
        vBox1.setSpacing(20);
        
        // go to the game screens
        blackjackBtn.setOnAction(e -> blackjackScreen());
        slotBtn.setOnAction(e -> slotsScreen());

        // go to the save chooser screen when save is clicked
        saveBtn.setOnAction(e -> saveChooserScreen());

        //Set scene and Stage
        Scene scene = new Scene(vBox1, 600, 400);

        setScene(scene);
        setTitle("Casino");
        if(!this.isShowing()) {
            showAndWait();
        }

    }
    
    /**
     * The user chooses which save slot to overwrite.
     * * @author Laura Stewart (file i/o) and Nicholas Dias (UI formatting)
     */
    public void saveChooserScreen() {
        
        // Create the buttons and title label
        Label lblTitle = new Label("Choose a slot to overwrite:");
        lblTitle.setFont(titleFont);
        Button btnSave1 = new Button("Save 1:");
        Button btnSave2 = new Button("Save 2:");
        Button btnSave3 = new Button("Save 3:");
        
        //formating the screen
        VBox vBox = new VBox(lblTitle,btnSave1,btnSave2,btnSave3);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(40);
        
        // Create files for loading the player names from each save slot
        File save1 = new File("save1.json");
        File save2 = new File("save2.json");
        File save3 = new File("save3.json");
      
        String name;
        
        // Set the text for each save slot button to include the player's name
        if (save1.exists()) {
            
            // set the text
            name = loadName(save1);
            btnSave1.setText("Save 1: " + name);
            
        } else {
            btnSave1.setText("Save 1: Empty");
        }
        
        if (save2.exists()) {
            
            // set the text
            name = loadName(save2);
            btnSave2.setText("Save 2: " + name);
            
            
        } else {
            btnSave2.setText("Save 2: Empty");
        }
        
        if (save3.exists()) {
            
            // set the text
            name = loadName(save3);
            btnSave3.setText("Save 3: " + name);
            
        } else {
            btnSave3.setText("Save 3: Empty");
        }
        
        // Save the player in the slot corresponding to the button pressed
        btnSave1.setOnAction(e -> {
            
            // Save the file
            saveFile(new File("save1.json"));
            
            // Close this stage
            close();
            
            // Show the main menu
            menuStage.show();
        });
        btnSave2.setOnAction(e -> {
            
            // Save the file
            saveFile(new File("save2.json"));
            
            // Close this stage
            close();
            
            // Show the main menu
            menuStage.show();
        });
        btnSave3.setOnAction(e -> {
            
            // Save the file
            saveFile(new File("save3.json"));
            
            // Close this stage
            close();
            
            // Show the main menu
            menuStage.show();
        });
        
        Scene scene = new Scene(vBox, 400, 250);
        setScene(scene);
        
    }

    /**
     * Screen for playing the Blackjack game.
     * @author Laura Stewart (game logic and UI)
     */
    public void blackjackScreen() {
        
        // Make a blackjack game
        Blackjack blackjack = new Blackjack(player);
        
        // Game title
        Label title = new Label("Blackjack");
        title.setStyle(textStyle + "-fx-font-weight:bold;");
        title.setFont(titleFont);
        StackPane titlePane = new StackPane(title);
        
        // Make a pane to hold the deck image
        Image deckImg = new Image("images/b2fh.png");
        StackPane deck = new StackPane(new ImageView(deckImg));
        deck.setPadding(new Insets(10));
        
        // Make all game labels
        Label lblDealerTotal = new Label();
        lblDealerTotal.setStyle(textStyle);
        lblDealerTotal.setFont(bodyFont);
        Label lblPlayerTotal = new Label();
        lblPlayerTotal.setStyle(textStyle);
        lblPlayerTotal.setFont(bodyFont);
        Label moneyLbl = new Label(String.format("Money: $%.2f", player.getMoney()));
        moneyLbl.setStyle(textStyle);
        moneyLbl.setFont(bodyFont);
        Label gameMsg = new Label();
        gameMsg.setStyle(textStyle);
        gameMsg.setFont(bodyFont);
        
        // Make all buttons
        Button btnQuit = new Button("Quit");
        Button btnPlay = new Button("Play");
        Button btnHit = new Button("Hit");
        Button btnStand = new Button("Stand");
        Button btnSurrender = new Button("Surrender");
        
        // Set all the buttons to be the same width
        btnQuit.setPrefWidth(96);
        btnPlay.setPrefWidth(96);
        btnHit.setPrefWidth(96);
        btnStand.setPrefWidth(96);
        btnSurrender.setPrefWidth(96);
        
        // Panes for holding cards and totals
        HBox dealerCards = new HBox(5);
        dealerCards.setPadding(new Insets(10, 10, 10, 10));
        HBox playerCards = new HBox(5);
        playerCards.setPadding(new Insets(10, 10, 10, 10));
        VBox cards = new VBox(lblDealerTotal, dealerCards, lblPlayerTotal,playerCards);
        cards.setPadding(new Insets(10, 10, 10, 10));
        
        // Put the gameMsg and the player's amount of money in a pane
        StackPane message = new StackPane(gameMsg);
        StackPane money = new StackPane(moneyLbl);
        VBox gameInfo = new VBox(message, money);
        gameInfo.setSpacing(20);
        gameInfo.setPadding(new Insets(20));
        
        // Make a pane to hold the game buttons
        VBox gameButtons = new VBox(btnHit, btnStand, btnSurrender);
        gameButtons.setAlignment(Pos.CENTER);
        gameButtons.setSpacing(20);
        gameButtons.setPadding(new Insets(10));
        
        // Make a pane to hold the start and quit buttons
        VBox buttons = new VBox(btnPlay, btnQuit);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(20);
        buttons.setPadding(new Insets(10));
        
        // Make BorderPane to hold everything
        BorderPane root = new BorderPane(buttons);
        root.setTop(titlePane);
        root.setLeft(deck);
        Label formatLbl = new Label("");
        formatLbl.setPrefWidth(96);
        root.setRight(formatLbl);
        root.setBottom(gameInfo);
        
        // Add a background image
        Image bgImage = new Image("images/table.jpg", 700, 420, false, false);
        root.setBackground(new Background(new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        
        // Set all button actions
        BackButtonListener back = new BackButtonListener();
        btnQuit.setOnAction(back);
        
        // button for starting a round of blackjack
        btnPlay.setOnAction(e -> {

            // Start the game by dealing two to player, two to dealer
            blackjack.start();
            
            // Update the gameMsg and moneyLbl
            gameMsg.setText(String.format("You have bet $%.2f. Choose to hit, stand, or surrender.", blackjack.BET));
            moneyLbl.setText(String.format("Money: $%.2f", player.getMoney()));
            
            // Display the dealer's initial cards (one face up, one face down)
            dealerCards.getChildren().clear();
            dealerCards.getChildren().add(new ImageView("images/b2fv.png"));
            Card dealerCard = blackjack.getDealerHand().get(1);
            dealerCards.getChildren().add(new ImageView(dealerCard.getFace()));
            
            // Display the player's inital cards
            updateCards(blackjack.getPlayerHand(), playerCards);
            
            // display hand totals
            lblDealerTotal.setText("Dealer: ?");
            lblPlayerTotal.setText("Player: " + blackjack.getPlayerTotal());
            
            // Put the cards pane in the center of the root BorderPane
            root.setCenter(cards);
            
            // Show game buttons
            btnHit.setVisible(true);
            btnSurrender.setVisible(true);
            root.setRight(gameButtons);
            
        });
        btnHit.setOnAction(e-> {
            
            // Hide the surrender button
            btnSurrender.setVisible(false);
            
            // Give player a card and update the display
            blackjack.hit();
            updateCards(blackjack.getPlayerHand(), playerCards);
            lblPlayerTotal.setText("Player: " + blackjack.getPlayerTotal());
            
            // Check if the player has lost, gotten 21, or can keep drawing
            if(blackjack.getPlayerTotal() > 21) {
                
                gameMsg.setText("Bust! Click Stand to continue.");
                btnHit.setVisible(false);
                
            } else if (blackjack.getPlayerTotal() == 21) {
                
                gameMsg.setText("Your total is 21. Click Stand to continue.");
                btnHit.setVisible(false);
                
            }
            else
                gameMsg.setText("Choose to hit or stand.");
            
        });
        btnStand.setOnAction(e -> {
            
            // End the round by showing and hiding the appropriate buttons
            endBlackjackRound(buttons, btnPlay, root);

            // Start the dealer's turn once the player has decided to stand
            blackjack.stand();
            updateCards(blackjack.getDealerHand(), dealerCards);
            lblDealerTotal.setText("Dealer: " + blackjack.getDealerTotal());
            
            // Compare the hands and update the gameMsg
            gameMsg.setText(blackjack.compare());
            
            // Calculate what the player wins
            double moneyWon = blackjack.moneyWon();
            player.win(moneyWon);
            moneyLbl.setText(String.format("Money: $%.2f", player.getMoney()));
            
        });
        
        btnSurrender.setOnAction(e -> {
            
            // Update the dealer's cards
            updateCards(blackjack.getDealerHand(), dealerCards);
            
            // End the round by showing and hiding the appropriate buttons
            endBlackjackRound(buttons, btnPlay, root);

            // Player surrenders to get half their money back. Player and dealer hands are not compared
            double moneyWon = blackjack.surrender();
            player.win(moneyWon);
            gameMsg.setText("You surrendered.");
            moneyLbl.setText(String.format("Money: $%.2f", player.getMoney()));
            
        });

        //Set scene and Stage
        Scene scene = new Scene(root, 700, 420);

        setScene(scene);

    }

    /**
     * Ends a round of blackjack by replacing the game buttons with the "Play Again" and "Quit" buttons.
     * @param buttons The buttons that replace the game buttons.
     * @param btnPlay The button for playing again.
     * @param root The pane to add the buttons to
     * @author Laura Stewart
     */
    private void endBlackjackRound(VBox buttons, Button btnPlay, BorderPane root) {
        
        // Add the play and quit buttons to the sidebar
        btnPlay.setText("Play Again");
        root.setRight(buttons);
        
    }
    
    /**
     * Adds cards from a player's or dealer's hand to an HBox
     * @param cards The hand of cards to be displayed
     * @param pane The pane the cards are being displayed on
     * @author Laura Stewart
     */
    private void updateCards(ArrayList<Card> cards, HBox pane) {
       
        // Clear the pane
        pane.getChildren().clear();
        
        // Add the cards to the pane
        for (Card c: cards) {
            pane.getChildren().add(new ImageView(c.getFace()));
        }   
        
    }

    /**
     * Play a game of Slots.
     * @author Laura Stewart
     */
    public void slotsScreen() {

        // Make SlotMachine game
        SlotMachine slots = new SlotMachine();
        
        // Spin the slots without betting any money and get the images to be displayed
        slots.start();
        Image[][] displayImages = slots.getDisplayImages();
        
        // Initialize panes to hold the reels
        VBox[] reel = new VBox[3];
        for(int i = 0; i < reel.length; i++) {
            reel[i] = new VBox();
        }
        
        HBox allReels = new HBox();
        // Add the display images to the reels
        updateSlotReels(reel, allReels, displayImages, slots);
        
        // Make a rectangle to show divisions between slot reels
        Rectangle divider = new Rectangle(100, 225, Color.TRANSPARENT);
        divider.setStroke(Color.WHITE);
        divider.setStrokeWidth(2);
        divider.setX(100);
       
        // Make a rectangle to show which slots are selected.
        Rectangle selector = new Rectangle(290, 75, Color.TRANSPARENT);
        selector.setStroke(Color.GOLD);
        selector.setStrokeWidth(4);
        selector.setArcHeight(1.0);
        selector.setArcWidth(1.0);
        selector.setY(75);
        selector.setX(5);
        
        // Make a rectangle to be the screen border
        Rectangle screenBorder = new Rectangle(300, 225, Color.TRANSPARENT);
        screenBorder.setStroke(Color.GOLDENROD);
        screenBorder.setStrokeWidth(8);
        screenBorder.setArcHeight(1.0);
        screenBorder.setArcWidth(1.0);
        
        // Overlay the rectangle on the slots
        Pane screen = new Pane(divider, selector, screenBorder);
        StackPane displayPane = new StackPane(allReels, screen);
        displayPane.setPadding(new Insets(10));
        
        // Make all game labels
        Label moneyLbl = new Label(String.format("Money: $%.2f", player.getMoney()));
        moneyLbl.setFont(bodyFont);
        Label gameMsg = new Label("Click spin to spin the slots!");
        gameMsg.setFont(bodyFont);
        
        // Pane to hold the game labels
        VBox labelPane = new VBox(moneyLbl, gameMsg);
        labelPane.setPadding(new Insets(10));
        labelPane.setSpacing(10);
        labelPane.setAlignment(Pos.CENTER);
        
        // Button for quitting the game
        Button exitBtn = new Button("Exit");
        exitBtn.setFont(bodyFont);
        BackButtonListener back = new BackButtonListener();
        exitBtn.setOnAction(back);
        
        // Button for spinning the slots
        Button spinBtn = new Button("Spin! ($1)");
        spinBtn.setPadding(new Insets(20, 30, 20, 30));
        spinBtn.setFont(titleFont);
        spinBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                // The player bets the required amount for the slots game
                player.bet(slots.BET);
                
                // spin the slots
                slots.spin();
            
                // Clear the HBox
                allReels.getChildren().clear();
            
                // Update the images
                updateSlotReels(reel, allReels, displayImages, slots);
            
                // Calculate money won based on result
                player.win(slots.moneyWon());
                
                // Display how much the player has won
                moneyLbl.setText(String.format("Money: $%.2f", player.getMoney()));
                if (slots.moneyWon() > 0) {
                    gameMsg.setText(String.format("It's a match! You win $%.2f.", slots.moneyWon()));
                } else {
                    gameMsg.setText("Not a match. Try again.");
                }
                
            }
        });
        
        // Put the buttons in the pane
        VBox buttonPane = new VBox(spinBtn, exitBtn);
        buttonPane.setSpacing(30);
        buttonPane.setPadding(new Insets(50, 10, 50, 10));
        buttonPane.setAlignment(Pos.CENTER);
        
        // Make the title
        Label title = new Label("Slots");
        title.setFont(new Font("Arial", 24));
        title.setStyle("-fx-font-weight:bold;");
        StackPane titlePane = new StackPane(title);
        titlePane.setPadding(new Insets(10));
        
        // Make a legend
        VBox legend = new VBox(10);
        legend.setPadding(new Insets(10));
        double value = 2.5;
        // Make ImageViews and Labels for each slot and add to the legend
        for (int i = 1; i <= 6; i++) {
            
            // Create the ImageView
            ImageView slotImg = new ImageView("images/slot" + i + ".png");
            slotImg.setPreserveRatio(true);
            slotImg.setFitWidth(50);
            
            // Store the value in a label
            Label valueLbl = new Label(String.format("$%.2f", value));
            valueLbl.setFont(bodyFont);
            
            // Increase the value
            value *= 2;
            
            // Add the value and slotImg to an HBox and add to the legend
            HBox hbox = new HBox(slotImg, valueLbl);
            hbox.setSpacing(5);
            legend.getChildren().add(hbox);
            
        }
        
        // Add the elements to the root BorderPane
        BorderPane root = new BorderPane();
        root.setTop(titlePane);
        root.setCenter(displayPane);
        root.setRight(buttonPane);
        root.setBottom(labelPane);
        root.setLeft(legend);
        
        //Set scene and Stage
        Scene scene = new Scene(root, 650, 420);

        setScene(scene);
        
    }
    
    /**
     * Updates the slot images that will be displayed in the reel.
     * @param reel The array of VBoxes (each index is a reel).
     * @param allReels The HBox to hold all three reels.
     * @param displayImages The 2D array of images that will be displayed.
     * @author Laura Stewart
     */
    private void updateSlotReels(VBox[] reel, HBox allReels, Image[][] displayImages, SlotMachine slots) {
        
        // Get the images that will be displayed
        displayImages = slots.getDisplayImages();
        
        // Add the display images to the reels
        for (int i = 0; i < reel.length; i++) {
           
           // Clear the reel
           reel[i].getChildren().clear();
            
            for (int j = 0; j < 3; j++) {
                
                // Add a slot image to the reel
                ImageView slotImg = new ImageView(displayImages[i][j]);
                slotImg.setFitHeight(75);
                slotImg.setFitWidth(100);
                reel[i].getChildren().add(slotImg);
                
            }
            
            // Add the full reel to the HBox
            allReels.getChildren().add(reel[i]);
            
        }
        
    }
    
    /**
     * Saves the player as a JSONObject to a file.
     * @param file The file the player will be saved to.
     * @author Laura Stewart
     */
    private void saveFile(File file) {
       
        // Convert the player to a JSONObject
        JSONObject playerJSON = player.toJSONObject();
        
        // Save in a file corresponding to the correct save slot
        try(PrintWriter write = new PrintWriter(new FileWriter(file, false))) {
            
            write.println(playerJSON);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Game saved.");
            alert.showAndWait();
            
        } catch (FileNotFoundException ex) {
            
            alertError("Could not save the file");
            
        }catch (IOException ex) {
            
            alertError("Could not save the file");
            
        }
        
    }
    
    /** 
     * Loads the player from a save slot in order to display the player's name (for displaying the names corresponding to the save slots when the user saves the game).
     * @return The player's name
     * @author Laura Stewart
     */
    private String loadName(File file) {
        
        String name = "";
        
        // For storing JSON input from file
        StringBuilder builder = new StringBuilder();
        
        try (Scanner in = new Scanner(file)) {

            while (in.hasNextLine()) {
                builder.append(in.nextLine());
            }

        } catch (IOException e) {
            // Display an alert if there was an error
            alertError("Could not access the file.");
        }
        
        // create a JSON parser
        JSONParser parser = new JSONParser();
        
        JSONObject loadedPlayer;
            
        try {
            // Load the Player data as a JSONObject
            loadedPlayer = (JSONObject) parser.parse(builder.toString());
            
            // Set the name that will be returned
            name = (String) loadedPlayer.get("name");
            
        } catch (Exception e) {
            
            alertError("There was an error getting the player names.");
        
        }
        
        return name;
        
    }

    /**
     * Displays an alert if there is an error.
     * @param message The error message to be displayed.
     * @author Laura Stewart
     */
    private void alertError(String message) {
        // Display an alert if there was an error
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
    
    /**
     * A listener that changes the scene to the main menu.
     * @author Laura Stewart
     */
    private class BackButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

            gameChoiceMenu();

        }

    }

}
