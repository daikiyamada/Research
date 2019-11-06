import Parameter.Parameter;
import Executer.*;
public class Main {
    private static class Par extends Parameter{}
    private static class exe extends  Executer{}
    public static void main(String[] args) {
        Par par = new Par();
        /**要素数32以上になった場合、バグが出るため、tim sortをbubble sortに変更*/
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        /**Executerの定義*/
        exe exe = new exe();
        /**グラフの種類を変更（詳細はParameterに記載している）*/
        for(int i=0;i<par.graph_list.length;i++){
            /**パスアルゴリズムの選択（詳細はParameterに記載している）*/
            for(int j=0;j<par.path_algo_list.length;j++){
                    /**コストの与え方（詳細はParameterに記載している）*/
                    for(int l=0;l<par.cost_type.length;l++){
                        /**実行関数*/
                        exe.Executer(par.graph_List[i],par.algo_name[j],par.graph_list[i],par.path_algo_list[j],par.place_algo_list[j],par.p_list[i],par.cost_type[l]);
                    }
            }
        }
    }
}
