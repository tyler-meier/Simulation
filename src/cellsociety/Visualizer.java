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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public Scene setUpSimulationScene(int width, int height, Paint background, String stringName, simulation myCurrSim, ReadXML mySimFileReader, Button simButton, Timeline animation, Button speedUp, Button slowDown) {

        createAllButtons(speedUp, slowDown, simButton);
        createTopLabels(stringName);
        createButtonLabel();
        setUpGrid(myCurrSim, mySimFileReader);

        BorderPane.setAlignment(allButtonsVBox, Pos.CENTER_LEFT);
        BorderPane.setAlignment(topLabelsVBox, Pos.TOP_CENTER);
        BorderPane.setAlignment(buttonDescriptions, Pos.CENTER_RIGHT);
        BorderPane.setAlignment(group, Pos.CENTER);
        BorderPane boPane = new BorderPane(group, topLabelsVBox, buttonDescriptions, null, allButtonsVBox);

        animation.play();

        Scene scene = new Scene(boPane, width, height, background);
        return scene;
    }

    public void step(simulation myCurrSim){
        myCurrSim.update();
        for (int row = 0; row < myGrid.length; row++) {
            for (int col = 0 ; col < myGrid[0].length ; col++) {
                if (myCurrSim.cellStatus(row,col) == 1){
                    myGrid[row][col].setFill(Color.LIGHTGREEN);
                }
                else if (myCurrSim.cellStatus(row,col) == 2){
                    myGrid[row][col].setFill(Color.ORANGERED);
                }
                else {
                    myGrid[row][col].setFill(Color.BLACK);
                }
            }
        }
    }

    public void setUpGrid(simulation myCurrSim, ReadXML mySimFileReader){
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
                    rec.setFill(Color.BLACK);
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

    private VBox createAllButtons(Button speedUp, Button slowDown, Button simButton){
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
                    "1. Some cells start as open (light green), some start as closed (black), and one starts as open and full (orange red)\n" +
                    "2. The open and full cell will then look at all of its eight surrounding neighbors (up, down, left, right and diagonals) and if any of them are open and not full, it will fill them (turn orange red).\n" +
                    "3. The simulation appears stopped when there are no more cells that are able to be filled");
        }
        else if (stringName.equals("GameOfLife")){
            rules.setText("Game Of Life Rules:\n\n" +
                    "1. Some cells start as alive (light green) and others start as dead (black)\n" +
                    "2. If a cell that is alive has greater than three or less than two neighbors that are also alive, then that cell will die (turn black). If the cell has exactly 2 or 3 neighbors that are alive, then that cell stays alive (stays light green)\n" +
                    "3. If a cell that is dead has exactly 3 alive neighbors, then it will become alive (turn light green)\n" +
                    "4. The simulation is continuous");
        }
        else if (stringName.equals("Fire")){
            rules.setText("Fire Rules:\n\n" +
                    "1. All cells start as trees (light green) except for one cell that is set to burning (orange red) and an outer boundary that is set to burnt/empty (black)\n" +
                    "2. Every burnt/empty cell throughout the simulation will stay burnt/empty\n" +
                    "3. Each burning cell will look at each of its four neighbors (up, down, left, right). If a neighbor is burnt/empty, it won’t affect that cell. If a neighbor cell is a tree, then that cell may or may not be changed to burning based off of a preset probability\n" +
                    "4. All burning cells only stay burning for one cycle, and then they change to burnt/empty\n" +
                    "5. The simulation stops running when there are no more burning cells");
        }
        else if (stringName.equals("PredatorPrey")){
            rules.setText("");
        }
        else if (stringName.equals("Segregation")){
            rules.setText("");
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
