package cellsociety;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * The visualization class that controls the simulation visuals
 *
 * @author Tyler Meier (tkm22), Farzeen Najam(fn26), Erik Gregorio(eg158)
 */
public class Visualization extends Application {
    public static final String TITLE = "Simulation Project";
    public static final int SIZE = 700;
    public static final Paint BACKGROUND = Color.GOLD;
    public static final Font titleFont = new Font("Arial", 80);
    public static final Font subtitleFont = new Font("Arial", 25);
    private int FRAMES_PER_SECOND = 1;
    private int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private Boolean simStarted = false;

    private Scene startScene, simScene;
    private Stage myStage;
    private Button chooseSimButton,speedUp, slowDown;
    private simulation myCurrSim;
    private ReadXML mySimFileReader;
    private Visualizer myView;
    Timeline animation;
    KeyFrame frame;


    /**
     * Initializes what will be displayed and how to display it
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("stylesheet.css"));
        myStage = primaryStage;
        myStage.setTitle(TITLE);
        startScene = setupStartScene(SIZE, SIZE, BACKGROUND);
        myView = new Visualizer();
        mySimFileReader = new ReadXML();

        frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);

        chooseSimButton.setOnAction(e -> {
            try {
                String simName = setUpFile(mySimFileReader);
                returnSim(simName);
                if (!simStarted){
                    animation.play();
                    simStarted = true;
                }
                else {
                    animation.setRate(FRAMES_PER_SECOND);
                }
                simScene = myView.setUpSimulationScene(SIZE, SIZE, BACKGROUND, simName, myCurrSim, mySimFileReader, chooseSimButton, animation, speedUp, slowDown);
                myStage.setScene(simScene);
                myStage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (SAXException ex) {
                ex.printStackTrace();
            } catch (ParserConfigurationException ex) {
                ex.printStackTrace();
            }
        });
        speedUp.setOnAction(e -> speedSimUp());
        slowDown.setOnAction(e -> slowSimDown());

        myStage.setScene(startScene);
        //startScene.getStylesheets().add("/Users/tylermeier/Documents/comp308 workspace/simulation_team20/doc/stylesheet.css");
        myStage.show();
    }


    private Scene setupStartScene(int width, int height, Paint background){
        VBox vBox = new VBox();
        VBox vBox2 = new VBox();

        Label welcomeLabel = new Label("MAIN MENU");
        welcomeLabel.setFont(titleFont);
        Label explainLabel = new Label("Click on “Choose Simulation” to choose the file for the simulation that you would like to run. Once you choose the first simulation, you will be able to switch to a different simulation from the current simulation scene. You will not be able to come back to this main menu screen. Have fun looking at the simulations!");
        explainLabel.setFont(subtitleFont);
        explainLabel.setWrapText(true);
        explainLabel.setTextAlignment(TextAlignment.CENTER);

        chooseSimButton = new Button("Choose Simulation");
        speedUp = new Button("Speed Up");
        slowDown = new Button("Slow Down");

        vBox.getChildren().addAll(welcomeLabel, explainLabel);
        vBox2.getChildren().addAll(chooseSimButton);

        vBox.setAlignment(Pos.CENTER);
        vBox2.setAlignment(Pos.CENTER);
        vBox.setSpacing(7);

        BorderPane.setAlignment(vBox, Pos.TOP_CENTER);
        BorderPane.setAlignment(vBox2, Pos.CENTER);

        BorderPane boPane = new BorderPane(vBox2, vBox, null, null, null);

        Scene scene = new Scene(boPane, width, height, background);
        return scene;
    }

    public String setUpFile(ReadXML mySimFileReader) throws IOException, SAXException {
        FileChooser fileChoose = new FileChooser();
        fileChoose.setTitle("Choose File");
        File simFile = fileChoose.showOpenDialog(myStage);
        mySimFileReader.setUpFile(simFile);
        String simName = mySimFileReader.getParameters(mySimFileReader.TYPE);
        return simName;
    }

    public void returnSim(String simName) throws IOException, SAXException, ParserConfigurationException {
        if(simName.equals("Percolation")){
            myCurrSim = new Percolation(mySimFileReader);
        }
        else if(simName.equals("PredatorPrey")){
           myCurrSim = new PredatorPrey(mySimFileReader);
        }
        else if(simName.equals("Segregation")){
           myCurrSim = new Segregation(mySimFileReader);
        }
        else if(simName.equals("Fire")){
           myCurrSim = new Fire(mySimFileReader);
        }
        else if(simName.equals("GameOfLife")){
           myCurrSim = new GameOfLife(mySimFileReader);
        }
    }

    public void slowSimDown(){
        if (animation.getRate() != 1){
            animation.setRate(animation.getRate() - 1);
        }
    }

    public void speedSimUp(){
        if (animation.getRate() != 60){
            animation.setRate(animation.getRate() + 1);
        }
    }

    private void step(){
        if (myCurrSim != null) {
            myView.step(myCurrSim);
        }
    }

    public static void main (String[] args) { launch(args); }
}
