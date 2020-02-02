package cellsociety;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
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
        if(col < 0|| row>= myGrid[0].length){
            return false;
        }

        return true;

    }
    public void prevState()

    {
        int[][] initial = myGrid; //initial state, will update more when the file parsing happens. this current

    }
   //count the number of dead and alive for each cell. this method is called for every cell in update method.
    public void  getNeighbourCount() {
        for ( int i  = 0; i < myGrid.length; i++) {
            for (int j = 0; j < myGrid[0].length; j++) {
                if (isBounds(i-1,j-1) && (myGrid[i - 1][j - 1] == ALIVE)) aliveCount++;
                if (isBounds(i+1,j+1) && (myGrid[i + 1][j + 1] == ALIVE))  aliveCount++;
                if (isBounds(i+1,j-1) && (myGrid[i + 1][j - 1] == ALIVE))  aliveCount++;
                if (isBounds(i-1,j+1) && (myGrid[i - 1][j+1] == ALIVE))  aliveCount++;
                if (isBounds(i,j+1) && (myGrid[i ][j+1] == ALIVE))  aliveCount++;
                if (isBounds(i,j-1) && (myGrid[i ][j-1] == ALIVE)) aliveCount++;
                if (isBounds(i-1,j) && (myGrid[i-1][j] == ALIVE)) aliveCount++;
                if (isBounds(i+1,j) && (myGrid[i+1][j] == ALIVE)) aliveCount++;
                if (myGrid[i][j] == ALIVE) {
                    if (aliveCount > 2) myGrid[i][j] = DEAD; //underpopulation
                    else if (aliveCount > 3) myGrid[i][j] = DEAD; //overpopulation
                }
                else if (myGrid[i][j]== DEAD){
                    if(aliveCount == 3)myGrid[i][j] = ALIVE; //reproduction
                }
            } }}


    @Override
    public void update() {
        getNeighbourCount();

    }


  public void nextState(){
      int[][] nextState = myGrid;
  }


    @Override
    public int cellStatus(int row, int column) {
        return -1;
    }

    @Override
    public void readFile() {
        myGrid = new int[reader.getRow()][reader.getCol()];
        for(int i = 0; i< myGrid.length; i++){
            for(int j = 0; j< myGrid[0].length; j++){
                myGrid[i][j] = reader.getCell(i, j);
            }
        }
    }
}
