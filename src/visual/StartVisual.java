package visual;

import javafx.scene.control.Alert;
import ruleset.*;
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
import xmlreading.ReadXML;

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
public class StartVisual extends Application {
    public static final int HEIGHT = 900;
    public static final int WIDTH = 850;
    public static final Font titleFont = new Font("Arial", 80);

    private static final Font subtitleFont = new Font("Arial", 25);
    private static final String RESOURCES = "resources";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
    private int FRAMES_PER_SECOND = 1;
    private int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private Boolean simStarted = false;
    private Boolean firstRound = false;
    private Scene startScene, simScene;
    private Stage myStage, anotherStage;
    private Button chooseSimButtonMain, anotherWindow;
    private Label welcomeLabel, explainLabel;
    private Simulation myCurrSim;
    private ReadXML mySimFileReader;
    private SimVisual myView;
    private Timeline animation, newAnimation;
    private KeyFrame frame, newFrame;
    private ResourceBundle myResources;

    /**
     * Initializes what will be displayed and how to display it, also sets the sim button
     * so you can choose a simulation to display. Also calls for the start scene to be created
     * @param primaryStage the primary stage of the program
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws ParserConfigurationException {
        myStage = primaryStage;
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "allStrings");
        myStage.setTitle(myResources.getString("TITLE"));
        startScene = setupStartScene(WIDTH, HEIGHT);

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

        vBox.getChildren().addAll(welcomeLabel, explainLabel, chooseSimButtonMain);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        Scene scene = new Scene(vBox, width, height);
        return scene;
    }

    private String setUpFile(ReadXML mySimFileReader) throws IOException, SAXException {
        FileChooser fileChoose = new FileChooser();
        fileChoose.setTitle("Choose File");
        File simFile;
        if (!firstRound) {
            simFile = fileChoose.showOpenDialog(myStage);
            firstRound = true;
        } else {
            simFile = fileChoose.showOpenDialog(anotherStage);
        }

        try{
            mySimFileReader.setUpFile(simFile);
        } catch(IllegalArgumentException e){
            dealWithException();
            return ("");
        }
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
        chooseSimButtonMain = new Button(myResources.getString("chooseSimButton"));
        anotherWindow = new Button(myResources.getString("windowButton"));

        myView = new SimVisual();
        mySimFileReader = new ReadXML();

        simButtonFunction();
        windowButtonFunction();
    }

    private void simButtonFunction(){
        chooseSimButtonMain.setOnAction(e -> {
            try {
                String simName = setUpFile(mySimFileReader);
                if (simName.equals("")) return;
                returnSim(simName);
                if (!simStarted){
                    animation.play();
                    simStarted = true;
                }
                else {
                    animation.setRate(FRAMES_PER_SECOND);
                }
                simScene = myView.setUpSimulationScene(WIDTH, HEIGHT, simName, myCurrSim, mySimFileReader, chooseSimButtonMain, anotherWindow, animation);
                myStage.setScene(simScene);
                myStage.show();
            } catch (IOException ex) {
                dealWithException();
            } catch (SAXException ex) {
                dealWithException();
            } catch (ParserConfigurationException ex) {
                dealWithException();
            }
        });
    }

    private void windowButtonFunction(){
        anotherWindow.setOnAction(e ->{
            anotherStage = new Stage();
            try {
                String simName = setUpFile(mySimFileReader);
                returnSim(simName);

                newFrame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), ev -> step());
                newAnimation = new Timeline();
                newAnimation.setCycleCount(Timeline.INDEFINITE);
                newAnimation.getKeyFrames().add(newFrame);

                simScene = myView.setUpSimulationScene(WIDTH, HEIGHT, simName, myCurrSim, mySimFileReader, chooseSimButtonMain, anotherWindow, newAnimation);
                anotherStage.setScene(simScene);
                anotherStage.show();
            } catch (IOException ex) {
                dealWithException();
            } catch (SAXException ex) {
                dealWithException();
            } catch (ParserConfigurationException ex) {
                dealWithException();
            }
        });
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

    private void step(){
        if (myCurrSim != null) {
            myView.step(myCurrSim);
        }
    }

    private void dealWithException(){
        Alert errorMessage = new Alert(Alert.AlertType.ERROR);
        errorMessage.setTitle(myResources.getString("Error_Title"));
        errorMessage.setContentText(myResources.getString("Error_Message"));
        errorMessage.show();
    }

}
