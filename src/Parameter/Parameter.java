package Parameter;
public class Parameter {
    /**実験回数*/
    public int exe_num =2000;
    /**VNF関係*/
    /**VNF集合に含まれるVNF数*/
    public int VNF_num =30;
    /**VNF要求計算リソースの最小値・最大値*/
    public int VNF_resource_min = 1;
    public int VNF_resource_max = 30;
    /**SFC関連*/
    /**SFC数最小値・最大値*/
    public int SFC_num_min = 5;
    public int SFC_num_max = 5;
    public int SFC_VNFnum = 3;
    public int SFC_resource_min=1;
    public int SFC_resource_max =10;
    public int failure_num_min= 1;
    public int failure_num_max = 2;
    /**物理ネットワーク関係*/
    /**ノード数*/
    public int node_num = 400;
    /**ノードリソース量最小値・最大値*/
    public int node_resource_min = 100;
    public int node_resource_max = 500;
    /**リソース単価（最小値・最大値）*/
    public int node_cost_max = 30;
    public int node_cost_min = 1;
    /**リンク関係*/
    public int link_resource_min=1;
    public int link_resource_max=5;
    public int link_cost_min = 1;
    public int link_cost_max = 30;
    /**NWSグラフ*/
    public int k =3;
    /**実験パラメータ*/
    public String [] graph_List = {"nws_0.4_"};
    public int [] graph_list = {1};
    public String [] algo_name = {"一般2_"};
    public int [] path_algo_list ={2};
    public int [] place_algo_list = {2};
    public double[] p_list = {0.4};
    public int [] cost_type = {1};
    public String[] cost_type2 = {"ランダム_"};

    /**結果の出力関連*/
    public String path1 = "/Users/yamadadaiki/OneDrive/Laboratory/M1&2/研究成果・考察/Simulation_result/比較実験/";
    //public String path1 = "C:\\Users\\Daiki Yamada\\Google ドライブ\\実験結果2\\実験結果(ランダム)\\";
    public String path2 = "C:\\Users\\Daiki Yamada\\Google ドライブ\\実験結果\\実験結果(比例)\\";
    public String path3 = "C:\\Users\\Daiki Yamada\\Google ドライブ\\実験結果\\実験結果(反比例)\\";
    //public String path ="C:\\Users\\Daiki Yamada\\OneDrive\\Laboratory\\M1&2\\研究成果・考察\\Simulation_result\\比較実験\\";
}
