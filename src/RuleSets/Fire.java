package RuleSets;

import Neighbourhood.Square;
import cell.CELL;
import org.xml.sax.SAXException;
import grid.FiniteGrid;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class Fire extends simulation {
    private CELL[][] myGrid;
    public static final int TREE = 1;
    public static final int BURNING = 2;
    public static final int EMPTY = 0;
    public double life_time;


    private ReadXML reader;
    public Fire(ReadXML myReader) throws ParserConfigurationException, IOException, SAXException {
        reader = myReader;
        readFile();

    }
    public ArrayList<CELL> formNeighbours(int i, int j, CELL[][] Grid){
        Square nei = new Square(i, j);
        return nei.getFourNeighbourCount(i,j,myGrid);
    }

    @Override
    public void update() {
        CELL[][] futureState = new CELL[myGrid.length][myGrid[0].length]; //new cell set
        for (int i = 0; i < myGrid.length; i++) {
            for (int j =0; j < myGrid[0].length; j++) {
                ArrayList<CELL> neighbours = formNeighbours(i, j,myGrid); //GET the neighbours for each cell
                if (myGrid[i][j].getType() == BURNING) {  //simply the burning tree dies.
                    futureState[i][j] = new CELL(EMPTY,i,j,0); //burnt or empty
                } //works
                else if (myGrid[i][j].getType() == TREE) {
                    for (CELL cell : neighbours) {  //for all the neighburs of the current cell
                        if (cell.getType() == BURNING ) { //if the neighbours burning, tree
                            double random = Math.random(); //generate a number btw 0 and 1
                            if (random <= life_time) { // if the percent is greater than prob catch
                                futureState[i][j] = new CELL(BURNING,i,j,0); //it burns
                                break;
                            } }
                        futureState[i][j] = new CELL(TREE,i,j,0); //does nothing.
                    } }
                else{ futureState[i][j] = myGrid[i][j]; }
            } }
        myGrid = futureState;
    }

    @Override
    public int cellStatus(int row, int column) {
        return myGrid[row][column].getType();
    }

    @Override
    public void readFile() { //updates the grid in the way rules say. the first and last column and the first and last
        life_time = Double.parseDouble(reader.getParameters("probCatch"));
        FiniteGrid abc = new FiniteGrid(reader.getRow(),reader.getCol(),reader);//creates a grid class
        myGrid = abc.Grid_Make(reader);  //calls in the method from the class
    }



}
