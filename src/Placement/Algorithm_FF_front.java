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
    class result extends Result{}
        public void Placement_FF_front(Graph<MyNode, MyEdge> G, ArrayList<MySFC> S, Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> P, int Q,int num){
            Value.cost_node = 0;
            Map<MyNode,Integer> r_n2 = new HashMap<>();
            /**SFC集合をr_sに基づいて降順にソートする*/
            Collections.sort(S, new Comparator<MySFC>() {
                @Override
                public int compare(MySFC o1, MySFC o2) {
                    return o1.Demand_Link_Resource>o2.Demand_Link_Resource ? -1:1;
                }
            });
            /**残容量リストの作成*/
            for(MyNode n:G.getVertices()) r_n2.put(find_node(n),Value.r_n.get(n));
            /**何回目の配置なのか出力*/
            result rw = new result();
            rw.placement_writer_times(num,S.size());
            /**配置開始*/
            whole:for(MySFC s:S){
                for(int i=0;i<Q;i++){
                    /**配置候補となるノードの算出（パス上のノードを算出）*/
                    ArrayList<MyNode> V = new ArrayList<>(P.get(s).get(i).getVertices());
                    ArrayList<MyVNF> U = s.VNF;
                    /**始点から順番にノードをを選択し、配置する*/
                    ArrayList<MyNode> sn = (ArrayList)G.getNeighbors(find_node(s.source));
                    MyNode node = sn.get(0);
                    V.remove(find_node(s.source));
                    V.remove(find_node(node));
                    Map<MyVNF,MyNode> List = new HashMap<>();
                    for(MyVNF f:U){
                        if(find_node(node)==find_node(s.sink)){
                            Value.cost_node=0;
                            break;
                        }
                        while(find_node(node)!=find_node(s.sink)){
                            /**ノード容量の確認*/
                            /**容量があれば配置、なければ隣のノードに移動*/
                            if(r_n2.get(node)-f.cap_VNF>=0){
                                Value.cost_node+=f.cap_VNF*c_n.get(node);
                                r_n2.replace(find_node(node),r_n2.get(find_node(node))-f.cap_VNF);
                                List.put(f,node);
                                break;
                            }
                            else{
                                /**次のノードに移動する*/
                                ArrayList<MyNode> neighbor_list = (ArrayList)G.getNeighbors(node);
                                for(MyNode n:neighbor_list) if(V.contains(n)!=true) node = find_node(n);
                                V.remove(find_node(node));
                            }
                        }
                    }
                    /**結果の出力*/
                    rw.placement_writer(P.get(s).get(i),r_n2,List,U,s.SFC_num,i,S.size());
                }
            }
        }
}

