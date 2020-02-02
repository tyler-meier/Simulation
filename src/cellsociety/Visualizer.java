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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

class Visualizer extends Application {
    public static final int RECTANGLE_SIZE = 30;
    public static final int OPEN = 1;
    public static final int FULL = 2;
    public static final double PREF_BUTTON_WIDTH = 250;
    public static final double PREF_BUTTON_HEIGHT = 100;
    public static final Font titleFont = new Font("Arial", 50);
    public static int FRAMES_PER_SECOND = 1;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;

    private Button mainMenu, pause, resume;
    private Rectangle[][] myGrid;
    private Group group;

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public Scene setUpSimulationScene(int width, int height, Paint background, String stringName, Stage myStage, Scene startScene, simulation myCurrSim, ReadXML mySimFileReader, Button simButton, Timeline animation, Button speedUp, Button slowDown) {
        VBox buttonsVBox = new VBox();

        Label nameLabel = new Label(stringName);
        nameLabel.setFont(titleFont);
        nameLabel.setAlignment(Pos.CENTER);

        mainMenu = new Button("Main Menu");
        pause = new Button("Pause");
        resume = new Button ("Resume");

        mainMenu.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        pause.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        resume.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        speedUp.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        slowDown.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);
        simButton.setMaxSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);

        group = new Group();
        setUpGrid(myCurrSim, mySimFileReader);

        mainMenu.setOnAction(e -> myStage.setScene(startScene));
        pause.setOnAction(e -> animation.stop());
        resume.setOnAction(e -> animation.play());

        buttonsVBox.getChildren().addAll(mainMenu, pause, resume, speedUp, slowDown, simButton);
        buttonsVBox.setAlignment(Pos.CENTER_LEFT);
        BorderPane.setAlignment(nameLabel, Pos.TOP_CENTER);
        BorderPane.setAlignment(group, Pos.CENTER);
        BorderPane boPane = new BorderPane(group, nameLabel, null, null, buttonsVBox);

        Scene scene = new Scene(boPane, width, height, background);
        return scene;
    }

    public void step(simulation myCurrSim){
        myCurrSim.update();
        for (int row = 0; row < myGrid.length; row++) {
            for (int col = 0 ; col < myGrid[0].length ; col++) {
                if (myCurrSim.cellStatus(row,col) == 1){
                    myGrid[row][col].setFill(Color.BLUE);
                }
                else if (myCurrSim.cellStatus(row,col) == 2){
                    myGrid[row][col].setFill(Color.GREEN);
                }
                else {
                    myGrid[row][col].setFill(Color.BLACK);
                }
            }
        }
    }

    public void setUpGrid(simulation myCurrSim, ReadXML mySimFileReader){
        myGrid = new Rectangle[mySimFileReader.getRow()][mySimFileReader.getCol()];
        for (int row = 0; row < myGrid.length; row++) {
            for (int col = 0 ; col < myGrid[row].length ; col++) {
                Rectangle rec = myRectangle(col*RECTANGLE_SIZE, row*RECTANGLE_SIZE, RECTANGLE_SIZE, RECTANGLE_SIZE);
                if (myCurrSim.cellStatus(row,col) == OPEN){
                    rec.setFill(Color.BLUE);
                }
                else if (myCurrSim.cellStatus(row,col) == FULL){
                    rec.setFill(Color.GREEN);
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

}
