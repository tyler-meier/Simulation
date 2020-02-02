package cellsociety;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Percolation extends simulation {

    /*
    Possible states for the percolation cells are: BLOCKED,OPEN,FULL.
     */

    private int [][] myGrid;
    public static final int BLOCKED = 0;
    public static final int OPEN = 1;
    public static final int FULL = 2;

    private ReadXML reader;


    public Percolation(ReadXML myReader) throws ParserConfigurationException, IOException, SAXException {
        reader = myReader;
        readFile();

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
        return myGrid[row][column];
    }



    /*
    The update method updates the state of the cell if it has a neighbour which is

     */
   //value of the grid is the initial value of the grid.

    @Override
    public void update() {
            //if the cell is open but not full
            int[][] futureState = new int[myGrid.length][myGrid[0].length];
            for(int i =0; i < myGrid.length; i++) {
                //check its 8 neighbours
                for (int j = 0; j < myGrid[0].length; j++) {
                    if (isNeighborFull(i, j) && myGrid[i][j] == OPEN ) {
                       futureState[i][j] = FULL;
                    }
                    else
                        futureState[i][j] = myGrid[i][j];
                }
            }
            myGrid = futureState;
        }

    public Boolean isNeighborFull(int row, int col){
        if (isBounds(row - 1, col) && (myGrid[row - 1][col]) == FULL) return true;
        else if (isBounds(row + 1, col) && (myGrid[row + 1][col]) == FULL) return true;
        else if (isBounds(row, col - 1) && (myGrid[row][col - 1]) == FULL) return true;
        else if (isBounds(row, col + 1) && (myGrid[row][col + 1]) == FULL) return true;
        else if (isBounds(row - 1, col - 1) && (myGrid[row - 1][col - 1]) == FULL) return true;
        else if (isBounds(row - 1, col + 1) && (myGrid[row - 1][col + 1]) == FULL) return true;
        else if (isBounds(row + 1, col + 1) && (myGrid[row + 1][col + 1]) == FULL) return true;
        else if (isBounds(row + 1, col - 1) && (myGrid[row + 1][col - 1]) == FULL) return true;
        return false;
    }


     public void nextState(){  //the updated state of the cell is the nextState which becomes myGrid now.
         int[][] nextState = myGrid;
     }


    @Override
    public void readFile() {
        myGrid = new int[reader.getRow()][reader.getCol()];
        for(int i = 0; i< myGrid.length; i++){
            for(int j = 0; j< myGrid[0].length; j++){
                myGrid[i][j] = reader.getCell(i, j);
            }
        }
    }


    public static void main( String[] args) throws IOException, SAXException, ParserConfigurationException {


    }
}
