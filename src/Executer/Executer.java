package Executer;
import Input.*;
import Output.Visualization;
import SFC.*;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import java.util.*;
import Parameter.*;
public class Executer {
    /**<Variable Explaination>
     * gn:Graph Name
     * algo_name：Algorithm Name
     * graph_type：Type of Graph
     * path_algo_num：The number of Routing Algorithm
     * place_algo_num：The number of Deployment Algorithm
     * p：Possibility of Graph
     * cost_type：The number of Cost type*/
    public void Executer(String gn,String algo_name,int graph_type,int path_algo_num,int place_algo_num,double p,int cost_type,int x,int y,int z) {
        Parameter par = new Parameter();
        Output.Calculation Cal = new Output.Calculation();
        Output.Result out = new Output.Result();
        /**Generator of Output table*/
        out.write_algo1(gn, algo_name,cost_type,x,y,z);
        /**The number of assumed VNF failure*/
        for (int failure_num = par.failure_num_min; failure_num <= par.failure_num_max; failure_num++) {
            /**The nubmer of SFC*/
            for (int SFC_num = par.SFC_num_min; SFC_num <= par.SFC_num_max; SFC_num++) {
                ArrayList<Integer> cl_List = new ArrayList<>();
                ArrayList<Integer> nl_List = new ArrayList<>();
                ArrayList<Long> Time_List = new ArrayList<>();
                /**Variables for calculating the number of fail*/
                int error_sum = 0;
                int error_route = 0;
                int error_deployment = 0;
                /**Output process*/
                System.out.println(gn + algo_name + "failure_num:"+failure_num+"SFC:" + SFC_num * 10 + "("+x+","+y+","+z+")"+":start");
                Date date = new Date();
                System.out.println(date);
                /**The number of execution time*/
                for (int exe_num = 0; exe_num < par.exe_num; exe_num++) {
                    /**Variable for execution time*/
                    long start = System.currentTimeMillis();
                    Value.cycle=true;
                    /**Output process*/
                    if (exe_num % 100 == 0) {
                        Date date2 = new Date();
                        System.out.println(exe_num + ":" + date2);
                        System.out.println("error_num:"+error_sum);
                    }
                    /**Initializing the variable*/
                    /**c:cost, r:capacity, n:node, e:edge*/
                    /**cost: objective function*/
                    Value.c_n.clear();
                    Value.c_e.clear();
                    Value.r_n.clear();
                    Value.r_e.clear();
                    Value.cost_link = 0;
                    Value.cost_node = 0;
                    /**Graph*/
                    Graph<MyNode, MyEdge> graph = new SparseGraph<MyNode,MyEdge>();
                    /**Node Generator*/
                    Input.MyNode_Maker n = new Input.MyNode_Maker();
                    if(graph_type!=3&&graph_type!=5&&graph_type!=6) graph = n.MyNode_Maker(cost_type);
                    /**グラフ作成*/
                    Input.NWS_Maker nws = new Input.NWS_Maker();
                    Input.Lattice_GraphMaker lat = new Input.Lattice_GraphMaker();
                    Input.SINET_GraphMaker sinet = new Input.SINET_GraphMaker();
                    Input.ConnectedERGraph_Maker er = new Input.ConnectedERGraph_Maker();
                    Input.Germany50 germany = new Input.Germany50();
                    Input.Pioro pioro = new Input.Pioro();
                    /**graph_type==1：NWS Graph Generator　
                     * graph_type==2：Lattice Graph Generator
                     * graph_type==3：Sinet Graph
                     * graph_type==4: ER Graph
                     * graph_type==5: Germany50 Graph
                     * graph_type==6: Pioro Graph*/

                    if (graph_type ==1) graph = nws.NWS_GraphMaker(graph, p,cost_type);
                    else if (graph_type == 2) graph = lat.LatticeGraph_Maker(graph,cost_type);
                    else if(graph_type==3) graph = sinet.Sinet_graphmaker(graph,cost_type);
                    else if (graph_type==4) graph = er.generator(graph);
                    else if (graph_type==5) graph = germany.Generator(graph,cost_type);
                    else if (graph_type==6) graph = pioro.Generator(graph,cost_type);
                    /**Generating VNF Set*/
                    SFC.VNF_Maker vnf = new SFC.VNF_Maker();
                    ArrayList<MyVNF> VNF_Set = vnf.VNF_Maker();
                    /**Generating SFC Set*/
                    SFC.SFC_Maker sfc = new SFC.SFC_Maker();
                    SFC.Source_Node_Generator make = new SFC.Source_Node_Generator();
                    Collection<MyNode> node1 = graph.getVertices();
                    ArrayList<MyNode> node2 = new ArrayList<>(node1);
                    if(graph_type==3||graph_type==5||graph_type==6) node2 = make.Generator(graph,node2);
                    ArrayList<MySFC> SFC_Set = sfc.SFCMaker(node2, VNF_Set, SFC_num * 10);
                    /**Path algorithm*/
                    Path.Algorithm_Based_MECF al1 = new Path.Algorithm_Based_MECF();
                    Path.Algorithm_KVDSP al2 = new Path.Algorithm_KVDSP();
                    Path.Algorithm_Based_MECF_usual al3 = new Path.Algorithm_Based_MECF_usual();
                    Path.Algorithm2 al4 = new Path.Algorithm2();
                    Map<MySFC, ArrayList<Graph<MyNode, MyEdge>>> Path_set = new HashMap<>();
                    /**path_algo_num==1:tencon 2019 algorithm
                     * path_algo_num==2:KVDSP
                     * path_algo_num==3:MECF
                     * path_algor_num==4:CIC algorithm*/
                    if (path_algo_num == 1) Path_set = al1.Routing_Algo(graph, SFC_Set, failure_num);
                    else if (path_algo_num==2) Path_set = al2.KNode_Disjoint_Path(graph, SFC_Set,failure_num);
                    else if (path_algo_num==3) Path_set = al3.Routing_Algo(graph, SFC_Set,failure_num);
                    else if(path_algo_num==4) Path_set = al4.KNode_Disjoint_Path(graph,SFC_Set,failure_num);
                    /**Deployment Algorithm*/
                    Placement.Algorithm_Based_GAP alp1 = new Placement.Algorithm_Based_GAP();
                    Placement.Algorithm_FF_front alp2 = new Placement.Algorithm_FF_front();
                    Placement.Deployment_Algorithm2 alp3 = new Placement.Deployment_Algorithm2();
                    /**If the routing is successful, moving to deployment*/
                    if (Value.cost_link != 0) {
                        /**Deployment Algorithm
                         * 1:GAP
                         * 2:FF
                         * 3:GAP with value function*/
                        if (place_algo_num == 1) alp1.Placement_Algo(graph, SFC_Set, Path_set, failure_num);
                        else if (place_algo_num == 2) alp2.Placement_FF_front(graph, SFC_Set, Path_set,failure_num);
                        else if (place_algo_num == 3) alp3.Deploy_algo2(graph, Path_set, SFC_Set, failure_num,x,y,z);
                    }
                    /**Calculating the resource usage and error num*/
                    if (Value.cost_link == 0 || Value.cost_node == 0) error_sum++;
                    if (Value.cost_link == 0) error_route++;
                    if (Value.cost_link != 0 && Value.cost_node == 0) error_deployment++;
                    if (Value.cost_node != 0 && Value.cost_link != 0) {
                        long end = System.currentTimeMillis();
                        long time = end - start;
                        Time_List.add(time);
                        cl_List.add(Value.cost_link);
                        nl_List.add(Value.cost_node);
                        /**Generating the blot data*/
                        out.write_each_result(Value.cost_node, Value.cost_link, gn, algo_name, failure_num,SFC_Set.size(), cost_type,x,y,z);
                    }
                    node2.clear();
                    SFC_Set.clear();
                    Path_set.clear();
                }
                System.out.println(gn + algo_name + "failure_num:" + failure_num + "SFC:" + SFC_num * 10 + "("+x+","+y+","+z+")"+":finish");
                System.out.println(error_sum+":("+error_route+","+error_deployment+")");
                /**Exetime */
                /**結果の出力*/
                /**エラー数の算出*/
                /**平均値、中央値、標準偏差の算出*/
                double average_node = Cal.average_cal(nl_List);
                double average_edge = Cal.average_cal(cl_List);
                long ave_time = Cal.average_cal_long(Time_List);
                int median_node = Cal.median_cal(nl_List);
                int median_edge = Cal.median_cal(cl_List);
                double SD_node = Cal.standard_deviation_cal(nl_List, average_node);
                double SD_edge = Cal.standard_deviation_cal(cl_List, average_edge);
                double error_rate = (double)error_sum/par.exe_num;
                out.write_algo( average_node, average_edge, median_node, median_edge, SD_node, SD_edge, error_sum, error_route, error_deployment, error_rate,ave_time, failure_num, gn, algo_name,cost_type,x,y,z);
                nl_List.clear();
                cl_List.clear();
            }
        }
    }
}