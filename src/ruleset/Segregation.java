package ruleset;

import cell.CELL;
import org.xml.sax.SAXException;
import grid.FiniteGrid;
import xmlreading.ReadXML;


import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Segregation extends Simulation {
    public   final int EMPTY = 0;
    public  final int TYPE_1 = 1;
    public  final int TYPE_2= 2;

    private CELL[][] myGrid, myGrid2;
    private  double percent;
    private  double totalN;
    private int satis_Factor; //this will be given in the file.
    private FiniteGrid abc;


    private ReadXML reader;


    public Segregation(ReadXML myReader) {
        reader = myReader;
        readFile();
    }

    private double getPercent(int i, int j, int type){
        ArrayList<CELL> neighbours = abc.getEightNeighbourCount(i, j, myGrid);
        for(CELL cell: neighbours){
            if(cell.getType() != EMPTY) totalN++;
                if(cell.getType()==type) percent++;

        }
        percent = (percent / totalN) * 100;
        return percent;
    }


    private void move(int type, CELL[][] futureState){
        int x =0;
        for(int row=0; row<myGrid2.length;row++){
            for(int col = 0; col< myGrid2[0].length;col++){
                if(myGrid2[row][col].getType() == EMPTY){
                    futureState[row][col] =new CELL(type,row,col,0);
                    myGrid2[row][col].setType(type);
                    x = 1;
                    break;

                }
            }
            if(x==1) break;
        }
    }


     private void setUp(int i, int j, int type, CELL[][] myGrid, CELL[][] futureState, CELL[][] myGrid2){
             double percentage = getPercent(i, j, type);
             if (percentage < satis_Factor) {
                 move(type, futureState);
                 futureState[i][j] = new CELL(EMPTY,i,j,0);
                 myGrid2[i][j].setType(EMPTY);
             }
             else{
                 futureState[i][j] = myGrid[i][j];
             }
         }

    @Override
    //updates the neighbours by letting them move according to the rules.
    public void update() {
        CELL[][] futureState = new CELL[myGrid.length][myGrid[0].length];
        for (int i = 0; i < myGrid.length; i++) {
            for (int j = 0; j < myGrid[0].length; j++) {
                if(myGrid[i][j].getType()== TYPE_1){
                    setUp(i,j, TYPE_1,myGrid,futureState,myGrid2);
                }
                else if(myGrid[i][j].getType() == TYPE_2){
                    setUp(i,j, TYPE_2,myGrid,futureState,myGrid2);
                }
                else {
                    futureState[i][j] = myGrid2[i][j];
                }
                percent = 0;
                totalN =0;
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
        satis_Factor = Integer.parseInt(reader.getParameters("similarity"));
        abc = new FiniteGrid(reader.getRow(),reader.getCol(),reader);//creates a grid class
        myGrid = abc.Grid_Make(reader);
        myGrid2 = abc.Grid_Make(reader);  //calls in the method from the class
    }

    public static void main(String args[]) throws ParserConfigurationException, IOException, SAXException {
        ReadXML mySim = new ReadXML();
        File xmlFile = new File("data/SampleSegregation.xml");
        mySim.setUpFile(xmlFile);
        Segregation abc = new Segregation(mySim);
        // System.out.println();
        abc.update();
    }
    @Override
    public int changeCellStatus(int row, int col, int currStatus) {
        return myGrid[row][col].changeType(currStatus);
    }
}
