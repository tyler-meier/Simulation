package cell;

public class CELL {
    private  int Xrow, Ycol;
    private int type;
    private int life_time;



    public CELL(int state, int row, int col, int life){
        Xrow = row;
        Ycol = col;
        type = state;
        life_time = life;

}


    public int getX(){
        return Xrow;
    }

    public int getY(){
        return Ycol;
    }

    public void setLife(int life_time) { this.life_time = life_time;}

    public int getType(){
        return type;
    }
    public void setType(int type){
        this.type = type;
    }

    public int getLife() {
        return life_time;
    }

}
