package cellsociety;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * The visualization class that controls start of the program
 * and sets up the main scene that allows users to choose simulation
 *
 * @author Tyler Meier (tkm22)
 */
public class Visualization extends Application {
    public static final int SIZE = 700;
    public static final Font titleFont = new Font("Arial", 80);
    public static final Font subtitleFont = new Font("Arial", 25);
    private static final String RESOURCES = "resources";
    public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
    public static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";

    private int FRAMES_PER_SECOND = 1;
    private int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private Boolean simStarted = false;
    private Scene startScene, simScene;
    private Stage myStage;
    private Button chooseSimButton,speedUp, slowDown;
    private Label welcomeLabel, explainLabel;
    private simulation myCurrSim;
    private ReadXML mySimFileReader;
    private Visualizer myView;
    private Timeline animation;
    private KeyFrame frame;
    private ResourceBundle myResources;

    /**
     * Initializes what will be displayed and how to display it, also sets the sim button
     * so you can choose a simulation to display
     * @param primaryStage the primary stage of the program
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        myStage = primaryStage;
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "allStrings");
        myStage.setTitle(myResources.getString("TITLE"));
        startScene = setupStartScene(SIZE, SIZE);

        frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);

        myStage.setScene(startScene);
        myStage.show();
    }

    private Scene setupStartScene(int width, int height) throws ParserConfigurationException {
        VBox vBox = new VBox();

        setUpWelcomeLabel();
        setUpExplainLabel();
        setUpButtons();

        vBox.getChildren().addAll(welcomeLabel, explainLabel, chooseSimButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        Scene scene = new Scene(vBox, width, height);
        return scene;
    }

    private String setUpFile(ReadXML mySimFileReader) throws IOException, SAXException {
        FileChooser fileChoose = new FileChooser();
        fileChoose.setTitle("Choose File");
        File simFile = fileChoose.showOpenDialog(myStage);
        mySimFileReader.setUpFile(simFile);
        String simName = mySimFileReader.getParameters(mySimFileReader.TYPE);
        return simName;
    }

    private void returnSim(String simName) throws IOException, SAXException, ParserConfigurationException {
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

    private void setUpButtons() throws ParserConfigurationException {
        chooseSimButton = new Button(myResources.getString("chooseSimButton"));
        speedUp = new Button(myResources.getString("speedUpButton"));
        slowDown = new Button(myResources.getString("slowDownButton"));

        myView = new Visualizer();
        mySimFileReader = new ReadXML();

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
                simScene = myView.setUpSimulationScene(SIZE, SIZE, simName, myCurrSim, mySimFileReader, chooseSimButton, animation, speedUp, slowDown);
                myStage.setScene(simScene);
                myStage.show();
            } catch (IOException ex) {
                ex.printStackTrace(); //need to fix this to something else, change these exceptions, REALLY NEED TO CHANGE
            } catch (SAXException ex) {
                ex.printStackTrace();
            } catch (ParserConfigurationException ex) {
                ex.printStackTrace();
            }
        });
        speedUp.setOnAction(e -> speedSimUp());
        slowDown.setOnAction(e -> slowSimDown());
    }

    private void setUpWelcomeLabel(){
        welcomeLabel = new Label(myResources.getString("Main_Menu"));
        welcomeLabel.setFont(titleFont);
    }

    private void setUpExplainLabel(){
        explainLabel = new Label(myResources.getString("Explain_Label"));
        explainLabel.setFont(subtitleFont);
        explainLabel.setWrapText(true);
        explainLabel.setTextAlignment(TextAlignment.CENTER);
    }

    private void slowSimDown(){
        if (animation.getRate() != 1){
            animation.setRate(animation.getRate() - 1);
        }
    }

    private void speedSimUp(){
        if (animation.getRate() != 60){
            animation.setRate(animation.getRate() + 1);
        }
    }

    private void step(){
        if (myCurrSim != null) {
            myView.step(myCurrSim);
        }
    }

    /**
     * Starts the program, acts as main class
     * @param args what the program takes to run
     */
    public static void main (String[] args) { launch(args); }
}
