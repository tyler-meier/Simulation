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
    private ArrayList<FCELL> neighbours;


    private ReadXML reader;
    public Fire(ReadXML myReader) throws ParserConfigurationException, IOException, SAXException {
        reader = myReader;
        readFile();

    }

    @Override
    public void update() {
        FCELL[][] futureState = new FCELL[myGrid.length][myGrid[0].length]; //new cell set
        for(int i =1; i < myGrid.length-1; i++) {
            for (int j = 1; j < myGrid[0].length-1; j++) {
                neighbours = myGrid[i][j].Adjacent_Neighbours();  //GET the neighbours for each cell
                if(myGrid[i][j].getType()== BURNING){  //simply the burning tree dies.
                    futureState[i][j].setType(EMPTY);
                }
                else
                    for( FCELL cell : neighbours){  //for all the neighburs of the current cell
                        if(cell.getType() == BURNING && myGrid[i][j].getType()==TREE){ //if the neighbours burning, tree
                            double random = Math.random(); //generate a number btw 0 and 1
                            if(random >= myGrid[i][j].getPRobCatch()){ // if the percent is greater than prob catch
                                futureState[i][j].setType(BURNING); //it burns

                            }

                            else{
                                futureState[i][j].setType(TREE);

                            }

                        } }
            }
        }
        myGrid = futureState;

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
                myGrid[i][j] = reader.getFireCell(myGrid[i][j].getX(),myGrid[i][j].getY());
            }
        }
    }

}
