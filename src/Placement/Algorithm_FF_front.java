package Placement;
import Input.MyEdge;
import Input.MyNode;
import Input.Value;
import SFC.MySFC;
import java.util.*;
import SFC.MyVNF;
import edu.uci.ics.jung.graph.Graph;
import Output.Result;
public class Algorithm_FF_front extends Value{
        public void Placement_FF_front(Graph<MyNode, MyEdge> G, ArrayList<MySFC> S, Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> P, int Q){
            Value.cost_node = 0;
            Map<MyNode,Integer> r_n2 = new HashMap<>();
            /**残容量リストの作成(グラフGのインスタンスを用いている）)*/
            for(MyNode n:G.getVertices()) r_n2.put(find_node(n),Value.r_n.get(n));
            /**配置開始*/
            whole:for(MySFC s:S){
                for(int i=0;i<Q+1;i++){
                    /**配置候補となるノードの算出（パス上のノードを算出）*/
                    Graph<MyNode,MyEdge> p = P.get(s).get(i);
                    ArrayList<MyNode> V = new ArrayList<>(p.getVertices());
                    /**始点から順番にノードをを選択し、配置する*/
                    MyNode node = find_node2(V,s.source);
                    for(MyVNF f:s.VNF){
                        while(true){
                            if(V.size()==0){
                                Value.cost_node=0;
                                break whole;
                            }
                            V.remove(node);
                            MyNode now = find_node(node);
                            /**ノード容量の確認*/
                            /**容量があれば配置、なければ隣のノードに移動*/
                            if(r_n2.get(now)-f.cap_VNF>=0){
                                Value.cost_node+=f.cap_VNF*c_n.get(now);
                                r_n2.replace(now,r_n2.get(now)-f.cap_VNF);
                                break;
                            }
                            else{
                                /**次のノードに移動する*/
                                Collection<MyNode> neighbor_list2 = p.getNeighbors(node);
                                ArrayList<MyNode> neighbor_list = new ArrayList<MyNode>(neighbor_list2);
                                for(MyNode n:neighbor_list) if(V.contains(n)) node = n;
                            }
                        }
                    }
                }
            }
        }
}

