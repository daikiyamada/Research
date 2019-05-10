package Input;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import java.util.*;
import Parameter.Parameter;
public class MyNode_Maker  extends Value{
    public Graph<MyNode,MyEdge> MyNode_Maker(){
        Random rnd = new Random();
        Parameter par = new Parameter();
        Graph<MyNode, MyEdge> Graph = new UndirectedSparseGraph<MyNode, MyEdge>();
        /**グラフのノード形成*/
        ArrayList<Integer> num_list = new ArrayList<>();
        for(int a=0;a<par.node_num;a++) num_list.add(a);
        for(int a=0;a<par.terminalnode;a++){
            Collections.shuffle(num_list);
            MyNode t = new MyNode("t",num_list.get(0));
            node_resource(t,-1);
            node_cost(t,0);
            Graph.addVertex(t);
            num_list.remove(0);
        }
        for(int a=0;a<par.servicenode;a++){
            int p = par.node_resource/100;
            int r = rnd.nextInt(10-p)+p;
            int resource = 100*r;
            int cost = rnd.nextInt(par.node_cost_max-par.node_cost_min)+par.node_cost_min;
            MyNode s = new MyNode("s",num_list.get(0));
            node_resource(s,resource);
            node_cost(s,cost);
            num_list.remove(0);
            Graph.addVertex(s);
        }
        return Graph;
    }
}
