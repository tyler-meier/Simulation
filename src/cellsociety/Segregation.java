package cellsociety;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Segregation extends simulation {
    public  static final int EMPTY = 0;
    public static final int TYPE_1 = 1;
    public static final int TYPE_2= 2;
    private int[][] myGrid;
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




    public void move(int type, int i, int j, int[][] futureState){
        for(int row=0; row<myGrid.length;row++){
            for(int col = 0; col< myGrid[0].length;col++){
                if(myGrid[row][col] == EMPTY){
                    futureState[row][col] = type;
                    futureState[i][j] = EMPTY;

                }

            }
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
                        move(TYPE_1, i ,j, futureState);

                    }

                }

                else if (myGrid[i][j] == TYPE_2) {
                    double percentage = getPercent(i, j, TYPE_2);
                    if (percentage < satis_Factor) {
                        move(TYPE_2, i ,j, futureState);


                    }
                }
                percent = 0;
                totalN =0;

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
                //System.out.print(myGrid[i][j]);
            }
        }
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
