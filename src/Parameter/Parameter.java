package Parameter;

public class Parameter {
    /**ΐ±ρ*/
    public int exe_num = 1000;
    /**SFCΦW*/
    /**VNFΦW*/
    public int VNF_num =10;
    public int VNF_resource_min = 1;
    public int VNF_resource_max = 10;
    /**SFCΦA*/
    public int SFC_VNFnum=3;
    public int SFC_resource_min=1;
    public int SFC_resource_max =10;
    public int failure_num = 1;
    /**Physical NetworkΦW*/
    /**m[hΦW*/
    public int node_num = 100;
    public int servicenode = 70;
    public int terminalnode = node_num-servicenode;
    public int node_resource = 100;
    public int node_cost_min = 1;
    public int node_cost_max = 10;
    /**NΦW*/
    public int link_resource_min=3;
    public int link_resource_max =10;
    public int link_cost_min = 1;
    public int link_cost_max = 10;
    /**NWSOt*/
    public double p = 0.01;
    public int k = 2;

    /**ΚΜoΝΦA*/
    public String file_name = "nws_";
    //public String path = "/Users/yamadadaiki/OneDrive/Laboratory/M1&2/€¬ΚEl@/V~[VΚ/δrΐ±/";
    public String path ="C:\\Users\\Daiki Yamada\\OneDrive\\Laboratory\\M1&2\\€¬ΚEl@\\Simulation_result\\δrΐ±\\";
    public String path_algo = "algo1-1_";
    public String placement_algo = "fn"+failure_num+"_";
}
