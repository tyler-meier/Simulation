package cellsociety;


import java.util.ArrayList;

public class PPCell  {
    private  int Xrow, Ycol;
    private PPCell[][] myGrid;
    protected ArrayList<PPCell> myAdjacentNeighbours = new ArrayList<PPCell>();
    private int Grid_Size;
    private  ArrayList<PPCell> EMPTY_N = new ArrayList<PPCell>();
    private int type;
    private int life_time;



    public PPCell( int state, int row, int col, int life){
    Xrow = row;
    Ycol = col;
    type = state;
    life_time = life;

}


    public int getX(){
        return Xrow;
    }

    public int getY(){
        return Ycol;
    }

    public void setLife(int life_time) { this.life_time = life_time;}
    public int getTYPE(){
        return type;
    }
    public void setType(int type){
        this.type = type;
    }

    public int getLife() {
        return life_time;
    }

}
