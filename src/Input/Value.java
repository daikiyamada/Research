package Input;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import Parameter.*;
import java.util.*;
public class Value{
    public class parameter extends Parameter{}
    /**目的関数値・容量・コストのリスト*/
    public static Map<MyNode,Integer> r_n = new HashMap<>();
    public static Map<MyNode,Integer> c_n = new HashMap<>();
    public static Map<MyEdge,Integer> r_e = new HashMap<>();
    public static Map<MyEdge,Integer> c_e = new HashMap<>();
    public static int cost_link;
    public static int cost_node;

    /**使用率計算のためのリスト*/
    public static ArrayList<Map<Integer,Double>> Util_Edge_List = new ArrayList<>();
    public static ArrayList<Map<Integer,Integer>> Edge_Total_num = new ArrayList<>();
    public static ArrayList<Map<Integer,Integer>> Edge_Used_num = new ArrayList<>();
    public static ArrayList<Map<Integer,Double>> Util_Node_List = new ArrayList<>();
    public static ArrayList<Map<Integer,Integer>> Node_Total_num = new ArrayList<>();
    public static ArrayList<Map<Integer,Integer>> Node_Used_num = new ArrayList<>();
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
        Graph<MyNode,MyEdge> Path = new SparseGraph<>();
        for(int a=0;a<Edge_List.size();a++){
            MyEdge e = new MyEdge(Edge_List.get(a).Edge_ID);
            Pair<MyNode> pair = path.getEndpoints(Edge_List.get(a));
            if(path.getEdgeType(Edge_List.get(a))== EdgeType.DIRECTED) Path.addEdge(e,pair.getFirst(),pair.getSecond(),EdgeType.DIRECTED);
            else Path.addEdge(e,pair.getFirst(),pair.getSecond(),EdgeType.UNDIRECTED);
        }
        return Path;
    }
    public void Edge_Utilization(Graph<MyNode,MyEdge> G,Set<MyEdge> Edge_List,Map<MyEdge,Integer> r_e2){
        /**初期化*/
        Map<Integer,Double> edge_util = new HashMap<>();
        Map<Integer,Integer> count_edge = new HashMap<>();
        Map<Integer,Integer> count_sum_edge = new HashMap<>();
        Map<Integer,Integer> count_edge_list = new HashMap<>();
        parameter par = new parameter();
        for(int i = par.link_cost_min; i<=par.link_cost_max;i++){
            edge_util.put(i,0.0);
            count_edge.put(i,0);
            count_sum_edge.put(i,0);
            count_edge_list.put(i,0);
        }
        /**コスト別の辺数*/
        for(MyEdge e:G.getEdges()){
            int cost = c_e.get(find_edge(e));
            count_sum_edge.replace(cost,count_sum_edge.get(cost)+1);
        }
        /**コスト別の使用辺数*/
        for(MyEdge e: Edge_List){
            int cost = c_e.get(find_edge(e));
            count_edge.replace(cost,count_edge.get(cost)+1);
            double util = 1-(double)r_e2.get(e)/r_e.get(e);
            edge_util.replace(cost,edge_util.get(cost)+util);
        }

        /**使用率の計算*/
        for(int i:edge_util.keySet()){
            int nun_edge = count_edge.get(i);
            edge_util.replace(i,(double)edge_util.get(i)/nun_edge);
        }
        Util_Edge_List.add(edge_util);
        Edge_Total_num.add(count_sum_edge);
        Edge_Used_num.add(count_edge);
    }
    public void node_Utilization(Graph<MyNode,MyEdge> G,Set<MyNode> Node_List,Map<MyNode,Integer> r_n2){
        /**初期化*/
        Map<Integer,Double> node_util = new HashMap<>();
        Map<Integer,Integer> count_node = new HashMap<>();
        Map<Integer,Integer> count_sum_node = new HashMap<>();
        parameter par = new parameter();
        for(int i = 0; i<=par.node_cost_max;i++){
            count_node.put(i,0);
            count_sum_node.put(i,0);
            node_util.put(i,0.0);
        }
        for(MyNode n:G.getVertices()){
            int cost = c_n.get(find_node(n));
            count_sum_node.replace(cost,count_sum_node.get(cost)+1);
        }
        /**使用されたノードの計算*/
        for(MyNode n: Node_List){
            int cost = c_n.get(find_node(n));
            count_node.replace(cost,count_node.get(cost)+1);
            double util = 1-(double)r_n2.get(n)/r_n.get(n);
            node_util.replace(cost,(double)node_util.get(cost)+util);
        }
        /**使用率の計算*/
        for(int i : node_util.keySet()){
            int num_node = count_node.get(i);
            node_util.replace(i,(double)node_util.get(i)/num_node);
        }
        Util_Node_List.add(node_util);
        Node_Total_num.add(count_sum_node);
        Node_Used_num.add(count_node);
    }

}
