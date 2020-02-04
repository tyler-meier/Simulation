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

    private Button pause, resume, stepThrough;
    private Rectangle[][] myGrid;
    private Group group;
    private VBox allButtonsVBox, topLabelsVBox;
    private Label buttonDescriptions;

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

        pause = new Button("Pause");
        resume = new Button ("Resume");
        stepThrough = new Button("Step Through Sim");

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
            rules.setText("Percolation Rules: \n\n" +
                    "1. Some cells start as open (light green), some start as closed (white), and one starts as open and full (orange red)\n" +
                    "2. The open and full cell will then look at all of its eight surrounding neighbors (up, down, left, right and diagonals) and if any of them are open and not full, it will fill them (turn orange red).\n" +
                    "3. The simulation appears stopped when there are no more cells that are able to be filled");
        }
        else if (stringName.equals("GameOfLife")){
            rules.setText("Game Of Life Rules:\n\n" +
                    "1. Some cells start as alive (light green) and others start as dead (white)\n" +
                    "2. If a cell that is alive has greater than three or less than two neighbors that are also alive, then that cell will die (turn white). If the cell has exactly 2 or 3 neighbors that are alive, then that cell stays alive (stays light green)\n" +
                    "3. If a cell that is dead has exactly 3 alive neighbors, then it will become alive (turn light green)\n" +
                    "4. The simulation is continuous");
        }
        else if (stringName.equals("Fire")){
            rules.setText("Fire Rules:\n\n" +
                    "1. All cells start as trees (light green) except for one cell that is set to burning (orange red) and an outer boundary that is set to burnt/empty (white)\n" +
                    "2. Every burnt/empty cell throughout the simulation will stay burnt/empty\n" +
                    "3. Each burning cell will look at each of its four neighbors (up, down, left, right). If a neighbor is burnt/empty, it won’t affect that cell. If a neighbor cell is a tree, then that cell may or may not be changed to burning based off of a preset probability\n" +
                    "4. All burning cells only stay burning for one cycle, and then they change to burnt/empty\n" +
                    "5. The simulation stops running when there are no more burning cells");
        }
        else if (stringName.equals("PredatorPrey")){
            rules.setText("");
        }
        else if (stringName.equals("Segregation")){
            rules.setText("Segregation Rules:\n\n" +
                    "1. Some cells start as population 1 (light green), some start as population 2 (orange red) and some are empty (white)\n" +
                    "2. Each cell will determine if it is satisfied with its position. This means that it will check if it is surrounded by a preset percentage of cells like itself (of the same population) \n" +
                    "3. If a cell is not satisfied, then it will move to an empty cell\n" +
                    "4. The simulation will run as long as it can until all cells are completely satisfied and ‘segregated’. This means the simulation could be continuous");
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
        buttonDescriptions = new Label("Pause - Pauses the simulation\n\n" +
                "Resume - Resumes the simulation if paused\n\n" +
                "Step Through Sim - Steps through the simulation one frame at a time\n\n" +
                "Speed Up - Speeds up the simulation\n\n" +
                "Slow Down - Slows down the simulation\n\n" +
                "Choose Simulation - Allows user to choose a different simulation to run");
        buttonDescriptions.setPrefWidth(150);
        buttonDescriptions.setWrapText(true);
        return buttonDescriptions;
    }
}
