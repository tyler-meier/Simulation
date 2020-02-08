import visual.StartVisual;
import javafx.application.Application;
import javafx.stage.Stage;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Starts the entire program and simulations
 *
 * @author Tyler Meeir
 */
public class Main extends Application {
    private static StartVisual startView = new StartVisual();

    /**
     * Calls the start method in StartVisual to start the scenes
     * @param primaryStage will be the main stage, but is set in the StartVisual
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws ParserConfigurationException {
        startView.start(primaryStage);
    }

    /**
     * Starts the program, acts as main class
     * @param args what the program takes to run
     */
    public static void main (String[] args) { launch(args); }
}

