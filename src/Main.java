import Parameter.Parameter;
import Executer.*;
public class Main {
    private static class Par extends Parameter{}
    private static class exe extends  Executer{}
    public static void main(String[] args) {
        Par par = new Par();
        /**tim sortをbubble sortに変更*/
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        /**Executerの定義*/
        exe exe = new exe();
        /**実験開始*/

        /**グラフの種類を変更*/
        for(int i=0;i<par.graph_list.length;i++){
            /**パスアルゴリズムの選択*/
            for(int j=0;j<par.path_algo_list.length;j++){
                /**配置アルゴリズムの選択*/
                for(int k =0;k<par.place_algo_list.length;k++){
                    /**コストの与え方*/
                    for(int l=0;l<par.cost_type.length;l++){
                        /**実行*/
                        exe.Executer(par.graph_List[i],par.algo_name[j][k],par.graph_list[i],par.path_algo_list[j],par.place_algo_list[k],par.p_list[i],par.cost_type[l]);
                    }
                }
            }
        }
    }
}
