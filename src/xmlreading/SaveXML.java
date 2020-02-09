package xmlreading;

import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import ruleset.GameOfLife;
import ruleset.Percolation;
import ruleset.Simulation;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class SaveXML {
    public static final String XML_PATH = "";

    private final DocumentBuilder DOCUMENT_BUILDER;
    private final Document mySaveFile;
    private Element myRoot;
    private Simulation mySimulation;
    private Stage myStage;

    /**
     * Create Parser for a given XML file
     */
    public SaveXML(Simulation sim, Stage stage) throws ParserConfigurationException {
        DOCUMENT_BUILDER = getDocumentBuilder();
        mySaveFile = DOCUMENT_BUILDER.newDocument();
        mySimulation = sim;
        myStage = stage;
    }
    public SaveXML(Simulation sim) throws ParserConfigurationException {
        DOCUMENT_BUILDER = getDocumentBuilder();
        mySaveFile = DOCUMENT_BUILDER.newDocument();
        mySimulation = sim;
    }
    public DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        ReadXML mySim = new ReadXML();
        File xmlFile = new File("data/sampleGOL");
        mySim.setUpFile(xmlFile);
        GameOfLife gol = new GameOfLife(mySim);
        gol.update();
        SaveXML mySave = new SaveXML(gol);

    }

}
