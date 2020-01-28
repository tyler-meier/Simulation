package cellsociety;

abstract class simulation {

     abstract public int[][] update();

     abstract public Boolean cellIsOpen(int row, int column);

     abstract public void readFile();

}
