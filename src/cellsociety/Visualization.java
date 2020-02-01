package cellsociety;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * The visualization class that controls the simulation visuals
 *
 * @author Tyler Meier (tkm22), Farzeen Najam(fn26), Erik Gregorio(eg158)
 */
public class Visualization extends Application {
    public static final String TITLE = "Simulation Project";
    public static final int SIZE = 700;
    public static final int FRAMES_PER_SECOND = 1;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final double PREF_BUTTON_WIDTH = 250;
    public static final double PREF_BUTTON_HEIGHT = 100;
    public static final Paint BACKGROUND = Color.GOLD;
    public static final String PERCOLATION = "Percolation Simulation";
    public static final String GAME_OF_LIFE = "Game of Life Simulation";
    public static final String PREDATOR_PREY = "Predator-Prey Simulation";
    public static final String FIRE = "Fire Simulation";
    public static final String SEGREGATION = "Segregation Simulation";
    public static final Font titleFont = new Font("Arial", 50);
    public static final Font subtitleFont = new Font("Arial", 25);

    private Scene startScene, percolationScene, gameOfLifeScene, predatorPreyScene, fireScene, segregationScene;
    private Stage myStage;
    private Button gameOfLifeButton, percolationButton, segregationButton, predatorPreyButton, fireButton, mainMenu, pause, resume, speedUp, slowDown;
    private Percolation myPercolationGrid;
    private GameOfLife myGoLGrid;
    private Segregation mySegregationGrid;
    private Rectangle[][] myGrid;
    private Group group;
    private Visualizer myView;


    /**
     * Initializes what will be displayed and how to display it
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        myStage = primaryStage;
        myStage.setTitle(TITLE);

        myView = new Visualizer();

        startScene = setupStartScene(SIZE, SIZE, BACKGROUND);

        percolationButton.setOnAction(e -> myView.setUpSimulationScene(SIZE, SIZE, BACKGROUND, PERCOLATION, myStage, startScene));
        gameOfLifeButton.setOnAction(e -> myView.setUpSimulationScene(SIZE, SIZE, BACKGROUND, GAME_OF_LIFE, myStage, startScene));
        predatorPreyButton.setOnAction(e -> myView.setUpSimulationScene(SIZE, SIZE, BACKGROUND, PREDATOR_PREY, myStage, startScene));
        fireButton.setOnAction(e -> myView.setUpSimulationScene(SIZE, SIZE, BACKGROUND, FIRE, myStage, startScene));
        segregationButton.setOnAction(e -> myView.setUpSimulationScene(SIZE, SIZE, BACKGROUND, SEGREGATION, myStage, startScene));

        myStage.setScene(startScene);
        myStage.show();
    }


    private Scene setupStartScene(int width, int height, Paint background){
        VBox vBox = new VBox();
        VBox vBox2 = new VBox();

        Label welcomeLabel = new Label("MAIN MENU");
        welcomeLabel.setFont(titleFont);
        Label explainLabel = new Label("Click on the simulation that you would like to see");
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

        gameOfLifeButton.setStyle("-fx-font-size: 2em; ");
        percolationButton.setStyle("-fx-font-size: 2em; ");
        segregationButton.setStyle("-fx-font-size: 2em; ");
        predatorPreyButton.setStyle("-fx-font-size: 2em; ");
        fireButton.setStyle("-fx-font-size: 2em; ");

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

    public static void main (String[] args) { launch(args); }
}
