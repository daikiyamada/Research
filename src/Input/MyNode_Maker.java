package Input;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Collections;
import Parameter.Parameter;
public class MyNode_Maker {
    public Graph<MyNode,MyEdge> MyNode_Maker(){
        Random rnd = new Random();
        Parameter par = new Parameter();
        Graph<MyNode, MyEdge> Graph = new UndirectedSparseGraph<MyNode, MyEdge>();
        /**グラフのノード形成*/
        ArrayList<Integer> num_list = new ArrayList<>();
        for(int a=0;a<par.node_num;a++) num_list.add(a);
        for(int a=0;a<par.terminalnode;a++){
            Collections.shuffle(num_list);
            MyNode t = new MyNode("t",num_list.get(0),-1,0,new HashMap<Integer,Integer>(),0);
            Graph.addVertex(t);
            num_list.remove(0);
        }
        for(int a=0;a<par.servicenode;a++){
            int resource = rnd.nextInt(par.node_resource_max-par.node_resource_min)+par.node_resource_min;
            int cost = rnd.nextInt(par.node_cost_max-par.node_cost_min)+par.node_cost_min;
            MyNode s = new MyNode("s",num_list.get(0),resource,cost,new HashMap<Integer,Integer>(),resource);
            num_list.remove(0);
            Graph.addVertex(s);
        }
        return Graph;
    }
}
