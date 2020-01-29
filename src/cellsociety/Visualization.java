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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
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
    public static final double PREF_BUTTON_HEIGHT = 50;
    public static final Paint BACKGROUND = Color.GOLD;
    public static final String PERCOLATION = "Percolation Simulation";
    public static final String GAME_OF_LIFE = "Game of Life Simulation";
    public static final String PREDATOR_PREY = "Predator-Prey Simulation";
    public static final String FIRE = "Fire Simulation";
    public static final String SEGREGATION = "Segregation Simulation";
    public static final Font titleFont = new Font("Arial", 24);
    public static final Font subtitleFont = new Font("Arial", 15);

    private Scene startScene, percolationScene, gameOfLifeScene, predatorPreyScene, fireScene, segregationScene;
    private Stage myStage;
    private Button gameOfLifeButton, percolationButton, segregationButton, predatorPreyButton, fireButton, mainMenu;


    /**
     * Initializes what will be displayed and how to display it
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        myStage = primaryStage;
        myStage.setTitle(TITLE);

        startScene = setupStartScene(SIZE, SIZE, BACKGROUND);
        percolationScene = setUpSimulationScene(SIZE, SIZE, BACKGROUND, PERCOLATION);
        gameOfLifeScene = setUpSimulationScene(SIZE, SIZE, BACKGROUND, GAME_OF_LIFE);
        predatorPreyScene = setUpSimulationScene(SIZE, SIZE, BACKGROUND, PREDATOR_PREY);
        fireScene = setUpSimulationScene(SIZE, SIZE, BACKGROUND, FIRE);
        segregationScene = setUpSimulationScene(SIZE, SIZE, BACKGROUND, SEGREGATION);

        percolationButton.setOnAction(e -> myStage.setScene(percolationScene));
        gameOfLifeButton.setOnAction(e -> myStage.setScene(gameOfLifeScene));
        predatorPreyButton.setOnAction(e -> myStage.setScene(predatorPreyScene));
        fireButton.setOnAction(e -> myStage.setScene(fireScene));
        segregationButton.setOnAction(e -> myStage.setScene(segregationScene));

        myStage.setScene(startScene);
        myStage.show();
    }

    private Scene setupStartScene(int width, int height, Paint background){
        VBox vBox = new VBox();
        VBox vBox2 = new VBox();

        Label welcomeLabel = new Label("MAIN MENU");
        welcomeLabel.setFont(titleFont);
        Label explainLabel = new Label("Click on the simulation that you would like to see.");
        explainLabel.setFont(subtitleFont);

        gameOfLifeButton = new Button("Game of Life");
        percolationButton = new Button("Percolation");
        segregationButton = new Button("Segregation");
        predatorPreyButton = new Button("Predator-Prey");
        fireButton = new Button("Fire");

        gameOfLifeButton.setPrefSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        percolationButton.setPrefSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        segregationButton.setPrefSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        predatorPreyButton.setPrefSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        fireButton.setPrefSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);

        vBox.getChildren().addAll(welcomeLabel, explainLabel);
        vBox2.getChildren().addAll(gameOfLifeButton, percolationButton, segregationButton, predatorPreyButton, fireButton);

        vBox.setAlignment(Pos.CENTER);
        vBox2.setAlignment(Pos.CENTER);
        vBox.setSpacing(7);
        vBox2.setSpacing(18);

        BorderPane.setAlignment(vBox, Pos.TOP_CENTER);
        BorderPane.setAlignment(vBox2, Pos.CENTER);

        BorderPane boPane = new BorderPane(vBox2, vBox, null, null, null);
        boPane.setStyle("-fx-background-color: mediumspringgreen");

        Scene scene = new Scene(boPane, width, height, background);
        return scene;
    }

    private Scene setUpSimulationScene(int width, int height, Paint background, String stringName) {
        Label nameLabel = new Label(stringName);
        nameLabel.setFont(titleFont);
        nameLabel.setAlignment(Pos.CENTER);

        mainMenu = new Button("Main Menu");
        mainMenu.setOnAction(e -> myStage.setScene(startScene));

        BorderPane.setAlignment(nameLabel, Pos.TOP_CENTER);
        BorderPane.setAlignment(mainMenu, Pos.BOTTOM_CENTER);

        BorderPane boPane = new BorderPane(null, nameLabel, null, mainMenu, null);

        Scene scene = new Scene(boPane, width, height, background);
        scene.setOnKeyPressed(event -> handleKeyInput(event.getCode()));
        return scene;
    }

    private void step(double elapsedTime){

    }

    private void handleKeyInput(KeyCode code){

    }

    public static void main (String[] args) { launch(args); }
}
