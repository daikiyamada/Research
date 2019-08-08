package Input;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import java.util.*;
import Parameter.Parameter;
public class MyNode_Maker  extends Value{
    public SparseGraph<MyNode,MyEdge> MyNode_Maker(int cost_type){
        Random rnd = new Random();
        Parameter par = new Parameter();
        SparseGraph<MyNode, MyEdge> Graph = new SparseGraph<MyNode, MyEdge>();
        /**グラフのノード形成*/
        ArrayList<Integer> num_list = new ArrayList<>();
        /**ノード番号の挿入*/
        for(int a=0;a<par.node_num;a++) num_list.add(a+1);
        /**ターミナルノードの構成*/
        for(int a=0;a<par.terminalnode;a++){
            Collections.shuffle(num_list);
            MyNode t = new MyNode("t",num_list.get(0));
            node_resource(t,-1);
            node_cost(t,0);
            Graph.addVertex(t);
            num_list.remove(0);
        }
        /**サービスノードの構成*/
        for(int a=0;a<par.servicenode;a++){
            /**サービスノードの容量決定（最低でも３０個配置できるように設定）*/
            /**ランダム設定*/
            int r = rnd.nextInt(10);
            int resource = par.node_resource+r*100;
            int cost =0;
            if(cost_type==1){
                /**ランダム設定*/
                cost = rnd.nextInt(par.node_cost_max-par.node_cost_min+1)+par.node_cost_min;
            }
            else if(cost_type==2){
                /**キャパシティに比例して設定*/
                cost = resource/100;
            }
            else if(cost_type==3){
                /**キャパシティに反比例して設定*/
                cost = 11-resource/100;
            }
            MyNode s = new MyNode("s",num_list.get(0));
            node_resource(s,resource);
            node_cost(s,cost);
            num_list.remove(0);
            Graph.addVertex(s);
        }
        return Graph;
    }
}
