package cellsociety;


import java.util.ArrayList;

public class PPCell  {
    private  int Xrow, Ycol;
    private PPCell[][] myGrid;
    protected ArrayList<PPCell> myAdjacentNeighbours = new ArrayList<PPCell>();
    private int Grid_Size;
    private  ArrayList<PPCell> EMPTY_N = new ArrayList<PPCell>();
    private int type;



    public PPCell(int xPos, int yPos, int state){
    xPos= Xrow;
    yPos = Ycol;
    state = type;


}



    public void  Adjacent_Neighbours() { //all the neighbours of the cell
      ArrayList<PPCell> myAdjacentNeighbours = new ArrayList<>();
      if(Ycol<Grid_Size - 1) myAdjacentNeighbours.add(myGrid[Xrow][Ycol+1]); //East
      if(Ycol>0) myAdjacentNeighbours.add((myGrid[Xrow][Ycol-1])); //West
      if(Xrow <Grid_Size - 1) myAdjacentNeighbours.add((myGrid[Xrow+1][Ycol]));  //South
      if(Xrow>0) myAdjacentNeighbours.add((myGrid[Xrow-1][Ycol]));  //North

  }


  public ArrayList<PPCell> EMPTY_list(){  //all the empty neighbours of the cell.
      for( PPCell cell: myAdjacentNeighbours){
          if(type == 0){
              EMPTY_N.add(cell);   //all the empty neighbours
          }
      }
      return EMPTY_N;

  }



    public  int getX() {
      return Xrow;
    }

    public int getY(){
      return Ycol;
    }

    private ArrayList<PPCell> getEMPTY_N(){
        return EMPTY_N;
    }
    public int getTYPE(){
        return type;
    }
    public void setType(int type){
        this.type = type;
    }
    public void setX(int row){
        this.Xrow = row;
    }
    public void setY(int col){
        this.Ycol = col;
    }


}
