package Parameter;
public class Parameter {
    /**ΐ±ρ*/
    public int exe_num = 1;
    /**SFCΦW*/
    /**VNFΦW*/
    public int VNF_num =10;
    public int VNF_resource_min = 1;
    public int VNF_resource_max = 10;
    /**SFCΦA*/
    public int SFC_num = 1;
    public int SFC_VNFnum=3;
    public int SFC_resource_min=1;
    public int SFC_resource_max =10;
    public int failure_num = 3;
    /**Physical NetworkΦW*/
    /**m[hΦW*/
    public int node_num = 100;
    public int servicenode = 65;
    public int terminalnode = node_num-servicenode;
    public int node_resource = 200;
    public int node_cost_min = 1;
    public int node_cost_max = 10;
    /**NΦW*/
    public int link_resource_min=11;
    public int link_resource_max=20;
    public int link_cost_min = 1;
    public int link_cost_max = 10;
    /**NWSOt*/
    public int k = 2;
    /**ΐ±p[^*/
    /**1:NWS,2:Lattice*/
    public String [] graph_List = {"nws_0.1_","nws_0.5_","lattice_"};
    public int [] graph_list = {1,1,2};
    public String [][] algo_name = {{"1-1_","1-2_"},{"2-1_","2-2_"},{"3-1_","3-2_"}};
    public int [] path_algo_list ={1,2,3};
    public int [] place_algo_list = {1,2};
    public double[] p_list = {0.1,0.5,0};
    /**ΚΜoΝΦA*/
    public String path = "/Users/yamadadaiki/OneDrive/Laboratory/M1&2/€¬ΚEl@/Simulation_result/δrΐ±/";
    //public String path ="C:\\Users\\Daiki Yamada\\OneDrive\\Laboratory\\M1&2\\€¬ΚEl@\\Simulation_result\\δrΐ±\\";
}
