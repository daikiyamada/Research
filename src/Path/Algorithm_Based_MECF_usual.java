package Path;

import Input.MyEdge;
import Input.MyNode;
import java.util.*;

import Input.*;
import Output.Result;
import SFC.MySFC;
import SFC.MyVNF;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import Placement.Placement_Maker;
import edu.uci.ics.jung.graph.util.Pair;
import org.apache.commons.collections15.Transformer;
public class Algorithm_Based_MECF_usual extends Value{
    class OPT extends Placement_Maker {}
    class writer extends Result {}
    public Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> Routing_Algo(Graph<MyNode, MyEdge> G,ArrayList<MySFC> S,int num,int fn,String gn,String an){
        /**クラスの宣言*/
        OPT opt = new OPT();
        /**各変数の初期化*/
        Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> P = new HashMap<>();
        Map<MyEdge,Integer> r_e2 = new HashMap<>();
        /**残容量リストの作成*/
        for(MyEdge e:G.getEdges()) r_e2.put(e,Value.r_e.get(e));
        /**パスの選定*/
        whole:for(MySFC s:S){
            /**グラフのコピー（ディープコピー）*/
            Graph<MyNode,MyEdge> G2 = opt.Clone_Graph(G);
            ArrayList<Graph<MyNode,MyEdge>> graph = new ArrayList<>();
            for(int i =0;i<fn+1;i++){
                while(true){
                    /**リソース単価を基にした最小重みパスの計算（ダイクストラ)*/
                    Transformer<MyEdge,Number> list = new Transformer<MyEdge, Number>() {
                        @Override
                        public Number transform(MyEdge myEdge) {
                            return Value.c_e.get(find_edge(myEdge));
                        }
                    };
                    Graph<MyNode,MyEdge> p = new UndirectedSparseGraph<>();
                    DijkstraDistance dd = new DijkstraDistance(G2);
                    MyNode source = find_Graph(G2,s.source);
                    MyNode sink = find_Graph(G2,s.sink);
                    if(dd.getDistance(source,sink)!=null) {
                        List<MyEdge> p_list = new ArrayList<>();
                        DijkstraShortestPath ds = new DijkstraShortestPath(G2,list);
                        p_list = ds.getPath(source, sink);
                        p = Dijkstra_Path(G2, p_list);
                    }
                    else p=null;
                    /**パスがない場合の対処法*/
                    if(p==null) {
                        /**グラフの可視化*/
                        writer rw = new writer();
                        //rw.graph_writer(G,Value.r_e,num,S.size(),fn,gn,an);
                        /*for(int a=0;a<P.size();a++){
                            for(int b=0;b<P.get(S.get(a)).size();b++){
                                ArrayList<MyEdge> p_list2 = new ArrayList<>(P.get(S.get(a)).get(b).getEdges());
                                rw.path_writer(G,p_list2,S.get(a).SFC_num,i,S.size(),fn,gn,an);
                            }
                        }
                        for(int a=0;a<i;a++){
                            ArrayList<MyEdge> p_list2 = new ArrayList<>(graph.get(a).getEdges());
                            rw.path_writer(G,p_list2,s.SFC_num,i,S.size(),fn,gn,an);
                        }*/
                        Value.cost_link=0;
                        break whole;
                    }
                    /**r:=minを出す*/
                    int r = s.Demand_Link_Resource;
                    ArrayList<MyEdge> min_edge_list = new ArrayList<>();
                    for(MyEdge e:p.getEdges()){
                        MyEdge e2 = find_edge2(G,e);
                        int cap_edge = r_e2.get(e2);
                        if(cap_edge<s.Demand_Link_Resource){
                            r = cap_edge;
                            min_edge_list.add(e2);
                        }
                    }
                    /**リンクのキャパシティが十分にある場合*/
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
                        break;
                    }
                    else if(r<s.Demand_Link_Resource){
                        /**キャパシティが十分にない場合、その辺を削除する*/
                        G2 = Remover_Edge(G2,min_edge_list);
                    }
                    else break whole;
                }
            }
            P.put(s,graph);
        }
        //        rw.each_path_writer(feas,num,S.size());
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
            if(s.source.Node_Num!=n2.Node_Num && s.sink.Node_Num!=n2.Node_Num) G.removeVertex(n2);
        }
        return G;
    }
    private Graph<MyNode,MyEdge> Remover_Edge(Graph<MyNode,MyEdge> G,ArrayList<MyEdge> min_edge_list){
        for(MyEdge e:min_edge_list) G.removeEdge(e);
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
