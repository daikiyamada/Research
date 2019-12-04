package Input;
import edu.uci.ics.jung.graph.SparseGraph;
import java.util.*;
import Parameter.Parameter;
public class MyNode_Maker  extends Value{
    public SparseGraph<MyNode,MyEdge> MyNode_Maker(int cost_type){
        Random rnd = new Random();
        Parameter par = new Parameter();
        SparseGraph<MyNode, MyEdge> Graph = new SparseGraph<MyNode, MyEdge>();
        /**Generating Node*/
        for(int a=0;a<par.node_num;a++){
            /**Giving available computation resource*/
            int r = rnd.nextInt(par.node_resource_max/100);
            int resource = par.node_resource_min+r*100;
            /**Giving unit cost*/
            int cost =0;
            if(cost_type==1){
                /**Random*/
                cost = rnd.nextInt(par.node_cost_max-par.node_cost_min+1)+par.node_cost_min;
            }
            else if(cost_type==2){
                /**Comparable*/
                cost = resource/100;
            }
            else if(cost_type==3){
                /**uncomparable*/
                cost = 11-resource/100;
            }
            /**Generating Node */
            MyNode s = new MyNode("v",a);
            node_resource(s,resource);
            node_cost(s,cost);
            Graph.addVertex(s);
        }
        return Graph;
    }
}
