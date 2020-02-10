package ruleset;

import cell.CELL;
import org.xml.sax.SAXException;
import grid.FiniteGrid;
import xmlreading.ReadXML;



import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class GameOfLife extends Simulation {
    public  final int ALIVE = 1;
    public final int DEAD = 0;

    private CELL myGrid[][];
    private int aliveCount=0;
    private ReadXML reader;
    private FiniteGrid abc;


    public GameOfLife(ReadXML myReader) throws ParserConfigurationException, IOException, SAXException {
        reader = myReader;
        readFile();

    }
/*
count the number of neighbours which are alive.
 */
    private int getNeighbourCount (int row, int col){
        ArrayList<CELL> neighbours = abc.getEightNeighbourCount(row, col, myGrid);
        for(CELL cell: neighbours){
            if(cell.getType()== ALIVE) {
                aliveCount++;
            }
        }
        return aliveCount;
    }


    @Override
    public void update() {
        CELL[][] futureState = new CELL[myGrid.length][myGrid[0].length];
        for(int i =0; i < myGrid.length; i++) {
            for (int j = 0; j < myGrid[0].length; j++) {
                int alive = getNeighbourCount(i,j);
                if (myGrid[i][j].getType() == ALIVE) {
                    if (alive < 2) futureState[i][j] = new CELL(DEAD,i,j,0);
                    else if (alive>3) futureState[i][j] = new CELL(DEAD,i,j,0);
                    else futureState[i][j] = new CELL(ALIVE,i,j,0);
                }
                else if( myGrid[i][j].getType() == DEAD){
                        if(alive ==3) futureState[i][j] = new CELL(ALIVE,i,j,0);
                        else  futureState[i][j] = myGrid[i][j];
                }
                aliveCount =0;
            }
        }
        myGrid = futureState;


    }

    @Override
    public int cellStatus(int row, int column) {
      return myGrid[row][column].getType();

    }

    @Override
    public void readFile() {
         abc = new FiniteGrid(reader.getRow(),reader.getCol(),reader);//creates a grid class
        myGrid = abc.Grid_Make(reader);  //calls in the method from the class

    }

    public static void main(String args[]) throws ParserConfigurationException, IOException, SAXException {


    }

    @Override
    public int changeCellStatus(int row, int col, int currStatus) {
        return myGrid[row][col].changeType(currStatus);
    }

}
