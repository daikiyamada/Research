package Path;

import Input.MyEdge;
import Input.MyNode;
import java.util.*;

import Input.*;

import SFC.MySFC;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import Placement.Placement_Maker;
import edu.uci.ics.jung.graph.util.Pair;
import org.apache.commons.collections15.Transformer;

public class Algorithm_Based_MECF extends Value{
    public Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> Routing_Algo(Graph<MyNode, MyEdge> G,ArrayList<MySFC> S,int R){
        /**P: Path set, r_e2: residual resource*/
        Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> P = new HashMap<>();
        Map<MyEdge,Integer> r_e2 = new HashMap<>();
        /**Sorting the SFC set based on r_s in reverse order*/
        Collections.sort(S, new Comparator<MySFC>() {
            @Override
            public int compare(MySFC o1, MySFC o2) {
                return o1.Demand_Link_Resource>=o2.Demand_Link_Resource ? -1:1;
            }
        });
        /**Generating the residual resource list*/
        for(MyEdge e:G.getEdges()) r_e2.put(e,Value.r_e.get(e));
        /**Calculating the Path*/
        whole:for(MySFC s:S){
            /**Graph Copy(Deep copy) and graph set for path*/
            Graph<MyNode,MyEdge> G2 = Clone_Graph(G);
            ArrayList<Graph<MyNode,MyEdge>> graph = new ArrayList<>();
            /**removing the link if edge between source node and sink node are exited*/
            MyNode source = find_original_Node(G2, s.source);
            MyNode sink = find_original_Node(G2, s.sink);
            MyEdge e10 = G2.findEdge(source, sink);
            if (e10 != null) G2.removeEdge(e10);
            for(int i = 0;i<R+1;i++){
                part:while(true){
                    /**calculating the shortest path based on dijkstra*/
                    Transformer<MyEdge,Number> list = new Transformer<MyEdge, Number>() {
                        @Override
                        public Number transform(MyEdge myEdge) {
                            return Value.c_e.get(find_edge(myEdge));
                        }
                    };
                    Graph<MyNode,MyEdge> p = new UndirectedSparseGraph<>();
                    DijkstraShortestPath<MyNode,MyEdge> ds = new DijkstraShortestPath<>(G2,list);
                    /**in the case of no path between source and sink*/
                    if(ds.getDistance(source,sink)!=null) {
                        List<MyEdge> p_list  = ds.getPath(source, sink);
                        p = Dijkstra_Path(G2, p_list);
                    }
                    else {
                        Value.cost_link=0;
                        P.clear();
                        break whole;
                    }
                    /**r:=min*/
                    int r = s.Demand_Link_Resource;
                    ArrayList<MyEdge> min_edge_list = new ArrayList<>();
                    for(MyEdge e:p.getEdges()){
                        MyEdge e2 = find_Edge(G,e);
                        int cap_edge = r_e2.get(e2);
                        if(cap_edge<s.Demand_Link_Resource){
                            r = cap_edge;
                            min_edge_list.add(e2);
                        }
                    }
                    /**if the link has enough resource*/
                    if(r==s.Demand_Link_Resource){
                        /**modifying the residual resource of each link on the path*/
                        for(MyEdge e:p.getEdges()){
                            MyEdge e2 = find_Edge(G,e);
                            r_e2.replace(e2,r_e2.get(e2) - s.Demand_Link_Resource);
                        }
                        /**Removing the nodes and the edges on the path*/
                        G2=Remover_Graph(G2,p,s);
                        graph.add(p);
                        /**Calculating c_link*/
                        for(MyEdge e:p.getEdges()){
                            MyEdge e2 = find_Edge(G,e);
                            Value.cost_link+=s.Demand_Link_Resource*Value.c_e.get(e2);
                        }
                        break part;
                    }
                    else if(r<s.Demand_Link_Resource){
                        /**if the link has not enough resource, removing it*/
                        G2=Remover_Edge(G2,min_edge_list);
                    }
                    else{
                        Value.cost_link=0;
                        P.clear();
                        break whole;
                    }
                }
            }
            P.put(s,graph);
        }
        return P;
    }
    public Graph<MyNode,MyEdge> Dijkstra_Path(Graph<MyNode,MyEdge> G,List<MyEdge> p_list){
        Graph<MyNode,MyEdge> p = new UndirectedSparseGraph<>();
        /**リンクからパスを選択する*/
        for(MyEdge e:p_list){
            Pair<MyNode> node = G.getEndpoints(e);
            for(MyNode n:node){
                if(!p.containsVertex(n)){
                    p.addVertex(n);
                }
            }
            p.addEdge(e,node.getFirst(),node.getSecond());
        }
        return p;
    }
    public Graph<MyNode,MyEdge> Remover_Graph(Graph<MyNode,MyEdge> G,Graph<MyNode,MyEdge> p,MySFC s){
        for(MyNode n:p.getVertices()){
            MyNode n2 = find_original_Node(G,n);
            if(s.source.Node_Num!=n2.Node_Num && s.sink.Node_Num!=n2.Node_Num) G.removeVertex(n2);
        }
        return G;
    }
    public Graph<MyNode,MyEdge> Remover_Edge(Graph<MyNode,MyEdge> G,ArrayList<MyEdge> min_edge_list){
        for(MyEdge e:min_edge_list) G.removeEdge(find_Edge(G,e));
        return G;
    }

}
