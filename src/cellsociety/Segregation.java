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
        int i,j = 0;
        for( i =0; i<myGrid.length;i++){
            for( j =0; j <myGrid[0].length;j++){
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
            move_1(i,j);
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
                move_2(i,j);
            }


    }

    public void move_1(int i, int j){
        for(int row=0; row<myGrid.length;row++){
            for(int col = 0; col< myGrid.length;col++){
                if(myGrid[row][col] == EMPTY){



                }
            }

        }





    }

    public void move_2(int row, int col){  //if any neighbour of population type 2 is empty change the status
        if (isBounds(row-1,col-1) && myGrid[row-1][col-1] == EMPTY){
            myGrid[row][col] = EMPTY;
            myGrid[row-1][col -1] = TYPE_2;
        }
        if (isBounds(row+1,col+1) && myGrid[row+1][col+1] == EMPTY){
            myGrid[row][col] = EMPTY;
            myGrid[row+1][col +1] = TYPE_2;
        }
        if (isBounds(row-1,col+1) && myGrid[row-1][col+1] == EMPTY){
            myGrid[row][col] = EMPTY;
            myGrid[row-1][col +1] = TYPE_2;
        }
        if (isBounds(row+1,col-1) && myGrid[row+1][col-1] == EMPTY){
            myGrid[row][col] = EMPTY;
            myGrid[row+1][col -1] = TYPE_2;
        }
        if (isBounds(row,col-1) && myGrid[row][col-1] == EMPTY){
            myGrid[row][col] = EMPTY;
            myGrid[row][col-1] = TYPE_2;
        }
        if (isBounds(row,col+1) && myGrid[row][col+1] == EMPTY){
            myGrid[row][col] = EMPTY;
            myGrid[row][col +1] = TYPE_2;
        }
        if (isBounds(row,col) && myGrid[row][col] == EMPTY){
            myGrid[row][col] = EMPTY;
            myGrid[row][col] = TYPE_2;
        }
        if (isBounds(row-1,col) && myGrid[row-1][col] == EMPTY){
            myGrid[row][col] = EMPTY;
            myGrid[row][col-1] = TYPE_2;
        }
        
    }

    @Override
    public int[][] update() {
        getPercent_1();
        getPercent_2();
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
