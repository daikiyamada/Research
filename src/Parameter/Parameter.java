package Parameter;
public class Parameter {
    /**������*/
    public int exe_num =2000;
    /**VNF�֌W*/
    /**VNF�W���Ɋ܂܂��VNF��*/
    public int VNF_num =30;
    /**VNF�v���v�Z���\�[�X�̍ŏ��l�E�ő�l*/
    public int VNF_resource_min = 1;
    public int VNF_resource_max = 30;
    /**SFC�֘A*/
    /**SFC���ŏ��l�E�ő�l*/
    public int SFC_num_min = 5;
    public int SFC_num_max = 5;
    public int SFC_VNFnum = 3;
    public int SFC_resource_min=1;
    public int SFC_resource_max =10;
    public int failure_num_min= 1;
    public int failure_num_max = 2;
    /**�����l�b�g���[�N�֌W*/
    /**�m�[�h��*/
    public int node_num = 400;
    /**�m�[�h���\�[�X�ʍŏ��l�E�ő�l*/
    public int node_resource_min = 100;
    public int node_resource_max = 500;
    /**���\�[�X�P���i�ŏ��l�E�ő�l�j*/
    public int node_cost_max = 30;
    public int node_cost_min = 1;
    /**�����N�֌W*/
    public int link_resource_min=1;
    public int link_resource_max=5;
    public int link_cost_min = 1;
    public int link_cost_max = 30;
    /**NWS�O���t*/
    public int k =3;
    /**�����p�����[�^*/
    public String [] graph_List = {"nws_0.4_"};
    public int [] graph_list = {1};
    public String [] algo_name = {"���2_"};
    public int [] path_algo_list ={2};
    public int [] place_algo_list = {2};
    public double[] p_list = {0.4};
    public int [] cost_type = {1};
    public String[] cost_type2 = {"�����_��_"};

    /**���ʂ̏o�͊֘A*/
    public String path1 = "/Users/yamadadaiki/OneDrive/Laboratory/M1&2/�������ʁE�l�@/Simulation_result/��r����/";
    //public String path1 = "C:\\Users\\Daiki Yamada\\Google �h���C�u\\��������2\\��������(�����_��)\\";
    public String path2 = "C:\\Users\\Daiki Yamada\\Google �h���C�u\\��������\\��������(���)\\";
    public String path3 = "C:\\Users\\Daiki Yamada\\Google �h���C�u\\��������\\��������(�����)\\";
    //public String path ="C:\\Users\\Daiki Yamada\\OneDrive\\Laboratory\\M1&2\\�������ʁE�l�@\\Simulation_result\\��r����\\";
}
