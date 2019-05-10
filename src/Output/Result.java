package Output;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import Input.MyEdge;
import Input.MyNode;
import Parameter.Parameter;
import SFC.MySFC;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.event.GraphEvent;

public class Result {
    /**結果の出力や分析のためのクラス*/
    public void write_algo(Map<Integer,Double> ave_node,Map<Integer,Double> ave_link,Map<Integer,Integer> med_node,Map<Integer,Integer> med_link,Map<Integer,Double> sd_node,Map<Integer,Double> sd_link,Map<Integer,Integer> error, Map<Integer,Long> exetime){
        Parameter par = new Parameter();
        String Path = new String();
        /**日付の選択*/
        Date now = new Date();
        DateFormat YMD = new SimpleDateFormat("YYYYMMDD");
        Path = par.path+par.file_name+par.path_algo+par.placement_algo+YMD.format(now)+".csv";
        File file = new File(Path);
        PrintWriter pw;
        try {
            FileWriter fw = new FileWriter(Path, true);
            pw = new PrintWriter(Path);
        } catch(IOException ex){
            System.out.println("ファイルの出力に失敗しました");
            return;
        }
        /**結果をcsvファイルに出力*/
        /**SFC数の出力*/
        pw.print("SFC数"+",");
        for(int i=1;i<=10;i++) pw.print(i*10+",");
        pw.println();
        /**表の作成*/
        pw.print("ノードコスト値"+",");
        for(int i=1;i<=10;i++) pw.print(ave_node.get(i*10)+",");
        pw.println();
        pw.print("ノード中央値"+",");
        for(int i=1;i<=10;i++) pw.print(med_node.get(i*10)+",");
        pw.println();
        pw.print("ノード標準偏差"+",");
        for(int i=1;i<=10;i++) pw.print(sd_node.get(i*10)+",");
        pw.println();
        pw.print("リンクコスト値"+",");
        for(int i=1;i<=10;i++) pw.print(ave_link.get(i*10)+",");
        pw.println();
        pw.print("リンク中央値"+",");
        for(int i=1;i<=10;i++) pw.print(med_link.get(i*10)+",");
        pw.println();
        pw.print("リンク標準偏差"+",");
        for(int i=1;i<=10;i++) pw.print(sd_link.get(i*10)+",");
        pw.println();
        pw.print("エラー数"+",");
        for(int i=1;i<=10;i++) pw.print(error.get(i*10)+",");
        pw.println();
        pw.print("実行時間"+",");
        for(int i=1;i<=10;i++) pw.print(exetime.get(i*10)+",");
        pw.println();

        pw.close();
    }

    public int Total_Placement_Calculator(ArrayList<Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>>> Path_List,ArrayList<MySFC> S){
        ArrayList<Integer> error_num = new ArrayList<>();
        /**配置組み合わせ数算出*/
        int total = 0;
        for(Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> List:Path_List){
            int num =1;
            for(int i=0;i<List.size();i++){
                ArrayList<Graph<MyNode,MyEdge>> Graph_List = new ArrayList<>(List.get(S.get(i)));
                for(Graph<MyNode,MyEdge> graph:Graph_List){
                    Collection<MyNode> node_list1 = graph.getVertices();
                    ArrayList<MyNode> node_list2 = new ArrayList<>(node_list1);
                    int service =0;
                    for(MyNode n : node_list2) if(n.Node_ID.equals("s")==true) service++;
                    if(service>0)for(int d=0;d<S.get(i).VNF.size();d++)num*=service;
                }
            }
            total+=num;
        }
        return total;
    }
    /**バグチェック出力関数*/
    public int Total_Placement_Calculator2(Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> Path_List,ArrayList<MySFC> S){
        int num=1;
        /***/
        for(int i =0;i<Path_List.size();i++){
            ArrayList<Graph<MyNode,MyEdge>> graph = new ArrayList<>(Path_List.get(S.get(i)));
            for(int j=0;j<graph.size();j++){
                /**１つのパスでの配置数*/
                Graph<MyNode,MyEdge> Path = graph.get(j);
                Collection<MyNode> node_list = Path.getVertices();
                ArrayList<MyNode> node_list2 = new ArrayList<>(node_list);
                int service =1;
                for(int d=0;d<node_list2.size();d++) if(node_list2.get(d).Node_ID.equals("s")==true) service++;
                if(service>1){
                   for(int k=0;k<S.get(i).VNF.size();k++) num*=(service-1);
                }
                else if(service==1)num*=service;

            }
        }
        return num;
    }

}
