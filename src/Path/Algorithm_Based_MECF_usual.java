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
public class Algorithm_Based_MECF_usual extends Value{
    public Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> Routing_Algo(Graph<MyNode, MyEdge> G,ArrayList<MySFC> S,int R){
        /**P:Path set, r_e2: residual resource list*/
        Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> P = new HashMap<>();
        Map<MyEdge,Integer> r_e2 = new HashMap<>();
        /**generating the residual resource list*/
        for(MyEdge e:G.getEdges()) r_e2.put(e,Value.r_e.get(e));
        whole:for(MySFC s:S){
            /**Graph Deep copy*/
            Graph<MyNode,MyEdge> G2 = Clone_Graph(G);
            ArrayList<Graph<MyNode,MyEdge>> graph = new ArrayList<>();
            /**Removing the link if the edge between source and sink is exited*/
            for(int i =0;i<R+1;i++){
                part:while(true){
                    /**Calculating the shortest path based on c_e*/
                    Transformer<MyEdge,Number> list = new Transformer<MyEdge, Number>() {
                        @Override
                        public Number transform(MyEdge myEdge) {
                            return Value.c_e.get(find_edge(myEdge));
                        }
                    };
                    Graph<MyNode,MyEdge> p = new UndirectedSparseGraph<>();
                    DijkstraDistance<MyNode,MyEdge> dd = new DijkstraDistance<>(G2);
                    MyNode source = find_original_Node(G2, s.source);
                    MyNode sink = find_original_Node(G2, s.sink);
                    if(dd.getDistance(source,sink)!=null) {
                        DijkstraShortestPath<MyNode,MyEdge> ds = new DijkstraShortestPath<>(G2,list);
                        List<MyEdge> p_list = ds.getPath(source, sink);
                        p = Dijkstra_Path(G2, p_list);
                    }
                    else{
                        Value.cost_link = 0;
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
                    /**the case of the link has enough resource*/
                    if(r==s.Demand_Link_Resource){
                        /**modifying the residual link resource on the path*/
                        for(MyEdge e:p.getEdges()){
                            MyEdge e2 = find_Edge(G,e);
                            r_e2.replace(e2,r_e2.get(e2) - s.Demand_Link_Resource);
                        }
                        /**G'からパスに所属する頂点とそれらに接続する辺を取り除く*/
                        Remover_Graph(G2,p,s);
                        graph.add(p);
                        /**c_linkの算出*/
                        for(MyEdge e:p.getEdges()){
                            MyEdge e2 = find_Edge(G,e);
                            Value.cost_link+=s.Demand_Link_Resource*Value.c_e.get(e2);
                        }
                        break part;
                    }
                    else if(r<s.Demand_Link_Resource){
                        /**キャパシティが十分にない場合、その辺を削除する*/
                        Remover_Edge(G2,min_edge_list);
                    }
                    else {
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
    private Graph<MyNode,MyEdge> Dijkstra_Path(Graph<MyNode,MyEdge> G,List<MyEdge> p_list){
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
    private void Remover_Graph(Graph<MyNode,MyEdge> G,Graph<MyNode,MyEdge> p,MySFC s){
        for(MyNode n:p.getVertices()){
            MyNode n2 = find_original_Node(G,n);
            if(s.source.Node_Num!=n2.Node_Num && s.sink.Node_Num!=n2.Node_Num) G.removeVertex(n2);
        }

    }
    private void Remover_Edge(Graph<MyNode,MyEdge> G,ArrayList<MyEdge> min_edge_list){
        for(MyEdge e:min_edge_list) G.removeEdge(find_Edge(G,e));

    }

}
