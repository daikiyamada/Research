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
import Executer.*;
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
    static class exe extends  Executer{}
    public static void main(String[] args) {
        Par par = new Par();
        output out = new output();
        /**tim sortをbubble sortに変更*/
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        /**Executerの定義*/
        exe exe = new exe();
        for(int i=0;i<par.graph_list.length;i++){
            for(int j=0;j<par.path_algo_list.length;j++){
                for(int k =0;k<par.place_algo_list.length;k++){
                    exe.Executer(par.graph_List[i],par.algo_name[j][k],par.graph_list[i],par.path_algo_list[j],par.place_algo_list[k],par.p_list[i]);
                }
            }
        }
    }
}
