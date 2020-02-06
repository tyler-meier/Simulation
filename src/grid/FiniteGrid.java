package grid;


import cell.CELL;
import cellsociety.ReadXML;


public class FiniteGrid {
    public int row, col;
    CELL[][] myGrid;
    CELL[][] Grid;
    public ReadXML reader;
    private int turn;


    public FiniteGrid(int x, int y, ReadXML myReader) {
        reader = myReader;
        row = x;
        col =y;
    }

    public int getXsize(){
        System.out.print(reader.getRow());
        return reader.getRow();

    }

    public int getYsize(){
        return reader.getCol();

    }
//    public  getCell(int x, int y) {
//        return myCells[x][y];
//    }

    //makes the finite grid for 4 classes
    public CELL[][] Grid_Make1(ReadXML reader){ //grid for predator prey
       myGrid = new CELL[reader.getRow()][reader.getCol()];
       for(int i = 0; i< myGrid.length; i++){
           for(int j = 0; j< myGrid[0].length; j++){
               myGrid[i][j] = new CELL(reader.getCell(i, j),i,j, 0);
           }
       }
       return myGrid;
   }

  //makes the finite grid for 1 class
   public CELL[][] PPGrid(ReadXML reader){
       Grid = new CELL[reader.getRow()][reader.getCol()];
       //System.out.print(reader.getCol());
       for(int i = 0; i< Grid.length; i++){
           for(int j = 0; j< Grid[0].length; j++){
               Grid[i][j] = new CELL(reader.getCell(i, j),i,j,0);

           }
       }
       return Grid;

   }


}