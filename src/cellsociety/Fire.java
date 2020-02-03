package cellsociety;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class Fire extends simulation {
    private FCELL[][] myGrid;
    public static final int TREE = 1;
    public static final int BURNING = 2;
    public static final int EMPTY = 0;
    private int myGrid_size;
    private ArrayList<FCELL> myNeighbours;


    private ReadXML reader;
    public Fire(ReadXML myReader) throws ParserConfigurationException, IOException, SAXException {
        reader = myReader;
        readFile();

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


    public ArrayList<FCELL> getNeighbourCount(int i, int j) {
        myNeighbours = new ArrayList<>();
        if (isBounds(i,j+1))  myNeighbours.add(myGrid[i][j+1]);
        if (isBounds(i,j-1))  myNeighbours.add(myGrid[i][j-1]);
        if (isBounds(i-1,j))  myNeighbours.add(myGrid[i-1][j]);
        if (isBounds(i+1,j))  myNeighbours.add(myGrid[i+1][j]);
        return myNeighbours;
    }

    @Override
    public void update() {
        FCELL[][] futureState = new FCELL[myGrid.length][myGrid[0].length]; //new cell set
        futureState = myGrid;
        for (int i = 0; i < myGrid.length; i++) {
            for (int j = 0; j < myGrid[0].length; j++) {
                ArrayList<FCELL> neighbours = getNeighbourCount(i, j); //GET the neighbours for each cell
                if (myGrid[i][j].getType() == BURNING) {  //simply the burning tree dies.
                    futureState[i][j].setType(EMPTY); //burnt or empty
                } //works
                else if (myGrid[i][j].getType() == TREE) {
                    for (FCELL cell : neighbours) {  //for all the neighburs of the current cell
                        if (cell.getType() == BURNING ) { //if the neighbours burning, tree
                            double random = Math.random(); //generate a number btw 0 and 1
                            if (random >= myGrid[i][j].getPRobCatch()) { // if the percent is greater than prob catch
                                futureState[i][j].setType(BURNING); //it burns

                            } else {
                                futureState[i][j].setType(TREE); //does nothing.

                            }
                        }
                    }
                }
                else{
                    futureState[i][j] = myGrid[i][j];
                }
                myNeighbours.clear();
            }
            myGrid = futureState;

        }
    }




    @Override
    public int cellStatus(int row, int column) {
        return myGrid[row][column].getType();
    }

    @Override
    public void readFile() { //updates the grid in the way rules say. the first and last column and the first and last
        myGrid = new FCELL[reader.getRow()][reader.getCol()];
        for(int i = 0; i< myGrid.length; i++){
            for(int j = 0; j< myGrid[0].length; j++){
                myGrid[i][j] = new FCELL(reader.getCell(i, j));
            }
        }
    }

}
