package Path;

import Input.MyEdge;
import Input.MyNode;
import SFC.MySFC;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;


import java.util.*;

public class OPT_Path_Maker {
    public Map<MySFC,ArrayList<Graph<MyNode, MyEdge>>> Path_Maker(Graph<MyNode,MyEdge> graph, ArrayList<MySFC> S){
        Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> Path_List = new HashMap<>();
        ArrayList<ArrayList<MyNode>> SD = new ArrayList<>();
        /**始点・終点のペア算出*/
        for(int a=0;a<S.size();a++){
            MyNode s = S.get(a).source;
            MyNode t = S.get(a).sink;
            int num = 0;
            for(int b=0;b<SD.size();b++){
                if(SD.get(b).contains(s)==true&&SD.get(b).contains(t)==true){
                    num++;
                    break;
                }
            }
            if(num==0){
                ArrayList<MyNode> st = new ArrayList<>();
                st.add(s);
                st.add(t);
                SD.add(st);
            }
        }
        for(int a=0;a<SD.size();a++){
            ArrayList<Graph<MyNode,MyEdge>> pathset = new ArrayList<>();
            pathset = path_search(graph,SD.get(a).get(0),SD.get(a).get(1));
            /**対象の始点・終点を含むSFCが存在するかどうかチェック*/
            for(int b=0;b<S.size();b++){
                if(SD.get(a).contains(S.get(b).source)==true&&SD.get(a).contains(S.get(b).sink)==true) {
                    Path_List.put(S.get(b),pathset);
                }
            }
        }
        return Path_List;
    }

    private ArrayList<Graph<MyNode,MyEdge>> path_search(Graph<MyNode,MyEdge> graph, MyNode source,MyNode sink){
        ArrayList<Graph<MyNode,MyEdge>> Path_List = new ArrayList<>();
        Graph<MyNode,MyEdge> Path = new UndirectedSparseGraph<>();
        Path_List = Path_selection(graph,Path,null,source,sink,Path_List);
        return Path_List;
    }

    private ArrayList<Graph<MyNode,MyEdge>> Path_selection(Graph<MyNode,MyEdge> graph,Graph<MyNode,MyEdge> path,MyNode before,MyNode now, MyNode sink,ArrayList<Graph<MyNode,MyEdge>> Path_List){
        /**now nodeとリンク追加*/
        if(before!=null){
            MyEdge e = graph.findEdge(before,now);
            path.addEdge(e,before,now);
        }
        before = now;
        /**now nodeがsinkノードかどうかチェック*/
        /**sink nodeの場合、Path_Listに追加*/
        /**その他*/
        /**now nodeのネイバーチェック*/
        /**pathに含まれていないnodeのみ再帰でpathselection*/
        if(now==sink){
            path = Clone_Graph(path,before);
            Path_List.add(path);
        }
       else{
           Collection<MyNode> neighbor1 = graph.getNeighbors(before);
           ArrayList<MyNode> neighbor = new ArrayList<>(neighbor1);
            Graph<MyNode,MyEdge> Path = new UndirectedSparseGraph<>();
           for(int a=0;a<neighbor.size();a++){
               if(path.containsVertex(neighbor.get(a))!=true) {
                   Path = Clone_Graph(path,before);
                   Path_List=Path_selection(graph,Path,before,neighbor.get(a),sink,Path_List);
               }
           }
        }
        return Path_List;
    }

    public Graph<MyNode,MyEdge> Clone_Graph(Graph<MyNode,MyEdge> path,MyNode before){
        /**Pathのディープコピー*/
      Collection<MyEdge> Edge_List1 = path.getEdges();
        ArrayList<MyEdge> Edge_List = new ArrayList<>(Edge_List1);
        Graph<MyNode,MyEdge> Path = new UndirectedSparseGraph<>();
        for(int a=0;a<Edge_List.size();a++){
            MyEdge e = new MyEdge(Edge_List.get(a).Edge_ID,Edge_List.get(a).resource,Edge_List.get(a).cost,Edge_List.get(a).location_list,Edge_List.get(a).r_resource);
            Pair<MyNode> pair = path.getEndpoints(Edge_List.get(a));
            Path.addEdge(e,pair.getFirst(),pair.getSecond());
        }
        return Path;
    }
}
