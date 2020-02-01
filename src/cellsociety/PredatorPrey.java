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


    public PredatorPrey() throws ParserConfigurationException, IOException, SAXException {
        myGrid = new PPCell[grid_size][grid_size]; //makes a grid of PPCell
        ReadXML set_grid = new ReadXML(); //new object XML
        File xmlFile = new File("data/samplePP.xml"); //created the file object
        set_grid.setRoot(xmlFile);
        set_grid.setMyGrid(); //set up the grid with PPCell.
        //still need to set up the grid.
    }


    public void Fish_MOVE() {
        for (int i = 0; i < myGrid.length; i++) {
            for (int j = 0; j < myGrid[0].length; j++) {  //loop through the PPCell grid.
                  myGrid[i][j].Adjacent_Neighbours();
                  ArrayList<PPCell> Empty=  myGrid[i][j].EMPTY_list();
                if (myGrid[i][j].getTYPE() == FISH && EAT == false) {  //if the type is FISH then the fish moves to nearby empty cell if it is alive.
                      PPCell goal =   chooseRandomNeighbour(Empty); //this is the goal of the fish to move
                      myGrid[i][j].setType(EMPTY);
                       goal.setType(FISH);


                }
            }
        }

    }



    public PPCell chooseRandomNeighbour(List<PPCell> neighbours) {
        Random rand = new Random();
        return neighbours.get(rand.nextInt(neighbours.size())); //find a random cell
    }









    @Override
    public int[][] update() {
        Fish_MOVE();
        
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
