import Input.MyEdge;
import Input.MyNode;
import Path.OPT_Path_Maker;
import Placement.Algorithm_Based_GAP;
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
    static class algo1 extends Algorithm_Based_MECF{}
    static class algop1 extends Algorithm_Based_GAP{}
    static class cal extends Calculation{}

    public static void main(String[] args){
        Par par = new Par();
        Map<Integer,Double> node_cost = new HashMap<>();
        Map<Integer,Double> link_cost = new HashMap<>();
        Map<Integer,Integer> node_median = new HashMap<>();
        Map<Integer,Integer> link_median = new HashMap<>();
        Map<Integer,Double> node_sd = new HashMap<>();
        Map<Integer,Double> link_sd = new HashMap<>();
        Map<Integer,Integer> error_num_list = new HashMap<>();
        Map<Integer,Long> exe_time_list = new HashMap<>();

        /**SFC数を変えての実験*/
        for(int j=1;j<=10;j++) {
            System.out.println("SFC:"+j*10+":start");
            ArrayList<Integer> cl_List = new ArrayList<>();
            ArrayList<Integer> nl_List = new ArrayList<>();
            int error_num = par.exe_num;
            long start = System.currentTimeMillis();
            /**１つの設定で行う回数*/
            for (int i = 0; i < par.exe_num; i++) {
                Value.cost_link = 0;
                Value.cost_node = 0;
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
                /**SFC関連*/
                VNF vnf = new VNF();
                ArrayList<MyVNF> VNF_List = vnf.VNF_Maker();
                SFC sfc = new SFC();
                Collection<MyNode> node1 = graph.getVertices();
                ArrayList<MyNode> node2 = new ArrayList<>(node1);
                ArrayList<MySFC> S = sfc.SFCMaker(node2, VNF_List,j*10);
                /**Path　全探索*/
                //Path_All pa = new Path_All();
                /*Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> Path_Set = pa.Path_Maker(graph,S);*/
                /**パスアルゴリズム*/
                algo1 al1 = new algo1();
                Map<MySFC, ArrayList<Graph<MyNode, MyEdge>>> Path_set = al1.Routing_Algo1(graph, S, par.failure_num);
                if (Value.cost_link != 0) cl_List.add(Value.cost_link);
                /**VNF Placement*/
                Place1 p1 = new Place1();
                output op = new output();
/*        Map<MySFC,ArrayList<ArrayList<Graph<MyNode,MyEdge>>>> SFC_Path_List = p1.Path_Selection1(S,Path_Set,par.failure_num+1);
        Path_Set.clear();
        int error_num =SFC_Path_List.size();
        ArrayList<Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>>> Path_List = p1.Path_seleciton2(graph,S,SFC_Path_List);
        SFC_Path_List.clear();
        error_num-= Path_List.size();
        ArrayList<ArrayList<Integer>> OPT = p1.SFC_Placement_executer(graph,S,Path_List);
        System.out.println("最適解サイズ:"+OPT.size());*/
                /**GAPベースのアルゴリズム*/
                algop1 alp1 = new algop1();
                alp1.Placement_Algo1(graph, S, Path_set, par.failure_num + 1);
                if (Value.cost_node != 0) nl_List.add(Value.cost_node);
                // System.out.println("link-node:"+Value.cost_link+"-"+Value.cost_node);
                /**結果の出力*/
//        op.write(OPT,error_num,i);
            }
            long end = System.currentTimeMillis();
            long time = end - start;
            System.out.println("SFC:"+j*10+":finish");
            /**実行時間の算出*/
            time = time / 1000;
            exe_time_list.put(j*10,time);
            /**結果の出力*/
            /**エラー数の算出*/
            error_num = par.exe_num-cl_List.size();
            error_num = cl_List.size()-nl_List.size();
            error_num_list.put(j*10,error_num);
            /**平均値、中央値、標準偏差の算出*/
            cal Cal = new cal();
            double average_node = Cal.average_cal(nl_List);
            double average_edge = Cal.average_cal(cl_List);
            node_cost.put(j*10,average_node);
            link_cost.put(j*10,average_edge);
            int median_node = Cal.median_cal(nl_List);
            int median_edge = Cal.median_cal(cl_List);
            node_median.put(j*10,median_node);
            link_median.put(j*10,median_edge);
            double SD_node = Cal.standard_deviation_cal(nl_List,average_node);
            double SD_edge = Cal.standard_deviation_cal(cl_List,average_edge);
            node_sd.put(j*10,SD_node);
            link_sd.put(j*10,SD_edge);
        }
        output out = new output();
        out.write_algo(node_cost,link_cost,node_median,link_median,node_sd,link_sd,error_num_list,exe_time_list);
    }
}
