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
    public void write(ArrayList<ArrayList<Integer>>OPT,double time,double error){
        Parameter par = new Parameter();
        String Path = new String();
        /**日付の選択*/
        Date now = new Date();
        DateFormat YMD = new SimpleDateFormat("YYYYMMDD");
        Path = par.path+par.file_name+YMD.format(now)+".csv";
        File file = new File(Path);
        PrintWriter pw;
        if(file.exists()) Path+="2";
        try {
            FileWriter fw = new FileWriter(Path, false);
            pw = new PrintWriter(Path);
        } catch(IOException ex){
            System.out.println("ファイルの出力に失敗しました");
            return;
        }
        /**結果をcsvファイルに出力*/
        /**パラメータの出力*/
        pw.println("パラメータ概要");
        pw.println("ノード数"+","+"サービスノード数"+","+"ターミナルノード数"+","+"SFC数"+","+"SFCのVNF数"+","+"障害想定数");
        pw.println(par.node_num+","+par.servicenode+","+par.terminalnode+","+par.SFC_num+","+par.SFC_VNFnum+","+par.failure_num);

        /**OPTの出力*/
        pw.println("目的関数値（ノード）"+","+"目的関数値（リンク）");
        double average_node = 0;
        double average_link = 0;
        for(int a=0;a<OPT.size();a++){
            average_node += OPT.get(a).get(0);
            average_link += OPT.get(a).get(1);
            pw.println(OPT.get(a).get(0)+","+OPT.get(a).get(1));
        }
        average_node = average_node/OPT.size();
        average_link = average_link/OPT.size();
        pw.println("平均目的関数値（ノード）"+","+"平均目的関数値（リンク）"+","+"実行時間"+"エラー数");
        pw.println(average_node+","+average_link+","+time+","+error);
        pw.close();
    }

    public ArrayList<Integer> Total_Placement_Calculator(ArrayList<Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>>> Path_List,ArrayList<MySFC> S){
        ArrayList<Integer> error_num = new ArrayList<>();
        for(int a=0;a<Path_List.size();a++){
            Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> List = new HashMap<>(Path_List.get(a));
            int num =1;
            for(int b=0;b<List.size();b++){
                ArrayList<Graph<MyNode,MyEdge>> Graph_List = new ArrayList<>(List.get(S.get(b)));
                for(Graph<MyNode,MyEdge> graph:Graph_List){
                    Collection<MyNode> node_list = graph.getVertices();
                    ArrayList<MyNode> node_list2 = new ArrayList<>(node_list);
                    int service =1;
                    for(int d=0;d<node_list2.size();d++) if(node_list2.get(d).Node_ID.equals("s")==true) service++;
                    if(service>1)for(int d=0;d<S.get(b).VNF.size();d++)num*=(service-1);
                    else if(service==1)num*=service;
                }
            }
            error_num.add(num);
        }
        return error_num;
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
