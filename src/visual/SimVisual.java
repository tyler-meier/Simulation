package visual;

import xmlreading.ReadXML;
import ruleset.Simulation;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.ResourceBundle;

/**
 * The visualizer class that sets up the actual simulation and all of its features and
 * everything found in it. Also holds the main step function that updates the simulation and
 * updates the grid of rectangles as a result.
 *
 * @author Tyler Meier (tkm22)
 */
public class SimVisual extends Application {
    public static final int EMPTY = 0;
    public static final int OPEN = 1;
    public static final int FULL = 2;
    public static final double PREF_BUTTON_WIDTH = 250;
    public static final double PREF_BUTTON_HEIGHT = 100;
    public static final Font titleFont = new Font("Arial", 50);
    public static final int GRID_SIZE = 350;
    public static final String RESOURCES = "resources";
    public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
    public int LIGHTGREEN_COUNT = 0;
    public int ORANGERED_COUNT = 0;
    public int WHITE_COUNT = 0;
    public int STEP_COUNT = 0;

    private int RECTANGLE_SIZE_ROW;
    private int RECTANGLE_SIZE_COL;
    private Button pause, resume, stepThrough, slowSimDown, speedSimUp, chooseSimButtonSim, saveButton;
    private Rectangle[][] myGrid;
    private Group group;
    private VBox allButtonsVBox, topLabelsVBox;
    private Label buttonDescriptions;
    private ResourceBundle myResources;
    private Timeline theAnimation;
    private Chart myChart;

    /**
     * Start method for visualizer, just need so it can extend application, this start
     * method doesn't actually do anything
     * @param secondaryStage null stage, doesn't actually show anything
     * @throws Exception
     */
    @Override
    public void start(Stage secondaryStage) throws Exception {

    }

    /**
     * Sets up the scene for each simulation. Creates a border pane as the layout for the scene.
     * It then creates all the buttons for the scene, then creates the top labels for the scene (title
     * and rules), then creates the description for the buttons, and then calls setUpGrid to actually
     * create the grid of rectangles. It then adds all of these to the border pane in their designated
     * areas and plays the animation, then returns the created scene so the scene can show on stage
     * @param width width of the entire border pane/scene (700)
     * @param height height of the entire border pane/scene (700)
     * @param stringName name of the simulation file as a string that is being set up in scene
     * @param myCurrSim the current simulation that is about to be run, what the scene is creating
     * @param mySimFileReader a ReadXML object that allows the grid to read the file and set up
     *                        the rectangles in the correct positions and correct colors
     * @param oldSimButton the button that allows the user to file choose a new simulation to run, everything
     *                  is already set up for this button, it is just being passed onto this scene to be 'copied'
     * @param animation the animation for this scene, being passed from cellsociety.Visualization so that the animation
     *                  can play in this scene
     * @return scene, the whole set up scene for the simulation
     */
    public Scene setUpSimulationScene(int width, int height, String stringName, Simulation myCurrSim, ReadXML mySimFileReader, Button oldSimButton, Timeline animation) {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "allStrings");
        STEP_COUNT = 0;
        theAnimation = animation;
        myChart = new Chart();

        createAllButtons(myCurrSim, oldSimButton, stringName);
        createTopLabels(stringName);
        createButtonLabel();
        setUpGrid(myCurrSim, mySimFileReader);

        BorderPane.setAlignment(allButtonsVBox, Pos.CENTER);
        BorderPane.setAlignment(topLabelsVBox, Pos.TOP_CENTER);
        BorderPane.setAlignment(buttonDescriptions, Pos.CENTER);
        BorderPane.setAlignment(group, Pos.TOP_CENTER);
        BorderPane boPane = new BorderPane(group, topLabelsVBox, buttonDescriptions, myChart.createChart(width, stringName, DEFAULT_RESOURCE_PACKAGE), allButtonsVBox);

        animation.play();

