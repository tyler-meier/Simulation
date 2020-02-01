package cellsociety;

abstract class simulation {

     abstract public void update();

     abstract public int cellStatus(int row, int column);

     abstract public void readFile();

}
