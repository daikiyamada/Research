package Input;

import java.util.ArrayList;
import java.util.Map;

public class MyNode {
    public String Node_ID;
    public int Node_Num;
    public int resource;
    public int cost;

    public MyNode(String Node_ID,int Node_num,int resource,int cost){
        this.Node_ID = Node_ID;
        this.Node_Num = Node_num;
        this.resource =resource;
        this.cost = cost;
    }
    @Override
    public String toString(){
        return Node_ID+Node_Num;
    }
}
