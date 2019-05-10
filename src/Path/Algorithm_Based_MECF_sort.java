package Path;

import Input.MyEdge;
import Input.MyNode;
import Input.Value;
import Placement.Placement_Maker;
import SFC.MySFC;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import org.apache.commons.collections15.Transformer;

import java.util.*;

public class Algorithm_Based_MECF_sort extends Value{
    class OPT extends Placement_Maker {}
    public Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> Routing_Algo1(Graph<MyNode, MyEdge> G,ArrayList<MySFC> S,int Q){
        /**クラスの宣言*/
        OPT opt = new OPT();
        /**各変数の初期化*/
        Value.cost_link = 0;
        Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> P = new HashMap<>();
        Map<MyEdge,Integer> r_e2 = new HashMap<>();
        String feas = "yes";
        /**SFC集合をr_sに基づいて昇順にソートする*/
        Collections.sort(S, new Comparator<MySFC>() {
            @Override
            public int compare(MySFC o1, MySFC o2) {
                return o1.Demand_Link_Resource<o2.Demand_Link_Resource ? -1:1;
            }
        });
        /**残容量リストの作成*/
        for(MyEdge e:G.getEdges()) r_e2.put(e,Value.r_e.get(e));
        /**パスの選定*/
        for(MySFC s:S){
            /**グラフのコピー（ディープコピー）*/
            Graph<MyNode,MyEdge> G2 = opt.Clone_Graph(G);
            ArrayList<Graph<MyNode,MyEdge>> graph = new ArrayList<>();
            for(int i =0;i<Q+1;){
                /**リソース単価を基にした最小重みパスの計算（ダイクストラ)*/
                Transformer<MyEdge,Number> list = new Transformer<MyEdge, Number>() {
                    @Override
                    public Number transform(MyEdge myEdge) {
                        return Value.c_e.get(find_edge(myEdge));
                    }
                };
                DijkstraShortestPath ds = new DijkstraShortestPath(G2,list);
                DijkstraDistance dd = new DijkstraDistance(G2);
                List<MyEdge> p_list = new ArrayList<>();
                Graph<MyNode,MyEdge> p = new UndirectedSparseGraph<>();
                MyNode source = find_Graph(G2,s.source);
                MyNode sink = find_Graph(G2,s.sink);
                if(dd.getDistance(source,sink)!=null) {
                    p_list = ds.getPath(source, sink);
                    p = Dijkstra_Path(G2, p_list);
                }
                if(p==null) {
                    feas="no";
                    break;
                }
                /**r:=minを出す*/
                int r = s.Demand_Link_Resource;
                for(MyEdge e:p.getEdges()){
                    MyEdge e2 = find_edge2(G,e);
                    int cap_edge = r_e2.get(e2);
                    if(cap_edge<r) r = cap_edge;
                }
                if(r==s.Demand_Link_Resource){
                    /**パスpの各辺に対して残容量をを変更する*/
                    for(MyEdge e:p.getEdges()){
                        MyEdge e2 = find_edge2(G,e);
                        r_e2.replace(e2,r_e2.get(e2) - s.Demand_Link_Resource);
                    }
                    /**G'からパスに所属する頂点とそれらに接続する辺を取り除く*/
                    G2 = Remover_Graph(G2,p,s);
                    graph.add(p);
                    /**c_linkの算出*/
                    for(MyEdge e:p.getEdges()){
                        MyEdge e2 = find_edge2(G,e);
                        Value.cost_link+=s.Demand_Link_Resource*Value.c_e.get(e2);
                    }
                    i++;
                }
                else if(feas=="no") break;
            }
            if(feas=="no")break;
            else P.put(s,graph);
        }
        return P;
    }
    private Graph<MyNode,MyEdge> Dijkstra_Path(Graph<MyNode,MyEdge> G,List<MyEdge> p_list){
        Graph<MyNode,MyEdge> p = new UndirectedSparseGraph<>();
        /**リンクからパスを選択する*/
        for(MyEdge e:p_list){
            Pair<MyNode> node = G.getEndpoints(e);
            for(MyNode n:node){
                if(p.containsVertex(n)!=true){
                    p.addVertex(n);
                }
            }
            p.addEdge(e,node.getFirst(),node.getSecond());
        }
        return p;
    }
    private Graph<MyNode,MyEdge> Remover_Graph(Graph<MyNode,MyEdge> G,Graph<MyNode,MyEdge> p,MySFC s){
        for(MyNode n:p.getVertices()){
            MyNode n2 = find_Graph(G,n);
//            for(MyEdge e:G.getInEdges(n2)) if(G.containsEdge(e)==true) G.removeEdge(e);
//            for(MyEdge e:G.getOutEdges(n2)) if (G.containsEdge(e)==true) G.removeEdge(e);
            if(s.source.Node_Num!=n2.Node_Num && s.sink.Node_Num!=n2.Node_Num) G.removeVertex(n2);
        }
        return G;
    }
    private MyNode find_Graph(Graph<MyNode,MyEdge> G,MyNode n){
        MyNode n2 = null;
        for(MyNode n3:G.getVertices()){
            if(n.Node_Num==n3.Node_Num) n2= n3;
        }
        return n2;
    }
    private MyEdge find_edge2(Graph<MyNode,MyEdge> G,MyEdge e){
        MyEdge e2 =null;
        for(MyEdge e1:G.getEdges()) if(e1.Edge_ID==e.Edge_ID) e2 = e1;
        return e2;
    }
}
