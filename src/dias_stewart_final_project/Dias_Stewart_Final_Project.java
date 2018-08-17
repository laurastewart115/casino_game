package dias_stewart_final_project;

import dias_stewart_final_project.player.Player;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Nicholas Dias
 */
public class Dias_Stewart_Final_Project extends Application {
    
    // The player of this game (initialized when a game is loaded or a new game is started)
    private Player player;
    
    // The stage the casino games are played on
    private GameStage gameStage;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Entry point into the application
     * @param primaryStage
     * @throws Exception 
     * @author Nicholas Dias
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Open the main menu
        mainMenu(primaryStage);
    }

    public void mainMenu(Stage primaryStage) {
        Label label = new Label();
        label.setText("Casino");

        Button newGame = new Button();
        newGame.setText("New Game");

        Button loadGame = new Button();
        loadGame.setText("Load Game");

        VBox vBox = new VBox(label, newGame, loadGame);
        vBox.setAlignment(Pos.BASELINE_CENTER);

        vBox.setSpacing(50);

        //when click new game load interface for new game
        newGame.setOnAction(e -> {
            newGameMenu(primaryStage);
        });

        //when click new game load interface for new game
        loadGame.setOnAction(e -> {
            loadGameMenu(primaryStage);
        });

        //Set scene and Stage
        Scene scene = new Scene(vBox, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Casino");
        primaryStage.show();

    }

    /**
     * Displays a screen with the textbox for the user to create a player with a custom name.
     * @param primaryStage 
     * @author Nicholas Dias
     */
    public void newGameMenu(Stage primaryStage) {
        //creating all button, Labels, and textFields
        Button backBtn = new Button("Back");
        Label newGameLabel = new Label("New Game");
        Label nameLbl = new Label("Name");
        TextField nameTf = new TextField("Enter Name Here");
        Button startBtn = new Button("Start");

        //Formating for all the spacing
        HBox newGameTopHBox = new HBox(135, backBtn, newGameLabel);
        newGameTopHBox.setAlignment(Pos.CENTER_LEFT);
        HBox newGameBottomHBox = new HBox(nameLbl, nameTf);
        newGameBottomHBox.setAlignment(Pos.CENTER);
        VBox newGamevBox = new VBox(newGameTopHBox, newGameBottomHBox, startBtn);
        newGamevBox.setAlignment(Pos.BASELINE_CENTER);
        newGamevBox.setSpacing(50);

        backBtn.setOnAction(e -> {
            mainMenu(primaryStage);
        });
        
        startBtn.setOnAction(e -> {
            
            // Start the game and change to the game stage
            String name = nameTf.getText();
            player = new Player(name);
            
            // Open the game stage
            startGame(primaryStage);
            
        });

        //Set scene and Stage
        Scene scene = new Scene(newGamevBox, 400, 250);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Casino");
        primaryStage.show();
    }


    /**
     * Screen for loading a saved file.
     * @param primaryStage 
     * @author Nicholas Dias (UI) and Laura Stewart (file I/O)
     */
    public void loadGameMenu(Stage primaryStage) {
        //creating all button, Labels, and textFields
        Button backBtn = new Button("Back");
        Label saveGamesLabel = new Label("Saved Games");
        Button save1Btn = new Button("Save 1");
        Button save2Btn = new Button("Save 2");
        Button save3Btn = new Button("Save 3");
        
        // Create files for loading the player names from each save slot
        File save1 = new File("save1.json");
        File save2 = new File("save2.json");
        File save3 = new File("save3.json");
        
        String name;
        
        // Set the text of each save slot by loading the player name from the file
        if (save1.exists()) {
            name = loadName(save1);
            save1Btn.setText("Save 1: " + name);
            
            // Allow the button to load the file from save 1
            save1Btn.setOnAction(e -> {

                try {
                    loadGame(save1); 
                } catch (ParseException ex) {
                    // Display an alert if there was an error
                    alertError("There was an error loading the save in slot 1.");
                }

                // Open the game stage
                startGame(primaryStage);

            });
            
        } else {
            save1Btn.setText("Save 1: Empty");
        }
        
        if (save2.exists()) {
            name = loadName(save2);
            save2Btn.setText("Save 2: " + name); 
            
            // Allow the button to load the file from save 2
            save2Btn.setOnAction(e -> {
            
                try {
                    loadGame(save2);
                } catch (ParseException ex) {
                    // Display an alert if there was an error
                    alertError("There was an error loading the save in slot 2.");
                }

                // Open the game stage
                startGame(primaryStage);

            });
        } else {
            save2Btn.setText("Save 2: Empty");
        }
        
        if (save3.exists()) {
            name = loadName(save3);
            save3Btn.setText("Save 3: " + name);
            
            // Allow the button to open save 3
            save3Btn.setOnAction(e -> {

                try {
                    loadGame(save3);
                } catch (ParseException ex) {
                    // Display an alert if there was an error
                    alertError("There was an error loading the save in slot 3.");
                }

                // Open the game stage
                startGame(primaryStage);

            });
            
        } else {
            save3Btn.setText("Save 3: Empty");
        }

        //Formating for all the spacing
        HBox loadGameTopHBox = new HBox(130, backBtn, saveGamesLabel);
        loadGameTopHBox.setAlignment(Pos.CENTER_LEFT);
        VBox loadGameHBox = new VBox(loadGameTopHBox, save1Btn, save2Btn, save3Btn);
        loadGameHBox.setAlignment(Pos.BASELINE_CENTER);
        loadGameHBox.setSpacing(40);

        // Set the action for the back button
        backBtn.setOnAction(e -> {
            mainMenu(primaryStage);
        });

        //Set scene and Stage
        Scene scene = new Scene(loadGameHBox, 400, 250);

        primaryStage.setScene(scene);

    }
    
    /**
     * Loads a player from a file.
     * @param file The file the player is being loaded from.
     * @throws ParseException 
     * @author Laura Stewart
     */
    private void loadGame(File file) throws ParseException {
        
        // For storing JSON input from file
        StringBuilder builder = new StringBuilder();
        
        try (Scanner in = new Scanner(file)) {

            // load the player as a JSONString
            while (in.hasNextLine()) {
                builder.append(in.nextLine());
            }

        } catch (IOException e) {
            alertError("There was an error loading the save file.");
        }
        
        // create a JSON parser
        JSONParser parser = new JSONParser();
        
        JSONObject loadedPlayer;
            
        try {
            // Load the Player data as a JSONObject
            loadedPlayer = (JSONObject) parser.parse(builder.toString());
            
            String name = (String) loadedPlayer.get("name");
            double money = (double) loadedPlayer.get("money");
            
            // Initialize the Player with the JSON data
            player = new Player(name, money);
            
        } catch (Exception e) {
            
            // Display an error message if the file did not load
            alertError("Could not start the game.");
        
        }
        
    }
    
    /** 
     * Loads the player from a specific save slot in order to display the player's name (for displaying what player name corresponds to what save slot)
     * @return The player's name.
     * @author Laura Stewart
     */
    private String loadName(File file) {
        
        String name = "";
        
        // For storing JSON input from file
        StringBuilder builder = new StringBuilder();
        
        try (Scanner in = new Scanner(file)) {

            // try to read in the name
            while (in.hasNextLine()) {
                builder.append(in.nextLine());
            }

        } catch (IOException e) {
            
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
            
            // Display an alert if there was an error
            alertError("There was an error loading the save file name.");
        
        }
        
        return name;
        
    }
    
    /**
     * Enters the casino once the player has been created or loaded.
     * @param primaryStage The primary stage.
     * @author Laura Stewart
     */
    private void startGame(Stage primaryStage) {
        
        // Return to the the Main Menu in this stage and hide this stage
        mainMenu(primaryStage);
        primaryStage.hide();
        
        // Open a GameStage
        gameStage = new GameStage(player, primaryStage);
        gameStage.gameChoiceMenu();
        
    }
    
    /**
     * Displays an alert when there is an error.
     * @param message The error message.
     * @author Laura Stewart
     */
    private void alertError(String message) {
        // Display an alert if there was an error
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

}
