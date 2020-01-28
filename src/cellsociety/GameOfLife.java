package cellsociety;

import java.util.Arrays;

public class GameOfLife extends simulation {
    public static final int ALIVE = 1;
    public static final int DEAD = 0;
    private int myGrid[][];

    public GameOfLife(int size){
        myGrid = new int[size][size];
        for(int[] row: myGrid){
            Arrays.fill(row,DEAD);  //initialize the
        }

    }




    @Override
    public void update() {

    }

    @Override
    public Boolean cellIsOpen(int row, int column) {
        return null;
    }

    @Override
    public void readFile() {

    }
}