        Scene scene = new Scene(boPane, width, height);
        return scene;
    }

    /**
     * Allows the scene to step through each cycle. Each time this function is called,
     * it calls update on the current sim and goes to that simulation to update based
     * on its rules. After updating, it updates the rectangle colors to match what just
     * updated in the simulation
     * @param myCurrSim the current simulation that is running at the moment
     */
    public void step(Simulation myCurrSim, String simulationName){
        myCurrSim.update();
        LIGHTGREEN_COUNT = 0;
        ORANGERED_COUNT = 0;
        WHITE_COUNT = 0;
        updateGrid(myCurrSim, simulationName);
        STEP_COUNT ++;
        myChart.updateChart(LIGHTGREEN_COUNT, ORANGERED_COUNT, WHITE_COUNT, STEP_COUNT);
    }

    private void updateGrid(Simulation myCurrSim, String simulationName){
        for (int row = 0; row < myGrid.length; row++) {
            for (int col = 0 ; col < myGrid[0].length ; col++) {
                int finalRow = row;
                int finalCol = col;
                if (myCurrSim.cellStatus(row,col) == OPEN){
                    LIGHTGREEN_COUNT ++;
                    myGrid[row][col].setFill(Color.LIGHTGREEN);
                    if (!simulationName.equals("GameOfLife")){
                        myGrid[row][col].setOnMouseClicked(e -> myCurrSim.changeCellStatus(finalRow, finalCol, OPEN));
                    }
                }
                else if (myCurrSim.cellStatus(row,col) == FULL){
                    ORANGERED_COUNT ++;
                    myGrid[row][col].setFill(Color.ORANGERED);
                    myGrid[row][col].setOnMouseClicked(e -> myCurrSim.changeCellStatus(finalRow, finalCol, FULL));
                }
                else {
                    WHITE_COUNT ++;
                    myGrid[row][col].setFill(Color.WHITE);
                    myGrid[row][col].setOnMouseClicked(e -> myCurrSim.changeCellStatus(finalRow, finalCol, EMPTY));
                }
            }
        }
    }

    private VBox createAllButtons(Simulation myCurrSim, Button oldSimButton, String simName){
        allButtonsVBox = new VBox();

        pause = new Button(myResources.getString("Pause"));
        resume = new Button (myResources.getString("Resume"));
        stepThrough = new Button(myResources.getString("StepThrough"));
        speedSimUp = new Button(myResources.getString("speedUpButton"));
        slowSimDown = new Button(myResources.getString("slowDownButton"));
        chooseSimButtonSim = new Button(myResources.getString("chooseSimButton"));
        saveButton = new Button(myResources.getString("saveButton"));

        buttonSizes();
        buttonActions(myCurrSim, oldSimButton, simName);
        allButtonsVBox.getChildren().addAll(pause, resume, stepThrough, speedSimUp, slowSimDown, chooseSimButtonSim, saveButton);
        allButtonsVBox.setAlignment(Pos.CENTER);
        return allButtonsVBox;
    }

    private void buttonSizes(){
        pause.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        resume.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        stepThrough.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        speedSimUp.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        slowSimDown.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        chooseSimButtonSim.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        saveButton.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
    }

    private void buttonActions(Simulation myCurrSim, Button oldSimButton, String simName){
        pause.setOnAction(e -> {
            theAnimation.stop();
            stepThrough.setOnAction(ev -> step(myCurrSim, simName));
        });
        resume.setOnAction(e -> {
            theAnimation.play();
            stepThrough.setOnAction(ev -> theAnimation.play());
        });
        speedSimUp.setOnAction(e -> speedSimUp());
        slowSimDown.setOnAction(e -> slowSimDown());
        chooseSimButtonSim.setOnAction(oldSimButton.getOnAction());
    }

    private VBox createTopLabels(String stringName){
        topLabelsVBox = new VBox();
        Label nameLabel = new Label(stringName);
        Label rules = new Label();
        setRulesText(stringName, rules);

        nameLabel.setFont(titleFont);
        nameLabel.setAlignment(Pos.CENTER);

        rules.setAlignment(Pos.CENTER);
        rules.setWrapText(true);

        topLabelsVBox.getChildren().addAll(nameLabel, rules);
        topLabelsVBox.setAlignment(Pos.TOP_CENTER);
        return topLabelsVBox;
    }

    private void setRulesText(String simName, Label rules){
        if (simName.equals("Percolation")){
            rules.setText(myResources.getString("PercolationRules"));
        }
        else if (simName.equals("GameOfLife")){
            rules.setText(myResources.getString("GOLRules"));
        }
        else if (simName.equals("Fire")){
            rules.setText(myResources.getString("FireRules"));
        }
        else if (simName.equals("PredatorPrey")){
            rules.setText(myResources.getString("PPRules"));
        }
        else if (simName.equals("Segregation")){
            rules.setText(myResources.getString("SegRules"));
        }
        else if (simName.equals("PRS")){
            rules.setText(myResources.getString("RPSRules"));
        }
    }

    private Label createButtonLabel(){
        buttonDescriptions = new Label(myResources.getString("ButtonRules"));
        buttonDescriptions.setPrefWidth(150);
        buttonDescriptions.setWrapText(true);
        return buttonDescriptions;
    }

    private void setUpGrid(Simulation myCurrSim, ReadXML mySimFileReader){
        group = new Group();
        myGrid = new Rectangle[mySimFileReader.getRow()][mySimFileReader.getCol()];
        RECTANGLE_SIZE_ROW = (GRID_SIZE/mySimFileReader.getRow());
        RECTANGLE_SIZE_COL = (GRID_SIZE/mySimFileReader.getCol());
        for (int row = 0; row < myGrid.length; row++) {
            for (int col = 0 ; col < myGrid[row].length ; col++) {
                Rectangle rec = myRectangle(col*RECTANGLE_SIZE_COL, row*RECTANGLE_SIZE_ROW, RECTANGLE_SIZE_COL, RECTANGLE_SIZE_ROW);
                if (myCurrSim.cellStatus(row,col) == OPEN){
                    rec.setFill(Color.LIGHTGREEN);
                }
                else if (myCurrSim.cellStatus(row,col) == FULL){
                    rec.setFill(Color.ORANGERED);
                }
                else {
                    rec.setFill(Color.WHITE);
                }
                group.getChildren().add(rec);
                myGrid[row][col] = rec;
            }
        }
    }

    private Rectangle myRectangle(double x, double y, double w, double h){
        Rectangle rectangle = new Rectangle(w, h);
        rectangle.setX(x);
        rectangle.setY(y);
        return rectangle;
    }

    private void speedSimUp(){
        if (theAnimation.getRate() != 60){
            theAnimation.setRate(theAnimation.getRate() + 1);
        }
    }

    private void slowSimDown(){
        if (theAnimation.getRate() != 1){
            theAnimation.setRate(theAnimation.getRate() - 1);
        }
    }
}
