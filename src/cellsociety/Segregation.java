package cellsociety;

import java.util.Arrays;

public class Segregation extends simulation {
    public  static final int EMPTY = 0;
    public static final int TYPE_1 = 1;
    public static final int TYPE_2= 2;
    private int[][] myGrid;
    public static int percent =0;
    public static int totalN =0;
    public static final int satis_Factor = 10; //this will be given in the file.


    public void Segregation(int size){
        myGrid = new int[size][size];
        for(int [] row: myGrid){
            Arrays.fill(row,EMPTY);
        }
    }

    public Boolean isBounds(int row, int col){
        if(row< 0 || row>= myGrid.length){
            return false;
        }
        if(col < 0|| row>= myGrid[0].length){
            return false;
        }

        return true;

    }

    public void prevState()
    {
        int[][] initial = myGrid; //initial state, will update more when the file parsing happens. this current

    }//value of the grid is the initial value of the grid.

    public void getPercent_1(){

        for(int i =0; i<myGrid.length;i++){
            for(int j =0; j <myGrid[0].length;j++){
                if(myGrid[i][j]== TYPE_1) {  //if the cell is equal to type 1 pop
                    if (isBounds(i - 1, j) && myGrid[i-1][j] != EMPTY) { //if the cell neigbour is in the bound
                        totalN++; //if the cell is NOT EQUAL TO EMPTY, then calculate total
                        if (myGrid[i - 1][j] == TYPE_1) percent++;  //calculate the number of pop 1 neighbours
                    }
                    if (isBounds(i +1, j)  && myGrid[i+1][j] != EMPTY ) {
                        totalN++;
                        if (myGrid[i +1][j] == TYPE_1) percent++;
                    }
                    if (isBounds(i +1, j+1)  && myGrid[i+1][j+1] != EMPTY) {
                        totalN++;
                        if (myGrid[i +1][j+1] == TYPE_1) percent++;
                    }
                    if (isBounds(i -1, j-1)  && myGrid[i-1][j-1] != EMPTY) {
                        totalN++;
                        if (myGrid[i -1][j-1] == TYPE_1) percent++;
                    }
                    if (isBounds(i+1, j-1) && myGrid[i+1][j-1] != EMPTY) {
                        totalN++;
                        if (myGrid[i +1][j-1] == TYPE_1) percent++;
                    }
                    if (isBounds(i-1, j+1) && myGrid[i-1][j+1] != EMPTY) {
                        totalN++;
                        if (myGrid[i -1][j+1] == TYPE_1) percent++;
                    }
                    if (isBounds(i, j-1)&& myGrid[i][j-1] != EMPTY) {
                        totalN++;
                        if (myGrid[i ][j+1] == TYPE_1) percent++;
                    }
                    if (isBounds(i, j-1) && myGrid[i][j-1] != EMPTY) {
                        totalN++;
                        if (myGrid[i ][j-1] == TYPE_1) percent++;
                    }

                }}}

        percent= (percent/totalN)*100;
        if (percent <= satis_Factor){
            update();
        }


        }

        public void getPercent_2(){
            int i,j = 0;
            for(i =0; i<myGrid.length;i++){
                for( j =0; j <myGrid[0].length;j++){
                    if(myGrid[i][j]== TYPE_2) {
                        if (isBounds(i - 1, j) && myGrid[i-1][j] != EMPTY) {
                            totalN++;
                            if (myGrid[i - 1][j] == TYPE_2) percent++;
                        }
                        if (isBounds(i +1, j)  && myGrid[i+1][j] != EMPTY) {
                            totalN++;
                            if (myGrid[i +1][j] == TYPE_2) percent++;
                        }
                        if (isBounds(i +1, j+1) && myGrid[i+1][j+1] != EMPTY) {
                            totalN++;
                            if (myGrid[i +1][j+1] == TYPE_2) percent++;
                        }
                        if (isBounds(i -1, j-1)  && myGrid[i-1][j-1] != EMPTY) {
                            totalN++;
                            if (myGrid[i -1][j-1] == TYPE_2) percent++;
                        }
                        if (isBounds(i+1, j-1)  && myGrid[i+1][j-1] != EMPTY) {
                            totalN++;
                            if (myGrid[i +1][j-1] == TYPE_2) percent++;
                        }
                        if (isBounds(i-1, j+1) && myGrid[i-1][j+1] != EMPTY) {
                            totalN++;
                            if (myGrid[i -1][j+1] == TYPE_2) percent++;
                        }
                        if (isBounds(i, j-1) && myGrid[i][j-1] != EMPTY) {
                            totalN++;
                            if (myGrid[i ][j+1] == TYPE_2) percent++;
                        }
                        if (isBounds(i, j-1) && myGrid[i][j-1] != EMPTY) {
                            totalN++;
                            if (myGrid[i ][j-1] == TYPE_2) percent++;
                        }

                }}}
            percent = (percent/totalN)*100;
            if (percent <= satis_Factor){
                move(i,j);
            }


    }

    public void move(int row, int col){
        //if any neighbour is empty change the value of that neighbour to pop type





    }

    @Override
    public int[][] update() {
        return myGrid;
    }

    @Override
    public Boolean cellIsOpen(int row, int column) {
        return null;
    }

    @Override
    public void readFile() {

    }
}
