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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

class Visualizer extends Application {
    public static final int OPEN = 1;
    public static final int FULL = 2;
    public static final double PREF_BUTTON_WIDTH = 250;
    public static final double PREF_BUTTON_HEIGHT = 100;
    public static final Font titleFont = new Font("Arial", 50);
    public static int FRAMES_PER_SECOND = 1;
    public static int RECTANGLE_SIZE_ROW;
    public static int RECTANGLE_SIZE_COL;
    public static int GRID_SIZE = 350;

    private Button pause, resume, stepThrough;
    private Rectangle[][] myGrid;
    private Group group;

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public Scene setUpSimulationScene(int width, int height, Paint background, String stringName, simulation myCurrSim, ReadXML mySimFileReader, Button simButton, Timeline animation, Button speedUp, Button slowDown) {
        VBox buttonsVBox = new VBox();
        VBox topLabels = new VBox();

        Label nameLabel = new Label(stringName);
        nameLabel.setFont(titleFont);
        nameLabel.setAlignment(Pos.CENTER);

        Label rules = createRuleLabel(stringName);
        Label buttonDescriptions = createButtonLabel();

        topLabels.getChildren().addAll(nameLabel, rules);
        topLabels.setSpacing(15);

        pause = new Button("Pause");
        resume = new Button ("Resume");
        stepThrough = new Button("Step Through Sim");

        pause.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        resume.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        stepThrough.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        speedUp.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        slowDown.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        simButton.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);

        group = new Group();
        setUpGrid(myCurrSim, mySimFileReader);

        pause.setOnAction(e -> {
            animation.stop();
            stepThrough.setOnAction(ev -> step(myCurrSim)); // when paused, can step through sim
        });
        resume.setOnAction(e -> {
            animation.play();
            stepThrough.setOnAction(ev -> animation.play()); //when it resumes, can't step through anymore (unsure if animation.play is the right thing to do
        });

        buttonsVBox.getChildren().addAll(pause, resume, stepThrough, speedUp, slowDown, simButton);
        buttonsVBox.setAlignment(Pos.CENTER_LEFT);
        topLabels.setAlignment(Pos.TOP_CENTER);
        BorderPane.setAlignment(nameLabel, Pos.TOP_CENTER);
        BorderPane.setAlignment(buttonDescriptions, Pos.CENTER_RIGHT);
        BorderPane.setAlignment(group, Pos.CENTER);
        BorderPane boPane = new BorderPane(group, topLabels, buttonDescriptions, null, buttonsVBox);

        animation.play();

        Scene scene = new Scene(boPane, width, height, background);
        return scene;
    }

    public void step(simulation myCurrSim){
        myCurrSim.update();
        for (int row = 0; row < myGrid.length; row++) {
            for (int col = 0 ; col < myGrid[0].length ; col++) {
                if (myCurrSim.cellStatus(row,col) == 1){
                    myGrid[row][col].setFill(Color.GREEN);
                }
                else if (myCurrSim.cellStatus(row,col) == 2){
                    myGrid[row][col].setFill(Color.ORANGERED);
                }
                else {
                    myGrid[row][col].setFill(Color.MIDNIGHTBLUE);
                }
            }
        }
    }

    public void setUpGrid(simulation myCurrSim, ReadXML mySimFileReader){
        myGrid = new Rectangle[mySimFileReader.getRow()][mySimFileReader.getCol()];
        RECTANGLE_SIZE_ROW = (GRID_SIZE/mySimFileReader.getRow());
        RECTANGLE_SIZE_COL = (GRID_SIZE/mySimFileReader.getCol());
        for (int row = 0; row < myGrid.length; row++) {
            for (int col = 0 ; col < myGrid[row].length ; col++) {
                Rectangle rec = myRectangle(col*RECTANGLE_SIZE_COL, row*RECTANGLE_SIZE_ROW, RECTANGLE_SIZE_COL, RECTANGLE_SIZE_ROW);
                if (myCurrSim.cellStatus(row,col) == OPEN){
                    rec.setFill(Color.GREEN);
                }
                else if (myCurrSim.cellStatus(row,col) == FULL){
                    rec.setFill(Color.ORANGERED);
                }
                else {
                    rec.setFill(Color.MIDNIGHTBLUE);
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

    private Label createRuleLabel(String stringName){
        Label rules = new Label();
        if (stringName.equals("Percolation")){
            rules.setText("Percolation Rules: \n\n" +
                    "1. Some cells start as open(green), some start as closed(blue), and one starts as open and full(red)\n\n" +
                    "2. The open and full cell will then look at all of its eight surrounding neighbors (up, down, left, right and diagonals) and if any of them are open and not full, it will fill them.\n\n" +
                    "3. The simulation appears stopped when there are no more cells that are able to be filled");
        }
        rules.setAlignment(Pos.CENTER);
        rules.setWrapText(true);
        return rules;
    }

    private Label createButtonLabel(){
        Label buttons = new Label("Pause - Pauses the simulation\n\n" +
                "Resume - Resumes the simulation if paused\n\n" +
                "Step Through Sim - Steps through the simulation one frame at a time\n\n" +
                "Speed Up - Speeds up the simulation\n\n" +
                "Slow Down - Slows down the simulation\n\n" +
                "Choose Simulation - Allows user to choose a different simulation to run");
        buttons.setPrefWidth(150);
        buttons.setWrapText(true);
        return buttons;
    }


}
