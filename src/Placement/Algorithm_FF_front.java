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
        public void Placement_FF_front(Graph<MyNode, MyEdge> G, ArrayList<MySFC> S, Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> P, int Q){
            Value.cost_node = 0;
            Map<MyNode,Integer> r_n2 = new HashMap<>();
            /**residual resource list*/
            for(MyNode n:G.getVertices()) r_n2.put(find_node(n),Value.r_n.get(n));
            /**Start Deployment*/
            whole:for(MySFC s:S){
                for(int i=0;i<Q+1;i++){
                    /**Pick up candidate node*/
                    Graph<MyNode,MyEdge> p = P.get(s).get(i);
                    ArrayList<MyNode> V = new ArrayList<>(p.getVertices());
                    /**Selecting the ndoe from source node*/
                    MyNode node = find_node2(V,s.source);
                    for(MyVNF f:s.VNF){
                        while(true){
                            if(V.size()==0){
                                Value.cost_node=0;
                                break whole;
                            }
                            V.remove(node);
                            MyNode now = find_node(node);
                            /**if the node has enough resource → deploy
                             * otherelse → next node*/
                            if(r_n2.get(now)-f.cap_VNF>=0){
                                Value.cost_node+=f.cap_VNF*c_n.get(now);
                                r_n2.replace(now,r_n2.get(now)-f.cap_VNF);
                                break;
                            }
                            else{
                                Collection<MyNode> neighbor_list2 = p.getNeighbors(node);
                                ArrayList<MyNode> neighbor_list = new ArrayList<MyNode>(neighbor_list2);
                                for(MyNode n:neighbor_list) if(V.contains(n)) node = n;
                            }
                        }
                    }
                }
            }
        }
}

