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
    private int SHARK_BREEDING =1;
    private int FISH_BREEDING =1;
    private PPCell[][] myGrid;
    private boolean EAT = false;
    private static final int EMPTY = 0;
    private static final int SHARK = 2;
    private static final int FISH = 1;


    private ArrayList<PPCell> Empty;
    private ArrayList<PPCell> FishNei;
    private ArrayList<PPCell> NEW;
    private ReadXML reader;
    private int FishN=0;
    private  ArrayList<PPCell> myAdjN;
    private  PPCell[][] futureState;

    public PredatorPrey(ReadXML myReader) throws ParserConfigurationException, IOException, SAXException {
        reader = myReader;
        readFile(); //reads the file

        }

        private  Boolean SHARK_EAT_FISH(int i, int  j){
            myAdjN = getNeighbourCount(i,j); //find adjN of the cell
            if(myGrid[i][j].getTYPE() == SHARK) { //if it is shark
                FishNei = FISH_NUMBER(); //get the arraylist of all its neighbours and count the number of fishes
                if (FishN == 0) { // if number of fishes is empty
                    move(i,j,SHARK);  //shark moves

                }
                else if(FishN > 1) { //if more than one fishie in neighbour
                        PPCell goal_to_eat = chooseRandomNeighbour(FishNei); //choose 1 randomly
                        futureState[goal_to_eat.getX()][goal_to_eat.getY()] = new PPCell(goal_to_eat.getX(),goal_to_eat.getY(),EMPTY);//set its type to empty
                        EAT = true; //eaten
                }

                else if(FishN ==1){ //if only one fish in neighbour
                    for (PPCell cell : FishNei) {
                        if (cell.getTYPE() == FISH) { //if this cell is THAT fish
                            futureState[cell.getX()][cell.getY()] = new PPCell(cell.getX(),cell.getY(),EMPTY); //shark should eat it
                            EAT = true;
                        }
                    }
                }

                    }
            return EAT;
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
        futureState[i][j] = new PPCell(i,j,EMPTY);
        futureState[goal.getX()][goal.getY()] = new PPCell(goal.getX(),goal.getY(),type);
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

    int turn =0;
    boolean eaten =false;
    @Override
    public void update() {

        futureState = new PPCell[myGrid.length][myGrid[0].length]; //new cell set
        for (int i = 0; i < myGrid.length; i++) {
            for (int j = 0; j < myGrid[0].length; j++) {  //loop through the PPCell grid.
                eaten = SHARK_EAT_FISH(i,j); //get the number of fishes in sharks neighbourhood. and them getting eaten
                if(myGrid[i][j].getTYPE() == FISH && eaten == false){
                    move(i,j,FISH);
            }
                NEW = EMPTY_LIST(); //breeding
                PPCell new_bi = chooseRandomNeighbour(NEW); //chose a new cell for fish to breed
                if(myGrid[i][j].getTYPE() == FISH && turn > FISH_BREEDING && eaten == false){
                    futureState[new_bi.getX()][new_bi.getY()] = new PPCell(new_bi.getX(),new_bi.getY(),FISH);
                    if(myGrid[i][j].getTYPE() == FISH && turn> SHARK_BREEDING){
                        futureState[new_bi.getX()][new_bi.getY()] = new PPCell(new_bi.getX(),new_bi.getY(),SHARK);

                    }
                }
            }
        }
         turn++;
        futureState = myGrid;
    }

    @Override
    public int cellStatus(int row, int column) {
      return myGrid[row][column].getTYPE();
    }


    @Override
    public void readFile() { //sets up the grid
        myGrid = new PPCell[reader.getRow()][reader.getCol()];
        for(int i = 0; i< myGrid.length; i++){
            for(int j = 0; j< myGrid[0].length; j++){
                myGrid[i][j] = new PPCell(i,j,reader.getCell(i, j));

                System.out.println(myGrid[i][j].getY()); //null
                System.out.println(myGrid[i][j].getY()); //null
            }
            System.out.println();
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
