package Executer;
import Input.*;
import Output.Visualization;
import SFC.*;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import java.util.*;
import Parameter.*;
public class Executer {
    public void Executer(String gn,String algo_name,int graph_type,int path_algo_num,int place_algo_num,double p) {
        Parameter par = new Parameter();
        /**障害数の設定*/
        for (int i = par.failure_num_min; i <= par.failure_num_max; i++) {
            /**結果の表項目を作成*/
            Output.Result out = new Output.Result();
            out.write_algo1(i, gn, algo_name);
            ArrayList<Integer> cl_List = new ArrayList<>();
            ArrayList<Integer> nl_List = new ArrayList<>();
            /**サービス数の設定*/
            for (int j = par.SFC_num_min; j <= par.SFC_num_max; j++) {
                /**エラー数計算のための変数*/
                int error_num = 0;
                int error_num2 = 0;
                int error_num3 = 0;
                /**途中経過表示*/
                System.out.println(gn + algo_name + "fn_" + i + "SFC:" + j * 10 + ":start");
                Date date = new Date();
                System.out.println(date);
                long start = System.currentTimeMillis();
                /**実験数*/
                for (int k = 0; k < par.exe_num; k++) {
                    /**途中経過の表示*/
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
                    /**Graphの定義*/
                    Graph<MyNode, MyEdge> graph = new UndirectedSparseGraph<>();
                    /**Node作成*/
                    Input.MyNode_Maker n = new Input.MyNode_Maker();
                    graph = n.MyNode_Maker();
                    /**graphクラスの定義*/
                    Input.NWS_Maker nws = new Input.NWS_Maker();
                    Input.Lattice_GraphMaker lat = new Input.Lattice_GraphMaker();
                    if (graph_type ==1) graph = nws.NWS_GraphMaker(graph, p);
                    else if (graph_type == 2) graph = lat.LatticeGraph_Maker(graph);
                   // Visualization.Layout_Graph(graph);
                    /**VNF_Listの構成*/
                    SFC.VNF_Maker vnf = new SFC.VNF_Maker();
                    ArrayList<MyVNF> VNF_List = vnf.VNF_Maker();
                    /**SFCの構成*/
                    SFC.SFC_Maker sfc = new SFC.SFC_Maker();
                    Collection<MyNode> node1 = graph.getVertices();
                    ArrayList<MyNode> node2 = new ArrayList<>(node1);
                    ArrayList<MySFC> S = sfc.SFCMaker(node2, VNF_List, j * 10);
                    /**パスアルゴリズム*/
                    Path.Algorithm_Based_MECF al1 = new Path.Algorithm_Based_MECF();
                    Path.Algorithm_Based_MECF_sort al2 = new Path.Algorithm_Based_MECF_sort();
                    Path.Algorithm_Based_MECF_usual al3 = new Path.Algorithm_Based_MECF_usual();
                    Map<MySFC, ArrayList<Graph<MyNode, MyEdge>>> Path_set = new HashMap<>();
                    if (path_algo_num == 1) Path_set = al1.Routing_Algo(graph, S, k, i, gn, algo_name);
                    else if (path_algo_num==2) Path_set = al2.Routing_Algo(graph, S, k, i, gn, algo_name);
                    else if (path_algo_num==3) Path_set = al3.Routing_Algo(graph, S, k, i, gn, algo_name);
                    /**配置アルゴリズム*/
                    Placement.Algorithm_Based_GAP alp1 = new Placement.Algorithm_Based_GAP();
                    Placement.Algorithm_FF_front alp2 = new Placement.Algorithm_FF_front();
                    if(Value.cost_link!=0){
                        if (place_algo_num == 1) alp1.Placement_Algo(graph, S, Path_set, i, k, gn, algo_name);
                        else if (place_algo_num == 2) alp2.Placement_FF_front(graph, S, Path_set, i,k, gn, algo_name);
                    }
                    /**コストの計算*/
                    if (Value.cost_link == 0 || Value.cost_node == 0) error_num++;
                    if (Value.cost_link == 0) error_num2++;
                    if (Value.cost_node == 0) error_num3++;
                    if (Value.cost_node != 0 && Value.cost_link != 0) {
                        cl_List.add(Value.cost_link);
                        nl_List.add(Value.cost_node);
                        out.write_each_result(Value.cost_node,Value.cost_link,gn,algo_name,i,S.size(),graph.getEdgeCount());
                    }
                    node2.clear();
                    S.clear();
                    Path_set.clear();
                }
                long end = System.currentTimeMillis();
                long time = end - start;
                System.out.println(gn + algo_name + "fn_" + i + "SFC:" + j * 10 + ":finish");
                /**実行時間の算出*/
                /**結果の出力*/
                /**エラー数の算出*/
                System.out.println(error_num);
                /**平均値、中央値、標準偏差の算出*/
                Output.Calculation Cal = new Output.Calculation();
                double average_node = Cal.average_cal(nl_List);
                double average_edge = Cal.average_cal(cl_List);
                int median_node = Cal.median_cal(nl_List);
                int median_edge = Cal.median_cal(cl_List);
                double SD_node = Cal.standard_deviation_cal(nl_List, average_node);
                double SD_edge = Cal.standard_deviation_cal(cl_List, average_edge);
                out.write_algo(j * 10, average_node, average_edge, median_node, median_edge, SD_node, SD_edge, error_num, error_num2, error_num3, time, i, gn, algo_name);
                nl_List.clear();
                cl_List.clear();
            }
        }
    }
}
