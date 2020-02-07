package xmlreading;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * A class built to read XML files for the purpose of Simulating various Games. It can receive a file
 * and parse to set up the intial value of cells
 * @author erikgregorio
 */
public class ReadXML {
    public static final String TYPE = "simulationType";
    public static final String ROW = "row";
    public static final String COL = "col";
    public static final String CELL = "cell";
    public static final String STATUS = "status";
    public static final String GRID_TYPE = "grid";

    private final DocumentBuilder DOCUMENT_BUILDER;
    private Map<String, String> myParameters;
    private Element myRoot;
    private int[][] myGrid;
    private int row;
    private int col;

    /**
     * Create Parser for a given XML file
     */
    public ReadXML() throws ParserConfigurationException {
        DOCUMENT_BUILDER = getDocumentBuilder();
    }
    public DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    /**
     * Acquires the root element of the xml file and reads grid and parameters
     */
    public void setUpFile(File xmlFile) throws IOException, SAXException {
        myRoot = getRootElement(xmlFile);
        setUpParameters();
        setMyGrid();
    }
    public boolean isValidFile(String type){
        return getAttribute(myRoot, TYPE).equals(type);
    }
    /**
     * Returns the data for given attribute field
     */
    public String getParameters(String parameter){
        return getAttribute(myRoot, parameter);
    }
    public int getRow(){
        return row;
    }
    public int getCol() {
        return col;
    }
    /**
     * Returns cell status for the purpose of simulation filling
     */
    public int getCell(int row, int col){
        return myGrid[row][col];
    }

    private Element getRootElement(File xmlFile) throws IOException, SAXException {
        DOCUMENT_BUILDER.reset();
        Document xmlDoc = DOCUMENT_BUILDER.parse(xmlFile);
        return xmlDoc.getDocumentElement();
    }
    /**
     *
     */
    private void setUpParameters(){
        myParameters = new HashMap<>();
        myParameters.put(TYPE, getParameters(TYPE));
        myParameters.put(GRID_TYPE, getParameters(GRID_TYPE));

    }
    /**
     * Retrieves column and row values from xml files
     */
    private void setMyGrid(){
        row = Integer.parseInt(getTextValue(myRoot, ROW));
        col = Integer.parseInt(getTextValue(myRoot, COL));
        myGrid = new int[row][col];
        setCells();
    }
    /**
     * Retrieves status value from xml files and assigns it to the cells in the global data structure
     */
    private void setCells(){
        NodeList list = myRoot.getElementsByTagName(CELL);
        int index = 0;
        for(int i = 0; i< row; i++){
            for(int j = 0; j< col; j++){
                myGrid[i][j] = Integer.parseInt(getTextValue((Element)list.item(index), STATUS));
                index++;
            }
        }
    }
    /**
     * Returns true or false based on if the xml type is a given simulation type
     */
    private String getAttribute(Element e, String attributeName){
        return e.getAttribute(attributeName);
    }
    /**
     * Retrives data by tag
     */
    private String getTextValue(Element e, String tagName){
        NodeList list = e.getElementsByTagName(tagName);
        if(list != null && list.getLength()>0){
            return list.item(0).getTextContent();
        }
        else return "";
    }

    /*public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        ReadXML mySim = new ReadXML();
        File xmlFile = new File("data/samplePercolation.xml");
        mySim.setRoot(xmlFile);
        Element root = mySim.getRootElement(xmlFile);
        System.out.println("Simulation Type is: "+ mySim.getParameters("simulationType"));
        System.out.println("My first val is: "+ mySim.getTextValue(root, "col"));
        mySim.setMyGrid();
    }*/
}

