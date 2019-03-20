import Input.MyEdge;
import Input.MyNode;
import Path.OPT_Path_Maker;
import Placement.Placement_Maker;
import SFC.*;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import Input.*;
import Output.*;
import java.util.*;
import Parameter.Parameter;
import Path.*;
public class Main {
    static class NWSGraph extends NWS_Maker{}
    static class LatticeGraph extends Lattice_GraphMaker{}
    static class vis extends Visualization{}
    static class node extends MyNode_Maker{}
    static class VNF extends VNF_Maker{}
    static class SFC extends SFC_Maker{}
    static class Path_All extends OPT_Path_Maker{}
    static class Place1 extends Placement_Maker{}
    static class Par extends Parameter{}
    static class Connected_ER extends ConnectedERGraph_Maker{}
    static class output extends Result{}
    public static void main(String[] args){
        double start = System.currentTimeMillis();
        /**１つの設定で行う回数*/
        for(int i=0;i<1;i++){
        /**Graph 関係*/
        Graph<MyNode, MyEdge> graph = new UndirectedSparseGraph<>();
        /**ノード関連*/
        node n = new node();
        graph = n.MyNode_Maker();
        /**NWS*/
        //NWSGraph nws = new NWSGraph();
        //    graph = nws.NWS_GraphMaker(graph);
        /**Lattice*/
        LatticeGraph lat = new LatticeGraph();
        graph = lat.LatticeGraph_Maker(graph);
        /**Connected_ER*/
        //     Connected_ER ce = new Connected_ER();
        //     graph = ce.generator(graph);
        /**グラフの可視化*/
        //vis v = new vis();
        //v.Layout_Graph(graph);
            /**同じグラフでパラメータを変えての実験*/
        /**SFC関連*/
        VNF vnf = new VNF();
        ArrayList<MyVNF> VNF_List = new ArrayList<>();
        VNF_List=vnf.VNF_Maker();
        SFC sfc = new SFC();
        ArrayList<MySFC> S = new ArrayList<>();
        Collection<MyNode> node1 = graph.getVertices();
        ArrayList<MyNode> node2 = new ArrayList<>(node1);
        S=sfc.SFCMaker(node2,VNF_List);
        /**Path　全探索*/
        Path_All pa = new Path_All();
        Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> Path_Set = new HashMap<>();
        Path_Set = pa.Path_Maker(graph,S);
        /**VNF Placement*/
        Map<MySFC,ArrayList<ArrayList<Graph<MyNode,MyEdge>>>> SFC_Path_List = new HashMap<>();
        Place1 p1 = new Place1();
        Par par = new Par();
        output op = new output();
        SFC_Path_List=p1.Path_Selection1(S,Path_Set,par.failure_num+1);
        Path_Set.clear();
        int error_num =SFC_Path_List.size();
        int num_Path =1;
        for(int a=0;a<SFC_Path_List.size();a++) num_Path*=SFC_Path_List.get(S.get(a)).size();
        System.out.println("最大パス数:"+num_Path);
        ArrayList<Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>>> Path_List = new ArrayList<>();
        Path_List=p1.Path_seleciton2(graph,S,SFC_Path_List);
        SFC_Path_List.clear();
        error_num-= Path_List.size();
        System.out.println("リンク容量確認後パス数:"+Path_List.size());
        int total = op.Total_Placement_Calculator(Path_List,S);
        System.out.println("配置組み合わせ数:"+total);
        ArrayList<ArrayList<Integer>> OPT = new ArrayList<>();
        OPT = p1.SFC_Placement_executer(graph,S,Path_List);
        error_num+= (total-OPT.size());
        System.out.println("最適解サイズ:"+OPT.size());
        /**結果の出力*/
        op.write(OPT,error_num,i,0);
    }
        double end = System.currentTimeMillis();
        double time = end - start;
        time = time/1000;
        System.out.println(time+"s");

    }
}
