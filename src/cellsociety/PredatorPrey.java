package cellsociety;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PredatorPrey extends simulation {
    public static final int FISH = 1;
    public static final int SHARK = 2;
    public static final int EMPTY = 0;
    private  int SHARK_BREEDING;
    private int FISH_BREEDING;
    private int[][] myGrid;
    private boolean EAT = false;
    



    public PredatorPrey() throws ParserConfigurationException, IOException, SAXException {
        ReadXML set_grid = new ReadXML(); //new object XML
        File xmlFile = new File("data/samplePP.xml"); //created the file object
        set_grid.setRoot(xmlFile);
        set_grid.setMyGrid(); //set up the grid

        for(int i =0; i < myGrid.length; i++){
            for (int j = 0; j < myGrid[0].length; j++){
                set_grid.getCell(i,j);
            }
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

    public void Detect_EMPTY() {
        for (int i = 0; i < myGrid.length; i++) {
            for (int j = 0; j < myGrid[0].length; j++) {
                if (myGrid[i][j] != EMPTY && (isBounds(i - 1, j)) && myGrid[i - 1][j] == EMPTY) {




                }

            }
        }
    }


    public void FishMove(){
        for(int i =0; i < myGrid.length;i++){
            for(int j =0; j< myGrid[0].length;j++){
//                Random rand = new Random();
//                int new_x = rand.nextInt(((i+1) - (i-1)) + 1) + (i-1); //generates a random number
//                //between max and min value of x coordinate of the cell FOR EACH CELL. //neighbours
//                int new_y = rand.nextInt(((j+1) - (j-1)) + 1) + (j-1); //max min for y coordinate
//                if(myGrid[new_x][new_y] != EMPTY) {
//                    do{
//                        new_x = rand.nextInt(((i + 1) - (i - 1)) + 1) + (i - 1);
//                        new_y = rand.nextInt(((j + 1) - (j - 1)) + 1) + (j - 1);
//                        //find a new value for x and y if they are not empty.
//                    }
//                    while (myGrid[new_x][new_y] == EMPTY);
//
//                }





                //if( EAT == false && ((myGrid[new_x][new_y]) == EMPTY)) { // only move to the next neighbour of u are not eaten.


                }



            }
        }
    }

    @Override
    public int[][] update() {
        return new int[0][];
    }

    @Override
    public Boolean cellIsOpen(int row, int column) {
        return null;
    }

    @Override
    public void readFile() {

    }
}
