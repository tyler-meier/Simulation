package xmlreading;

import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import ruleset.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class SaveXML {
    public static final String TITLE = "title";
    public static final String AUTHOR = "author";
    public static final String CELLS = "cells";
    public static final String GOL = "GameOfLife";
    public static final String GOL_TITLE = "Game of Life Simulation";
    public static final String FIRE = "Fire";
    public static final String FIRE_TITLE = "Fire Simulation";
    public static final String SEGREGATION = "Segregation";
    public static final String SEGREGATION_TITLE ="Segregation Simulation";
    public static final String PREDATOR_PREY = "PredatorPrey";
    public static final String PREDATOR_PREY_TITLE ="Predator Prey Simulation";
    public static final String PERCOLATION = "Percolation";
    public static final String PERCOLATION_TITLE = "Percolation Simulation";
    public static final String RPS = "RPS";
    public static final String RPS_TITLE = "Rock Paper Scissors Simulation";
    public static final String MY_AUTHOR = "Erik Gregorio";
    public static final String OUTPUTSETTING = "{http://xml.apache.org/xslt}indent-amount";

    private final DocumentBuilder DOCUMENT_BUILDER;
    private final Document mySaveFile;
    private TransformerFactory transformerFactory;
    private Transformer transformer;
    private Element myRoot;
    private Element myTitle;
    private Element myAuthor;
    private Simulation mySimulation;
    private ReadXML myReader;
    private File userFile;

    /**
     * Create Parser for a given XML file
     */
    public SaveXML(Simulation sim, ReadXML reader, File file) throws ParserConfigurationException {
        DOCUMENT_BUILDER = getDocumentBuilder();
        mySaveFile = DOCUMENT_BUILDER.newDocument();
        mySimulation = sim;
        userFile = file;
        createRoot();
        createTitleAuthor();
        myReader = reader;
    }
    public SaveXML(Simulation sim) throws ParserConfigurationException {
        DOCUMENT_BUILDER = getDocumentBuilder();
        mySaveFile = DOCUMENT_BUILDER.newDocument();
        mySimulation = sim;
        createRoot();
        createTitleAuthor();
    }
    public void saveCells(){
        setGrid();
        Element cells = mySaveFile.createElement(CELLS);
        for(int i = 0; i<myReader.getRow(); i++){
            for(int j = 0; j<myReader.getRow(); j++){
                cells.appendChild(createCell(i,j));
            }
        }
        myRoot.appendChild(cells);
    }
    public void setGrid(){
        Element row = mySaveFile.createElement(ReadXML.ROW);
        Element col = mySaveFile.createElement(ReadXML.COL);
        row.appendChild(mySaveFile.createTextNode(Integer.toString(myReader.getRow())));
        col.appendChild(mySaveFile.createTextNode(Integer.toString(myReader.getCol())));
        myRoot.appendChild(row);
        myRoot.appendChild(col);
    }
    public Element createCell(int row, int col){
        Element cell = mySaveFile.createElement(ReadXML.CELL);
        Element status = mySaveFile.createElement(ReadXML.STATUS);
        status.appendChild(getCellStatus(row, col));
        cell.appendChild(status);
        return cell;
    }
    private Node getCellStatus(int row, int col){
        return mySaveFile.createTextNode(Integer.toString(mySimulation.cellStatus(row, col)));
    }
    private DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }
    public void createRoot(){
        myRoot = mySaveFile.createElement(ReadXML.SIM);
        myRoot.setAttribute(ReadXML.TYPE,getSimulationType());
        mySaveFile.appendChild(myRoot);
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
        return getString(GOL, FIRE, PERCOLATION, PREDATOR_PREY, SEGREGATION, RPS);
    }
    private String getSimulationTitle(){
        return getString(GOL_TITLE, FIRE_TITLE, PERCOLATION_TITLE, PREDATOR_PREY_TITLE, SEGREGATION_TITLE, RPS_TITLE);
    }

    private String getString(String golTitle, String fireTitle, String percolationTitle, String predatorPreyTitle, String segregationTitle, String rpsTitle) {
        if(mySimulation instanceof GameOfLife) return golTitle;
        if(mySimulation instanceof Fire) return fireTitle;
        if(mySimulation instanceof Percolation) return percolationTitle;
        if(mySimulation instanceof PredatorPrey) return predatorPreyTitle;
        if(mySimulation instanceof Segregation) return segregationTitle;
        return rpsTitle;
    }

    public void saveFile() throws TransformerException {
        saveCells();
        setTransformer();
        System.out.println("here");
        DOMSource source = new DOMSource(mySaveFile);
        StreamResult result = new StreamResult(userFile);
        transformer.transform(source,result);
    }

    private void setTransformer(){
        transformerFactory= TransformerFactory.newInstance();
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        setTransformerOutput();
    }
    private void setTransformerOutput(){
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OUTPUTSETTING, "4");
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        ReadXML mySim = new ReadXML();
        File xmlFile = new File("data/SampleGOL.xml");
        File userFile = new File("data/emptyXML.xml");
        mySim.setUpFile(xmlFile);
        GameOfLife gol = new GameOfLife(mySim);
        gol.update();
        SaveXML mySave = new SaveXML(gol, mySim, userFile);
        //mySave.saveFile();
    }

}
