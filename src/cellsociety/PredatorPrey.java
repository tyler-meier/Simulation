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
    private ArrayList<PPCell> NEW;
    private ReadXML reader;


    public PredatorPrey(ReadXML myReader) throws ParserConfigurationException, IOException, SAXException {
        reader = myReader;
        readFile(); //reads the file

        }

        private void SHARK_EAT_FISH(){

        }

//    private int FishN= 0;
//    private void SHARK_EAT_FISH() {
//        for (int i = 0; i < myGrid.length; i++) {
//            for (int j = 0; j < myGrid[0].length; j++) {  //loop through the PPCell grid.
//                if(myGrid[i][j].getTYPE() == SHARK) {
//                    ArrayList<PPCell> myAdjN = myGrid[i][j].Adjacent_Neighbours(); //creates my adjacent neighbour ;
//                    for (PPCell cell : myAdjN) { //foe every adjacent cell
//                        if(cell.getTYPE() == FISH ){ //if the type is fish
//                            FishN++; //count the number of fishes
//                            if(FishN >1){  //if they are more than 1
//                                FishNei =myGrid[i][j].FISH_NUMBER(); //make an arraylist of fishes
//                                PPCell goal_to_eat = chooseRandomNeighbour(FishNei); //choose 1 randomly
//                                goal_to_eat.setType(EMPTY); //eat that!
//                                EAT = true; //eaten
//                            }
//                            if(FishN == 1){ //if fish is 1
//                                cell.setType(EMPTY); //eat that
//                                EAT = true;  //eaten
//                            }
//                        }
//                        else if (!EAT){ MOVE_SHARK(i,j); }
//                    }
//                }
//                else if (!EAT)
//                    { FISH_MOVE(i,j);}
//
//            } } }
//            public void MOVE_SHARK(int i, int j) {
//                        //if the type is FISH then the fish moves to nearby empty cell if it is alive.
//                Empty = myGrid[i][j].EMPTY_list();
//                PPCell goal = chooseRandomNeighbour(Empty);
//                myGrid[i][j].setType(EMPTY);
//                goal.setType(SHARK);
//                Empty.clear();
//
//                }
//
//        public void FISH_MOVE(int i, int j){
//            Empty = myGrid[i][j].EMPTY_list();
//            PPCell goal = chooseRandomNeighbour(Empty); //this is the goal of the fish to move
//            myGrid[i][j].setType(EMPTY);
//            goal.setType(FISH);
//            Empty.clear();  //clear it
//
//          }
//
//
//    public PPCell chooseRandomNeighbour(List<PPCell> neighbours) {
//        Random rand = new Random();
//        return neighbours.get(rand.nextInt(neighbours.size())); //find a random cell
//    }
//
//
//      public void BREED() {
//          for (int i = 0; i < myGrid.length; i++) {
//              for (int j = 0; j < myGrid[0].length; j++) {  //
//                  NEW = myGrid[i][j].Adjacent_Neighbours();
//                  NEW = myGrid[i][j].EMPTY_list();
//                  PPCell new_bi = chooseRandomNeighbour(NEW); //chose a new cell for fish to breed
//                  if(myGrid[i][j].getTYPE() == FISH && myGrid[i][j].getLife()> FISH_BREEDING && EAT == false){
//                      new_bi.setType(FISH);
//                  if(myGrid[i][j].getTYPE() == FISH && myGrid[i][j].getLife()> SHARK_BREEDING){
//                          new_bi.setType(SHARK);
//
//                      }
//
//                  }
//
//
//              }
//          }
//      }


    @Override
    public void update() {
       // SHARK_EAT_FISH();
       // BREED();




    }

    @Override
    public int cellStatus(int row, int column) {
        return -1;
    }

    @Override
    public void readFile() { //sets up the grid
        myGrid = new PPCell[reader.getRow()][reader.getCol()];
        for(int i = 0; i< myGrid.length; i++){
            for(int j = 0; j< myGrid[0].length; j++){
                myGrid[i][j] = new PPCell(reader.getCell(i, j));
                System.out.println(myGrid[i][j].getTYPE());
            }
            System.out.println();
        }


    }


    public static void main(String args[]) throws ParserConfigurationException, IOException, SAXException {
        ReadXML mySim = new ReadXML();
        File xmlFile = new File("data/samplePP.xml");
        mySim.setUpFile(xmlFile);
        PredatorPrey abc = new PredatorPrey(mySim);
        System.out.println();
        abc.update();
    }
}
