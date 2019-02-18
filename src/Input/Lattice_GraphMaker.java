package Input;

import Parameter.Parameter;
import SFC.MySFC;
import edu.uci.ics.jung.graph.Graph;

import java.util.*;

public class Lattice_GraphMaker {
    public Graph<MyNode,MyEdge> LatticeGraph_Maker(Graph<MyNode,MyEdge> graph){
        Parameter par = new Parameter();
        int vertexCount = graph.getVertexCount();
        Collection<MyNode> node_list = graph.getVertices();
        List<MyNode> vertexes = new ArrayList<MyNode>(node_list);
        int latticeSize = (int) Math.sqrt(vertexCount);
        int num_edge=0;
        Random rnd = new Random();
        // 最初に一行目を作る
        // ex) latticeSize = 3のとき、以下の辺を作る
        // 1   2   3
        // + - + - +
        for(int i = 0; i < latticeSize-1; i++) {
            int cost = rnd.nextInt(par.link_cost_max-par.link_cost_min)+par.link_cost_min;
            int capacity = rnd.nextInt(par.link_resource_max-par.link_resource_min)+par.link_resource_min;

            graph.addEdge(new MyEdge(num_edge,capacity,cost,new HashMap<Integer,Integer>(),capacity), vertexes.get(i), vertexes.get(i+1));
            num_edge++;
        }
        // 2行目以降を作る
        // ex) latticeSize = 3のとき、以下の辺を作る
        //      0   1   2
        // row0 +   +   +
        //      |   |   |
        // row1 + - + - +
        //      3   4   5
        //
        // ※一番左（baseVertex）は latticeSize * rowの値
        for(int row = 1; row < latticeSize; row++) {
            int baseVertex = latticeSize * row;
            for(int i = 0; i < latticeSize-1; i++) {
                double t = Math.random();
                int capacity1 = rnd.nextInt(par.link_resource_max-par.link_resource_min)+par.link_resource_min;
                int capacity2 = rnd.nextInt(par.link_resource_max-par.link_resource_min)+par.link_resource_min;
                int cost1 = rnd.nextInt(par.link_cost_max-par.link_cost_min)+par.link_cost_min;
                int cost2 = rnd.nextInt(par.link_cost_max-par.link_cost_min)+par.link_cost_min;
                ArrayList<MySFC> Allocation_List = new ArrayList<MySFC>();
                graph.addEdge(new MyEdge(num_edge,capacity1,cost1,new HashMap<Integer,Integer>(),capacity1), vertexes.get(baseVertex+i), vertexes.get(baseVertex+i-latticeSize)); // 上の頂点と接続
                num_edge++;
                ArrayList<MySFC> Allocation_List2 = new ArrayList<MySFC>();
                graph.addEdge(new MyEdge(num_edge,capacity2,cost2,new HashMap<Integer,Integer>(),capacity2), vertexes.get(baseVertex+i), vertexes.get(baseVertex+i+1)); // 右の頂点と接続
                num_edge++;
            }
            // 上の頂点と接続. 一番右の頂点は右に頂点は無いので上だけ.
            double t = Math.random();
            int capacity = rnd.nextInt(par.link_resource_max-par.link_resource_min)+par.link_resource_min;
            int cost = rnd.nextInt(par.link_cost_max-par.link_cost_min)+par.link_cost_min;
            ArrayList<MySFC> Allocation_List = new ArrayList<MySFC>();
            graph.addEdge(new MyEdge(num_edge,capacity,cost,new HashMap<Integer,Integer>(),capacity), vertexes.get(baseVertex+(latticeSize-1)), vertexes.get(baseVertex+-1));
        }
        return graph;
    }
}
