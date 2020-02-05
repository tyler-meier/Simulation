package resources.cell;

public class PCELL {

    private  int state;


    public PCELL(int type){
        state = type;
    }

    public int getType(){

        return state;
    }


    public void setType(int state) {
        this.state = state;

    }
}
