package Executer;
import Input.*;
import Output.Visualization;
import SFC.*;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import java.util.*;
import Parameter.*;
public class Executer {
    public void Executer(String gn,String algo_name,int graph_type,int path_algo_num,int place_algo_num,double p,int cost_type) {
        Parameter par = new Parameter();
        Output.Calculation Cal = new Output.Calculation();
        /**障害数の設定*/
        for (int i = par.failure_num_min; i <= par.failure_num_max; i++) {
            /**結果の表項目を作成*/
            Output.Result out = new Output.Result();
            out.write_algo1(i, gn, algo_name,cost_type);
            /**サービス数の設定*/
            for (int j = par.SFC_num_min; j <= par.SFC_num_max; j++) {
                ArrayList<Integer> cl_List = new ArrayList<>();
                ArrayList<Integer> nl_List = new ArrayList<>();
                /**エラー数計算のための変数*/
                int error_num = 0;
                int error_num2 = 0;
                int error_num3 = 0;
                /**詳細情報のリスト*/
                ArrayList<Double> Path_length = new ArrayList<>();
                /**途中経過表示*/
                System.out.println(gn + algo_name + "SFC:" + j * 10 + ":start");
                Date date = new Date();
                System.out.println(date);
                long start = System.currentTimeMillis();
                /**実験数*/
                for (int k = 0; k < par.exe_num; k++) {
                    /**途中経過の表示*/
                    if (k % 100 == 0) {
                        Date date2 = new Date();
                        System.out.println(k + ":" + date2);
                    }
                    /**コストの初期化*/
                    Value.c_n.clear();
                    Value.c_e.clear();
                    Value.r_n.clear();
                    Value.r_e.clear();
                    Value.cost_link = 0;
                    Value.cost_node = 0;
                    /**Graphの定義*/
                    Graph<MyNode, MyEdge> graph = new SparseGraph<MyNode,MyEdge>();
                    /**Node作成*/
                    Input.MyNode_Maker n = new Input.MyNode_Maker();
                    graph = n.MyNode_Maker(cost_type);
                    /**graphクラスの定義*/
                    Input.NWS_Maker nws = new Input.NWS_Maker();
                    Input.Lattice_GraphMaker lat = new Input.Lattice_GraphMaker();
                    if (graph_type ==1) graph = nws.NWS_GraphMaker(graph, p,cost_type);
                    else if (graph_type == 2) graph = lat.LatticeGraph_Maker(graph,cost_type);
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
                    Path.Algorithm2 al4 = new Path.Algorithm2();
                    Map<MySFC, ArrayList<Graph<MyNode, MyEdge>>> Path_set = new HashMap<>();
                    if (path_algo_num == 1) Path_set = al1.Routing_Algo(graph, S, i);
                    else if (path_algo_num==2) Path_set = al2.Routing_Algo(graph, S,i);
                    else if (path_algo_num==3) Path_set = al3.Routing_Algo(graph, S,i);
                    else if(path_algo_num==4) Path_set = al4.KNode_Disjoint_Path(graph,S,i);
                    /**配置アルゴリズム*/
                    Placement.Algorithm_Based_GAP alp1 = new Placement.Algorithm_Based_GAP();
                    Placement.Algorithm_FF_front alp2 = new Placement.Algorithm_FF_front();
                    Placement.Deployment_Algorithm2 alp3 = new Placement.Deployment_Algorithm2();
                    /**パスが成功時に配置に移動*/
                    if(Value.cost_link!=0){
                        /**パスの長さ*/
                        double ave_path_length = Cal.average_path_length(Path_set,S);
                        Path_length.add(ave_path_length);
                        /**配置アルゴリズムの実行*/
                        if (place_algo_num == 1) alp1.Placement_Algo(graph, S, Path_set, i);
                        else if (place_algo_num == 2) alp2.Placement_FF_front(graph, S, Path_set, i);
                        else if(place_algo_num == 3) alp3.Deploy_algo2(graph,Path_set,S,i);
                    }
                    /**コストの計算*/
                    if (Value.cost_link == 0 || Value.cost_node == 0) error_num++;
                    if (Value.cost_link == 0) error_num2++;
                    if (Value.cost_link!=0&&Value.cost_node == 0) error_num3++;
                    if (Value.cost_node != 0 && Value.cost_link != 0) {
                        cl_List.add(Value.cost_link);
                        nl_List.add(Value.cost_node);
                        out.write_each_result(Value.cost_node,Value.cost_link,gn,algo_name,i,S.size(),cost_type);
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
                /**平均値、中央値、標準偏差の算出*/
                double average_node = Cal.average_cal(nl_List);
                double average_edge = Cal.average_cal(cl_List);
                int median_node = Cal.median_cal(nl_List);
                int median_edge = Cal.median_cal(cl_List);
                double SD_node = Cal.standard_deviation_cal(nl_List, average_node);
                double SD_edge = Cal.standard_deviation_cal(cl_List, average_edge);
                out.write_algo(j * 10, average_node, average_edge, median_node, median_edge, SD_node, SD_edge, error_num, error_num2, error_num3, time, i, gn, algo_name,cost_type);
                nl_List.clear();
                cl_List.clear();
                    /**パスの長さの出力*/
                    double ave = Cal.average_cal2(Path_length);
                    double std = Cal.standard_deviation_cal2(Path_length, ave);
                    out.path_length_writer(ave, std, gn, algo_name, i, j * 10, cost_type);
                /**辺使用率の算出*/
                    Map<Integer, Double> ave_list1 = Cal.average_cal3(Value.Util_Edge_List);
                    Map<Integer, Double> ave_list2 = Cal.average_cal4(Value.Edge_Total_num);
                    Map<Integer, Double> ave_list3 = Cal.average_cal4(Value.Edge_Used_num);
                    Map<Integer, Double> std_list1 = Cal.standard_deviation_cal3(Value.Util_Edge_List, ave_list1);
                    Map<Integer, Double> std_list2 = Cal.standard_deviation_cal4(Value.Edge_Total_num, ave_list2);
                    Map<Integer, Double> std_list3 = Cal.standard_deviation_cal4(Value.Edge_Used_num, ave_list3);
                    out.edge_info_writer(ave_list2, std_list2, ave_list3, std_list3, gn, algo_name, i, j * 10, cost_type);
                    out.edge_utilization_writer(ave_list1, std_list1, gn, algo_name, i, j * 10, cost_type);
                    std_list1.clear();
                    ave_list1.clear();
                    std_list2.clear();
                    ave_list2.clear();
                    std_list3.clear();
                    ave_list3.clear();

                /**頂点詳細情報*/
                   ave_list1 = Cal.average_cal3(Value.Util_Node_List);
                    ave_list2 = Cal.average_cal4(Value.Node_Total_num);
                    ave_list3 = Cal.average_cal4(Value.Node_Used_num);
                    std_list1 = Cal.standard_deviation_cal3(Value.Util_Node_List, ave_list1);
                    std_list2 = Cal.standard_deviation_cal4(Value.Node_Total_num, ave_list2);
                    std_list3 = Cal.standard_deviation_cal4(Value.Node_Used_num, ave_list3);
                    out.node_info_writer(ave_list2, std_list2, ave_list3, std_list3, gn, algo_name, i, j * 10, cost_type);
                    out.node_utilization_writer(ave_list1, std_list1, gn, algo_name, i, j * 10, cost_type);
                    std_list1.clear();
                    ave_list1.clear();
                    std_list2.clear();
                    ave_list2.clear();
                    std_list3.clear();
                    ave_list3.clear();
                Value.Util_Edge_List.clear();
                Value.Edge_Used_num.clear();
                Value.Edge_Total_num.clear();
                Value.Util_Node_List.clear();
                Value.Node_Used_num.clear();
                Value.Node_Total_num.clear();
            }
        }
    }
}
