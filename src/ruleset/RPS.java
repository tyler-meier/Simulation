package ruleset;

import cell.CELL;
import grid.FiniteGrid;
import org.xml.sax.SAXException;
import xmlreading.ReadXML;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class RPS extends Simulation  {
    private int threshold;
    private FiniteGrid abc;
    private CELL[][] myGrid;
    public final int ROCK =1;
    public final int SCISSORS =0;
    public final int PAPER = 2;
    private ArrayList<CELL> ans = new ArrayList<>();
    private CELL[][] futureState;


    private ReadXML reader;
    public RPS(ReadXML myReader) throws ParserConfigurationException, IOException, SAXException {
        reader = myReader;
        readFile();
    }

    public int handle_Rock(int i, int j){
        int count_R = 0;
        ans  = abc.getEightNeighbourCount(i,j, myGrid);
            for (CELL cell : ans) {
                if (cell.getType() == PAPER) count_R++; }
            ans.clear();
        return count_R;
    }

    public int handle_PAPER(int i, int j){
        int count_R = 0;
        ans  = abc.getEightNeighbourCount(i,j, myGrid);
        for (CELL cell : ans) {
            if (cell.getType() == SCISSORS) count_R++;
        }
        ans.clear();
        return count_R;
    }

    public int handle_SCISSORS(int i, int j){
        int count_R = 0;
        ans  = abc.getEightNeighbourCount(i,j, myGrid);
        for (CELL cell : ans) {
            if (cell.getType() == ROCK) count_R++;
        }
        ans.clear();
        return count_R;
    }


   int count =0;
    public void update_Rock(int i, int j, CELL[][] future){
            count = handle_Rock(i, j);
            //System.out.println(count);
            if (count >= threshold) {
                futureState[i][j] = new CELL(PAPER, i, j, 0);
            }
            else {
                futureState[i][j] = myGrid[i][j];
            }
        }


    @Override
    public void update() {
         futureState= new CELL[myGrid.length][myGrid[0].length];
         count = 0;
        for(int i =0; i < myGrid.length;i++) {
            for (int j = 0; j < myGrid[0].length; j++) {
                //System.out.print(myGrid[i][j].getType());// in this loop
                if (myGrid[i][j].getType()== ROCK){
                    update_Rock(i,j,futureState);
                }
                else if (myGrid[i][j].getType() == SCISSORS) {
                    count = handle_SCISSORS(i, j);
                    if (count >= threshold) futureState[i][j] = new CELL(ROCK, i, j, 0);
                    else {
                        futureState[i][j] = myGrid[i][j];
                    }
                } else {
                    count = handle_PAPER(i,j);
                    if (count >= threshold) futureState[i][j] = new CELL(SCISSORS, i, j, 0);
                    else {
                        futureState[i][j] = myGrid[i][j];
                    }
                }
            }
        }
        myGrid = futureState;
    }

    @Override
    public int cellStatus(int row, int column) {
        return myGrid[row][column].getType();
    }

    @Override
    public void readFile() throws IOException, SAXException, ParserConfigurationException {
        threshold = Integer.parseInt(reader.getParameters("threshold"));
        abc = new FiniteGrid(reader.getRow(),reader.getCol(),reader);
        myGrid = abc.Grid_Make(reader);

    }

    @Override
    public int changeCellStatus(int row, int col, int currStatus) {
        return myGrid[row][col].changeType(currStatus);
    }
}
