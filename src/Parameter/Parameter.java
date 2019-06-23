package Parameter;
public class Parameter {
    /**ÀŒ±‰ñ”*/
    public int exe_num = 1;
    /**SFCŠÖŒW*/
    /**VNFŠÖŒW*/
    public int VNF_num =10;
    public int VNF_resource_min = 1;
    public int VNF_resource_max = 10;
    /**SFCŠÖ˜A*/
    public int SFC_num = 1;
    public int SFC_VNFnum=3;
    public int SFC_resource_min=1;
    public int SFC_resource_max =10;
    public int failure_num = 3;
    /**Physical NetworkŠÖŒW*/
    /**ƒm[ƒhŠÖŒW*/
    public int node_num = 100;
    public int servicenode = 65;
    public int terminalnode = node_num-servicenode;
    public int node_resource = 200;
    public int node_cost_min = 1;
    public int node_cost_max = 10;
    /**ƒŠƒ“ƒNŠÖŒW*/
    public int link_resource_min=11;
    public int link_resource_max=20;
    public int link_cost_min = 1;
    public int link_cost_max = 10;
    /**NWSƒOƒ‰ƒt*/
    public int k = 2;
    /**ÀŒ±ƒpƒ‰ƒ[ƒ^*/
    /**1:NWS,2:Lattice*/
    public String [] graph_List = {"nws_0.1_","nws_0.5_","lattice_"};
    public int [] graph_list = {1,1,2};
    public String [][] algo_name = {{"1-1_","1-2_"},{"2-1_","2-2_"},{"3-1_","3-2_"}};
    public int [] path_algo_list ={1,2,3};
    public int [] place_algo_list = {1,2};
    public double[] p_list = {0.1,0.5,0};
    /**Œ‹‰Ê‚Ìo—ÍŠÖ˜A*/
    public String path = "/Users/yamadadaiki/OneDrive/Laboratory/M1&2/Œ¤‹†¬‰ÊEl@/Simulation_result/”äŠrÀŒ±/";
    //public String path ="C:\\Users\\Daiki Yamada\\OneDrive\\Laboratory\\M1&2\\Œ¤‹†¬‰ÊEl@\\Simulation_result\\”äŠrÀŒ±\\";
}
