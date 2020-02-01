package cellsociety;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
    public static final int FRAMES_PER_SECOND = 1;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final double PREF_BUTTON_WIDTH = 250;
    public static final double PREF_BUTTON_HEIGHT = 100;
    public static final Paint BACKGROUND = Color.GOLD;
    public static final Font titleFont = new Font("Arial", 50);
    public static final Font subtitleFont = new Font("Arial", 25);

    private Scene startScene, simScene;
    private Stage myStage;
    private Button startSimButton;
    private simulation myCurrSim;
    private ReadXML mySimFileReader;
    private Visualizer myView;


    /**
     * Initializes what will be displayed and how to display it
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        myStage = primaryStage;
        myStage.setTitle(TITLE);
        startScene = setupStartScene(SIZE, SIZE, BACKGROUND);
        myView = new Visualizer();
        mySimFileReader = new ReadXML();
        startSimButton.setOnAction(e -> {
            try {
                String simName = setUpFile();
                simScene = myView.setUpSimulationScene(SIZE, SIZE, BACKGROUND, simName, myStage, startScene);
                myView.setUpGrid(myCurrSim, mySimFileReader);
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
        myStage.setScene(startScene);
        myStage.show();
    }


    private Scene setupStartScene(int width, int height, Paint background){
        VBox vBox = new VBox();
        VBox vBox2 = new VBox();

        Label welcomeLabel = new Label("MAIN MENU");
        welcomeLabel.setFont(titleFont);
        Label explainLabel = new Label("Click on the simulation that you would like to see");
        explainLabel.setFont(subtitleFont);

        startSimButton = new Button("Start a Simulation");

        startSimButton.setPrefSize(PREF_BUTTON_WIDTH, PREF_BUTTON_HEIGHT);

        startSimButton.setStyle("-fx-font-size: 2em; ");

        vBox.getChildren().addAll(welcomeLabel, explainLabel);
        vBox2.getChildren().addAll(startSimButton);

        vBox.setAlignment(Pos.CENTER);
        vBox2.setAlignment(Pos.CENTER);
        vBox.setSpacing(7);
        vBox2.setSpacing(18);

        BorderPane.setAlignment(vBox, Pos.TOP_CENTER);
        BorderPane.setAlignment(vBox2, Pos.CENTER);

        BorderPane boPane = new BorderPane(vBox2, vBox, null, null, null);
        boPane.setStyle("-fx-background-color: mediumspringgreen");

        Scene scene = new Scene(boPane, width, height, background);
        return scene;
    }

    public String setUpFile() throws IOException, SAXException {
        FileChooser fileChoose = new FileChooser();
        fileChoose.setTitle("BALKNA");
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
           myCurrSim = new PredatorPrey();
        }
        else if(simName.equals("Segregation")){
           myCurrSim = new Segregation();
        }
        else if(simName.equals("Fire")){
           myCurrSim = new Fire();
        }
        else if(simName.equals("GameOfLife")){
           myCurrSim = new GameOfLife(10);
        }
    }
    private void step(double elapsedTime){
        myView.step(elapsedTime, myCurrSim);
    }

    public static void main (String[] args) { launch(args); }
}
