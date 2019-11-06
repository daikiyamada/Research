package Path;
import java.util.*;
import Input.MyNode;
import Input.MyEdge;
import Output.Visualization;
import Parameter.Parameter;
import SFC.MySFC;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import Input.Value;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import org.apache.commons.collections15.Transformer;

public class Algorithm2 extends Value{
    public Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> KNode_Disjoint_Path(Graph<MyNode,MyEdge> G,ArrayList<MySFC> S,int R){
        Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> Path_set = new HashMap<>();
        Algorithm_KVDSP kvdsp = new Algorithm_KVDSP();
        /**Generating the residual resource list by deep copy*/
        Map<MyEdge,Integer> r_e2 = new HashMap<>();
        for(MyEdge e:G.getEdges())  r_e2.put(e,Value.r_e.get(e));
        /**Sorting SFC set based on r_s in reverse order*/
        S.sort(new MyComparator());
        Set<MyEdge> Edge_Set = new HashSet<>();
        /**KNDSP Search*/
        whole:for(MySFC s:S) {
            /**path list and graph deep copy*/
            ArrayList<Graph<MyNode, MyEdge>> Path = new ArrayList<>();
            Graph<MyNode, MyEdge> G2 = Clone_Graph(G);
            /**Confriming the link resource*/
            for (MyEdge e : G2.getEdges()) if (r_e2.get(find_edge(e)) - s.Demand_Link_Resource < 0) G2.removeEdge(e);
            /**removing the link if the edge between source node and sink node is exited*/
            /**find_original_Node can find the input node from the input graph*/
            MyNode source = find_original_Node(G2, s.source);
            MyNode sink = find_original_Node(G2, s.sink);
            /**findEdge can find the edge between source node and sink node from the graph*/
            MyEdge e10 = G2.findEdge(source, sink);
            if (e10 != null) G2.removeEdge(e10);
            /**Calculating the shortest path*/
            MyTransformer list = new MyTransformer();
            DijkstraShortestPath<MyNode, MyEdge> ds = new DijkstraShortestPath<>(G2, list);
            if(ds.getDistance(source,sink)==null){
                Value.cost_link = 0;
                Path_set.clear();
                break;
            }
            List<MyEdge> Edge_List2 = ds.getPath(source, sink);
            Graph<MyNode, MyEdge> p = Dijkstra_Path(G2, Edge_List2, source);
            Path.add(p);
            /**generating the backup SFC paths*/
            for (int j = 1; j <= R; j++) {
                /**cost list for modified graph*/
                Map<MyEdge, Integer> c_e2 = new HashMap<>();
                for (MyEdge e : G.getEdges()) c_e2.put(e, Value.c_e.get(e));
                /**modeifying the graph*/
                Graph<MyNode, MyEdge> G3 = kvdsp.Algorithm2(G2, Path, source, sink, c_e2);
                /**Generating the cost list c_e2 of G3*/
                Map<MyEdge, Integer> c_e3 = new HashMap<>();
                for (MyEdge e : c_e2.keySet()) c_e3.put(find_Edge(G3, e), c_e2.get(e));
                /**Calculating the shortest path*/
                Graph<MyNode, MyEdge> p2 = kvdsp.Modified_Dijkstra(G3, source, sink, c_e3);
                if (p2 == null||!Value.cycle) {
                    Value.cost_link = 0;
                    Path_set.clear();
                    break whole;
                }
                /**Modifying the path p2*/
                Graph<MyNode, MyEdge> p3 = kvdsp.Restoration_Path(G3, p2, c_e3);
                Path.add(p3);
                /**Removing and conecting the replicated link*/
                Path = kvdsp.Modified_Path(Path, s.source, s.sink);
                c_e3.clear();
            }
            for (Graph<MyNode, MyEdge> each : Path) {
                for (MyEdge e : each.getEdges()) {
                    MyEdge e2 = find_Edge(G, e);
                    Edge_Set.add(find_edge(e));
                    r_e2.replace(e2, r_e2.get(e2) - s.Demand_Link_Resource);
                    Value.cost_link += s.Demand_Link_Resource * Value.c_e.get(find_edge(e));
                }
            }
            Path_set.put(s, Path);
        }
        return Path_set;
    }

    /**comparator definition*/
    private class MyComparator implements Comparator<MySFC>{
        public int compare(MySFC o1,MySFC o2){
            return o1.Demand_Link_Resource>=o2.Demand_Link_Resource ? -1:1;
        }
    }
    /**Transformer definition*/
    private class MyTransformer implements Transformer<MyEdge,Number> {
        public Number transform(MyEdge myEdge) {
            return Value.c_e.get(find_edge(myEdge));
        }
    }
}
