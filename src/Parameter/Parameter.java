package Parameter;

public class Parameter {
    /**Physical Network関係*/
    /**ノード関係*/
    public int node_num = 9;
    public int servicenode = 6;
    public int terminalnode = node_num-servicenode;
    public int node_resource_min = 100;
    public int node_resource_max = 300;
    public int node_cost_min = 5;
    public int node_cost_max = 25;
    /**リンク関係*/
    public int link_resource_min=200;
    public int link_resource_max =300;
    public int link_cost_min = 5;
    public int link_cost_max = 20;
    /**NWSグラフ*/
    public double p = 0.1;
    public int k = 3;
    /**SFC関係*/
    /**VNF関係*/
    public int VNF_num =10;
    public int VNF_resource_min = 1;
    public int VNF_resource_max = 15;
    /**SFC関連*/
    public int SFC_num=2;
    public int SFC_VNFnum=2;
    public int SFC_resource_min=5;
    public int SFC_resource_max =25;
    public int failure_num = 1;
    /**結果の出力関連*/
    public String file_name = "Lattice_graph_OPT_result_";
    public String path = "/Users/yamadadaiki/OneDrive/Laboratory/M1&2/研究成果・考察/シミュレーション結果/最適解/";

}
