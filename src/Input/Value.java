package Input;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;

import java.util.*;
public class Value {
    public static Map<MyNode,Integer> r_n = new HashMap<>();
    public static Map<MyNode,Integer> c_n = new HashMap<>();
    public static Map<MyEdge,Integer> r_e = new HashMap<>();
    public static Map<MyEdge,Integer> c_e = new HashMap<>();
    public static int cost_link;
    public static int cost_node;

    public void node_resource(MyNode n, int capacity){
        Value.r_n.put(n,capacity);
    }
    public void node_cost(MyNode n,int cost){
        Value.c_n.put(n,cost);
    }
    public void edge_resource(MyEdge e,int capacity){
        Value.r_e.put(e,capacity);
    }
    public void edge_cost(MyEdge e,int cost){
        Value.c_e.put(e,cost);
    }

    public MyEdge find_edge(MyEdge e){
        MyEdge e2 =null;
        for(MyEdge e1:Value.c_e.keySet()) if(e1.Edge_ID==e.Edge_ID) e2 = e1;
        return e2;
    }
    public MyNode find_node(MyNode n){
        MyNode n2 = null;
        for(MyNode n1:Value.c_n.keySet()) if(n1.Node_Num==n.Node_Num) n2 = n1;
        return n2;
    }
    public MyNode find_node2(ArrayList<MyNode> p,MyNode n){
        MyNode n2 = null;
        for(MyNode now:p) if(now.Node_Num==n.Node_Num) n2 = now;
        return n2;
    }
    public Graph<MyNode,MyEdge> Clone_Graph(Graph<MyNode,MyEdge> path){
        /**Pathのディープコピー*/
        Collection<MyEdge> Edge_List1 = path.getEdges();
        ArrayList<MyEdge> Edge_List = new ArrayList<>(Edge_List1);
        Graph<MyNode,MyEdge> Path = new UndirectedSparseGraph<>();
        for(int a=0;a<Edge_List.size();a++){
            MyEdge e = new MyEdge(Edge_List.get(a).Edge_ID);
            Pair<MyNode> pair = path.getEndpoints(Edge_List.get(a));
            Path.addEdge(e,pair.getFirst(),pair.getSecond());
        }
        return Path;
    }
}
