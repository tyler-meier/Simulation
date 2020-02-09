package xmlreading;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
    public static final String PROB_CATCH = "probCatch";
    public static final String SIMILARITY = "similarity";
    public static final String CYCLE = "cycle";
    public static final String RANDOM = "Random";
    public static final String TOTAL_STATES = "states";


    private final DocumentBuilder DOCUMENT_BUILDER;
    private Map<String, String> myParameters;
    private Element myRoot;
    private int[][] myGrid;
    private int row;
    private int col;
    private int totalStates;
    private int DEFAULT_STATE = 0;

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
     * Acquires the root element of the xml file, reads grid and parameters
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
        String myParameter = getAttribute(myRoot, parameter);
        if(myParameter.equals("")) return myParameters.get(parameter);
        return myParameter;
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
     * Add parameters to map so it can be retrived
     */
    private void setUpParameters(){
        myParameters = new HashMap<>();
        myParameters.put(TYPE, getAttribute(myRoot, TYPE));
        myParameters.put(GRID_TYPE, getAttribute(myRoot,GRID_TYPE));
        setUpDefaultValues();
    }
    private void setUpDefaultValues(){
        myParameters.put(PROB_CATCH, "0.55");
        myParameters.put(CYCLE, "3");
        myParameters.put(SIMILARITY, "30");
    }
    /**
     * Retrieves column and row values from xml files
     */
    private void setMyGrid(){
        setUpGridSize();
        setTotalCellStates();
        myGrid = new int[row][col];
        if(myGridIsRandom()) createRandomGrid();
        else setCells();
    }
    private void setUpGridSize(){
        try {
            row = Integer.parseInt(getTextValue(myRoot, ROW));
            col = Integer.parseInt(getTextValue(myRoot, COL));
        } catch (NumberFormatException e){
            row = 10;
            col = 10;
        }
    }
    private void setTotalCellStates(){
        try{
            totalStates = Integer.parseInt(getParameters(TOTAL_STATES));
        } catch (NumberFormatException e){
            totalStates = 3;
        }
    }
    /**
     * Methods that set up cell states based on user set up or randomly
     */
    private void setCells(){
        NodeList list = myRoot.getElementsByTagName(CELL);
        int index = 0;
        for(int i = 0; i< row; i++){
            for(int j = 0; j< col; j++){
                int state = Integer.parseInt(getTextValue((Element)list.item(index), STATUS));
                if(state<=totalStates) myGrid[i][j] = state;
                else myGrid[i][j] = DEFAULT_STATE;
                index++;
            }
        }
    }
    private void createRandomGrid(){
        Random random = new Random();
        for(int i = 0; i< row; i++){
            for(int j = 0; j< col; j++){
                myGrid[i][j] = random.nextInt(totalStates);
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
    /**
     * Checks if grid cells are to be determined randomly
     */
    public boolean myGridIsRandom(){
        return myParameters.get(GRID_TYPE).equals(RANDOM);
    }
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        ReadXML mySim = new ReadXML();
        File xmlFile = new File("data/samplePercolationRandom");
        mySim.setUpFile(xmlFile);
        Element root = mySim.getRootElement(xmlFile);
        System.out.println("Simulation Type is: "+ mySim.getRow());
        System.out.println("My first val is: "+ mySim.getTextValue(root, "col"));
        mySim.setMyGrid();
    }
}

