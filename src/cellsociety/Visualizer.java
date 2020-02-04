package cellsociety;

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
class Visualizer extends Application {
    public static final int OPEN = 1;
    public static final int FULL = 2;
    public static final double PREF_BUTTON_WIDTH = 250;
    public static final double PREF_BUTTON_HEIGHT = 100;
    public static final Font titleFont = new Font("Arial", 50);
    public static int RECTANGLE_SIZE_ROW;
    public static int RECTANGLE_SIZE_COL;
    public static int GRID_SIZE = 350;
    private static final String RESOURCES = "resources";
    public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
    public static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";

    private Button pause, resume, stepThrough;
    private Rectangle[][] myGrid;
    private Group group;
    private VBox allButtonsVBox, topLabelsVBox;
    private Label buttonDescriptions;
    private ResourceBundle myResources;

    /**
     * Start method for visualizer, just need so it can extend application, this start
     * method doesn't actually do anything
     * @param primaryStage null stage, doesn't actually show anything
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

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
     * @param simButton the button that allows the user to file choose a new simulation to run, everything
     *                  is already set up for this button, it is just being passed onto this scene
     * @param animation the animation for this scene, being passed from Visualization so that the animation
     *                  can play in this scene
     * @param speedUp the button that allows the simulation to speed up by cycles, already set up in visualization
     * @param slowDown the button that allows the simulation to slow down the cycle, already set up in visualization
     * @return scene, the whole set up scene for the simulation
     */
    public Scene setUpSimulationScene(int width, int height, String stringName, simulation myCurrSim, ReadXML mySimFileReader, Button simButton, Timeline animation, Button speedUp, Button slowDown) {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "allStrings");

        createAllButtons(speedUp, slowDown, simButton, animation, myCurrSim);
        createTopLabels(stringName);
        createButtonLabel();
        setUpGrid(myCurrSim, mySimFileReader);

        BorderPane.setAlignment(allButtonsVBox, Pos.CENTER_LEFT);
        BorderPane.setAlignment(topLabelsVBox, Pos.TOP_CENTER);
        BorderPane.setAlignment(buttonDescriptions, Pos.CENTER_RIGHT);
        BorderPane.setAlignment(group, Pos.CENTER);
        BorderPane boPane = new BorderPane(group, topLabelsVBox, buttonDescriptions, null, allButtonsVBox);

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
    public void step(simulation myCurrSim){
        myCurrSim.update();
        for (int row = 0; row < myGrid.length; row++) {
            for (int col = 0 ; col < myGrid[0].length ; col++) {
                if (myCurrSim.cellStatus(row,col) == OPEN){
                    myGrid[row][col].setFill(Color.LIGHTGREEN);
                }
                else if (myCurrSim.cellStatus(row,col) == FULL){
                    myGrid[row][col].setFill(Color.ORANGERED);
                }
                else {
                    myGrid[row][col].setFill(Color.WHITE);
                }
            }
        }
    }

    private void setUpGrid(simulation myCurrSim, ReadXML mySimFileReader){
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

    private VBox createAllButtons(Button speedUp, Button slowDown, Button simButton, Timeline animation, simulation myCurrSim){
        allButtonsVBox = new VBox();

        pause = new Button(myResources.getString("Pause"));
        resume = new Button (myResources.getString("Resume"));
        stepThrough = new Button(myResources.getString("StepThrough"));

        pause.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        resume.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        stepThrough.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        speedUp.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        slowDown.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        simButton.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);

        pause.setOnAction(e -> {
            animation.stop();
            stepThrough.setOnAction(ev -> step(myCurrSim));
        });
        resume.setOnAction(e -> {
            animation.play();
            stepThrough.setOnAction(ev -> animation.play());
        });

        allButtonsVBox.getChildren().addAll(pause, resume, stepThrough, speedUp, slowDown, simButton);
        allButtonsVBox.setAlignment(Pos.CENTER_LEFT);
        return allButtonsVBox;
    }

    private VBox createTopLabels(String stringName){
        topLabelsVBox = new VBox();
        Label nameLabel = new Label(stringName);
        Label rules = new Label();
        if (stringName.equals("Percolation")){
            rules.setText(myResources.getString("PercolationRules"));
        }
        else if (stringName.equals("GameOfLife")){
            rules.setText(myResources.getString("GOLRules"));
        }
        else if (stringName.equals("Fire")){
            rules.setText(myResources.getString("FireRules"));
        }
        else if (stringName.equals("PredatorPrey")){
            rules.setText(myResources.getString("PPRules"));
        }
        else if (stringName.equals("Segregation")){
            rules.setText(myResources.getString("SegRules"));
        }
        nameLabel.setFont(titleFont);
        nameLabel.setAlignment(Pos.CENTER);

        rules.setAlignment(Pos.CENTER);
        rules.setWrapText(true);

        topLabelsVBox.getChildren().addAll(nameLabel, rules);
        topLabelsVBox.setAlignment(Pos.TOP_CENTER);
        topLabelsVBox.setSpacing(15);
        return topLabelsVBox;
    }

    private Label createButtonLabel(){
        buttonDescriptions = new Label(myResources.getString("ButtonRules"));
        buttonDescriptions.setPrefWidth(150);
        buttonDescriptions.setWrapText(true);
        return buttonDescriptions;
    }
}
