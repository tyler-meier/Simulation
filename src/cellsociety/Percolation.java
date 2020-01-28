package cellsociety;

import java.util.Arrays;

public class Percolation extends simulation {

    /*
    Possible states for the percolation cells are: BLOCKED,OPEN,FULL.
     */
    private int [][] myGrid;
    public static final int BLOCKED = 1;
    public static final int OPEN = 2;
    public static final int FULL = 4;
    private int [][] initial;
    private int[][] nextState;


    public Percolation(int size) {
        myGrid = new int[size][size]; //creates the grid of size whatever you put
        for (int[] row : myGrid) {
            Arrays.fill(row, BLOCKED); //set the initial state to blocked can be changed acc to file

        }

    }
    /*
    check if the following is in the bounds or not when the file parses in-- this is just a check method to make
    sure we are dealing with cells in the bounds.
     */

   public Boolean isBounds(int row, int col){
       if(row<0 || row>= myGrid.length){
          return false;
       }
       if(col <0|| row>= myGrid.length){
           return false;
       }

       return true;

   }

 /*
CellIsOpen method will check if the cell is open and empty, and if its open and full.
Methods are broken down into smaller methods to prioritize design.
  */
    @Override
    public Boolean cellIsOpen(int row, int column) {
        return null; //idk how to use this??
    }

    /*
    The update method updates the state of the cell if it has a neighbour which is

     */
    public void prevState()

    {
        initial = myGrid; //initial state, will update more when the file parsing happens. this current

    }//value of the grid is the initial value of the grid.

    @Override
    public void update() {
            //if the cell is open but not full
            for(int i =0; i < myGrid.length; i++) {
                for (int j = 0; j < myGrid.length; j++) {
                    if(myGrid[i][j]== OPEN){ //check its neighbours
                        if(isBounds(i-1, j) == true & (myGrid[i-1][j]) == FULL) {  //check to left of cell, check if its in bounds
                           myGrid[i][j] = FULL;
                        }
                        else  if(isBounds(i+1, j) == true & (myGrid[i+1][j]) == FULL) {  //check to right of cell, check if its in bounds
                            myGrid[i][j] = FULL;
                        }
                        else if(isBounds(i, j-1) == true & (myGrid[i][j-1]) == FULL) {  //check to see top of cell, check if its in bounds
                            myGrid[i][j] = FULL;
                        }
                        else if(isBounds(i, j+1) == true & (myGrid[i][j+1]) == FULL) {  //check to see top of cell, check if its in bounds
                            myGrid[i][j] = FULL;
                        }


                    }
                }

            }

        }

     public void nextState(){  //the updated state of the cell is the nextState which becomes myGrid now.
      nextState = myGrid;
     }





    @Override
    public void readFile() {

    }
    public static void main( String[] args){






    }
}
