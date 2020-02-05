package resources.cell;

public class CELL {
        private  int state;


        public CELL(int type){
            state = type;
        }

        public int getType(){

            return state;
        }


        public void setType(int state) {
            this.state = state;

        }
    }


