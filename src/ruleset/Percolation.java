package ruleset;


import cell.CELL;
import org.xml.sax.SAXException;
import grid.FiniteGrid;
import xmlreading.ReadXML;


import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


public class Percolation extends Simulation {

    /*
    Possible states for the percolation cells are: BLOCKED,OPEN,FULL.
     */

    //private PCELL [][] myGrid;
    public static final int OPEN = 0;
    public static final int FULL = 1;
    private ReadXML reader;
    CELL[][] myGrid;



    public Percolation(ReadXML myReader) throws ParserConfigurationException, IOException, SAXException {
        reader = myReader;
        readFile();

    }

    @Override
    public void readFile() {
        FiniteGrid abc = new FiniteGrid(reader.getRow(),reader.getCol(),reader);//creates a grid class
        myGrid = abc.Grid_Make(reader);  //calls in the method from the class
    }


    /*
    check if the following is in the bounds or not when the file parses in-- this is just a check method to make
    sure we are dealing with cells in the bounds.
     */

    public Boolean isBounds(int row, int col){
        if(row<0 || row>= myGrid.length){
            return false;
        }
        if(col <0|| col>= myGrid[0].length){
            return false;
        }

        return true;

    }

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
        if (isBounds(row - 1, col) && (myGrid[row - 1][col].getType()) == FULL) return true;
        else if (isBounds(row + 1, col) && (myGrid[row + 1][col].getType()) == FULL) return true;
        else if (isBounds(row, col - 1) && (myGrid[row][col - 1].getType()) == FULL) return true;
        else if (isBounds(row, col + 1) && (myGrid[row][col + 1].getType()) == FULL) return true;
        else if (isBounds(row - 1, col - 1) && (myGrid[row - 1][col - 1].getType()) == FULL) return true;
        else if (isBounds(row - 1, col + 1) && (myGrid[row - 1][col + 1].getType()) == FULL) return true;
        else if (isBounds(row + 1, col + 1) && (myGrid[row + 1][col + 1].getType()) == FULL) return true;
        else if (isBounds(row + 1, col - 1) && (myGrid[row + 1][col - 1].getType()) == FULL) return true;
        return false;
    }





    public static void main( String[] args) throws IOException, SAXException, ParserConfigurationException {


    }
}
