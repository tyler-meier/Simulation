package cellsociety;

import java.util.ArrayList;
import java.util.logging.XMLFormatter;

public class FCELL {
    private  int Xrow, Ycol;
    private FCELL[][] myGrid;
    protected ArrayList<FCELL> myAdjacentNeighbours = new ArrayList<>();
    private int Grid_Size;
    private int type;
    private double life_time;

    public FCELL(int xPos, int yPos, int state, double prob_catch){
        xPos= Xrow;
        yPos = Ycol;
        state = type;
        prob_catch = life_time;

    }

    public ArrayList<FCELL> Adjacent_Neighbours() { //all the neighbours of the cell
        ArrayList<FCELL> myAdjacentNeighbours = new ArrayList<>();
        if(Ycol<Grid_Size - 1) myAdjacentNeighbours.add(myGrid[Xrow][Ycol+1]); //East
        if(Ycol>0) myAdjacentNeighbours.add((myGrid[Xrow][Ycol-1])); //West
        if(Xrow <Grid_Size - 1) myAdjacentNeighbours.add((myGrid[Xrow+1][Ycol]));  //South
        if(Xrow>0) myAdjacentNeighbours.add((myGrid[Xrow-1][Ycol]));  //North

        return myAdjacentNeighbours;
    }

    public int getType(){

        return type;
    }

    public int getX(){
        return Xrow;
    }

    public int getY(){
        return Ycol;
    }


    public void setType(int typo) {
        this.type = type;

    }
    public double  getPRobCatch(){
        return life_time;
    }
}
