package grid;


import cell.CELL;
import xmlreading.ReadXML;

import java.util.ArrayList;


public class FiniteGrid {
    private int row, col;
    private CELL[][] myGrid;
    private ReadXML reader;




    public FiniteGrid(int x, int y, ReadXML myReader) {
        reader = myReader;
        row = x;
        col =y;
        myGrid = Grid_Make(reader);
    }
    /*
    This method makes the grid after reading the initial values from the file for each class
     */

    public CELL[][] Grid_Make(ReadXML reader){
       myGrid = new CELL[reader.getRow()][reader.getCol()];
       for(int i = 0; i< myGrid.length; i++){
           for(int j = 0; j< myGrid[0].length; j++){
               myGrid[i][j] = new CELL(reader.getCell(i, j),i,j, 0);
           }
       }
       return myGrid;
   }

   public CELL[][] getMyGrid() {
        return myGrid;
   }

    public ArrayList<CELL> getFourNeighbourCount(int i, int j, CELL[][] grid) {
        ArrayList<CELL> neighbours = new ArrayList<>();
        if (isBounds(i,j+1, grid))  neighbours.add(grid[i][j+1]);
        if (isBounds(i,j-1, grid))  neighbours.add(grid[i][j-1]);
        if (isBounds(i-1,j, grid))  neighbours.add(grid[i-1][j]);
        if (isBounds(i+1,j, grid))  neighbours.add(grid [i+1][j]);
        return neighbours;
    }

    public ArrayList<CELL> getEightNeighbourCount(int i, int j, CELL[][] grid) {
        ArrayList<CELL> neighbours = new ArrayList<>();
        if (isBounds(i,j+1,grid))  neighbours.add(grid[i][j+1]);
        if (isBounds(i,j-1, grid))  neighbours.add(grid[i][j-1]);
        if (isBounds(i-1,j, grid))  neighbours.add(grid[i-1][j]);
        if (isBounds(i+1,j, grid))  neighbours.add(grid[i+1][j]);
        if (isBounds(i-1,j-1, grid))  neighbours.add(grid[i-1][j-1]);
        if (isBounds(i+1,j+1, grid ))  neighbours.add(grid[i+1][j+1]);
        if (isBounds(i-1,j+1, grid))  neighbours.add(grid[i-1][j+1]);
        if (isBounds(i+1,j-1, grid))  neighbours.add(grid[i+1][j-1]);
        return neighbours;

    }
/*
 hexagonal grid for 6 neighbours
 */


    public ArrayList<CELL> getSixNeighbourCount(int i, int j, CELL[][] grid) {
        ArrayList<CELL> neighbours = new ArrayList<>();
        if (isBounds(i,j+1,grid))  neighbours.add(grid[i][j+1]);
        if (isBounds(i,j-1, grid))  neighbours.add(grid[i][j-1]);
        if (isBounds(i-1,j, grid))  neighbours.add(grid[i-1][j]);
        if (isBounds(i+1,j, grid))  neighbours.add(grid[i+1][j]);
        if (isBounds(i-1,j+1, grid))  neighbours.add(grid[i-1][j+1]);
        if (isBounds(i+1,j-1, grid))  neighbours.add(grid[i+1][j-1]);
        return neighbours;

    }


     /*
  find if the bounds are in the range or not for each cell. This method is used in making neighbour
  array.
    */
    private Boolean isBounds(int row, int col, CELL[][] grid){
        if(row< 0 || row>= grid.length){
            return false;
        }
        if(col < 0|| col>= grid[0].length){
            return false;
        }

        return true;

    }

  //creates a toroidal grid with 8 neighbours
    public ArrayList<CELL> getEightToroidal(int i, int j, CELL[][] grid){
        ArrayList<CELL> cellNeighbours = new ArrayList<CELL>();
        int[] xCoord = {i, i+1, i-1}; //for 8 neighbours
        int[] yCoord = {j, j+1, j-1}; //for eight neighbours

        for(int row: xCoord) {
            for(int  col : yCoord) {
                if(row == -1 || col == -1 || row == reader.getRow() || col == reader.getCol()) {
                    row = switchPosition(row);
                    col = switchPosition(col);
                    cellNeighbours.add(grid[row][col]);
                }
                if(row>-1 && col>-1 && row<(reader.getRow()) && col<(reader.getCol())) {
                    cellNeighbours.add(grid[row][col]);
                }
            }
        }
        cellNeighbours.remove(grid[i][j]);
        return cellNeighbours;
    }

    private int switchPosition(int edge) {
        if(edge == -1) edge = reader.getRow() -1;
        if(edge == reader.getCol()) edge = 0;
        return edge;
    }
   //four neighbours with a toroidal neighbours
    public ArrayList<CELL> getFourToroidal(int i , int j, CELL[][] grid) {
        ArrayList<CELL> cellNeighbours = new ArrayList<CELL>();
        if(j== reader.getCol()-1) cellNeighbours.add(grid[i][0]);
        else cellNeighbours.add(grid[i][j+1]);  //normal addition
        if(j == 0) cellNeighbours.add(grid[i][reader.getCol()-1]); //West
        else cellNeighbours.add(grid[i][j-1]);
        if(i == reader.getRow() - 1) cellNeighbours.add(grid[0][j]); //South
        else cellNeighbours.add(grid[i+1][j]);
        if(i == 0) cellNeighbours.add(grid[reader.getRow()-1][j]); //North
        else cellNeighbours.add(grid[i-1][j]);
        return cellNeighbours;
    }









}