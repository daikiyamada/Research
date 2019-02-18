package Input;

import java.util.Map;

public class MyEdge {
    public int Edge_ID;
    public int resource;
    public int cost;
    public Map<Integer,Integer> location_list;
    public int r_resource;

    public MyEdge(int Edge_ID,int resource,int cost,Map<Integer,Integer> location_list,int r_resource){
        this.Edge_ID = Edge_ID;
        this.resource = resource;
        this.cost = cost;
        this.location_list =location_list;
        this.r_resource = r_resource;
    }
    @Override
    public String toString(){
        return "edge:"+Edge_ID;
    }
}
