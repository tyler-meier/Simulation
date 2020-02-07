package neighbourhood;


import cell.CELL;

import java.util.ArrayList;

public class Square {
    private int row,col;
    public Square(int x, int y){
        row = x;
        col = y;
    }

    public ArrayList<CELL> getFourNeighbourCount(int i, int j, CELL[][] myGrid) {
        ArrayList<CELL> neighbours = new ArrayList<>();
        if (isBounds(i,j+1, myGrid))  neighbours.add(myGrid[i][j+1]);
        if (isBounds(i,j-1,myGrid))  neighbours.add(myGrid[i][j-1]);
        if (isBounds(i-1,j,myGrid))  neighbours.add(myGrid[i-1][j]);
        if (isBounds(i+1,j,myGrid))  neighbours.add(myGrid[i+1][j]);
        return neighbours;
    }

    public Boolean isBounds(int row, int col, CELL[][] myGrid){
        if(row< 0 || row>= myGrid.length){
            return false;
        }
        if(col < 0|| col>= myGrid[0].length){
            return false;
        }

        return true;

    }






}
