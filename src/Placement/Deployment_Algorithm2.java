package Placement;

import Input.MyEdge;
import Input.MyNode;
import Input.Value;
import Path.Algorithm2;
import SFC.MySFC;
import SFC.MyVNF;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.graph.Graph;

import javax.xml.soap.Node;
import java.util.*;
public class Deployment_Algorithm2 extends Input.Value{

    public void Deploy_algo2(Graph<MyNode,MyEdge> graph,Map<MySFC,ArrayList<Graph<MyNode, MyEdge>>> Path_Set,ArrayList<MySFC> S,int R,int x,int y,int z){
        /**Residual Resource List*/
        Map<MyNode,Integer> r_n2 = new HashMap<>();
        for(MyNode n:graph.getVertices()) r_n2.put(find_node(n), Input.Value.r_n.get(n));
        /**Start Deployment*/
        whole :for(MySFC s:S){
            for(int i=0;i<=R;i++){
                ArrayList<MyNode> Node_List = new ArrayList<MyNode>(Path_Set.get(s).get(i).getVertices());
                s.VNF.sort(new MyComparator());
                for(MyVNF f:s.VNF){
                    /**Pickup Candidate node*/
                    ArrayList<MyNode> U = NodeList_Generator(Node_List,r_n2,f);
                    if(U.size()==0){
                        Value.cost_node = 0;
                        break whole;
                    }
                    else{
                        /**Calculating the Value*/
                        Map<MyNode,Double> EL = Evaluation_Calculator(Path_Set.get(s).get(i),U,r_n2,s,x,y,z);
                        /**Selecting the min value node*/
                        double min = 2;
                        MyNode min_node = null;
                        for(MyNode n:U){
                            if(min>EL.get(n)){
                                min = EL.get(n);
                                min_node = n;
                            }
                        }
                        if(min_node!=null) {
                            /**Rewritng the residual resource and cost*/
                            MyNode min_now = find_node(min_node);
                            Value.cost_node += f.cap_VNF * c_n.get(min_now);
                            r_n2.replace(min_now, r_n2.get(min_now) - f.cap_VNF);
                            /**removing the nodes*/
                            DijkstraDistance<MyNode,MyEdge> dd = new DijkstraDistance<>(Path_Set.get(s).get(i));
                            MyNode source = find_original_Node(Path_Set.get(s).get(i),s.source);
                            MyNode sink = find_original_Node(Path_Set.get(s).get(i),min_node);
                            double hop_minnode2 = (double) dd.getDistance(source,sink);
                            int hop_minnode = (int)hop_minnode2;
                            for(int j=0;j<Node_List.size();j++){
                                MyNode now = find_original_Node(Path_Set.get(s).get(i),Node_List.get(j));
                                double hop_num2 = (double)dd.getDistance(source,now);
                                int hop_num = (int)hop_num2;
                                if(hop_minnode>hop_num) U.remove(now);
                            }
                        }
                        else{
                            Value.cost_node=0;
                            break whole;
                        }
                    }
                    U.clear();
                }
                Node_List.clear();
            }
        }
    }
    public ArrayList<MyNode> NodeList_Generator(Collection<MyNode> List,Map<MyNode,Integer> r_n2,MyVNF f){
        ArrayList<MyNode> Node_List = new ArrayList<>();
        for(MyNode n:List){
            if(r_n2.get(find_node(n))-f.cap_VNF>=0) {
                Node_List.add(n);
            }
        }
        return Node_List;
    }
    public Map<MyNode,Double> Evaluation_Calculator(Graph<MyNode,MyEdge> p,ArrayList<MyNode> Node_List,Map<MyNode,Integer> r_n2,MySFC s,int x,int y,int z){
        Map<MyNode,Double> List = new HashMap<>();
        /**Calculating the each max value*/
        int max_length = p.getVertexCount();
        int max_cost =0;
        Path.Algorithm2 al2 = new Path.Algorithm2();
        MyNode source =al2.find_original_Node(p,s.source);
        for(MyNode n: Node_List){
            int cost = c_n.get(find_node(n));
            if(max_cost<cost) max_cost = cost;
        }
        /**Calculating the value*/
        for(MyNode n:Node_List){
            MyNode n2 = al2.find_original_Node(p,n);
            /**Hop*/
            DijkstraDistance<MyNode,MyEdge> dd = new DijkstraDistance<>(p);
            double num2;
            if(n2.Node_Num==source.Node_Num) num2 = 1.0;
            else num2 = (double)dd.getDistance(source,n2)+1.0;
            int num = (int) num2;
            double hop = (double)num/max_length;
            /**Cost*/
            double cost = (double)c_n.get(find_node(n))/max_cost;
            /**Resource*/
            double cap = (double) r_n2.get(find_node(n))/r_n.get(find_node(n));
            /**Value*/
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
