package Input;

import edu.uci.ics.jung.graph.Graph;
import java.util.*;
import Parameter.Parameter;
public class ConnectedERGraph_Maker {

    public Graph<MyNode,MyEdge> generator(Graph<MyNode,MyEdge> graph){
        Parameter par = new Parameter();
        Random rnd = new Random();
        int Node_num = graph.getVertexCount();
        Collection<MyNode> Vertex = graph.getVertices();
        ArrayList<MyNode> vertexes = new ArrayList<>(Vertex);
        double p = 0;
        int num_edge =0;
        /** ランダムに辺の作成 */
        for(int i = 0; i < Node_num-1; i++) {
            for(int j = i+2; j < Node_num; j++) {
                if(Math.random() <= p) {
                    int cost = rnd.nextInt(par.link_cost_max-par.link_cost_min)+par.link_cost_min;
                    int capacity = rnd.nextInt(par.link_resource_max-par.link_resource_min)+par.link_resource_min;
                    MyEdge e = new MyEdge(num_edge);
                    graph.addEdge(e,vertexes.get(i), vertexes.get(i+1));
                    num_edge++;
                    Value.r_e.put(e,capacity);
                    Value.c_e.put(e,cost);
                }
            }
        }

        /** guarantee connectivity */
        Deque<MyNode> queue = new ArrayDeque<>();
        /** ある頂点が探索済みかどうかを判別するために探索済みの頂点を保存するリスト */
        List<MyNode> visitedVertexes = new ArrayList<>();
        /** check if graph is connected based on BFS & connect if not*/
        while (true) {
            ArrayList<MyNode> buf = new ArrayList<MyNode>(graph.getVertices());
            visitedVertexes.add(buf.get(0));
            queue.offer(buf.get(0));
            while (!queue.isEmpty()) {
                MyNode r = queue.poll();
                /** vの隣接点のうち、未探索のノードを木に追加し探索済みにする */
                for (MyNode v : graph.getNeighbors(r)) {
                    if (!visitedVertexes.contains(v)) {
                        visitedVertexes.add(v);
                        queue.offer(v);
                    }
                }
            }
            /** check if all vertices are visited */
            /** if so, break while */
            if (visitedVertexes.size() == graph.getVertexCount()) {
                break;
            }
            /** if not, connect an unvisited vertex to a randomly selected visited vertex */
            else {
                for (MyNode v : graph.getVertices()) {
                    if (!visitedVertexes.contains(v)) {
                        MyNode unvisitedV = v;
                        int cost = rnd.nextInt(par.link_cost_max - par.link_cost_min) + par.link_cost_min;
                        int capacity = rnd.nextInt(par.link_resource_max - par.link_resource_min) + par.link_resource_min;
                        MyEdge e = new MyEdge(num_edge);
                        graph.addEdge(e, visitedVertexes.get(rnd.nextInt(visitedVertexes.size())), unvisitedV);
                        Value.r_e.put(e,capacity);
                        Value.c_e.put(e,cost);
                        num_edge++;
                        break;
                    }
                }
            }
            /** clean */
            queue.clear();
            visitedVertexes.clear();
        }
        return graph;
    }
}
