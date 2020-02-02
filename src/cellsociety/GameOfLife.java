package cellsociety;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class GameOfLife extends simulation {
    public static final int ALIVE = 1;
    public static final int DEAD = 0;
    private int myGrid[][];
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
                if (isBounds(i-1,j-1) && (myGrid[i - 1][j - 1] == ALIVE)) aliveCount++;
                if (isBounds(i+1,j+1) && (myGrid[i + 1][j + 1] == ALIVE))  aliveCount++;
                if (isBounds(i+1,j-1) && (myGrid[i + 1][j - 1] == ALIVE))  aliveCount++;
                if (isBounds(i-1,j+1) && (myGrid[i - 1][j+1] == ALIVE))  aliveCount++;
                if (isBounds(i,j+1) && (myGrid[i ][j+1] == ALIVE))  aliveCount++;
                if (isBounds(i,j-1) && (myGrid[i ][j-1] == ALIVE)) aliveCount++;
                if (isBounds(i-1,j) && (myGrid[i-1][j] == ALIVE)) aliveCount++;
                if (isBounds(i+1,j) && (myGrid[i+1][j] == ALIVE)) aliveCount++;

        return aliveCount;
        }


    @Override
    public void update() {
        int[][] futureState = new int[myGrid.length][myGrid[0].length];
        for(int i =0; i < myGrid.length; i++) {
            for (int j = 0; j < myGrid[0].length; j++) {
                int alive = getNeighbourCount(i,j);
                if (myGrid[i][j] == ALIVE) {
                    if (alive < 2) futureState[i][j] = DEAD;
                    if (alive>3) futureState[i][j] = DEAD;
                    else futureState[i][j] = ALIVE;
                }
                else
                    if( myGrid[i][j] == DEAD){
                        if(alive ==3) futureState[i][j] = ALIVE;
                    }
                aliveCount =0;
            }


        }
        myGrid = futureState;


    }

    @Override
    public int cellStatus(int row, int column) {
        return myGrid[row][column];
    }

    @Override
    public void readFile() {
        myGrid = new int[reader.getRow()][reader.getCol()];
        for(int i = 0; i< myGrid.length; i++){
            for(int j = 0; j< myGrid[0].length; j++){
                myGrid[i][j] = reader.getCell(i, j);
                System.out.print(myGrid[i][j]);

            }
            System.out.println();
        }

    }

    public static void main(String args[]) throws ParserConfigurationException, IOException, SAXException {
        ReadXML mySim = new ReadXML();
        File xmlFile = new File("data/SampleGOL.xml");
        mySim.setUpFile(xmlFile);
        GameOfLife abc = new GameOfLife(mySim);
        System.out.println();
        abc.update();



    }


}
