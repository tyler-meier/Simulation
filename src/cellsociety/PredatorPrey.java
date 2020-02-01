package cellsociety;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PredatorPrey extends simulation {
    private int SHARK_BREEDING;
    private int FISH_BREEDING;
    private PPCell[][] myGrid;
    private boolean EAT = false;
    private int grid_size;
    private static final int EMPTY = 0;
    private static final int SHARK = 2;
    private static final int FISH = 1;
    private int MOVE = 0;

    private ArrayList<PPCell> Empty;
    private ArrayList<PPCell> FishNei;


    public PredatorPrey() throws ParserConfigurationException, IOException, SAXException {
        myGrid = new PPCell[grid_size][grid_size]; //makes a grid of PPCell
        ReadXML set_grid = new ReadXML(); //new object XML
        File xmlFile = new File("data/samplePP.xml"); //created the file object
        //set_grid.setRoot(xmlFile);
        set_grid.setMyGrid(); //set up the grid with PPCell.
        //still need to set up the grid.
    }
    private int FishN= 0;
    private void SHARK_EAT_FISH() {
        for (int i = 0; i < myGrid.length; i++) {
            for (int j = 0; j < myGrid[0].length; j++) {  //loop through the PPCell grid.
                if(myGrid[i][j].getTYPE() == SHARK) {
                    ArrayList<PPCell> myAdjN = myGrid[i][j].Adjacent_Neighbours(); //creates my adjacent neighbour ;
                    for (PPCell cell : myAdjN) { //foe every adjacent cell
                        if(cell.getTYPE() == FISH ){ //if the type is fish
                            FishN++; //count the number of fishes
                            if(FishN >1){  //if they are more than 1
                                FishNei =myGrid[i][j].FISH_NUMBER(); //make an arraylist of fishes
                                PPCell goal_to_eat = chooseRandomNeighbour(FishNei); //choose 1 randomly
                                goal_to_eat.setType(EMPTY); //eat that!
                                EAT = true; //eaten
                            }
                            if(FishN == 1){ //if fish is 1
                                cell.setType(EMPTY); //eat that
                                EAT = true;  //eaten

                            }

                        }

                        else{
                            Fish_MOVE_SHARK(i,j);
                        }

                        }
                }


            } } }


    public void Fish_MOVE_SHARK(int i, int j) {
                Empty = myGrid[i][j].EMPTY_list();
                    if (!EAT) {
                        //if the type is FISH then the fish moves to nearby empty cell if it is alive.
                        if (myGrid[i][j].getTYPE() == SHARK) {
                            PPCell goal = chooseRandomNeighbour(Empty);
                            myGrid[i][j].setType(EMPTY);
                            goal.setType(SHARK);
                }

                    }
                    MOVE++;

            }

      public void FISH_MOVE(int i, int j){
        if(!EAT){

            if (myGrid[i][j].getTYPE() == FISH) {
                PPCell goal = chooseRandomNeighbour(Empty); //this is the goal of the fish to move
                myGrid[i][j].setType(EMPTY);
                goal.setType(FISH);
            }
            Empty.clear();  //clear it

          }
      }



    public PPCell chooseRandomNeighbour(List<PPCell> neighbours) {
        Random rand = new Random();
        return neighbours.get(rand.nextInt(neighbours.size())); //find a random cell
    }


    @Override
    public void update() {
        SHARK_EAT_FISH();

    }

    @Override
    public Boolean cellIsOpen(int row, int column) {
        return null;
    }

    @Override
    public void readFile() {

    }
}
