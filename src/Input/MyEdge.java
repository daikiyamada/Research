package Input;

import java.util.Map;

public class MyEdge {
    public int Edge_ID;

    public MyEdge(int Edge_ID){
        this.Edge_ID = Edge_ID;
    }
    @Override
    public String toString(){
        return "edge:"+Edge_ID;
    }
}
