package Algorithm;

import Input.*;
import SFC.*;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import org.apache.commons.collections15.Transformer;
import java.util.*;

public class SinglePath extends Value{
    public void Generator(Graph<MyNode, MyEdge> Graph, ArrayList<MySFC> S,int R,int x,int y,int z) {
        /**Generating the capacity list(Graph)*/
        Map<MyEdge, Integer> r_e2 = new HashMap<>();
        for (MyEdge e : Graph.getEdges()) r_e2.put(e, r_e.get(e));
        Map<MyNode, Integer> r_n2 = new HashMap<>();
        for (MyNode n : Graph.getVertices()) r_n2.put(n, r_n.get(n));
        /**Sort SFC set in reverse order*/
        S.sort(new MyComparator());
        whole:for (MySFC s : S) {
            /**Deep copy of graph*/
            Graph<MyNode, MyEdge> Copy_Graph = Clone_Graph(Graph);
            /**Remove links which don't have enough capacity*/
            for (MyEdge e : Copy_Graph.getEdges()) if (s.Demand_Link_Resource > r_e.get(find_edge(e))) Copy_Graph.removeEdge(e);
            /**finding shortest path*/
            DijkstraShortestPath<MyNode, MyEdge> ds = new DijkstraShortestPath<>(Copy_Graph, new MyTransformer());
            DijkstraDistance<MyNode, MyEdge> dd = new DijkstraDistance<>(Copy_Graph);
            List<MyEdge> path;
            MyNode source = find_original_Node(Copy_Graph, s.source);
            MyNode sink = find_original_Node(Copy_Graph, s.sink);
            if (dd.getDistance(source, sink) != null) path = ds.getPath(source, sink);
            else {
                cost_link = 0;
                cost_node = 0;
                break whole;
            }
            Graph<MyNode, MyEdge> p = Dijkstra_Path(Copy_Graph, path, source);
            for (int i = 0; i < R; i++) {
                Map<MyNode, Set<MyVNF>> Deploy_List = new HashMap<>();
                ArrayList<MyNode> U = new ArrayList<MyNode>(p.getVertices());
                MyNode before = source;
                for (MyVNF f : s.VNF) {
                    U = Capacity_Confirm(U, f, r_n2);
                    if (U.size() != 0)before = Deploy_Value(U, Copy_Graph, p, Deploy_List, r_n2, f, s, x, y, z);
                    else {
                        step1:for (;;) {
                            /**selecting the before and next node*/
                            MyNode next = find_original_Node(Copy_Graph, Next_Generator(p, s.source, before));
                            MyEdge e = Copy_Graph.findEdge(before, next);
                            Copy_Graph.removeEdge(e);
                            Graph_Modificator(Copy_Graph, p, before, next, s);
                            step2:for (;;){
                                dd = new DijkstraDistance<>(Copy_Graph,new MyTransformer());
                                ds = new DijkstraShortestPath<>(Copy_Graph, new MyTransformer());
                                List<MyEdge> path2 = new ArrayList<>();
                                if (dd.getDistance(before, next) != null) {
                                    path2 = ds.getPath(before, next);
                                    Graph<MyNode, MyEdge> p2 = Dijkstra_Path(Copy_Graph, path2, source);
                                    for (MyNode n : p2.getVertices()) if(before.Node_Num!= n.Node_Num&&next.Node_Num!=n.Node_Num)U.add(n);
                                    if(U.size()!=0)Capacity_Confirm(U, f, r_n2);
                                    if (U.size() != 0) {
                                        List<MyEdge> path3 = new ArrayList<MyEdge>(path);
                                        path3.remove(e);
                                        path3.addAll(path2);
                                        Graph<MyNode, MyEdge> p3 = Dijkstra_Path(Graph, path3, source);
                                        before = find_original_Node(Copy_Graph,Deploy_Value(U, Graph, p3, Deploy_List, r_n2, f, s, x, y, z));
                                        U = List_Modificator(U, p3, before, source);
                                        p = p3;
                                        path = new ArrayList<MyEdge>(p3.getEdges());
                                        break step1;
                                    } else {
                                        ArrayList<MyNode> nl = new ArrayList<>(p2.getNeighbors(find_original_Node(p2,before)));
                                        Copy_Graph.removeEdge(Copy_Graph.findEdge(find_original_Node(Copy_Graph, nl.get(0)), find_original_Node(Copy_Graph, before)));
                                    }
                                } else {
                                    before = next;
                                    if (before.Node_Num == s.sink.Node_Num) {
                                        cost_link = 0;
                                        cost_node = 0;
                                        break whole;
                                    } else break step2;
                                }
                            }
                        }
                    }
                }
            }
            if (cost_node != 0) for (MyEdge e : p.getEdges()) cost_link += c_e.get(find_edge(e)) * s.Demand_Link_Resource;
        }
    }
    public ArrayList<MyNode> Capacity_Confirm(ArrayList<MyNode> U,MyVNF f,Map<MyNode,Integer> r_n2){
        ArrayList<MyNode> remove_list = new ArrayList<>();
        for(MyNode n:U) if(f.cap_VNF>r_n2.get(find_node(n))) remove_list.add(n);
        for(MyNode n:remove_list) U.remove(n);
        return U;
    }
    public MyNode Deploy_Value(ArrayList<MyNode> U,Graph<MyNode,MyEdge> Copy_Graph,Graph<MyNode,MyEdge> path,Map<MyNode,Set<MyVNF>> Deploy_List,Map<MyNode,Integer> r_n2,MyVNF f,MySFC s,int x,int y,int z){
        /**Calculating value*/
        MyNode min_node=Evaluation_Calculator(Copy_Graph,path,U,r_n2,s,x,y,z);
        min_node = find_node(min_node);
        if(Deploy_List.containsKey(min_node))Deploy_List.get(min_node).add(f);
        else {
            Set<MyVNF> F = new HashSet<>();
            F.add(f);
            Deploy_List.put(min_node,F);
        }
        r_n2.replace(min_node,r_n2.get(min_node)-f.cap_VNF);
        cost_node+=f.cap_VNF*c_n.get(min_node);
        ArrayList<MyNode> remove_list = new ArrayList<>();
        /**removing nodes*/
        for(MyNode n:U){
            DijkstraDistance<MyNode,MyEdge> dd = new DijkstraDistance<>(path);
            MyNode source = find_original_Node(path,s.source);
            MyNode min_node2 = find_original_Node(path,min_node);
            if((double)dd.getDistance(source,find_original_Node(path,n)) < (double)dd.getDistance(source,min_node2)) remove_list.add(n);
        }
        for(MyNode n:remove_list) U.remove(n);
        return min_node;
    }
    public MyNode Evaluation_Calculator(Graph<MyNode,MyEdge> g,Graph<MyNode,MyEdge> p,ArrayList<MyNode> Node_List,Map<MyNode,Integer> r_n2,MySFC s,int x,int y,int z){
        Map<MyNode,Double> List = new HashMap<>();
        /**Calculating the each max value*/
        int max_length = p.getVertexCount();
        int max_cost =0;
        MyNode source =find_original_Node(p,s.source);
        for(MyNode n: Node_List){
            int cost = c_n.get(find_node(n));
            if(max_cost<cost) max_cost = cost;
        }
        /**Calculating the value*/
        for(MyNode n:Node_List){
            MyNode n2 = find_original_Node(p,n);
            /**Hop*/
            DijkstraDistance<MyNode,MyEdge> dd = new DijkstraDistance<>(p);
            double num2;
            if(n2.Node_Num==source.Node_Num) num2 = 1.0;
            else num2 = (double)dd.getDistance(source,n2)+1.0;
            int num = (int) num2;
            double hop = Math.pow((double)num/max_length,x);
            /**Cost*/
            double cost = Math.pow((double)c_n.get(find_node(n))/max_cost,y);
            /**Resource*/
            double cap = Math.pow((double) r_n2.get(find_node(n))/r_n.get(find_node(n)),z);
            /**Value*/
            double price = hop*cap*cost;
            List.put(n,price);
        }
        double min = 2;
        MyNode min_node = null;
        for(MyNode n:Node_List){
            if(min>List.get(n)){
                min = List.get(n);
                min_node = n;
            }
        }
        return find_original_Node(g,min_node);
    }
    public MyNode Next_Generator(Graph<MyNode,MyEdge>path,MyNode source,MyNode before){
        before = find_original_Node(path,before);
        source = find_original_Node(path,source);
        ArrayList<MyNode> List = new ArrayList<>(path.getNeighbors(find_original_Node(path,before)));
        DijkstraDistance<MyNode,MyEdge> dd = new DijkstraDistance<>(path);
        if(List.size()==1) return List.get(0);
        else if((double)dd.getDistance(source,List.get(0))<(double)dd.getDistance(source,List.get(1))) return List.get(1);
        else return List.get(0);
    }
    public Graph<MyNode,MyEdge> Graph_Modificator(Graph<MyNode,MyEdge> G,Graph<MyNode,MyEdge> path,MyNode before, MyNode next, MySFC s){
        /**remove nodes from source node to before*/
        /**remove nodes from next to sink node*/
        DijkstraDistance<MyNode,MyEdge> dd = new DijkstraDistance<>(path);
        MyNode source = find_original_Node(path,s.source);
        double bd = (double)dd.getDistance(source,find_original_Node(path,before));
        double nd = (double)dd.getDistance(source,find_original_Node(path,next));
        for(MyNode n:path.getVertices()) if((double)dd.getDistance(source,n)!=bd&&(double)dd.getDistance(source,n)!=nd) for(MyEdge e:G.getIncidentEdges(find_original_Node(G,n))) G.removeEdge(e);
        return G;
    }
    public ArrayList<MyNode> List_Modificator(ArrayList<MyNode> U,Graph<MyNode,MyEdge> path,MyNode before, MyNode source){
        DijkstraDistance<MyNode,MyEdge> dd = new DijkstraDistance<>(path);
        source = find_original_Node(path,source);
        double bd = (double)dd.getDistance(source,find_original_Node(path,before));
        for(MyNode n:path.getVertices())  if((double)dd.getDistance(source,n)>=bd)U.add(n);
        return U;
    }
    private class MyComparator implements Comparator<MySFC>{
        public int compare(MySFC o1,MySFC o2){
            return o1.Demand_Link_Resource>=o2.Demand_Link_Resource ? -1:1;
        }
    }
    private class MyTransformer implements Transformer<MyEdge,Number> {
        public Number transform(MyEdge myEdge) {
            return Value.c_e.get(find_edge(myEdge));
        }
    }
}
