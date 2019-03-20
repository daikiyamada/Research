package Input;

import java.util.Map;

public class MyEdge {
    public int Edge_ID;
    public int resource;
    public int cost;
    public int r_resource;

    public MyEdge(int Edge_ID,int resource,int cost){
        this.Edge_ID = Edge_ID;
        this.resource = resource;
        this.cost = cost;
    }
    @Override
    public String toString(){
        return "edge:"+Edge_ID;
    }
}
