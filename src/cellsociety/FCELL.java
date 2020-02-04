package cellsociety;

import java.util.ArrayList;
import java.util.logging.XMLFormatter;

public class FCELL {
    private  int Xrow, Ycol;
    private FCELL[][] myGrid;
    protected ArrayList<FCELL> myAdjacentNeighbours = new ArrayList<>();
    private int Grid_Size;
    private int type;



    public FCELL(int t){
        type = t;
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


    public void setType(int type) {
        this.type = type;

    }


}
