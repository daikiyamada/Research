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
        public void Placement_FF_front(Graph<MyNode, MyEdge> G, ArrayList<MySFC> S, Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> P, int Q,int num,String gn,String an){
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
            //result rw = new result();
           // rw.placement_writer_times(num,S.size(),Q,gn,an);
            /**配置開始*/
            whole:for(MySFC s:S){
                for(int i=0;i<Q+1;i++){
                    /**配置候補となるノードの算出（パス上のノードを算出）*/
                    Graph<MyNode,MyEdge> p = P.get(s).get(i);
                    ArrayList<MyNode> V = new ArrayList<>(p.getVertices());
                    ArrayList<MyVNF> U = s.VNF;
                    /**始点から順番にノードをを選択し、配置する*/
                    MyNode source = find_node2(V,s.source);
                    Collection<MyNode> sn2 = p.getNeighbors(source);
                    ArrayList<MyNode> sn = new ArrayList<MyNode>(sn2);
                    MyNode node = sn.get(0);
                    V.remove(find_node2(V,s.source));
                    V.remove(node);
                    Map<MyVNF,MyNode> List = new HashMap<>();
                    for(MyVNF f:U){
                        while(true){
                            if(node.Node_Num==s.sink.Node_Num) break whole;
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
                                Collection<MyNode> neighbor_list2 = p.getNeighbors(node);
                                ArrayList<MyNode> neighbor_list = new ArrayList<MyNode>(neighbor_list2);
                                for(MyNode n:neighbor_list) if(V.contains(n)==true) node = n;
                                V.remove(node);
                            }
                        }
                    }
                    /**結果の出力*/
                   // rw.placement_writer(List,U,s.SFC_num,i,S.size(),Q,gn,an);
                }
            }
        }
}

