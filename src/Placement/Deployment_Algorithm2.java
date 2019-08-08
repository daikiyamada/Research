package Placement;

import Input.MyEdge;
import Input.MyNode;
import Path.Algorithm2;
import SFC.MySFC;
import SFC.MyVNF;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.graph.Graph;

import java.util.*;
public class Deployment_Algorithm2 extends Input.Value{

    public void Deploy_algo2(Graph<MyNode,MyEdge> graph,Map<MySFC,ArrayList<Graph<MyNode, MyEdge>>> Path_Set,ArrayList<MySFC> S,int R){
        cost_node =0;
        /**残容量リストの作成*/
        Map<MyNode,Integer> r_n2 = new HashMap<>();
        for(MyNode n:graph.getVertices()) r_n2.put(find_node(n), Input.Value.r_n.get(n));
        /**配置の開始*/
        whole :for(MySFC s:S){
            for(int i=0;i<=R;i++){
                ArrayList<MyNode> Node_List = new ArrayList<MyNode>(Path_Set.get(s).get(i).getVertices());
                s.VNF.sort(new MyComparator());
                for(MyVNF f:s.VNF){
                    /**候補ノードの作成*/
                    ArrayList<MyNode> U = NodeList_Generator(Node_List,r_n2,f);
                    if(U.size()==0){
                        cost_node = 0;
                        break whole;
                    }
                    else{
                        /**評価関数の計算*/
                        Map<MyNode,Double> EL = Evaluation_Calculator(Path_Set.get(s).get(i),U,r_n2,s);
                        /**最小評価値のノードをピックアップ*/
                        double min = 2;
                        MyNode min_node = null;
                        for(MyNode n:U){
                            if(min>EL.get(n)){
                                min = EL.get(n);
                                min_node = n;
                            }
                        }
                        if(min_node!=null) {
                            /**コストの計算・容量変更*/
                            cost_node += f.cap_VNF * c_n.get(find_node(min_node));
                            r_n2.replace(find_node(min_node), r_n2.get(find_node(min_node)) - f.cap_VNF);
                            /**配置したノードより前の物を削除*/
                            Algorithm2 al2 = new Algorithm2();
                            DijkstraDistance<MyNode,MyEdge> dd = new DijkstraDistance<>(Path_Set.get(s).get(i));
                            MyNode source = al2.find_original_Node(Path_Set.get(s).get(i),s.source);
                            MyNode sink = al2.find_original_Node(Path_Set.get(s).get(i),min_node);
                            double hop_minnode2 = (double) dd.getDistance(source,sink);
                            int hop_minnode = (int)hop_minnode2;
                            for(int j=0;j<Node_List.size();j++){
                                double hop_num2 = (double)dd.getDistance(source,al2.find_original_Node(Path_Set.get(s).get(i),Node_List.get(j)));
                                int hop_num = (int)hop_num2;
                                if(hop_minnode>hop_num) Node_List.get(j);
                            }
                        }
                        else{
                            cost_node=0;
                            break whole;
                        }
                    }
                }
            }
        }
    }
    public ArrayList<MyNode> NodeList_Generator(Collection<MyNode> List,Map<MyNode,Integer> r_n2,MyVNF f){
        ArrayList<MyNode> Node_List = new ArrayList<>();
        for(MyNode n:List){
            if(r_n2.get(find_node(n))-f.cap_VNF>=0) Node_List.add(n);
        }
        return Node_List;
    }
    public Map<MyNode,Double> Evaluation_Calculator(Graph<MyNode,MyEdge> p,ArrayList<MyNode> Node_List,Map<MyNode,Integer> r_n2,MySFC s){
        Map<MyNode,Double> List = new HashMap<>();
        /**各最大値計算*/
        int max_length = p.getEdgeCount()-1;
        int max_cost =0;
        Path.Algorithm2 al2 = new Path.Algorithm2();
        MyNode source =al2.find_original_Node(p,s.source);
        for(MyNode n: Node_List){
            int cost = c_n.get(find_node(n));
            if(max_cost<cost) max_cost = cost;
        }
        /**評価関数値の計算*/
        for(MyNode n:Node_List){
            MyNode n2 = al2.find_original_Node(p,n);
            /**ホップ数の計算*/
            DijkstraDistance<MyNode,MyEdge> dd = new DijkstraDistance<>(p);
            double num2 = (double)dd.getDistance(source,n2);
            int num = (int) num2;
            double hop = Math.pow((double)num/max_length,3);
            /**コストの計算*/
            double cost = Math.pow((double)c_n.get(find_node(n))/max_cost,2);
            /**容量計算*/
            double cap = Math.pow((double) r_n2.get(find_node(n))/r_n.get(find_node(n)),2);
            /**評価値の算出*/
            double price = hop*cap*cost;
            List.put(n,price);
        }
        return List;
    }
    private class MyComparator implements Comparator<MyVNF>{
        public int compare(MyVNF o1, MyVNF o2) {
            return o1.VNF_id<=o2.VNF_id ? -1:1;
        }
    }
}
