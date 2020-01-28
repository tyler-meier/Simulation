package cellsociety;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.Cursor;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * The visualization class that controls the simulation visuals
 *
 * @author Tyler Meier (tkm22), Farzeen Najam(fn26), Erik Gregorio(eg158)
 */
public class Visualization extends Application {
    public static final String TITLE = "Simulation Project";
    public static final int SIZE = 400;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final double PREF_BUTTON_WIDTH = 125;
    public static final double PREF_BUTTON_HEIGHT = 40;

    private Scene startScene;
    private Stage myStage;
    private Button gameOfLifeButton, percolationButton, segregationButton, predatorPreyButton, fireButton;


    /**
     * Initializes what will be displayed and how to display it
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        myStage = primaryStage;
        myStage.setTitle(TITLE);

        startScene = setupStartScene(SIZE, SIZE);
        myStage.setScene(startScene);

        myStage.show();
    }

    private Scene setupStartScene(int width, int height){
        VBox vBox = new VBox();

        Label welcomeLabel = new Label("Welcome to the Simulations!");
        welcomeLabel.setFont(new Font("Arial", 24));

        Label explainLabel = new Label("Click on the simulation that you would like to see.");
        explainLabel.setFont(new Font("Arial", 15));
        

        gameOfLifeButton = new Button("Game of Life");
        percolationButton = new Button("Percolation");
        segregationButton = new Button("Segregation");
        predatorPreyButton = new Button("Predator-Prey");
        fireButton = new Button("Fire");

        gameOfLifeButton.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        percolationButton.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        segregationButton.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        predatorPreyButton.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        fireButton.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);

        vBox.getChildren().add(welcomeLabel);
        vBox.getChildren().add(explainLabel);
        vBox.getChildren().add(gameOfLifeButton);
        vBox.getChildren().add(percolationButton);
        vBox.getChildren().add(segregationButton);
        vBox.getChildren().add(predatorPreyButton);
        vBox.getChildren().add(fireButton);

        vBox.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(vBox, width, height);
        return scene;
    }

    private Scene setupGameOfLifeScene(int width, int height){
        VBox vBox = new VBox();
        Scene scene = new Scene(vBox, width, height);
        scene.setOnKeyPressed(event -> handleKeyInput(event.getCode()));
        return scene;
    }

    private Scene setupPercolationScene(int width, int height){
        VBox vBox = new VBox();
        Scene scene = new Scene(vBox, width, height);
        scene.setOnKeyPressed(event -> handleKeyInput(event.getCode()));
        return scene;
    }

    private Scene setupSegregationScene(int width, int height){
        VBox vBox = new VBox();
        Scene scene = new Scene(vBox, width, height);
        scene.setOnKeyPressed(event -> handleKeyInput(event.getCode()));
        return scene;
    }

    private Scene setupPredatorPreyScene(int width, int height){
        VBox vBox = new VBox();
        Scene scene = new Scene(vBox, width, height);
        scene.setOnKeyPressed(event -> handleKeyInput(event.getCode()));
        return scene;
    }

    private Scene setupFireScene(int width, int height){
        VBox vBox = new VBox();
        Scene scene = new Scene(vBox, width, height);
        scene.setOnKeyPressed(event -> handleKeyInput(event.getCode()));
        return scene;
    }

    private void step(double elapsedTime){

    }

    private void handleKeyInput(KeyCode code){

    }


    public static void main (String[] args) { launch(args); }
}
