    package Placement;
import Input.MyEdge;
import Input.MyNode;
import Input.Value;
import SFC.MySFC;
import java.util.*;
import Parameter.Parameter;
import SFC.MyVNF;
import edu.uci.ics.jung.graph.Graph;
import Output.Result;
public class Algorithm_Based_GAP extends Value {
    class result extends Result{}
    public void Placement_Algo(Graph<MyNode, MyEdge> G,ArrayList<MySFC> S,Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> P,int Q,int num,String gn,String an){
        Value.cost_node = 0;
        Map<MyNode,Integer> r_n2 = new HashMap<>();
        String feas = "yes";
        /**残容量リストの作成*/
        for(MyNode n:G.getVertices()) r_n2.put(find_node(n),Value.r_n.get(n));
        /**何回目の配置なのか出力*/
        result rw = new result();
        rw.placement_writer_times(num,S.size(),Q,gn,an);
        /**配置開始*/
        whole:for(MySFC s:S){
            for(int i=0;i<Q+1;i++){
                ArrayList<MyNode> V = new ArrayList<>(P.get(s).get(i).getVertices());
                ArrayList<MyVNF> U = s.VNF;
                Map<MyVNF,MyNode> List = new HashMap<>();
                feas = "yes";
                    for(MyVNF f:U){
                        /**該当VNFを配置できるノードの選別*/
                        ArrayList<MyNode> Fk = new ArrayList<>();
                        for(MyNode n:V){
                            MyNode n2 = find_node(n);
                            if(r_n2.get(n2)-f.cap_VNF>=0) Fk.add(n);
                        }
                        if(Fk.size()==0){
                            feas = "no";
                            break whole;
                        }
                        else {
                            /**選別されたノードの中から最小コストのノードを選択する*/
                            /**最小値を見つける*/
                            int min = 100;
                            MyNode min_node = Fk.get(0);
                            for(MyNode v:Fk){
                                if(min>Value.c_n.get(find_node(v))){
                                    min_node = v;
                                    min = Value.c_n.get(find_node(v));
                                }
                            }
                            /**容量が残っているノードに対しての配置*/
                            List.put(f, min_node);
                            /**コストの計算*/
                            Value.cost_node += f.cap_VNF * c_n.get(find_node(min_node));
                            /**容量リストの書き換え*/
                            r_n2.replace(find_node(min_node), r_n2.get(find_node(min_node)) - f.cap_VNF);
                            /**該当ノードから始点までのノードを削除*/
                            for (int j = 0; j < V.size(); j++) {
                                if (V.get(j).Node_Num == min_node.Node_Num) break;
                                else V.remove(j);
                            }
                        }
                    }
                    /**結果の出力*/
                    rw.placement_writer(List,U,s.SFC_num,i,S.size(),Q,gn,an);
            }
        }
        if(feas=="no") Value.cost_node=0;
        rw.each_placement_writer(feas,num,S.size(),Q,gn,an);
    }
}
