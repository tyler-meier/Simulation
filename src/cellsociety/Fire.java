package cellsociety;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class Fire extends simulation {
    private FCELL[][] myGrid;
    public static final int TREE = 1;
    public static final int BURNING = 2;
    public static final int EMPTY = 0;
    private int myGrid_size;
    private ArrayList<FCELL> myNeighbours;
    public double life_time;


    private ReadXML reader;
    public Fire(ReadXML myReader) throws ParserConfigurationException, IOException, SAXException {
        reader = myReader;
        readFile();

    }



    public Boolean isBounds(int row, int col){
        if(row< 0 || row>= myGrid.length){
            return false;
        }
        if(col < 0|| col>= myGrid[0].length){
            return false;
        }

        return true;

    }


    public ArrayList<FCELL> getNeighbourCount(int i, int j) {
        ArrayList<FCELL> neighbours = new ArrayList<>();
        if (isBounds(i,j+1))  neighbours.add(myGrid[i][j+1]);
        if (isBounds(i,j-1))  neighbours.add(myGrid[i][j-1]);
        if (isBounds(i-1,j))  neighbours.add(myGrid[i-1][j]);
        if (isBounds(i+1,j))  neighbours.add(myGrid[i+1][j]);
        return neighbours;
    }

    @Override
    public void update() {
        FCELL[][] futureState = new FCELL[myGrid.length][myGrid[0].length]; //new cell set
        //futureState = myGrid;
        for (int i = 0; i < myGrid.length; i++) {
            for (int j =0; j < myGrid[0].length; j++) {
                ArrayList<FCELL> neighbours = getNeighbourCount(i, j); //GET the neighbours for each cell
                if (myGrid[i][j].getType() == BURNING) {  //simply the burning tree dies.
                    futureState[i][j] = new FCELL(EMPTY); //burnt or empty
                } //works
                else if (myGrid[i][j].getType() == TREE) {
                    for (FCELL cell : neighbours) {  //for all the neighburs of the current cell
                        if (cell.getType() == BURNING ) { //if the neighbours burning, tree
                            double random = Math.random(); //generate a number btw 0 and 1
                            if (random >= life_time) { // if the percent is greater than prob catch
                                futureState[i][j] = new FCELL(BURNING); //it burns
                                break;
                            } }
                        futureState[i][j] = new FCELL(TREE); //does nothing.
                    } }
                else{ futureState[i][j] = myGrid[i][j]; }
            } }
        myGrid = futureState;
    }


    @Override
    public int cellStatus(int row, int column) {
        return myGrid[row][column].getType();
    }

    @Override
    public void readFile() { //updates the grid in the way rules say. the first and last column and the first and last
        life_time = Double.parseDouble(reader.getParameters("probCatch"));
        //System.out.println(life_time);
        myGrid = new FCELL[reader.getRow()][reader.getCol()];
        for(int i = 0; i< myGrid.length; i++){
            for(int j = 0; j< myGrid[0].length; j++){
                myGrid[i][j] = new FCELL(reader.getCell(i, j));
            }
        }
    }



}
