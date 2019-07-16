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
        public int Placement_FF_front(Graph<MyNode, MyEdge> G, ArrayList<MySFC> S, Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> P, int Q){
            Value.cost_node = 0;
            int used_node_num=0;
            Map<MyNode,Integer> r_n2 = new HashMap<>();
            Value value = new Value();
            /**SFC集合をr_sに基づいて降順にソートする*/
            Collections.sort(S, new Comparator<MySFC>() {
                @Override
                public int compare(MySFC o1, MySFC o2) {
                    return o1.Demand_Link_Resource>o2.Demand_Link_Resource ? -1:1;
                }
            });
            Set<MyNode> Node_List = new HashSet<>();
            /**残容量リストの作成*/
            for(MyNode n:G.getVertices()) r_n2.put(find_node(n),Value.r_n.get(n));
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
                    V.remove(find_node2(V,s.source));
                    MyNode node = sn.get(0);
                    Map<MyVNF,MyNode> List = new HashMap<>();
                    for(MyVNF f:U){
                        while(true){
                            V.remove(node);
                            if(node.Node_Num==s.sink.Node_Num){
                                Value.cost_node=0;
                                break whole;
                            }
                            /**ノード容量の確認*/
                            /**容量があれば配置、なければ隣のノードに移動*/
                            if(r_n2.get(node)-f.cap_VNF>=0){
                                Value.cost_node+=f.cap_VNF*c_n.get(node);
                                r_n2.replace(find_node(node),r_n2.get(find_node(node))-f.cap_VNF);
                                List.put(f,node);
                                Node_List.add(node);
                                used_node_num++;
                                break;
                            }
                            else{
                                /**次のノードに移動する*/
                                Collection<MyNode> neighbor_list2 = p.getNeighbors(node);
                                ArrayList<MyNode> neighbor_list = new ArrayList<MyNode>(neighbor_list2);
                                for(MyNode n:neighbor_list) if(V.contains(n)==true) node = n;
                            }
                        }
                    }
                }
            }
            value.node_Utilization(G,Node_List,r_n2);
            return used_node_num;
        }
}

