package ruleset;

import cell.CELL;
import org.xml.sax.SAXException;
import grid.FiniteGrid;
import xmlreading.ReadXML;



import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class GameOfLife extends Simulation {
    public static final int ALIVE = 1;
    public static final int DEAD = 0;
    private CELL myGrid[][];
    private int aliveCount=0;
    private ReadXML reader;


    public GameOfLife(ReadXML myReader) throws ParserConfigurationException, IOException, SAXException {
        reader = myReader;
        readFile();

    }

    public Boolean isBounds(int row, int col){
        if(row< 0 || row>= myGrid.length){
            return false;
        }
        if(col < 0 || col >= myGrid[0].length){
            return false;
        }

        return true;

    }



   //count the number of dead and alive for each cell. this method is called for every cell in update method.
    public int  getNeighbourCount(int i, int j) {
                if (isBounds(i-1,j-1) && (myGrid[i - 1][j - 1].getType() == ALIVE)) aliveCount++;
                if (isBounds(i+1,j+1) && (myGrid[i + 1][j + 1].getType() == ALIVE))  aliveCount++;
                if (isBounds(i+1,j-1) && (myGrid[i + 1][j - 1].getType() == ALIVE))  aliveCount++;
                if (isBounds(i-1,j+1) && (myGrid[i - 1][j+1].getType() == ALIVE))  aliveCount++;
                if (isBounds(i,j+1) && (myGrid[i][j+1].getType()== ALIVE))  aliveCount++;
                if (isBounds(i,j-1) && (myGrid[i ][j-1].getType() == ALIVE)) aliveCount++;
                if (isBounds(i-1,j) && (myGrid[i-1][j].getType() == ALIVE)) aliveCount++;
                if (isBounds(i+1,j) && (myGrid[i+1][j].getType() == ALIVE)) aliveCount++;

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
        FiniteGrid abc = new FiniteGrid(reader.getRow(),reader.getCol(),reader);//creates a grid class
        myGrid = abc.Grid_Make(reader);  //calls in the method from the class

    }

    public static void main(String args[]) throws ParserConfigurationException, IOException, SAXException {
//        ReadXML mySim = new ReadXML();
//        File xmlFile = new File("data/SampleGOL.xml");
//        mySim.setUpFile(xmlFile);
//        GameOfLife abc = new GameOfLife(mySim);
//        //System.out.println();
//        abc.update();
//


    }


}