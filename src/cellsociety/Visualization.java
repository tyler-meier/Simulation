package cellsociety;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;


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

        myPercolationGrid = new Percolation();
        myGrid = new Rectangle[10][10];

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

    private Scene setUpSimulationScene(int width, int height, Paint background, String stringName) {
        VBox buttonsVBox = new VBox();

        Label nameLabel = new Label(stringName);
        nameLabel.setFont(titleFont);
        nameLabel.setAlignment(Pos.CENTER);

        mainMenu = new Button("Main Menu");
        pause = new Button("Pause");
        resume = new Button ("Resume");
        speedUp = new Button ("Speed Up");
        slowDown = new Button("Slow Down");

        mainMenu.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        pause.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        resume.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        speedUp.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        slowDown.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);

        mainMenu.setOnAction(e -> myStage.setScene(startScene));

        group = new Group();
        setUpGrid();

        buttonsVBox.getChildren().addAll(mainMenu, pause, resume, speedUp, slowDown);
        buttonsVBox.setAlignment(Pos.CENTER_LEFT);
        BorderPane.setAlignment(nameLabel, Pos.TOP_CENTER);
        BorderPane.setAlignment(group, Pos.CENTER);
        BorderPane boPane = new BorderPane(group, nameLabel, null, null, buttonsVBox);

        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

        Scene scene = new Scene(boPane, width, height, background);
        return scene;
    }

    private void step(double elapsedTime){
        //update cells here i'm guessing
        //also need to implement pause and resume here
        //and speed up and slow down i assume
        myPercolationGrid.update();

    }

    private void setUpGrid(){
        for (int row = 0; row < myGrid.length; row++) {
            for (int col = 0 ; col < myGrid[row].length ; col++) {
                Rectangle rec = myRectangle(col*15, row*15, 15, 15);
                if (myPercolationGrid.cellStatus(row,col) == 2){
                    rec.setFill(Color.BLUE);
                }
                if (myPercolationGrid.cellStatus(row,col) == 4){
                    rec.setFill(Color.GREEN);
                }
                group.getChildren().add(rec);
            }
        }
    }
    private Rectangle myRectangle(double x, double y, double w, double h){

        Rectangle rectangle = new Rectangle(w, h);


        rectangle.setX(x);
        rectangle.setY(y);

        return rectangle;
    }

    public static void main (String[] args) { launch(args); }
}
