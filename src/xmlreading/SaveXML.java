package xmlreading;

import javafx.stage.Stage;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import ruleset.GameOfLife;
import ruleset.Percolation;
import ruleset.Simulation;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SaveXML {
    public static final String TITLE = "title";
    public static final String AUTHOR = "author";
    public static final String ROW = "row";
    public static final String COLUMN = "col";
    public static final String CELLS = "cells";
    public static final String CELL = "cell";
    public static final String GOL = "GameOfLife";
    public static final String FIRE = "Fire";
    public static final String SEGREGATION = "Segregation";
    public static final String PREDATOR_PREY = "PredatorPrey";
    public static final String PERCOLATION = "Percolation";
    public static final String RPS = "RPS";
    public static final String XML_PATH = "/Users/erikgregorio/Desktop";
    public static final String MY_AUTHOR = "Erik Gregorio";

    private final DocumentBuilder DOCUMENT_BUILDER;
    private final Document mySaveFile;
    private Element myRoot;
    private Element myTitle;
    private Element myAuthor;
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
    private DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }
    public void createRoot(){
        myRoot = mySaveFile.createElement(ReadXML.SIM);
        Attr rootAttr = mySaveFile.createAttribute(ReadXML.TYPE);
        rootAttr.setValue(getSimulationType());
    }
    private void createTitleAuthor(){
        myTitle = mySaveFile.createElement(TITLE);
        myTitle.appendChild(mySaveFile.createTextNode(getSimulationTitle()));
        myAuthor = mySaveFile.createElement(AUTHOR);
        myAuthor.appendChild(mySaveFile.createTextNode(MY_AUTHOR));
        myRoot.appendChild(myTitle);
        myRoot.appendChild(myAuthor);
    }
    private String getSimulationType(){
        //TODO create if statement to check sim type
        return "GameOfLife";
    }
    private String getSimulationTitle(){
        //TODO create if statement to check sim type
        return "Game of Life Simulation";
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
