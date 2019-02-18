package Parameter;

public class Parameter {
    /**Physical Network関係*/
    /**ノード関係*/
    public int node_num = 10;
    public int servicenode = 7;
    public int terminalnode = node_num-servicenode;
    public int node_resource_min = 50;
    public int node_resource_max = 100;
    public int node_cost_min = 5;
    public int node_cost_max = 15;
    /**リンク関係*/
    public int link_resource_min=50;
    public int link_resource_max =300;
    public int link_cost_min = 5;
    public int link_cost_max = 20;
    /**VNF関係*/
    public int VNF_num =10;
    public int VNF_resource_min = 5;
    public int VNF_resource_max = 15;
    /**SFC関連*/
    public int SFC_num=10;
    public int SFC_VNFnum=3;
    public int SFC_resource_min=5;
    public int SFC_resource_max =15;
}
