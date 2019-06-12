import Input.MyEdge;
import Input.MyNode;
import Path.OPT_Path_Maker;
import Placement.Algorithm_Based_GAP;
import Placement.Algorithm_FF_front;
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
    static class algo2 extends Algorithm_Based_MECF_sort{}
    static class algo3 extends Algorithm_Based_MECF_usual{}
    static class algop1 extends Algorithm_Based_GAP{}
    static class algop2 extends Algorithm_FF_front {}

    static class cal extends Calculation{}

    public static void main(String[] args){
        Par par = new Par();
        output out = new output();
        System.setProperty("java.util.Arrays.useLegacyMergeSort","true");
        out.write_algo1();
        ArrayList<Integer> cl_List = new ArrayList<>();
        ArrayList<Integer> nl_List = new ArrayList<>();
        /**SFC数を変えての実験*/
        for(int j=1;j<=5;j++) {
            /**エラー数計算のための変数*/
            int error_num=0;
            int error_num2=0;
            int error_num3=0;
            /**途中経過表示*/
            System.out.println("SFC:" + j * 10 + ":start");
            Date date = new Date();
            System.out.println(date);
            long start = System.currentTimeMillis();
            /**実験*/
            for (int i = 0; i < par.exe_num; i++) {
                /**途中経過*/
                if (i % 100 == 0) {
                    Date date2 = new Date();
                    System.out.println(i + ":" + date2);
                }
                /**コストの初期化*/
                Value.c_n.clear();
                Value.c_e.clear();
                Value.r_n.clear();
                Value.r_e.clear();
                Value.cost_link = 0;
                Value.cost_node = 0;
                /**Graph*/
                Graph<MyNode, MyEdge> graph = new UndirectedSparseGraph<>();
                /**Node*/
                node n = new node();
                graph = n.MyNode_Maker();
                /**NWS*/
                NWSGraph nws = new NWSGraph();
                graph = nws.NWS_GraphMaker(graph);
                /**Lattice*/
                //LatticeGraph lat = new LatticeGraph();
                //graph = lat.LatticeGraph_Maker(graph);
                /**Connected_ER*/
                //Connected_ER ce = new Connected_ER();
                //graph = ce.generator(graph);
                /**SFCの構成*/
                VNF vnf = new VNF();
                ArrayList<MyVNF> VNF_List = vnf.VNF_Maker();
                SFC sfc = new SFC();
                Collection<MyNode> node1 = graph.getVertices();
                ArrayList<MyNode> node2 = new ArrayList<>(node1);
                ArrayList<MySFC> S = sfc.SFCMaker(node2, VNF_List, j * 10);
                /**パスアルゴリズム*/
                algo1 al1 = new algo1();
//              algo2 al2 = new algo2();
//              algo3 al3 = new algo3();
                Map<MySFC, ArrayList<Graph<MyNode, MyEdge>>> Path_set = al1.Routing_Algo1(graph, S, par.failure_num, i);
                //Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> Path_set =al2.Routing_Algo1(graph,S,par.failure_num);
                //Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> Path_set =al3.Routing_Algo1(graph,S,par.failure_num);
                /**VNF Placement*/
                /**GAPベースのアルゴリズム*/
                algop1 alp1 = new algop1();
                if(Value.cost_link != 0) alp1.Placement_Algo1(graph, S, Path_set, par.failure_num + 1, i);
                if(Value.cost_link==0||Value.cost_node==0)error_num++;
                if(Value.cost_link==0)error_num2++;
                if(Value.cost_node==0)error_num3++;
                node2.clear();
                S.clear();
                Path_set.clear();
            }
                long end = System.currentTimeMillis();
                long time = end - start;
                System.out.println("SFC:" + j * 10 + ":finish");
                /**実行時間の算出*/
                /**結果の出力*/
                /**エラー数の算出*/
                System.out.println(error_num);
                /**平均値、中央値、標準偏差の算出*/
                cal Cal = new cal();
                double average_node = Cal.average_cal(nl_List);
                double average_edge = Cal.average_cal(cl_List);
                int median_node = Cal.median_cal(nl_List);
                int median_edge = Cal.median_cal(cl_List);
                double SD_node = Cal.standard_deviation_cal(nl_List, average_node);
                double SD_edge = Cal.standard_deviation_cal(cl_List, average_edge);
                out.write_algo(j*10,average_node,average_edge,median_node,median_edge,SD_node,SD_edge,error_num,error_num2,error_num3,time);
                nl_List.clear();
                cl_List.clear();
                }
    }
}
