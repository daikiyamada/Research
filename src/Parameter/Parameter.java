package Parameter;
public class Parameter {
    /**������*/
    public int exe_num =100;
/**SFC�֌W*/
    /**VNF�֌W*/
    public int VNF_num =10;
    public int VNF_resource_min = 1;
    public int VNF_resource_max = 10;
    /**SFC�֘A*/
    public int SFC_num_min = 10;
    public int SFC_num_max = 10;
    public int SFC_VNFnum = 3;
    public int SFC_resource_min=1;
    public int SFC_resource_max =10;
    public int failure_num_min= 1;
    public int failure_num_max = 1;
    /**Physical Network�֌W*/
    /**�m�[�h�֌W*/
    public int node_num = 300;
    public int servicenode = 250;
    public int terminalnode = node_num-servicenode;
    public int node_resource = 100;
    public int node_cost_max = 10;
    public int node_cost_min = 1;
    /**�����N�֌W*/
    public int link_resource_min=1;
    public int link_resource_max=10;
    public int link_cost_min = 1;
    public int link_cost_max = 10;
    /**NWS�O���t*/
    public int k =2;
    /**�����p�����[�^*/
    /**1:NWS,2:Lattice*/
    /*public String [] graph_List = {"nws_0.1_","nws_0.2_","nws_0.3_"};
    public int [] graph_list = {1,1,1};
    public String [] algo_name = {"���_","��r�P_","���_"};
    public int [] path_algo_list ={4,1,3};
    public int [] place_algo_list = {3,1,2};
    public double[] p_list = {0.1,0.2,0.3};
    public int [] cost_type = {1};
    public String[] cost_type2 = {"�����_��_"};*/

    public String [] graph_List = {"nws_0.05_"};
    public int [] graph_list = {1};
    public String [] algo_name = {"1-1_"};
    public int [] path_algo_list ={4};
    public int [] place_algo_list = {3};
    public double[] p_list = {0.5};
    public int [] cost_type = {1};
    public String[] cost_type2 = {"�����_��_"};
    /**���ʂ̏o�͊֘A*/
    //public String path = "/Users/yamadadaiki/OneDrive/Laboratory/M1&2/�������ʁE�l�@/Simulation_result/��r����/";
    public String path1 = "C:\\Users\\Daiki Yamada\\Google �h���C�u\\��������\\��������(�����_��)\\";
    public String path2 = "C:\\Users\\Daiki Yamada\\Google �h���C�u\\��������\\��������(���)\\";
    public String path3 = "C:\\Users\\Daiki Yamada\\Google �h���C�u\\��������\\��������(�����)\\";
    //public String path ="C:\\Users\\Daiki Yamada\\OneDrive\\Laboratory\\M1&2\\�������ʁE�l�@\\Simulation_result\\��r����\\";
}
