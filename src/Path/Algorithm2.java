package Path;
import java.util.*;
import Input.MyNode;
import Input.MyEdge;
import Output.Visualization;
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
        Value val = new Value();
        Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> Path_set = new HashMap<>();
        /**残容量リスト・修正用コストの作成*/
        Map<MyEdge,Integer> r_e2 = new HashMap<>();
        for(MyEdge e:G.getEdges())  r_e2.put(e,Value.r_e.get(e));
        /**SFC集合をr_sに基づいて降順にソートする*/
        S.sort(new MyComparator());
        Set<MyEdge> Edge_Set = new HashSet<>();
        /**KNDSPの探索*/
        whole:for(MySFC s:S){
            /**修正後の辺コスト*/
            ArrayList<Graph<MyNode, MyEdge>> Path = new ArrayList<>();
            Graph<MyNode, MyEdge> G2 = Clone_Graph(G);
            ArrayList<MyEdge> Edge_List = new ArrayList<MyEdge>(G2.getEdges());
            /**辺の容量確認*/
            for (MyEdge e : Edge_List)
                if (r_e2.get(val.find_edge(e)) - s.Demand_Link_Resource < 0) G2.removeEdge(e);
            /**始点・終点に辺が存在していた場合削除*/
            MyEdge e10 = G2.findEdge(find_original_Node(G2, s.source), find_original_Node(G2, s.sink));
            if (e10 != null) G2.removeEdge(e10);
            /**最短経路の算出*/
            MyNode source = find_original_Node(G2, s.source);
            MyNode sink = find_original_Node(G2, s.sink);
            while(true) {
                MyTransformer list = new MyTransformer();
                DijkstraShortestPath<MyNode, MyEdge> ds = new DijkstraShortestPath<>(G2, list);
                List<MyEdge> Edge_List2 = ds.getPath(source, sink);
                Graph<MyNode, MyEdge> p = Dijkstra_Path(G2, Edge_List2, source);
                if (p == null && p.getEdgeCount() == 0) {
                    Value.cost_link = 0;
                    System.out.println("error");
                    break whole;
                }
                Path.add(p);
                for (int j = 1; j <= R; j++) {
                    Map<MyEdge, Integer> c_e2 = new HashMap<>();
                    for (MyEdge e : G.getEdges()) c_e2.put(e, Value.c_e.get(e));
                    Graph<MyNode, MyEdge> G3 = Algorithm2(G2, Path, source, c_e2);
                    Graph<MyNode, MyEdge> G4 = Algorithm3(G3, Path, source, sink, c_e2);
                    /**c_e2をG4の辺で作成する*/
                    Map<MyEdge, Integer> c_e3 = new HashMap<>();
                    for (MyEdge e : c_e2.keySet()) c_e3.put(find_Edge(G4, e), c_e2.get(e));
                    /**最短距離パスのアルゴリズムの実装*/
                    Graph<MyNode, MyEdge> p2 = Modified_Dijkstra(G4, source, sink, c_e3);
                    if (p2 == null) {
                        Value.cost_link = 0;
                        System.out.println("error");
                        break whole;
                    }
                    /**パスの修正*/
                    Graph<MyNode, MyEdge> p3 = Restoration_Path(G4, p2, c_e3);
                    Path.add(p3);
                    /**重複辺の削除と結合*/
                    Path = Modified_Path(Path, s.source, s.sink);
                    c_e3.clear();
                }
                /**パスのチェック*/
                boolean assess = true;
                for (Graph<MyNode, MyEdge> pp : Path) {
                    int num = 0;
                    for (MyNode n : pp.getVertices()) if (n.Node_ID.equals("s")) num++;
                    if (num == 0) {
                        assess = false;
                        break;
                    }
                }

                if (assess == true) {
                    /**容量を変更する*/
                    for (Graph<MyNode, MyEdge> each : Path) {
                        for (MyEdge e : each.getEdges()) {
                            MyEdge e2 = find_Edge(G, e);
                            Edge_Set.add(find_edge(e));
                            r_e2.replace(e2, r_e2.get(e2) - s.Demand_Link_Resource);
                            Value.cost_link += s.Demand_Link_Resource * Value.c_e.get(find_edge(e));
                        }
                    }
                    Path_set.put(s, Path);
                    break;
                } else {
                    int max_cost = 0;
                    MyEdge max_edge = null;
                    /**ターミナルノード１個削除*/
                    for (Graph<MyNode, MyEdge> each : Path) {
                        for (MyEdge e : each.getEdges()) {
                            if (Value.c_e.get(find_edge(e)) > max_cost) {
                                max_cost = Value.c_e.get(find_edge(e));
                                max_edge = e;
                            }
                        }
                    }
                    G2.removeEdge(find_Edge(G2, max_edge));
                    Path.clear();
                }
            }
        }
        if(Value.cost_link!=0) val.Edge_Utilization(G,Edge_Set,r_e2);
        return Path_set;
    }

    /**comparator の定義*/
    private class MyComparator implements Comparator<MySFC>{
        public int compare(MySFC o1,MySFC o2){
            return o1.Demand_Link_Resource>=o2.Demand_Link_Resource ? -1:1;
        }
    }
    /**Transformerの定義*/
    private class MyTransformer implements Transformer<MyEdge,Number> {
        public Number transform(MyEdge myEdge) {
            return Value.c_e.get(find_edge(myEdge));
        }
    }
    /**グラフ修正*/
    public  Graph<MyNode,MyEdge> Algorithm2(Graph<MyNode,MyEdge> graph,ArrayList<Graph<MyNode,MyEdge>> Path,MyNode s,Map<MyEdge,Integer> c_e2){
        /**負の辺に置き換え*/
        Graph<MyNode,MyEdge> graph2 = Clone_Graph(graph);
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
                c_e2.replace(find_edge(e),-c_e2.get(find_edge(e)));
            }
        }
        return graph2;
    }
    public Graph<MyNode,MyEdge> Algorithm3(Graph<MyNode,MyEdge> graph,ArrayList<Graph<MyNode,MyEdge>> Path ,MyNode s,MyNode t,Map<MyEdge,Integer> c_e2){
        /**additional nodeの作成とoriginal nodeとadditonal nodeの辺作成*/
        Graph<MyNode,MyEdge> graph2 = Clone_Graph(graph);
        ArrayList<MyNode> Path_Edge_List = new ArrayList<>();
        Map<MyEdge,Pair<MyNode>> Edge_List = new HashMap<>();
        for(Graph<MyNode,MyEdge> p:Path){
            for(MyNode n:p.getVertices()){
                MyNode n2 = new MyNode(n.Node_ID,n.Node_Num);
                Path_Edge_List.add(n2);
            }
        }
        int between_num = 999999999;
        for(Graph<MyNode,MyEdge> p:Path) {
            for (MyNode n : p.getVertices()) {
                MyNode now = find_original_Node(graph, n);
                MyNode now2 = find_original_Node(graph2, n);
                if (now.Node_Num != s.Node_Num && now.Node_Num != t.Node_Num) {
                    /**additional nodeの追加*/
                    MyNode n2 = new MyNode(now2.Node_ID, -now2.Node_Num);
                    graph2.addVertex(n2);
                    /**addtionalノードへの置き換え*/
                    for (MyEdge e : graph.getInEdges(now)) {
                        if (graph.getEdgeType(e) == EdgeType.DIRECTED) {
                            MyNode n3 = graph.getOpposite(now, e);
                            graph2.removeEdge(find_Edge(graph2, e));
                            MyEdge e3 = new MyEdge(e.Edge_ID);
                            graph2.addEdge(e3, find_original_Node(graph2, n3), n2, EdgeType.DIRECTED);
                        }
                    }
                    /**generating the edge between additonal node and original node*/
                    MyEdge e2 = new MyEdge(between_num);
                    graph2.addEdge(e2, n2, now2, EdgeType.DIRECTED);
                    c_e2.put(e2, 0);
                    between_num--;
                    /**In the case of undirected edge*/
                    Collection<MyEdge> edge_list = graph2.getIncidentEdges(now2);
                    for (MyEdge e : edge_list) {
                        MyNode n4 = graph2.getOpposite(now, e);
                        /**どのパスにも属していない頂点の場合*/
                        if (graph2.getEdgeType(e) == EdgeType.UNDIRECTED && !Find_Node(Path_Edge_List, n4)) {
                            MyEdge e3 = new MyEdge(e.Edge_ID);
                            MyEdge e4 = new MyEdge(-e.Edge_ID);
                            c_e2.put(e4, c_e2.get(find_edge(e)));
                            c_e2.put(e3, c_e2.get(find_edge(e)));
                            graph2.addEdge(e3, n2, n4, EdgeType.DIRECTED);
                            graph2.addEdge(e4, n4, now, EdgeType.DIRECTED);
                            c_e2.remove(find_edge(e));
                            graph2.removeEdge(e);

                        } else if (graph2.getEdgeType(e) == EdgeType.UNDIRECTED && Find_Node(Path_Edge_List, n4)){
                            Edge_List.put(e, graph2.getEndpoints(e));
                        }
                    }
                }
            }
        }
        for(MyEdge e:Edge_List.keySet()){
            /**グラフに存在する辺の削除*/
            Pair<MyNode> list = Edge_List.get(e);
            MyEdge e2 = new MyEdge(e.Edge_ID);
            MyEdge e3 = new MyEdge(-e.Edge_ID);
            MyNode original1,additional1,original2,additional2;
            /**originalとadditonalのノードに分ける*/
            original1 = list.getFirst();
            additional1 = new MyNode(list.getFirst().Node_ID,-list.getFirst().Node_Num);
            original2 = list.getSecond();
            additional2 = new MyNode(list.getSecond().Node_ID,-list.getSecond().Node_Num);
            /**Connecting the directed edge from the additional node to original node*/
            graph2.addEdge(e3,additional2,original1,EdgeType.DIRECTED);
            c_e2.put(e3,c_e2.get(find_edge(e)));
            /**Connecting the directed edge from the original node to additional node*/
            graph2.addEdge(e2,additional1,original2,EdgeType.DIRECTED);
            c_e2.put(e2,c_e2.get(find_edge(e)));
            c_e2.remove(find_edge(e));
            graph2.removeEdge(e);
        }
        return graph2;
    }
    public Graph<MyNode,MyEdge> Modified_Dijkstra(Graph<MyNode,MyEdge> graph,MyNode s,MyNode t,Map<MyEdge,Integer> c_e2){
        Graph<MyNode,MyEdge> p = new SparseGraph<MyNode,MyEdge>();
        Graph<MyNode,MyEdge> graph2 = Clone_Graph(graph);
        /**ダイクストラの開始*/
        /**距離リスト*/
        Map<MyNode,Integer> d = new HashMap<>();
        /**ノードリスト*/
        ArrayList<MyNode> S = new ArrayList<>();
        /**１つ前のノードを表すリスト*/
        Map<MyNode,MyNode> P = new HashMap<>();
            /**step1 初期設定*/
            /**始点の距離を設定*/
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
            /**step2*/
            /**Sから最短距離となるノードを見つける*/
            int dis =999999999;
            MyNode min_node = null;
            for(MyNode n:S){
                if(d.get(n)<dis) {
                    dis = d.get(n);
                    min_node = n;
                }
            }
            if(min_node==null) return null;
            /**集合Sから取り除く*/
            S.remove(min_node);
            /**終点の場合、終了*/
            if(min_node.Node_Num==t.Node_Num) break;
            /**step 3*/
            /**min_nodeのsuccessor*/
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
        /**求めたパスの構成*/
        MyNode sink = find_original_Node(graph2,t);
        p.addVertex(sink);
        while(true){
            /**predecessorを求める*/
            MyNode n = P.get(sink);
            /**辺を追加する*/
            MyEdge e = graph2.findEdge(find_original_Node(graph2,n),sink);
            p.addVertex(n);
            if(graph2.getEdgeType(e)==EdgeType.DIRECTED) p.addEdge(e,n,sink,EdgeType.DIRECTED);
            else p.addEdge(e,n,sink,EdgeType.UNDIRECTED);
            /**predecessorが始点の場合、終了*/
            if(n.Node_Num==s.Node_Num) break;
            /**sinkにnを代入する*/
            sink = n;
        }
        return p;
    }
    /**パスの生成*/
    public Graph<MyNode,MyEdge> Restoration_Path(Graph<MyNode,MyEdge> graph2,Graph<MyNode,MyEdge> p,Map<MyEdge,Integer> c_e2){
        /**頂点管理用のリスト作成*/
        ArrayList<MyNode> Node_List = new ArrayList<>();
        for(MyNode n:graph2.getVertices()){
            if(!Find_Node(Node_List,n)){
                MyNode n2 = new MyNode(n.Node_ID,n.Node_Num);
                Node_List.add(n2);
            }
        }
            /**各パスの頂点、辺をリスト化*/
            ArrayList<MyEdge> Edge_List = new ArrayList<MyEdge>(p.getEdges());
            Graph<MyNode,MyEdge> mp = new SparseGraph<>();
                /**必要な辺の追加*/
                for(MyEdge e:Edge_List){
                    /**無向辺の場合*/
                    if(p.getEdgeType(e)==EdgeType.UNDIRECTED){
                        Pair<MyNode> pair_node = p.getEndpoints(e);
                        MyNode source = Find_Node2(Node_List,pair_node.getFirst());
                        MyNode sink = Find_Node2(Node_List,pair_node.getSecond());
                        mp.addVertex(source);
                        mp.addVertex(sink);
                        MyEdge e2 = new MyEdge(e.Edge_ID);
                        mp.addEdge(e2,source,sink,EdgeType.UNDIRECTED);
                    }
                    /**有向辺の場合*/
                    else if(p.getEdgeType(e)==EdgeType.DIRECTED) {
                        /**コストが負の場合、sourceノードを追加*/
                        if(c_e2.get(find_Edge(graph2,e))<0){
                            MyNode source = Find_Node2(Node_List,p.getSource(e));
                            MyNode sink = Find_Node2(Node_List,p.getDest(e));
                            MyNode additional = Exchange_Node(Node_List,sink);
                            mp.addVertex(source);
                            mp.addVertex(additional);
                            MyEdge e2 = new MyEdge(e.Edge_ID);
                           mp.addEdge(e2,source,additional,EdgeType.UNDIRECTED);
                        }
                        /**コストが正の場合、無向辺の場合同様（バグあり）*/
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

                            else{
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
    private ArrayList<Graph<MyNode,MyEdge>> Modified_Path(ArrayList<Graph<MyNode,MyEdge>> Path,MyNode s,MyNode t){
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
            Path_Set.add(p);
            for(MyNode n:p.getVertices()) if(n.Node_Num!=s.Node_Num&&n.Node_Num!=t.Node_Num) G.removeVertex(find_original_Node(G,n));
        }
        return Path_Set;
    }
    private Graph<MyNode,MyEdge> Dijkstra_Path(Graph<MyNode,MyEdge> G,List<MyEdge> p_list,MyNode s){
        Graph<MyNode,MyEdge> p = new SparseGraph<>();
        /**リンクからパスを選択する*/
        for(MyEdge e:p_list){
            Pair<MyNode> node = G.getEndpoints(e);
            for(MyNode n:node){
                if(!p.containsVertex(n)){
                    p.addVertex(n);
                }
            }
            p.addEdge(e,node.getFirst(),node.getSecond(),EdgeType.UNDIRECTED);
        }
        return p;
    }
    public MyEdge find_Edge(Graph<MyNode,MyEdge> G,MyEdge e){
        MyEdge e2 = null;
        for(MyEdge e3:G.getEdges()){
            if(e.Edge_ID==e3.Edge_ID) e2= e3;
        }
        return e2;
    }
    public MyNode find_original_Node(Graph<MyNode,MyEdge> G,MyNode n){
        MyNode n2 = null;
        for(MyNode n3:G.getVertices()){
            if(n.Node_Num==n3.Node_Num) return n3;
        }
        return n2;
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
