package Input;

import java.util.ArrayList;
import java.util.Map;

public class MyNode {
    public String Node_ID;
    public int Node_Num;
    public int resource;
    public int cost;
    public Map<Integer,Integer> Location_List;
    public int residual_resource;

    public MyNode(String Node_ID,int Node_num,int resource,int cost,Map<Integer,Integer> Location_List,int residual_resource){
        this.Node_ID = Node_ID;
        this.Node_Num = Node_num;
        this.resource =resource;
        this.cost = cost;
        this.Location_List = Location_List;
        this.residual_resource=residual_resource;
    }
    @Override
    public String toString(){
        return Node_ID+Node_Num;
    }
}
