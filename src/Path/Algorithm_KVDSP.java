package Path;

import Input.MyEdge;
import Input.MyNode;
import Input.Value;
import SFC.MySFC;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import org.apache.commons.collections15.Transformer;

import java.util.*;

public class Algorithm_KVDSP extends Value{
    public Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> KNode_Disjoint_Path(Graph<MyNode,MyEdge> G,ArrayList<MySFC> S,int R){
        Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> Path_set = new HashMap<>();
        /**Generating the residual resource list*/
        Map<MyEdge,Integer> r_e2 = new HashMap<>();
        for(MyEdge e:G.getEdges())  r_e2.put(e,Value.r_e.get(e));
        Set<MyEdge> Edge_Set = new HashSet<>();
        /**KNDSP*/
        whole:for(MySFC s:S) {
            /**Deep copy graph, path set for each SFC*/
            ArrayList<Graph<MyNode, MyEdge>> Path = new ArrayList<>();
            Graph<MyNode, MyEdge> G2 = Clone_Graph(G);
            /**Calculating the shortest path*/
            MyNode source = find_original_Node(G2, s.source);
            MyNode sink = find_original_Node(G2, s.sink);
            MyTransformer list = new MyTransformer();
            DijkstraShortestPath<MyNode, MyEdge> ds = new DijkstraShortestPath<>(G2, list);
            List<MyEdge> Edge_List2 = ds.getPath(source, sink);
            Graph<MyNode, MyEdge> p = Dijkstra_Path(G2, Edge_List2, source);
            if (p == null) {
                Value.cost_link = 0;
                Path_set.clear();
                break whole;
            }
            Path.add(p);
            /**calculating the back up paths*/
            for (int j = 1; j <= R; j++) {
                Map<MyEdge, Integer> c_e2 = new HashMap<>();
                for (MyEdge e : G.getEdges()) c_e2.put(e, Value.c_e.get(e));
                Graph<MyNode, MyEdge> G3 = Algorithm2(G2, Path, source, sink, c_e2);
                /**generating the cost list for modified graph*/
                Map<MyEdge, Integer> c_e3 = new HashMap<>();
                for (MyEdge e : c_e2.keySet()) c_e3.put(find_Edge(G3, e), c_e2.get(e));
                /**calclating the shortest path*/
                Graph<MyNode, MyEdge> p2 = Modified_Dijkstra(G3, source, sink, c_e3);
                if (p2 == null && Value.cycle) {
                    Value.cost_link = 0;
                    Path_set.clear();
                    break whole;
                } else if (!Value.cycle) {
                    Path_set.clear();
                    Value.cost_link = 0;
                    break whole;
                }
                /**modefiying the path*/
                Graph<MyNode, MyEdge> p3 = Restoration_Path(G3, p2, c_e3);
                Path.add(p3);
                /**removing adn connecting the link on the paths*/
                Path = Modified_Path(Path, s.source, s.sink);
                c_e3.clear();
            }
            /**Confirming the capacity of link*/
            boolean assess = true;
            for(Graph<MyNode,MyEdge> each_path:Path) for(MyEdge e:each_path.getEdges()) if(r_e2.get(find_Edge(G,e))-s.Demand_Link_Resource<0) assess =false;
            if(assess) {
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
            else{
                Value.cost_link = 0;
                Path_set.clear();
                break whole;
            }
        }
        return Path_set;
    }
    /**Transformerの定義*/
    private class MyTransformer implements Transformer<MyEdge,Number> {
        public Number transform(MyEdge myEdge) {
            return Value.c_e.get(find_edge(myEdge));
        }
    }

    public  Graph<MyNode,MyEdge> Algorithm2(Graph<MyNode,MyEdge> graph,ArrayList<Graph<MyNode,MyEdge>> Path,MyNode s,MyNode t,Map<MyEdge,Integer> c_e2){
        Graph<MyNode,MyEdge> graph2 = Clone_Graph(graph);
        /**replacing the link with negative cost*/
        for(Graph<MyNode,MyEdge> p:Path){
            DijkstraDistance<MyNode,MyEdge> dd = new DijkstraDistance<>(p);
            for(MyEdge e:p.getEdges()){
                Pair<MyNode> pair = graph2.getEndpoints(find_Edge(graph2,e));
                graph2.removeEdge(find_Edge(graph2,e));
                MyNode n1= pair.getFirst();
                MyNode n2 = pair.getSecond();
                double d1 = (double)dd.getDistance(find_original_Node(p,s),find_original_Node(p,n1));
                double d2 = (double)dd.getDistance(find_original_Node(p,s),find_original_Node(p,n2));
                MyEdge e2 = new MyEdge(e.Edge_ID);
                if(d1<d2) graph2.addEdge(e2,n2,n1, EdgeType.DIRECTED);
                else graph2.addEdge(e2,n1,n2,EdgeType.DIRECTED);
                c_e2.put(find_edge(e),-c_e2.get(find_edge(e)));
            }
        }
        /**Adding additional node*/
        Map<MyNode,MyNode> Additional_List = new HashMap<>();
        for(Graph<MyNode,MyEdge>p:Path){
            for(MyNode n:p.getVertices()){
                MyNode now2 = find_original_Node(graph2, n);
                if (now2.Node_Num != s.Node_Num && now2.Node_Num != t.Node_Num) {
                    MyNode n2 = new MyNode(now2.Node_ID, -now2.Node_Num);
                    graph2.addVertex(n2);
                    Additional_List.put(now2,n2);
                }
            }
        }
        /**replacing the link to additional node*/
        for(Graph<MyNode,MyEdge>p:Path){
            for(MyNode n:p.getVertices()){
                /**connecting the link from addtional node to original node except the node next to source, sink node*/
                MyNode now2 = find_original_Node(graph2,n);
                if(now2.Node_Num!=s.Node_Num&&now2.Node_Num!=t.Node_Num){
                    for(MyEdge e:graph2.getInEdges(now2)){
                        MyNode n3 = graph2.getOpposite(now2,e);
                        graph2.removeEdge(e);
                        MyEdge e3 = new MyEdge(e.Edge_ID);
                        graph2.addEdge(e3,n3,Additional_List.get(now2),EdgeType.DIRECTED);
                    }
                }
            }
        }
        /**Generating the link with 0 cost*/
        int between = 999999999;
        for(MyNode n:Additional_List.keySet()){
            MyEdge e = new MyEdge(between);
            graph2.addEdge(e,Additional_List.get(n),n,EdgeType.DIRECTED);
            c_e2.put(e,0);
            between--;
        }
        ArrayList<MyNode> Path_Edge_List = new ArrayList<>();
        Map<MyEdge,Pair<MyNode>> Edge_List = new HashMap<>();
        /**Generating the node list*/
        for(Graph<MyNode,MyEdge> p:Path){
            for(MyNode n:p.getVertices()){
                MyNode n2 = new MyNode(n.Node_ID,n.Node_Num);
                Path_Edge_List.add(n2);
            }
        }
        for(Graph<MyNode,MyEdge> p:Path) {
            ArrayList<MyNode> Node_List = new ArrayList<MyNode>(p.getVertices());
            for (MyNode n : p.getVertices()) {
                MyNode now2 = find_original_Node(graph2, n);
                if (now2.Node_Num != s.Node_Num && now2.Node_Num != t.Node_Num) {
                    /**In the case of undirected edge*/
                    Collection<MyEdge> edge_list = graph2.getIncidentEdges(now2);
                    for (MyEdge e : edge_list) {
                        MyNode n4 = graph2.getOpposite(now2, e);
                        /**the case of the node don't belong to any path*/
                        if (graph2.getEdgeType(e) == EdgeType.UNDIRECTED && !Find_Node(Path_Edge_List, n4)) {
                            MyNode n2 = Additional_List.get(now2);
                            MyEdge e3 = new MyEdge(e.Edge_ID);
                            MyEdge e4 = new MyEdge(-e.Edge_ID);
                            c_e2.put(e4, c_e2.get(find_edge(e)));
                            c_e2.put(e3, c_e2.get(find_edge(e)));
                            graph2.addEdge(e3, n2, n4, EdgeType.DIRECTED);
                            graph2.addEdge(e4, n4, now2, EdgeType.DIRECTED);
                            c_e2.remove(find_edge(e));
                            graph2.removeEdge(e);
                        }
                        else if (graph2.getEdgeType(e) == EdgeType.UNDIRECTED && Find_Node(Path_Edge_List, n4)&&!Node_List.contains(find_original_Node(p,n4)))Edge_List.put(e, graph2.getEndpoints(e));
                    }
                }
            }
        }
        /**in the case of K>=2*/
        for(MyEdge e:Edge_List.keySet()){
            /**generating the link*/
            Pair<MyNode> list = Edge_List.get(e);
            MyEdge e2 = new MyEdge(e.Edge_ID);
            MyEdge e3 = new MyEdge(-e.Edge_ID);
            /**searching the original node or additional node*/
            MyNode original1,additional1,original2,additional2;
            ArrayList<MyNode> graph_list = new ArrayList<MyNode>(graph2.getVertices());
            if(list.getFirst().Node_Num>0){
                original1 = list.getFirst();
                additional1 = Exchange_Node(graph_list,original1);
            }
            else {
                additional1 = list.getFirst();
                original1 = Exchange_Node(graph_list,additional1);
            }
            if(list.getSecond().Node_Num>0){
                original2 = list.getSecond();
                additional2 = Exchange_Node(graph_list,original1);
            }
            else {
                additional2 = list.getSecond();
                original2 = Exchange_Node(graph_list,additional1);
            }
            /**Connecting the directed edge from the additional2 to original1*/
            graph2.addEdge(e3,additional2,original1,EdgeType.DIRECTED);
            c_e2.put(e3,c_e2.get(find_edge(e)));
            /**Connecting the directed edge from the original1 to additional1*/
            graph2.addEdge(e2,additional1,original2,EdgeType.DIRECTED);
            c_e2.put(e2,c_e2.get(find_edge(e)));
            c_e2.remove(find_edge(e));
            graph2.removeEdge(e);
        }
        Graph<MyNode,MyEdge> graph3 = Clone_Graph(graph2);
        return graph3;
    }
  public Graph<MyNode,MyEdge> Modified_Dijkstra(Graph<MyNode,MyEdge> graph,MyNode s,MyNode t,Map<MyEdge,Integer> c_e2){
        Graph<MyNode,MyEdge> p = new SparseGraph<MyNode,MyEdge>();
        Graph<MyNode,MyEdge> graph2 = Clone_Graph(graph);
        /**distance list*/
        Map<MyNode,Integer> d = new HashMap<>();
        /**node list*/
        ArrayList<MyNode> S = new ArrayList<>();
        /**node list which indicates the one before node*/
        Map<MyNode,MyNode> P = new HashMap<>();
            /**setting the distance of source node*/
            MyNode source = find_original_Node(graph2, s);
            d.put(source, 0);
            ArrayList<MyNode> list = new ArrayList<MyNode>(graph2.getSuccessors(source));
            for (MyNode n : graph2.getVertices()) {
                if (list.contains(n)) {
                    MyEdge e = graph2.findEdge(source, n);
                    d.put(n, c_e2.get(find_Edge(graph,e)));
                    P.put(n, source);
                    S.add(n);
                }
                else d.put(n, 999999999);
            }
        whole:while(true){
                if(S.size()>graph2.getVertexCount()){
                    Value.cycle = false;
                    return null;
                }
            /**step2*/
            /**finding the node, which has shortest distance, from S*/
            int dis =999999999;
            MyNode min_node = null;
            for(MyNode n:S){
                if(d.get(n)<dis) {
                    dis = d.get(n);
                    min_node = n;
                }
            }
            if(min_node==null) return null;
            /**removing the node from S*/
            S.remove(min_node);
            /**the case of sink node, exit*/
            if(min_node.Node_Num==t.Node_Num) break;
            /**step 3*/
            /**searching the successor of min_node*/
            ArrayList<MyNode> list2 = new ArrayList<MyNode>(graph2.getSuccessors(find_original_Node(graph2,min_node)));
            for(MyNode n:list2){
                MyEdge e = graph2.findEdge(find_original_Node(graph2,min_node),n);
                if(d.get(min_node)+c_e2.get(find_Edge(graph,e))<d.get(n)){
                    d.replace(n,d.get(min_node)+c_e2.get(find_Edge(graph,e)));
                    if(P.get(n)!=null) P.replace(n,min_node);
                    else P.put(n,min_node);
                    S.add(n);
                }
            }
        }
        /**Constructing the calculated path*/
        MyNode sink = find_original_Node(graph2,t);
        p.addVertex(sink);
        while(true){
            /**Calculating the predecessor*/
            MyNode n = P.get(sink);
            /**Adding the link*/
            MyEdge e = graph2.findEdge(find_original_Node(graph2,n),sink);
            p.addVertex(n);
            if(graph2.getEdgeType(e)==EdgeType.DIRECTED) p.addEdge(e,n,sink,EdgeType.DIRECTED);
            else p.addEdge(e,n,sink,EdgeType.UNDIRECTED);
            /**the case which source node is predecessor, exit*/
            if(n.Node_Num==s.Node_Num) break;
            /**adding n to sink*/
            sink = n;
        }
        return p;
    }
    public Graph<MyNode,MyEdge> Restoration_Path(Graph<MyNode,MyEdge> graph2,Graph<MyNode,MyEdge> p,Map<MyEdge,Integer> c_e2){
        /**node list for management*/
        ArrayList<MyNode> Node_List = new ArrayList<>();
        for(MyNode n:graph2.getVertices()){
            if(!Find_Node(Node_List,n)){
                MyNode n2 = new MyNode(n.Node_ID,n.Node_Num);
                Node_List.add(n2);
            }
        }
            /**generating the list of node and link on the path*/
            ArrayList<MyEdge> Edge_List = new ArrayList<MyEdge>(p.getEdges());
            Graph<MyNode,MyEdge> mp = new SparseGraph<>();
                /**adding the necessary link*/
                for(MyEdge e:Edge_List){
                    /**the case of undirected link*/
                    if(p.getEdgeType(e)==EdgeType.UNDIRECTED){
                        Pair<MyNode> pair_node = p.getEndpoints(e);
                        MyNode source = Find_Node2(Node_List,pair_node.getFirst());
                        MyNode sink = Find_Node2(Node_List,pair_node.getSecond());
                        mp.addVertex(source);
                        mp.addVertex(sink);
                        MyEdge e2 = new MyEdge(e.Edge_ID);
                        mp.addEdge(e2,source,sink,EdgeType.UNDIRECTED);
                    }
                    /**the case of directed link*/
                    else if(p.getEdgeType(e)==EdgeType.DIRECTED) {
                        /**the case of negative cost link、adding source node*/
                        if(c_e2.get(find_Edge(graph2,e))<0){
                            MyNode source = Find_Node2(Node_List,p.getSource(e));
                            MyNode sink = Find_Node2(Node_List,p.getDest(e));
                            MyNode additional = Exchange_Node(Node_List,sink);
                            mp.addVertex(source);
                            mp.addVertex(additional);
                            MyEdge e2 = new MyEdge(e.Edge_ID);
                           mp.addEdge(e2,source,additional,EdgeType.UNDIRECTED);
                        }
                        /**the case of positive cost, same step of undirected link*/
                        else if(c_e2.get(find_Edge(graph2,e))>0){
                            MyNode source = Find_Node2(Node_List,p.getSource(e));
                            MyNode sink = Find_Node2(Node_List,p.getDest(e));
                            if(source.Node_Num<0&&e.Edge_ID>0){
                                MyNode additional = Exchange_Node(Node_List,source);
                                mp.addVertex(additional);
                                mp.addVertex(sink);
                                MyEdge e2 = new MyEdge(e.Edge_ID);
                                mp.addEdge(e2,additional,sink,EdgeType.UNDIRECTED);
                            }
                            else if(source.Node_Num<0&&e.Edge_ID<0){
                                MyNode additional = Exchange_Node(Node_List,source);
                                mp.addVertex(additional);
                                mp.addVertex(sink);
                                MyEdge e2 = new MyEdge(-e.Edge_ID);
                                mp.addEdge(e2,additional,sink,EdgeType.UNDIRECTED);
                            }
                            else if(source.Node_Num>0&&sink.Node_Num>0){
                                mp.addVertex(source);
                                mp.addVertex(sink);
                                MyEdge e2 = new MyEdge(-e.Edge_ID);
                               mp.addEdge(e2,source,sink,EdgeType.UNDIRECTED);
                            }
                        }
                    }
                }
        return mp;
    }
    public ArrayList<Graph<MyNode,MyEdge>> Modified_Path(ArrayList<Graph<MyNode,MyEdge>> Path,MyNode s,MyNode t){
        Graph<MyNode,MyEdge> G = new SparseGraph<>();
        /**パス集合のみでグラフを作成する*/
        ArrayList<MyNode> Node_List = new ArrayList<>();
        /**パスの全頂点管理・追加*/
        for(Graph<MyNode,MyEdge> p:Path) {
            for (MyNode n : p.getVertices()) {
                if (!Find_Node(Node_List, n)) {
                    MyNode n2 = new MyNode(n.Node_ID, n.Node_Num);
                    Node_List.add(n2);
                    G.addVertex(n2);
                }
            }
        }
        ArrayList<MyEdge> Edge_List = new ArrayList<MyEdge>();
        Set<MyEdge> Edge_List2 = new HashSet<>();
        for(Graph<MyNode,MyEdge> p:Path){
            for (MyEdge e : p.getEdges()) {
                if(!Find_Edge(Edge_List,e)) {
                    Pair<MyNode> List = p.getEndpoints(e);
                    MyEdge e2 = new MyEdge(e.Edge_ID);
                    MyNode s1 = Find_Node2(Node_List, List.getFirst());
                    MyNode s2 = Find_Node2(Node_List, List.getSecond());
                    Edge_List.add(e2);
                    G.addEdge(e2, s1, s2);
                }
                else{
                    MyEdge e2 = find_Edge(G,e);
                    Edge_List2.add(e2);
                }
            }
        }
        /**重複辺の削除*/
        for(MyEdge e:Edge_List2) G.removeEdge(find_Edge(G,e));
        MyNode source = Find_Node2(Node_List,s);
        MyNode sink = Find_Node2(Node_List,t);
        /**点素パスの作成*/
       ArrayList<Graph<MyNode,MyEdge>> Path_Set = new ArrayList<>();
        for(int i=0;i<Path.size();i++){
            /**点素経路の探索*/
            MyTransformer list = new MyTransformer();
            DijkstraShortestPath<MyNode,MyEdge> ds = new DijkstraShortestPath<>(G,list);
            List<MyEdge> Edge_List3 = ds.getPath(find_original_Node(G,source),find_original_Node(G,sink));
            Graph<MyNode,MyEdge> p = Dijkstra_Path(G,Edge_List3,source);
            if(p.getVertexCount()==0)  Value.cycle=false;

            Path_Set.add(p);
            for(MyNode n:p.getVertices()) if(n.Node_Num!=s.Node_Num&&n.Node_Num!=t.Node_Num) G.removeVertex(find_original_Node(G,n));
        }
        return Path_Set;
    }


    public MyNode Exchange_Node(ArrayList<MyNode> List,MyNode n){
        for(MyNode n3:List){
            if(n3.Node_Num==-n.Node_Num) return n3;
        }
        return null;
    }
    private boolean Find_Edge(ArrayList<MyEdge> Edge_List,MyEdge e){
        for(MyEdge e2:Edge_List){
            if(e2.Edge_ID==e.Edge_ID){
                return true;
            }
        }
        return false;
    }
    private boolean Find_Node(ArrayList<MyNode> Node_List,MyNode n){
        for(MyNode n2:Node_List){
            if(n2.Node_Num==n.Node_Num){
                return true;
            }
        }
        return false;
    }
    private MyNode Find_Node2(ArrayList<MyNode> Node_List,MyNode n){
        for(MyNode n2:Node_List){
            if(n2.Node_Num==n.Node_Num){
                return n2;
            }
        }
        return null;
    }
}
