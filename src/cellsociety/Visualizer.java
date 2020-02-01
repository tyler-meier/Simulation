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
    public static final int FRAMES_PER_SECOND = 1;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final double PREF_BUTTON_WIDTH = 250;
    public static final double PREF_BUTTON_HEIGHT = 100;
    public static final Font titleFont = new Font("Arial", 50);
    public static final Font subtitleFont = new Font("Arial", 25);

    private Button mainMenu, pause, resume, speedUp, slowDown;
    private Rectangle[][] myGrid;
    private Group group;

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public Scene setUpSimulationScene(int width, int height, Paint background, String stringName, Stage myStage, Scene startScene) throws IOException, SAXException, ParserConfigurationException {
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

        /*myPercolationGrid = new Percolation();
        myGrid = new Rectangle[35][35];
        group = new Group();
        setUpGrid();*/

        buttonsVBox.getChildren().addAll(mainMenu, pause, resume, speedUp, slowDown);
        buttonsVBox.setAlignment(Pos.CENTER_LEFT);
        BorderPane.setAlignment(nameLabel, Pos.TOP_CENTER);
        //BorderPane.setAlignment(group, Pos.TOP_LEFT);
        BorderPane boPane = new BorderPane(group, nameLabel, null, null, buttonsVBox);

        /*KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();*/

        Scene scene = new Scene(boPane, width, height, background);
        return scene;
    }

    private void step(double elapsedTime){
       // myCurrSim.update();
    }

    /*public void setUpGrid(simulation myCurrSim){
        for (int row = 0; row < myGrid.length; row++) {
            for (int col = 0 ; col < myGrid[row].length ; col++) {
                Rectangle rec = myRectangle(col*15, row*15, 15, 15);
                if (myCurrSim.cellStatus(row,col) == 2){
                    rec.setFill(Color.BLUE);
                }
                if (myCurrSim.cellStatus(row,col) == 4){
                    rec.setFill(Color.GREEN);
                }
                group.getChildren().add(rec);
            }
        }
    }*/
    private Rectangle myRectangle(double x, double y, double w, double h){
        Rectangle rectangle = new Rectangle(w, h);
        rectangle.setX(x);
        rectangle.setY(y);
        return rectangle;
    }

}
