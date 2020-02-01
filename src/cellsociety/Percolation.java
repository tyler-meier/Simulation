package cellsociety;

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
    public void prevState()

    {
        int[][] initial = myGrid; //initial state, will update more when the file parsing happens. this current

    }//value of the grid is the initial value of the grid.

    @Override
    public void update() {
            //if the cell is open but not full
            for(int i =0; i < myGrid.length; i++)
                //check its 8 neighbours
                for (int j = 0; j < myGrid[0].length; j++)
                    if (myGrid[i][j] == OPEN)
                            if (isBounds(i - 1, j)  && (myGrid[i - 1][j]) == FULL) myGrid[i][j] = FULL;
                            else //check to right of cell, check if its in bounds
                                if (isBounds(i + 1, j) && (myGrid[i + 1][j]) == FULL) myGrid[i][j] = FULL;
                            else //check to see top of cell, check if its in bounds
                                if (isBounds(i, j - 1) && (myGrid[i][j - 1]) == FULL) myGrid[i][j] = FULL;
                            else //check to see top of cell, check if its in bounds
                                if (isBounds(i, j + 1) && (myGrid[i][j + 1]) == FULL) myGrid[i][j] = FULL;
                            else
                                if (isBounds(i-1, j - 1) && (myGrid[i-1][j - 1]) == FULL) myGrid[i][j] = FULL;
                            else
                                if (isBounds(i-1, j + 1) && (myGrid[i-1][j + 1]) == FULL) myGrid[i][j] = FULL;
                            else
                                if (isBounds(i+1, j + 1) && (myGrid[i+1][j + 1]) == FULL) myGrid[i][j] = FULL;
                            else
                                if (isBounds(i+1, j - 1)  && (myGrid[i+1][j - 1]) == FULL) myGrid[i][j] = FULL;


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
    public static void main( String[] args){






    }
}
