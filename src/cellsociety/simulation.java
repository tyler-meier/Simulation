package cellsociety;

abstract class simulation {

     abstract public void update();

     abstract public Boolean cellIsOpen(int row, int column);

     abstract public void pause();

     abstract public void resume();

     abstract public void readFile();

}
