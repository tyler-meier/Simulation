package RuleSets;

import cell.CELL;
import org.xml.sax.SAXException;
import grid.FiniteGrid;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PredatorPrey extends simulation {
    private static ReadXML reader;
    private static int breeding;
    private CELL[][] myGrid;
    private boolean EAT = false;
    private static final int EMPTY = 0;
    private static final int SHARK = 2;
    private static final int FISH = 1;
    private ArrayList<CELL> Empty;
    private ArrayList<CELL> FishNei;
    private ArrayList<CELL> NEW;
    private int FishN=0;
    private  ArrayList<CELL> myAdjN;
    private  CELL[][] futureState;
    private FiniteGrid abc;
    //private int turn;


    public PredatorPrey(ReadXML myReader) throws ParserConfigurationException, IOException, SAXException {
        reader = myReader;
        readFile(); //reads the file
        Empty = new ArrayList<>();
        FishNei = new ArrayList<>();
        NEW = new ArrayList<>();
        myAdjN = new ArrayList<>();
    }

        private  Boolean SHARK_EAT_FISH(int i, int  j){
            FishN= 0;
            myAdjN = formNeighbours(i,j); //find adjN of the cell
            if(myGrid[i][j].getType() == SHARK) { //if it is shark
                FishNei = new ArrayList<>();
                FishNei = FISH_NUMBER(); //get the arraylist of all its neighbours and count the number of fishes
                if (FishN == 0) { // if number of fishes is empty
                    move(i,j,SHARK);  //shark moves
                }
                else if(FishN > 1 && !FishNei.isEmpty()) { //if more than one fishie in neighbour
                        CELL goal_to_eat = chooseRandomNeighbour(FishNei); //choose 1 randomly
                        futureState[goal_to_eat.getX()][goal_to_eat.getY()] = new CELL(EMPTY,goal_to_eat.getX(),goal_to_eat.getY(),0);//set its type to empty
                        EAT = true; //eaten
                }
                else if(FishN ==1){ //if only one fish in neighbour
                    for (CELL cell : FishNei) {
                        if (cell.getType() == FISH) { //if this cell is THAT fish
                            futureState[cell.getX()][cell.getY()] = new CELL(EMPTY, cell.getX(),cell.getY(),0); //shark should eat it
                            EAT = true;
                        } } } }
            else if ( myGrid[i][j].getType() == FISH && EAT == false) {Fish_Move(i,j);}
            return EAT;
    }


    public ArrayList<CELL> formNeighbours(int i, int j){
        System.out.print(abc.getFourNeighbourCount(i,j));
        return abc.getFourNeighbourCount(i,j);
    }


    public void move(int i, int j, int type) {
        Empty = EMPTY_LIST();
        if (!Empty.isEmpty()) {
            CELL goal = chooseRandomNeighbour(Empty);
            futureState[i][j] = new CELL(i, j, EMPTY,0);
            futureState[goal.getX()][goal.getY()] = new CELL(type, goal.getX(), goal.getY(),0);
            Empty.clear();
        }
    }

    public CELL chooseRandomNeighbour(List<CELL> neighbours) {
        Random rand = new Random();
        return neighbours.get(Math.abs(rand.nextInt(neighbours.size()))); //find a random cell

    }

    public ArrayList<CELL> FISH_NUMBER() {  //all the empty neighbours of the cell.
        for (CELL cell : myAdjN) {
            if (cell.getType() == FISH) {
                FishNei.add(cell);
                FishN++;
            }
         }
        return FishNei;
    }

    public ArrayList<CELL> EMPTY_LIST(){  //all the empty neighbours of the cell.
        ArrayList<CELL> ans = new ArrayList<>();
        for( CELL cell: myAdjN ){
            if( cell.getType() == EMPTY){
                ans.add(cell);
            }
        }
        return ans;
    }

    public void Fish_Move(int i, int j){
       if(myGrid[i][j].getType() == FISH && eaten == false) {//&& futureState[i][j] != null && futureState[i][j].getTYPE() != EMPTY ) {
           //System.out.println("we are in move for fishie");
            move(i,j,FISH);
        }
    }


    boolean eaten =false;
    @Override
    public void update() {
        futureState = new CELL[myGrid.length][myGrid[0].length];
        for (int i = 0; i < myGrid.length; i++) {
            for (int j = 0; j < myGrid[0].length; j++) {  //loop through the CELL grid.
                eaten = SHARK_EAT_FISH(i, j);
                if (futureState[i][j] == null) futureState[i][j] = myGrid[i][j];
                futureState[i][j].setLife((futureState[i][j].getLife())+1);
                Breed(i, j);


            }
            myAdjN.clear();
            Empty.clear();
            FishNei.clear();
            NEW.clear();

        }

        myGrid = futureState;
    }

    @Override
    public int cellStatus(int row, int column) {
      return myGrid[row][column].getType();
    }

    public void Breed(int i, int j){
        NEW = EMPTY_LIST(); //breeding
        if(NEW != null && !NEW.isEmpty()){
            CELL new_bi = chooseRandomNeighbour(NEW); //chose a new cell for fish to breed
            if(myGrid[i][j].getType() == FISH && futureState[i][j].getLife() > breeding) {
                futureState[new_bi.getX()][new_bi.getY()] = new CELL(FISH, new_bi.getX(), new_bi.getY(),0);
            }
            else if (myGrid[i][j].getType() == SHARK && futureState[i][j].getLife() > breeding) {
               // System.out.println("sharkie reproduce");
                    futureState[new_bi.getX()][new_bi.getY()] = new CELL(SHARK,new_bi.getX(), new_bi.getY(),0);

                }
            }
        }

    @Override
    public void readFile() { //sets up the grid
        breeding = Integer.parseInt(reader.getParameters("cycle"));
        abc = new FiniteGrid(reader.getRow(),reader.getCol(),reader);
        myGrid = abc.Grid_Make(reader);
    }

    public static void main(String args[]) throws ParserConfigurationException, IOException, SAXException {
        breeding = Integer.parseInt(reader.getParameters("cycle"));
        ReadXML mySim = new ReadXML();
        File xmlFile = new File("data/samplePP.xml");
        mySim.setUpFile(xmlFile);
        PredatorPrey abc = new PredatorPrey(mySim);
        abc.update();
    }
}


