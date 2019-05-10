package Placement;

import Input.MyEdge;
import Input.MyNode;
import Input.Value;
import SFC.MySFC;
import SFC.MyVNF;
import edu.uci.ics.jung.graph.Graph;

import java.util.*;

public class Algorithm_Based_GAP_back extends Value {
    public void Placement_Algo1(Graph<MyNode, MyEdge> G,ArrayList<MySFC> S,Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> P,int Q){
        Value.cost_node = 0;
        Map<MyNode,Integer> r_n2 = new HashMap<>();
        String feas = "yes";
        /**SFCのソート*/
        Collections.sort(S, new Comparator<MySFC>() {
            @Override
            public int compare(MySFC o1, MySFC o2) {
                return o1.Demand_Link_Resource>o2.Demand_Link_Resource ? -1:1;
            }
        });
        /**残容量リストの作成*/
        for(MyNode n:G.getVertices()) r_n2.put(find_node(n),Value.r_n.get(n));
        /**配置開始*/
        whole:for(MySFC s:S){
            for(int i=0;i<Q;i++){
                ArrayList<MyNode> V = new ArrayList<>(P.get(s).get(i).getVertices());
                ArrayList<MyVNF> U = s.VNF;
                Collections.sort(U, new Comparator<MyVNF>() {
                    @Override
                    public int compare(MyVNF o1, MyVNF o2) {
                        return o1.VNF_id>o2.VNF_id ? -1:1;
                    }
                });
                feas = "yes";
                    long d2 = 999999999;
                    long d =0;
                    for(MyVNF f:U){
                        /**該当VNFを配置できるノードの選別*/
                        ArrayList<MyNode> Fk = selection_node(V,r_n2,f);
                        if(Fk.size()==0){
                            feas = "no";
                            break whole;
                        }
                        else{
                            /**選別されたノードの中から最小コストのノードを選択する*/
                            Collections.sort(Fk, new Comparator<MyNode>() {
                                        @Override
                                        public int compare(MyNode o1, MyNode o2) {
                                            return Value.c_n.get(find_node(o1)) < Value.c_n.get(find_node(o2)) ? -1 : 1;
                                        }
                                    });
                            MyNode min_node = Fk.get(0);
                            if(Fk.size()-1==0) d = -999999999;
                            else{
                                d = c_n.get(find_node(min_node))-c_n.get(find_node(Fk.get(1)));
                                if(d<d2) d2 = d;
                                if(feas=="yes"){
                                    Value.cost_node+=f.cap_VNF*c_n.get(find_node(min_node));
                                    r_n2.replace(find_node(min_node),r_n2.get(find_node(min_node))-f.cap_VNF);
                                    for(int j=V.size()-1;j>=0;j--){
                                        if(V.get(j).Node_Num==min_node.Node_Num) break;
                                        else V.remove(j);
                                    }
                                }
                            }
                        }
                    }
            }
        }
    }
    public ArrayList<MyNode> selection_node(ArrayList<MyNode> V,Map<MyNode,Integer> r_n2,MyVNF f){
        ArrayList<MyNode> Fk = new ArrayList<>();
        for(MyNode n:V){
            MyNode n2 = find_node(n);
            if(f.cap_VNF<=r_n2.get(n2)) Fk.add(n);
        }
        return Fk;
    }
}
