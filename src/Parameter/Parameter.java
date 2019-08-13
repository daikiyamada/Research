package Parameter;
public class Parameter {
    /**実験回数*/
    public int exe_num =100;
/**SFC関係*/
    /**VNF関係*/
    public int VNF_num =10;
    public int VNF_resource_min = 1;
    public int VNF_resource_max = 10;
    /**SFC関連*/
    public int SFC_num_min = 10;
    public int SFC_num_max = 10;
    public int SFC_VNFnum = 3;
    public int SFC_resource_min=1;
    public int SFC_resource_max =10;
    public int failure_num_min= 1;
    public int failure_num_max = 1;
    /**Physical Network関係*/
    /**ノード関係*/
    public int node_num = 300;
    public int servicenode = 250;
    public int terminalnode = node_num-servicenode;
    public int node_resource = 100;
    public int node_cost_max = 10;
    public int node_cost_min = 1;
    /**リンク関係*/
    public int link_resource_min=1;
    public int link_resource_max=10;
    public int link_cost_min = 1;
    public int link_cost_max = 10;
    /**NWSグラフ*/
    public int k =2;
    /**実験パラメータ*/
    /**1:NWS,2:Lattice*/
    /*public String [] graph_List = {"nws_0.1_","nws_0.2_","nws_0.3_"};
    public int [] graph_list = {1,1,1};
    public String [] algo_name = {"提案_","比較１_","一般_"};
    public int [] path_algo_list ={4,1,3};
    public int [] place_algo_list = {3,1,2};
    public double[] p_list = {0.1,0.2,0.3};
    public int [] cost_type = {1};
    public String[] cost_type2 = {"ランダム_"};*/

    public String [] graph_List = {"nws_0.05_"};
    public int [] graph_list = {1};
    public String [] algo_name = {"1-1_"};
    public int [] path_algo_list ={4};
    public int [] place_algo_list = {3};
    public double[] p_list = {0.5};
    public int [] cost_type = {1};
    public String[] cost_type2 = {"ランダム_"};
    /**結果の出力関連*/
    //public String path = "/Users/yamadadaiki/OneDrive/Laboratory/M1&2/研究成果・考察/Simulation_result/比較実験/";
    public String path1 = "C:\\Users\\Daiki Yamada\\Google ドライブ\\実験結果\\実験結果(ランダム)\\";
    public String path2 = "C:\\Users\\Daiki Yamada\\Google ドライブ\\実験結果\\実験結果(比例)\\";
    public String path3 = "C:\\Users\\Daiki Yamada\\Google ドライブ\\実験結果\\実験結果(反比例)\\";
    //public String path ="C:\\Users\\Daiki Yamada\\OneDrive\\Laboratory\\M1&2\\研究成果・考察\\Simulation_result\\比較実験\\";
}
