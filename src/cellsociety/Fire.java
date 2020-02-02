package cellsociety;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class Fire extends simulation {
    private FCELL[][] myGrid;
    public static final int TREE = 1;
    public static final int BURNING = 2;
    public static final int EMPTY = 0;
    private int myGrid_size;
    private ArrayList<FCELL> neighbours;


    private ReadXML reader;

    public Fire(ReadXML myReader) throws ParserConfigurationException, IOException, SAXException {
        reader = myReader;
        readFile();

    }

    public void spread(){
        for(int i = 1; i < myGrid.length-1;i++){  //first and last row do not count
            for(int j =1; j< myGrid[0].length-1;j++ ){ //first and last col dont count.
                neighbours = myGrid[i][j].Adjacent_Neighbours();  //GET the neighbours
                if(myGrid[i][j].getType()== BURNING){  //simply the burning tree dies.
                    myGrid[i][j].setType(EMPTY);
                }
                for( FCELL cell : neighbours){  //for all the neighburs of the current cell
                    if(cell.getType() == BURNING && myGrid[i][j].getType()==TREE){ //if the neighbours burning, tree
                        //is present
                        double random = Math.random(); //generate a number btw 0 and 1
                        if(random >= myGrid[i][j].getPRobCatch()){ // if the percent is greater than prob catch
                            myGrid[i][j].setType(BURNING); //it burns

                        }

                        else{
                            myGrid[i][j].setType(TREE);

                    }

                    }

                }

            }

        }


    }



    @Override
    public void update() {
        spread();

    }

    @Override
    public int cellStatus(int row, int column) {
        return -1;
    }

    @Override
    public void readFile() { //updates the grid in the way rules say. the first and last column and the first and last
        //row are empty.

//        myGrid = new FCELL[][][reader.getRow()][reader.getCol()];
//        for(int i = 0; i< myGrid.length; i++){
//            for(int j = 0; j< myGrid[0].length; j++){
//                myGrid[i][j] = reader.getCell(i, j);
//            }
//        }
//    }
//
//    }
    }

}
