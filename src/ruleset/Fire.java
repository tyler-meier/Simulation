package ruleset;
import cell.CELL;
import org.xml.sax.SAXException;
import grid.FiniteGrid;
import xmlreading.ReadXML;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class Fire extends Simulation {
    private CELL[][] myGrid;
    public  final int TREE = 1;
    public  final int BURNING = 2;
    public  final int EMPTY = 0;
    private double life_time;
    private FiniteGrid abc;
    private ArrayList<CELL> neighbours;
    private CELL[][] futureState;



    private ReadXML reader;
    public Fire(ReadXML myReader) throws ParserConfigurationException, IOException, SAXException {
        reader = myReader;
        readFile();
    }

    //literally removed the above code and put it in FiniteGrid class. and called it down in method

    @Override
    public void update() {
        futureState = new CELL[myGrid.length][myGrid[0].length]; //new cell set
        for (int i = 0; i < myGrid.length; i++) {
            for (int j =0; j < myGrid[0].length; j++) {
                 neighbours = abc.getFourNeighbourCount(i, j, myGrid); //neighbours added
                if (myGrid[i][j].getType() == BURNING) {  //simply the burning tree dies.
                    futureState[i][j] = new CELL(EMPTY,i,j,0); //burnt or empty

                }
                else if (myGrid[i][j].getType() == TREE) {
                    CellTree(i,j);
                }
                else{ futureState[i][j] = myGrid[i][j]; }
            }
        }
        myGrid = futureState;
    }



    private void CellTree(int i, int j ){
        for (CELL cell : neighbours) {  //for all the neighburs of the current cell
            if (cell.getType() == BURNING ) { //if the neighbours burning, tree
                double random = Math.random(); //generate a number btw 0 and 1
                if (random <= life_time) { // if the percent is greater than prob catch
                    futureState[i][j] = new CELL(BURNING,i,j,0); //it burns
                    break;
                }
            }
            else
                futureState[i][j] = new CELL(TREE,i,j,0); //does nothing.
        }
    }

    @Override
    public int cellStatus(int row, int column) {
        return myGrid[row][column].getType();
    }

    @Override
    public void readFile() { //updates the grid in the way rules say. the first and last column and the first and last
        life_time = Double.parseDouble(reader.getParameters("probCatch"));
         abc = new FiniteGrid(reader.getRow(),reader.getCol(),reader);//creates a grid class
         myGrid = abc.getMyGrid();  //calls in the method from the class
    }

    @Override
    public int changeCellStatus(int row, int col, int currStatus) {
        return myGrid[row][col].changeType(currStatus);
    }
}
