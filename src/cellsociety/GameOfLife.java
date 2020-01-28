package cellsociety;

import java.util.Arrays;

public class GameOfLife extends simulation {
    public static final int ALIVE = 1;
    public static final int DEAD = 0;
    private int myGrid[][];
    private int initial[][];
    private int nextState [][];
    private int count=0;

    public GameOfLife(int size){
        myGrid = new int[size][size];
        for(int[] row: myGrid){
            Arrays.fill(row,DEAD);  //initialize the grid by making everyone dead.
        }

    }

    public Boolean isBounds(int row, int col){
        if(row<0 || row>= myGrid.length){
            return false;
        }
        if(col <0|| row>= myGrid.length){
            return false;
        }

        return true;

    }
    public void prevState()

    {
        initial = myGrid; //initial state, will update more when the file parsing happens. this current

    }

    @Override
    public void update() {
        for(int i =0; i< myGrid.length;i++){
            for(int j =0;j<myGrid.length;j++){
                //if(myGrid[i-1][j-1] ==    )

            }

        }

    }

    @Override
    public Boolean cellIsOpen(int row, int column) {
        return null;
    }

    @Override
    public void readFile() {

    }
}
