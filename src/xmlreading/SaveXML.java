package xmlreading;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import ruleset.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * A class built to take in a simulation and create appropiate elements to store inside an xml and stored
 * where the user specifies.
 * @author Erik Gregorio
 */

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
     * Create Parser to create an xml file
     */
    public SaveXML(Simulation sim, ReadXML reader, File file) throws ParserConfigurationException {
        // Retrieve document builder to create file
        DOCUMENT_BUILDER = getDocumentBuilder();
        mySaveFile = DOCUMENT_BUILDER.newDocument();
        // Set up global variables that will be used in the simulation reading to create a XML File
        mySimulation = sim;
        userFile = file;
        // Create root and add the title and author
        createRoot();
        createTitleAuthor();
        myReader = reader;
    }
    private DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }
    private void createRoot(){
        myRoot = mySaveFile.createElement(ReadXML.SIM);
        myRoot.setAttribute(ReadXML.TYPE,getSimulationType());
        mySaveFile.appendChild(myRoot);
    }
    /**
     * Retrieves grid size and then creates an element for each cell and their status.
     */
    public void saveCells(){
        // Creates the elements that will be used to describe the grid in the XML File
        setGrid();
        // Loops through all cell and creates and element to them.
        Element cells = mySaveFile.createElement(CELLS);
        for(int i = 0; i<myReader.getRow(); i++){
            for(int j = 0; j<myReader.getRow(); j++){
                cells.appendChild(createCell(i,j));
            }
        }
        // Attaches cells to XML File
        myRoot.appendChild(cells);
    }
    private void setGrid(){
        // Creates the elements that will be used to describe the grid in the XML File
        Element row = mySaveFile.createElement(ReadXML.ROW);
        Element col = mySaveFile.createElement(ReadXML.COL);
        row.appendChild(mySaveFile.createTextNode(Integer.toString(myReader.getRow())));
        col.appendChild(mySaveFile.createTextNode(Integer.toString(myReader.getCol())));
        myRoot.appendChild(row);
        myRoot.appendChild(col);
    }
    /**
     * Create element for a cell and retrives its status
     */
    private Element createCell(int row, int col){
        // Creates the an element for each cell
        Element cell = mySaveFile.createElement(ReadXML.CELL);
        Element status = mySaveFile.createElement(ReadXML.STATUS);
        status.appendChild(getCellStatus(row, col));
        cell.appendChild(status);
        return cell;
    }
    private Node getCellStatus(int row, int col){
        // Retrieves the current status of a given cell
        return mySaveFile.createTextNode(Integer.toString(mySimulation.cellStatus(row, col)));
    }

    /**
     * Creates the author and title cell for the simulation
     */
    private void createTitleAuthor(){
        // Creates author and title elements for XML file
        myTitle = mySaveFile.createElement(TITLE);
        myTitle.appendChild(mySaveFile.createTextNode(getSimulationTitle()));
        myAuthor = mySaveFile.createElement(AUTHOR);
        myAuthor.appendChild(mySaveFile.createTextNode(MY_AUTHOR));
        myRoot.appendChild(myTitle);
        myRoot.appendChild(myAuthor);
    }
    /**
     * Retrieves simulation information (title and type)
     */
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
    /**
     * Calls save file method to create the cell elements for the XML file. It also writes the root node into
     * a user specified file.
     */
    public void saveFile() throws TransformerException {
        saveCells();
        setTransformer();
        DOMSource source = new DOMSource(mySaveFile);
        StreamResult result = new StreamResult(userFile);
        transformer.transform(source,result);
    }
    /**
     * Creates transformer factory and  then sets an output properly to have the XML have a specified
     * structure.
     */
    private void setTransformer(){
        // Creates a transformer factory in order to write the root into the XML File
        transformerFactory= TransformerFactory.newInstance();
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException();
        }
        setTransformerOutput();
    }
    private void setTransformerOutput(){
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OUTPUTSETTING, "4");
    }


}
