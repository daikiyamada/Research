package Placement;
import Input.MyEdge;
import Input.MyNode;
import Output.Result;
import Path.OPT_Path_Maker;
import SFC.MySFC;
import SFC.MyVNF;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;

import java.nio.file.Path;
import java.util.*;
public class Placement_Maker {
    class output extends Result{}
    /**メインのプレイスメントを行う関数*/
    /**【関数の構成方針】
     * １：各パスに対して全探索で配置を行う
     * ２：１つの配置が完了した場合、バックアップ関数の投げる
     * ３；エラーの場合、nullをバックアップ関数に投げる
     * ４：コストの出力*/
    /**バックアップのプレイスメントを行う関数*/
    /**【関数の構成方針】
     * １：nullの場合、すぐにコスト値０を返す
     * ２：メインの配置に対して点素となるパスを見つける
     * ３：点素となるパス全てに全探索配置をする
     * ４：failure_numに対応するだけ繰り返す
     * ５：コスト値を返す、エラーの場合、０を返す
     * */
    public Map<MySFC,ArrayList<ArrayList<Graph<MyNode,MyEdge>>>> Path_Selection1(ArrayList<MySFC> S, Map<MySFC, ArrayList<Graph<MyNode, MyEdge>>>Path_List,int Q){
        Map<MySFC,ArrayList<ArrayList<Graph<MyNode,MyEdge>>>> SFC_Path = new HashMap<>();
        /**先にパスを決める
         * 決定したパスに対して配置を行う*/
        /**SFCごとのパスを全探索*/
        for(int a=0;a<S.size();a++){
            ArrayList<Graph<MyNode,MyEdge>> Each_List = new ArrayList<>();
            Each_List.addAll(Path_List.get(S.get(a)));
            ArrayList<ArrayList<Graph<MyNode,MyEdge>>> Path = new ArrayList<>();
            for(int b=0;b<Path_List.get(S.get(a)).size();b++){
                ArrayList<ArrayList<Graph<MyNode,MyEdge>>> Path2 = new ArrayList<>();
                ArrayList<Graph<MyNode,MyEdge>> Each_List2 = new ArrayList<>();
                Each_List2.add(Each_List.get(b));
                Path2=Path_Selection(Each_List,Path2,Each_List2,Q,b+1);
                Path.addAll(Path2);
            }
            //  Path=Path_Remover(Path);
            SFC_Path.put(S.get(a),Path);
        }
        return SFC_Path;
    }
    public ArrayList<Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>>> Path_seleciton2(Graph<MyNode,MyEdge> graph,ArrayList<MySFC> S, Map<MySFC, ArrayList<ArrayList<Graph<MyNode, MyEdge>>>>SFC_Path){
        /**パスの組み合わせ全列挙*/
        ArrayList<Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>>> Path_List_All = new ArrayList<>();
        Path_List_All = Combination_Path_Selection(graph,S,SFC_Path,new HashMap<MySFC,ArrayList<Graph<MyNode,MyEdge>>>(),Path_List_All,0);
        return Path_List_All;
    }
    private ArrayList<ArrayList<Graph<MyNode,MyEdge>>> Path_Selection(ArrayList<Graph<MyNode,MyEdge>> Path_List,ArrayList<ArrayList<Graph<MyNode,MyEdge>>>SFC_Path_List,ArrayList<Graph<MyNode,MyEdge>> Each_Path,int Q,int Path_num){
        /**SFCパスの探索*/
        for(int i=Path_num;i<Path_List.size();i++){
            ArrayList<Graph<MyNode,MyEdge>> SFC_Path = new ArrayList<>();
            SFC_Path = DeepCopy(Each_Path);
            /**該当パスが点素かどうか判断する*/
            /**すでに選択済みのノードのリストアップ*/
            Set<Integer> Node_Set = new HashSet<>();
            for(Graph<MyNode,MyEdge>s2:SFC_Path){
                Collection<MyNode> Node_set = s2.getVertices();
                ArrayList<MyNode> Node_set2 = new ArrayList<MyNode>(Node_set);
                for(MyNode n:Node_set2) Node_Set.add(n.Node_Num);
            }
            /**探索パスのノードリストアップ*/
            Collection<MyNode> each_node_set = Path_List.get(i).getVertices();
            ArrayList<MyNode> node_set = new ArrayList<>(each_node_set);
            ArrayList<Integer> list = new ArrayList<>();
            for(MyNode n:node_set) list.add(n.Node_Num);
            int disjoint_num = 0;
            /**点素かどうかのチェック*/
            for(int k=0;k<list.size();k++) if(Node_Set.contains(list.get(k))==true) disjoint_num++;
            if(disjoint_num==2) {
                    ArrayList<Graph<MyNode, MyEdge>> Copy_Path = new ArrayList<>();
                    Copy_Path = DeepCopy(SFC_Path);
                    Copy_Path.add(Path_List.get(i));
                    if(Copy_Path.size()==Q) {
                        SFC_Path_List.add(Copy_Path);
                    }
                    else if(Copy_Path.size()<Q) SFC_Path_List = Path_Selection(Path_List, SFC_Path_List, Copy_Path, Q, i + 1);
            }
        }
        return SFC_Path_List;
    }
    private ArrayList<Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>>> Combination_Path_Selection(Graph<MyNode,MyEdge> graph, ArrayList<MySFC> S,Map<MySFC,ArrayList<ArrayList<Graph<MyNode,MyEdge>>>> SFC_Path,Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> Path,ArrayList<Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>>> Path_List,int SFC_num){
        ArrayList<ArrayList<Graph<MyNode,MyEdge>>> Each_List = new ArrayList<>();
        if(SFC_Path.get(S.get(SFC_num)).size()!=0) Each_List = SFC_Path.get(S.get(SFC_num));
        for(int a=0;a<Each_List.size();a++){
            /**各パスを追加して、ディープコピーして次のSFCの探索*/
            ArrayList<Graph<MyNode,MyEdge>> List_Path = new ArrayList<>();
            List_Path = DeepCopy(Each_List.get(a));
            Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> Combination_List = new HashMap<>();
            Path.put(S.get(SFC_num),List_Path);
            for(int b=0;b<Path.size();b++){
                ArrayList<Graph<MyNode,MyEdge>> LP = new ArrayList<>();
                LP = DeepCopy(Path.get(S.get(b)));
                Combination_List.put(S.get(b),LP);
            }
            if(SFC_num<S.size()-1)Path_List=Combination_Path_Selection(graph,S,SFC_Path,Combination_List,Path_List,SFC_num+1);
            else if(SFC_num ==S.size()-1) {
                /**リンクの容量確認*/
                int num = Link_Confirmation(graph,S,Combination_List);
                if(num==0) Path_List.add(Combination_List);
            }
        }
        return Path_List;
    }
    private int Link_Confirmation(Graph<MyNode,MyEdge> graph,ArrayList<MySFC> S,Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> List){
        int num=0;
        /**各リンク容量を管理するリストの宣言*/
        Map<Integer,Integer> Capacity_List = new HashMap<>();
        Collection<MyEdge> Edge_List1 = graph.getEdges();
        ArrayList<MyEdge> Edge_List = new ArrayList<>(Edge_List1);
        for(int a=0;a<Edge_List.size();a++)Capacity_List.put(Edge_List.get(a).Edge_ID,Edge_List.get(a).resource);
        /**各リンクの容量を確認*/
        for(int a=0;a<S.size();a++){
            int resource = S.get(a).Demand_Link_Resource;
            for(int b=0;b<List.get(S.get(a)).size();b++){
               Collection<MyEdge> Edge_List2 = List.get(S.get(a)).get(b).getEdges();
                Edge_List.clear();
                Edge_List.addAll(Edge_List2);
                for(int c=0;c<Edge_List.size();c++){
                    int edge_num = Edge_List.get(c).Edge_ID;
                    int capacity = Capacity_List.get(edge_num);
                    int r_capacity = capacity-resource;
                    if(r_capacity>=0) Capacity_List.replace(edge_num,r_capacity);
                    else if(r_capacity<0) num=1;
                }
            }
        }
        return num;
    }
    public ArrayList<ArrayList<Integer>> SFC_Placement_executer(Graph<MyNode,MyEdge> graph, ArrayList<MySFC> S, ArrayList<Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>>> SFC_Path_List){
        /**SFCリンクリソースの大きい順に配置*/
        /**SFCのリンクリソースの小さい順に配置*/
        /**そのままの順番で配置*/
        ArrayList<ArrayList<Integer>> OPT = new ArrayList<>();
        for(int a=0;a<SFC_Path_List.size();a++){
            /**リンクリソース使用量の算出*/
            int link_cost=0;
            link_cost = Link_Resource_Calculation(S,SFC_Path_List.get(a));
            /**配置*/
            ArrayList<Integer> Node_Cost = new ArrayList<>();
            Graph<MyNode,MyEdge> Copy_Graph = new UndirectedSparseGraph<>();
            ArrayList<Integer> Visit_List = new ArrayList<>();
            Copy_Graph = Clone_Graph(graph);
            Collection<MyNode> Clone_Set = Copy_Graph.getVertices();
            ArrayList<MyNode> Node_List = new ArrayList<>(Clone_Set);
            Map<Integer,Integer> Capacity_List = new HashMap<>();
            for(MyNode n:Node_List){
                int node_num =n.Node_Num;
                int capacity = n.resource;
                Capacity_List.put(node_num,capacity);
            }
            Node_Cost=Placement_executer(Capacity_List,S,SFC_Path_List.get(a),Node_Cost,0,S.get(0).VNF.size()-1,0,Visit_List,S.get(0).source,0,0);
            for(int b=0;b<Node_Cost.size();b++){
                ArrayList<Integer> Cost = new ArrayList<>();
                Cost.add(Node_Cost.get(b));
                Cost.add(link_cost);
                OPT.add(Cost);
            }
        }
        return OPT;
    }
    private ArrayList<Integer> Placement_executer(Map<Integer,Integer> Capacity_List, ArrayList<MySFC> S, Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> SFC_Path_List,ArrayList<Integer> OPT,int SFC_num,int VNF_num,int Path_num,ArrayList<Integer> Visit_List,MyNode now,int node_cost,int now_VNF){
        /**1つのVNFを各ノードに配置をする*/
        Graph<MyNode,MyEdge> now_path = SFC_Path_List.get(S.get(SFC_num)).get(Path_num);
        /**未訪問ノードの算出*/
        Collection<MyNode> Nonvisit1 = now_path.getVertices();
        ArrayList<Integer> Nonvisit = new ArrayList<>();
        ArrayList<MyNode> List = new ArrayList<>(Nonvisit1);
        for(MyNode n : List) if(Visit_List.contains(n.Node_Num)!=true) Nonvisit.add(n.Node_Num);
        /**始点から配置を行う*/
        while(Nonvisit.size()!=0){
            /**for文の中で変化してはいけない変数等のディープコピー*/
            Map<Integer,Integer> Capacity_List_Copy = new HashMap<>();
            Capacity_List_Copy.putAll(Capacity_List);
            int node_cost2 = node_cost;
            /**訪問済みリストに追加*/
            Visit_List.add(now.Node_Num);
            /**Visit Listのディープコピー*/
            ArrayList<Integer> Copy_Visit_List = new ArrayList<>();
            Copy_Visit_List.addAll(Visit_List);
            if(now.Node_ID.equals("s")==true){
                /**残キャパシティの計算*/
                int r1 = Capacity_List_Copy.get(now.Node_Num);
                int r2 = S.get(SFC_num).VNF.get(now_VNF).cap_VNF;
                int residual_capacity = r1-r2;
                if(residual_capacity>=0){
                    /**成功時：次のVNFの配置とノードリソースの計算*/
                    node_cost2 += S.get(SFC_num).VNF.get(now_VNF).cap_VNF*now.cost;
                    Capacity_List_Copy.replace(now.Node_Num,residual_capacity);
                    if(now_VNF<VNF_num) OPT=Placement_executer(Capacity_List_Copy,S,SFC_Path_List,OPT,SFC_num,VNF_num,Path_num,Copy_Visit_List,now,node_cost2,now_VNF+1);
                    else if(now_VNF==VNF_num&&Path_num<SFC_Path_List.get(S.get(SFC_num)).size()-1) OPT=Placement_executer(Capacity_List_Copy,S,SFC_Path_List,OPT,SFC_num, VNF_num,Path_num+1,new ArrayList<Integer>(),S.get(SFC_num).source,node_cost2,0);
                    else if(now_VNF==VNF_num&&Path_num==SFC_Path_List.get(S.get(SFC_num)).size()-1&&SFC_num<S.size()-1) OPT=Placement_executer(Capacity_List_Copy,S,SFC_Path_List,OPT,SFC_num+1,VNF_num,0,new ArrayList<Integer>(),S.get(SFC_num+1).source,node_cost2,0);
                    else if(now_VNF==VNF_num&&Path_num==SFC_Path_List.get(S.get(SFC_num)).size()-1&&SFC_num==S.size()-1)OPT.add(node_cost2);
                }
                /**失敗時：nownodeに隣接するノードの確定*/
                else if(residual_capacity<0){
                    if(Nonvisit.size()>1){
                        Collection<MyNode> neigbor_set = now_path.getNeighbors(now);
                        ArrayList<MyNode> neigbor_list = new ArrayList<>(neigbor_set);
                        for(MyNode n:neigbor_list) if(Nonvisit.contains(n.Node_Num)==true) OPT=Placement_executer(Capacity_List_Copy,S,SFC_Path_List,OPT,SFC_num,VNF_num,Path_num,Visit_List,n,node_cost2,now_VNF);
                    }
                }
            }
            /**nowの変換*/
            Collection<MyNode> neigbor_set = now_path.getNeighbors(now);
            ArrayList<MyNode> neigbor_list = new ArrayList<>(neigbor_set);
            Nonvisit.remove(0);
            for(MyNode n:neigbor_list) if(Nonvisit.contains(n.Node_Num)==true) now = n;
        }
        return OPT;
        }
    private int Link_Resource_Calculation(ArrayList<MySFC> S,Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> Path_List){
        int total_resource=0;
        for(int a=0;a<S.size();a++){
            int resource = S.get(a).Demand_Link_Resource;
            for(int b=0;b<Path_List.get(S.get(a)).size();b++){
                Collection<MyEdge> Edge_List = Path_List.get(S.get(a)).get(b).getEdges();
                ArrayList<MyEdge> Edge_List2 = new ArrayList<>(Edge_List);
                for(int c=0;c<Edge_List2.size();c++) total_resource+=resource*Edge_List2.get(c).cost;
            }
        }
        return total_resource;
    }
    private ArrayList<Graph<MyNode,MyEdge>> DeepCopy(ArrayList<Graph<MyNode,MyEdge>> SFC_Path){
        ArrayList<Graph<MyNode,MyEdge>> Copy_SFC_Path = new ArrayList<>();
        Graph<MyNode,MyEdge> copy_graph = new UndirectedSparseGraph<>();
        for(int a=0;a<SFC_Path.size();a++){
            Graph<MyNode,MyEdge> graph = SFC_Path.get(a);
            copy_graph = Clone_Graph(graph);
            Copy_SFC_Path.add(copy_graph);
        }
        return Copy_SFC_Path;
    }
    private Graph<MyNode,MyEdge> Clone_Graph(Graph<MyNode,MyEdge> path){
        /**Pathのディープコピー*/
        Collection<MyEdge> Edge_List1 = path.getEdges();
        ArrayList<MyEdge> Edge_List = new ArrayList<>(Edge_List1);
        Graph<MyNode,MyEdge> Path = new UndirectedSparseGraph<>();
        for(int a=0;a<Edge_List.size();a++){
            MyEdge e = new MyEdge(Edge_List.get(a).Edge_ID,Edge_List.get(a).resource,Edge_List.get(a).cost);
            Pair<MyNode> pair = path.getEndpoints(Edge_List.get(a));
            Path.addEdge(e,pair.getFirst(),pair.getSecond());
        }
        return Path;
    }
    private ArrayList<ArrayList<Graph<MyNode,MyEdge>>> Path_Remover(ArrayList<ArrayList<Graph<MyNode,MyEdge>>> Path){
        for(int a=0;a<Path.size();a++){
            ArrayList<Graph<MyNode,MyEdge>> Each_Path = new ArrayList<>(Path.get(a));
            for(int b=0;b<Path.get(a).size();b++){
                Collection<MyNode> C_Node_List = Each_Path.get(b).getVertices();
                ArrayList<MyNode> Node_List = new ArrayList<>(C_Node_List);
                int service_num=0;
                for(int c=0;c<Node_List.size();c++) if(Node_List.get(c).Node_ID.equals("s")==true) service_num++;
                if(service_num==0){
                    Path.remove(a);
                    break;
                }
            }
        }
        return Path;
    }

}
