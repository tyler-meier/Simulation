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
    private int FishN=0;
    private  ArrayList<PPCell> myAdjN;
    private  FCELL[][] futureState;

    public PredatorPrey(ReadXML myReader) throws ParserConfigurationException, IOException, SAXException {
        reader = myReader;
        readFile(); //reads the file

        }

        private void  SHARK_EAT_FISH(int i, int  j){
            myAdjN = getNeighbourCount(i,j); //find adjN of the cell
            if(myGrid[i][j].getTYPE() == SHARK) { //if it is shark
                FishNei = FISH_NUMBER(); //get the arraylist of all its neighbours and count the number of fishes
                if (FishN == EMPTY) { // if number of fishes is empty
                    move(i,j,SHARK);  //shark moves

                }
                else if(FishN > 1) { //if more than one fishie in neighbour
                        PPCell goal_to_eat = chooseRandomNeighbour(FishNei); //choose 1 randomly
                        futureState[goal_to_eat.getX()][goal_to_eat.getY()] = new FCELL(EMPTY);//set its type to empty
                        EAT = true; //eaten
                }

                else if(FishN ==1){ //if only one fish in neighbour
                    for (PPCell cell : FishNei) {
                        if (cell.getTYPE() == FISH) { //if this cell is THAT fish
                            futureState[cell.getX()][cell.getY()] = new FCELL(EMPTY); //shark should eat it
                            EAT = true;
                        }
                    }
                }

                    }
                }


    public Boolean isBounds(int row, int col){
        if(row< 0 || row>= myGrid.length){
            return false;
        }
        if(col < 0|| col>= myGrid[0].length){
            return false;
        }

        return true;

    }


    public ArrayList<PPCell> getNeighbourCount(int i, int j) {
        ArrayList<PPCell> neighbours = new ArrayList<>();
        if (isBounds(i,j+1))  neighbours.add(myGrid[i][j+1]);
        if (isBounds(i,j-1))  neighbours.add(myGrid[i][j-1]);
        if (isBounds(i-1,j))  neighbours.add(myGrid[i-1][j]);
        if (isBounds(i+1,j))  neighbours.add(myGrid[i+1][j]);
        return neighbours;
    }



    public void move(int i, int j, int type) {
        Empty = EMPTY_LIST();
        PPCell goal = chooseRandomNeighbour(Empty);
        futureState[i][j] = new FCELL(EMPTY);
        futureState[goal.getX()][goal.getY()] = new FCELL(type);
        Empty.clear();

                }

    public PPCell chooseRandomNeighbour(List<PPCell> neighbours) {
        Random rand = new Random();
        return neighbours.get(rand.nextInt(neighbours.size())); //find a random cell
    }


    public ArrayList<PPCell> FISH_NUMBER() {  //all the empty neighbours of the cell.
        for (PPCell cell : myAdjN) {
            if (cell.getTYPE() == FISH) {
                FishNei.add(cell);
                FishN++;
            }

         }
        return FishNei;

        }



    public ArrayList<PPCell> EMPTY_LIST(){  //all the empty neighbours of the cell.
        for( PPCell cell: myAdjN ){
            if( cell.getTYPE() == EMPTY){
               Empty.add(cell);

            }
        }
        return Empty;

    }


    @Override
    public void update() {
        int turn =0;
        futureState = new FCELL[myGrid.length][myGrid[0].length]; //new cell set
        for (int i = 0; i < myGrid.length; i++) {
            for (int j = 0; j < myGrid[0].length; j++) {  //loop through the PPCell grid.
                SHARK_EAT_FISH(i,j); //get the number of fishes in sharks neighbourhood. and them getting eaten
                if(myGrid[i][j].getTYPE() == FISH && EAT == false){
                    move(i,j,FISH);

            }

            }
        }


         turn++;

    }

    @Override
    public int cellStatus(int row, int column) {
      return myGrid[row][column].getTYPE();
    }



      public void BREED(int i, int j) {
                  NEW = myAdjN;
                  NEW = myGrid[i][j].EMPTY_list();
                  PPCell new_bi = chooseRandomNeighbour(NEW); //chose a new cell for fish to breed
                  if(myGrid[i][j].getTYPE() == FISH && myGrid[i][j].getLife()> FISH_BREEDING && EAT == false){
                      new_bi.setType(FISH);
                  if(myGrid[i][j].getTYPE() == FISH && myGrid[i][j].getLife()> SHARK_BREEDING){
                          new_bi.setType(SHARK);

                      }

                  }


              }




    @Override
    public void readFile() { //sets up the grid
        myGrid = new PPCell[reader.getRow()][reader.getCol()];
        for(int i = 0; i< myGrid.length; i++){
            for(int j = 0; j< myGrid[0].length; j++){
                myGrid[i][j] = new PPCell(reader.getCell(i, j),i,j);

                System.out.println(myGrid[i][j].getX());
            }
            //System.out.println();
        }


    }


    public static void main(String args[]) throws ParserConfigurationException, IOException, SAXException {
        ReadXML mySim = new ReadXML();
        File xmlFile = new File("data/samplePP.xml");
        mySim.setUpFile(xmlFile);
        PredatorPrey abc = new PredatorPrey(mySim);
        //System.out.println();
        abc.update();
    }
}
