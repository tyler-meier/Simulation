package cellsociety;

import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Segregation extends simulation {
    public  static final int EMPTY = 0;
    public static final int TYPE_1 = 1;
    public static final int TYPE_2= 2;
    private int[][] myGrid, myGrid2;
    public static double percent;
    public static double totalN;
    private int satis_Factor; //this will be given in the file.


    private ReadXML reader;


    public Segregation(ReadXML myReader) {
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

    public double getPercent(int i, int j, int type) {
            if (isBounds(i - 1, j) && myGrid[i - 1][j] != EMPTY) {
                totalN++; //count the total neighbours
                if (myGrid[i - 1][j] == type) percent++;
            }
            if (isBounds(i + 1, j) && myGrid[i + 1][j] != EMPTY) {
                totalN++;
                if (myGrid[i + 1][j] == type) percent++;
            }
            if (isBounds(i + 1, j + 1) && myGrid[i + 1][j + 1] != EMPTY) {
                totalN++;
                if (myGrid[i + 1][j + 1] == type) percent++;
            }
            if (isBounds(i - 1, j - 1) && myGrid[i - 1][j - 1] != EMPTY) {
                totalN++;
                if (myGrid[i - 1][j - 1] == type) percent++;
            }
            if (isBounds(i + 1, j - 1) && myGrid[i + 1][j - 1] != EMPTY) {
                totalN++;
                if (myGrid[i + 1][j - 1] == type) percent++;
            }
            if (isBounds(i - 1, j + 1) && myGrid[i - 1][j + 1] != EMPTY) {
                totalN++;
                if (myGrid[i - 1][j + 1] == type) percent++;
            }
            if (isBounds(i, j + 1) && myGrid[i][j +1] != EMPTY) {
                totalN++;
                if (myGrid[i][j + 1] == type) percent++;
            }
            if (isBounds(i, j - 1) && myGrid[i][j - 1] != EMPTY) {
                totalN++;
                if (myGrid[i][j - 1] == type) percent++;
            }



        percent = (percent / totalN) * 100;
        return percent;

    }




    public void move(int type, int[][] futureState){
        int x =0;
        for(int row=0; row<myGrid2.length;row++){
            for(int col = 0; col< myGrid2[0].length;col++){
                if(myGrid2[row][col] == EMPTY){
                    futureState[row][col] =(type);
                    myGrid2[row][col]=(type);
                    x = 1;
                    break;


                }

            }
            if(x==1) break;
        }
    }

    @Override
    public void update() {
        int[][] futureState = new int[myGrid.length][myGrid[0].length];
        for (int i = 0; i < myGrid.length; i++) {
            for (int j = 0; j < myGrid[0].length; j++) {
                if (myGrid[i][j] == TYPE_1) {
                    double percentage = getPercent(i, j, TYPE_1);
                    if (percentage < satis_Factor) {
                        move(TYPE_1, futureState);
                        futureState[i][j] = (EMPTY);
                        myGrid2[i][j]=(EMPTY);
                    }
                    else{
                        futureState[i][j] = myGrid[i][j];
                    } }
                else if (myGrid[i][j] == TYPE_2) {
                    double percentage = getPercent(i, j, TYPE_2);
                    if (percentage < satis_Factor) {
                        move(TYPE_2, futureState);
                        futureState[i][j] = (EMPTY);
                        myGrid2[i][j] =(EMPTY);
                    }
                    else{ futureState[i][j] = myGrid[i][j];
                    } }
                percent = 0;
                totalN =0;
            } }
        myGrid = futureState; }

    @Override
    public int cellStatus(int row, int column) {
        return myGrid[row][column];
    }

    public void createGrid(int[][] grid){
        for(int i = 0; i< grid.length; i++){
            for(int j = 0; j< grid[0].length; j++){
                grid[i][j] = reader.getCell(i, j);
                //System.out.print(myGrid[i][j]);
            }
        }
    }

    @Override
    public void readFile() {
        satis_Factor = Integer.parseInt(reader.getParameters("similarity"));
        myGrid = new int[reader.getRow()][reader.getCol()];
        myGrid2 = new int[reader.getRow()][reader.getCol()];
        createGrid(myGrid);
        createGrid(myGrid2);
    }


    public static void main(String args[]) throws ParserConfigurationException, IOException, SAXException {
        ReadXML mySim = new ReadXML();
        File xmlFile = new File("data/SampleSegregation.xml");
        mySim.setUpFile(xmlFile);
        Segregation abc = new Segregation(mySim);
       // System.out.println();
        abc.update();
    }
}
