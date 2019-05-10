package Parameter;

public class Parameter {
    /**実験回数*/
    public int exe_num = 1000;
    /**SFC関係*/
    /**VNF関係*/
    public int VNF_num =10;
    public int VNF_resource_min = 1;
    public int VNF_resource_max = 10;
    /**SFC関連*/
    public int SFC_VNFnum=5;
    public int SFC_resource_min=1;
    public int SFC_resource_max =10;
    public int failure_num = 2;
    /**Physical Network関係*/
    /**ノード関係*/
    public int node_num = 100;
    public int servicenode = 75;
    public int terminalnode = node_num-servicenode;
    public int node_resource = (10*5*100*(failure_num+1))/servicenode;
    public int node_cost_min = 1;
    public int node_cost_max = 10;
    /**リンク関係*/
    public int link_resource_min=1;
    public int link_resource_max =10;
    public int link_cost_min = 1;
    public int link_cost_max = 10;
    /**NWSグラフ*/
    public double p = 0.1;
    public int k = 3;

    /**結果の出力関連*/
    public String file_name = "COM_Lattice_graph_";
    public String path = "/Users/yamadadaiki/OneDrive/Laboratory/M1&2/研究成果・考察/シミュレーション結果/最適解/";
    public String path_algo = "Path1_";
    public String placement_algo = "Placement2_";

}
