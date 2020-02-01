package cellsociety;

abstract class simulation {

     abstract public void update();

     abstract public Boolean cellStatus(int row, int column);

     abstract public void readFile();

}
