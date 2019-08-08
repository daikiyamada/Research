package Input;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import Parameter.Parameter;
public class NWS_Maker extends Value{
    public Graph<MyNode,MyEdge> NWS_GraphMaker(Graph<MyNode,MyEdge> Physical_Network,double p,int cost_type){
        Parameter par = new Parameter();
        int numnode = Physical_Network.getVertexCount();
        int edge_num =1;
        Collection<MyNode> Node_List =Physical_Network.getVertices();
        ArrayList<MyNode> Node = new ArrayList<MyNode>(Node_List);
        Random rnd = new Random();
        /**リングの作成*/
        for(int a=0;a<numnode;a++){
            int capacity = (rnd.nextInt(par.link_resource_max-par.link_resource_min)+par.link_resource_min)*100;
            int cost =0;
            if(cost_type==1) cost = rnd.nextInt(par.link_cost_max-par.link_cost_min+1)+par.link_cost_min;
            else if(cost_type==2){
                cost = (capacity/100)-5;
                if(cost==0) cost = 10;
            }
            else if(cost_type==3){
                int r = (capacity/100)-5;
                if(r==0) cost = 1;
                else{
                    cost = par.link_cost_max+1-r;
                }
            }
            MyEdge e = new MyEdge(edge_num);
            edge_resource(e,capacity);
            edge_cost(e,cost);
            edge_num++;
            if(a==numnode-1)Physical_Network.addEdge(e,Node.get(0),Node.get(numnode-1));
            else Physical_Network.addEdge(e,Node.get(a),Node.get(a+1));
        }
        /**k-nearest　の作成*/
        for(int a=0;a<numnode;a++){
            int node_k = a+par.k;
            int capacity = (rnd.nextInt(par.link_resource_max-par.link_resource_min)+par.link_resource_min)*100;
            int cost =0;
            if(cost_type==1) cost = rnd.nextInt(par.link_cost_max-par.link_cost_min+1)+par.link_cost_min;
            else if(cost_type==2){
                cost = (capacity/100)-5;
                if(cost==0) cost = 10;
            }
            else if(cost_type==3){
                int r = (capacity/100)-5;
                if(r==0) cost = 1;
                else{
                    cost = par.link_cost_max+1-r;
                }
            }
            MyEdge e = new MyEdge(edge_num);
            edge_resource(e,capacity);
            edge_cost(e,cost);
            edge_num++;
            if(node_k<numnode){
                Physical_Network.addEdge(e,Node.get(a),Node.get(a+par.k));
            }
            else{
                int num = numnode - (a+1);
                num = par.k -num;
                Physical_Network.addEdge(e,Node.get(a),Node.get(num-1));
            }
        }
        /**リンクの付け足し*/
        Collection<MyEdge> List = Physical_Network.getEdges();
        ArrayList<MyEdge> Edge_List = new ArrayList<MyEdge>(List);
        for(int a=0;a<Edge_List.size();a++){
            Pair<MyNode> n = Physical_Network.getEndpoints(Edge_List.get(a));
            int capacity = (rnd.nextInt(par.link_resource_max-par.link_resource_min)+par.link_resource_min)*100;
            int cost =0;
            if(cost_type==1) cost = rnd.nextInt(par.link_cost_max-par.link_cost_min+1)+par.link_cost_min;
            else if(cost_type==2){
                cost = (capacity/100)-5;
                if(cost==0) cost = 10;
            }
            else if(cost_type==3){
                int r = (capacity/100)-5;
                if(r==0) cost = 1;
                else{
                    cost = par.link_cost_max+1-r;
                }
            }
            MyEdge e = new MyEdge(edge_num);
            edge_resource(e,capacity);
            edge_cost(e,cost);
            edge_num++;
            int node;
            for(;;) {
                node = rnd.nextInt(numnode - 1);
                if (n.getFirst().Node_Num != Node.get(node).Node_Num && n.getSecond().Node_Num != Node.get(node).Node_Num) break;
            }
            if(Math.random()<=p&&Physical_Network.findEdge(n.getFirst(),Node.get(node))==null){
                Physical_Network.addEdge(e,n.getFirst(),Node.get(node));
            }
        }
        return  Physical_Network;
    }
}
