package grid;


import cell.CELL;
import RuleSets.ReadXML;

import java.util.ArrayList;


public class FiniteGrid {
    public int row, col;
    CELL[][] myGrid;
    public ReadXML reader;
    private int turn;


    public FiniteGrid(int x, int y, ReadXML myReader) {
        reader = myReader;
        row = x;
        col =y;
    }

    public CELL[][] Grid_Make(ReadXML reader){ //grid for predator prey
       myGrid = new CELL[reader.getRow()][reader.getCol()];
       for(int i = 0; i< myGrid.length; i++){
           for(int j = 0; j< myGrid[0].length; j++){
               myGrid[i][j] = new CELL(reader.getCell(i, j),i,j, 0);
           }
       }
       return myGrid;
   }

    public ArrayList<CELL> getFourNeighbourCount(int i, int j) {
        ArrayList<CELL> neighbours = new ArrayList<>();
        if (isBounds(i,j+1))  neighbours.add(myGrid[i][j+1]);
        if (isBounds(i,j-1))  neighbours.add(myGrid[i][j-1]);
        if (isBounds(i-1,j))  neighbours.add(myGrid[i-1][j]);
        if (isBounds(i+1,j))  neighbours.add(myGrid[i+1][j]);
        return neighbours;
    }


    public ArrayList<CELL> getEightNeighbourCount(int i, int j) {
        ArrayList<CELL> neighbours = new ArrayList<>();
        if (isBounds(i,j+1))  neighbours.add(myGrid[i][j+1]);
        if (isBounds(i,j-1))  neighbours.add(myGrid[i][j-1]);
        if (isBounds(i-1,j))  neighbours.add(myGrid[i-1][j]);
        if (isBounds(i+1,j))  neighbours.add(myGrid[i+1][j]);
        if (isBounds(i-1,j-1))  neighbours.add(myGrid[i-1][j-1]);
        if (isBounds(i+1,j+1))  neighbours.add(myGrid[i+1][j+1]);
        if (isBounds(i-1,j+1))  neighbours.add(myGrid[i-1][j+1]);
        if (isBounds(i+1,j-1))  neighbours.add(myGrid[i+1][j-1]);
        return neighbours;

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








}