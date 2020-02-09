package cell;

public class CELL {
    private  int xRow, yCol;
    private int type;
    private int life_time;

    public CELL(int state, int row, int col, int life){
        xRow = row;
        yCol = col;
        type = state;
        life_time = life;
}

    public int getX(){
        return xRow;
    }

    public int getY(){
        return yCol;
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
    public int changeType(int currStatus){
        if (currStatus == 1){
            this.type = 2;
            return type;
        }
        else if (currStatus == 2){
            this.type = 0;
            return type;
        }
        else if (currStatus == 0){
            this.type = 1;
            return type;
        }
        return type;
    }

}
