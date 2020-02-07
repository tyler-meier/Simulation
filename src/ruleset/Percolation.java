package ruleset;


import cell.CELL;
import org.xml.sax.SAXException;
import grid.FiniteGrid;
import xmlreading.ReadXML;


import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;


public class Percolation extends Simulation {

    /*
    Possible states for the percolation cells are: BLOCKED,OPEN,FULL.
     */

    //private PCELL [][] myGrid;
    public static final int OPEN = 0;
    public static final int FULL = 1;
    private  FiniteGrid abc;
    private ReadXML reader;
    CELL[][] myGrid;
    private boolean full = false;



    public Percolation(ReadXML myReader) throws ParserConfigurationException, IOException, SAXException {
        reader = myReader;
        readFile();

    }

    @Override
    public void readFile() {
        abc = new FiniteGrid(reader.getRow(),reader.getCol(),reader);//creates a grid class
        myGrid = abc.Grid_Make(reader);  //calls in the method from the class
    }


    /*
    check if the following is in the bounds or not when the file parses in-- this is just a check method to make
    sure we are dealing with cells in the bounds.
     */


    /*
   CellIsOpen method will check if the cell is open and empty, and if its open and full.
   Methods are broken down into smaller methods to prioritize design.
     */
    @Override

    public int cellStatus(int row, int column){
        return myGrid[row][column].getType();
    }



    /*
    The update method updates the state of the cell if it has a neighbour which is

     */
    //value of the grid is the initial value of the grid.

    @Override
    public void update() {
        CELL[][] futureState = new CELL[myGrid.length][myGrid[0].length];
        for(int i =0; i < myGrid.length; i++) {
            for (int j = 0; j < myGrid[0].length; j++) {
                if (isNeighborFull(i, j) && myGrid[i][j].getType() == OPEN ) {
                    futureState[i][j] = new CELL(FULL,i,j,0);
                }
                else
                    futureState[i][j] = myGrid[i][j];
            }
        }
        myGrid = futureState;
    }



    public Boolean isNeighborFull(int row, int col){
        full = false;
        ArrayList<CELL> neighbours = abc.getFourNeighbourCount(row, col, myGrid);
        for(CELL cell: neighbours){
            if(cell.getType()== FULL) {
                full = true;
            }
        }
        return full;
    }


    public static void main( String[] args) throws IOException, SAXException, ParserConfigurationException {


    }
}
