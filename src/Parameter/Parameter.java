package Parameter;

public class Parameter {
    /**������*/
    public int exe_num = 1000;
    /**SFC�֌W*/
    /**VNF�֌W*/
    public int VNF_num =10;
    public int VNF_resource_min = 1;
    public int VNF_resource_max = 10;
    /**SFC�֘A*/
    public int SFC_VNFnum=3;
    public int SFC_resource_min=1;
    public int SFC_resource_max =10;
    public int failure_num = 1;
    /**Physical Network�֌W*/
    /**�m�[�h�֌W*/
    public int node_num = 100;
    public int servicenode = 70;
    public int terminalnode = node_num-servicenode;
    public int node_resource = 100;
    public int node_cost_min = 1;
    public int node_cost_max = 10;
    /**�����N�֌W*/
    public int link_resource_min=3;
    public int link_resource_max =10;
    public int link_cost_min = 1;
    public int link_cost_max = 10;
    /**NWS�O���t*/
    public double p = 0.01;
    public int k = 2;

    /**���ʂ̏o�͊֘A*/
    public String file_name = "nws_";
    //public String path = "/Users/yamadadaiki/OneDrive/Laboratory/M1&2/�������ʁE�l�@/�V�~�����[�V��������/��r����/";
    public String path ="C:\\Users\\Daiki Yamada\\OneDrive\\Laboratory\\M1&2\\�������ʁE�l�@\\Simulation_result\\��r����\\";
    public String path_algo = "algo1-1_";
    public String placement_algo = "fn"+failure_num+"_";
}
