package SFC;

import Input.*;
import edu.uci.ics.jung.graph.Graph;

import java.util.ArrayList;

public class Source_Node_Generator extends Value{
    public ArrayList<MyNode> Generator(Graph<MyNode,MyEdge> graph, ArrayList<MyNode> List){
        ArrayList<MyNode> Modified_List = new ArrayList<>();
        for(int i=0;i<List.size();i++){
           if(graph.getNeighborCount(List.get(i))<=2) Modified_List.add(List.get(i));
        }
        for(MyNode n:Modified_List) List.remove(n);
        return List;
    }

}
