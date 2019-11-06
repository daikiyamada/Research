    package Placement;
import Input.MyEdge;
import Input.MyNode;
import Input.Value;
import Path.Algorithm_Based_MECF;
import SFC.MySFC;
import java.util.*;
import SFC.MyVNF;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.graph.Graph;

    public class Algorithm_Based_GAP extends Value {
    public void Placement_Algo(Graph<MyNode, MyEdge> G,ArrayList<MySFC> S,Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> P,int Q){
        Map<MyNode,Integer> r_n2 = new HashMap<>();
        /**残容量リストの作成*/
        for(MyNode n:G.getVertices()) r_n2.put(find_node(n),Value.r_n.get(n));
        /**配置開始*/
        whole:for(MySFC s:S){
            for(int i=0;i<Q+1;i++){
                ArrayList<MyNode> V = new ArrayList<>(P.get(s).get(i).getVertices());
                Graph<MyNode,MyEdge> path = P.get(s).get(i);
                /**始点から何ホップか計算する*/
                    for(MyVNF f:s.VNF){
                        /**該当VNFを配置できるノードの選別*/
                        ArrayList<MyNode> Fk = new ArrayList<>();
                        for(MyNode n:V){
                            MyNode n2 = find_node(n);
                            if(r_n2.get(n2)-f.cap_VNF>=0) Fk.add(n);
                        }
                        if(Fk.size()==0){
                            Value.cost_node = 0;
                            break whole;
                        }
                        else {
                            /**選別されたノードの中から最小コストのノードを選択する*/
                            /**最小値を見つける*/
                            int min = 100;
                            MyNode min_node = Fk.get(0);
                            for(MyNode v:Fk){
                                MyNode now = find_node(v);
                                if(min>Value.c_n.get(now)){
                                    min_node = v;
                                    min = Value.c_n.get(now);
                                }
                            }
                            /**コストの計算*/
                            MyNode now_min = find_node(min_node);
                            Value.cost_node += f.cap_VNF * c_n.get(now_min);
                            /**容量リストの書き換え*/
                            r_n2.replace(now_min, r_n2.get(now_min) - f.cap_VNF);
                            /**該当ノードから始点までのノードを削除*/
                            /**リソース単価を基にした最小重みパスの計算（ダイクストラ)*/
                            DijkstraDistance<MyNode,MyEdge> dd2 = new DijkstraDistance<>(path);
                            Algorithm_Based_MECF MECF = new Algorithm_Based_MECF();
                            MyNode source = MECF.find_Graph(path,s.source);
                            int hop_num = dd2.getDistance(source,min_node).intValue();
                            for(MyNode v:path.getVertices()){
                                int hop_num2 = dd2.getDistance(source,v).intValue();
                                if(hop_num2<hop_num) V.remove(v);
                            }
                        }
                    }
            }
        }
    }
}
