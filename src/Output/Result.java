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
import SFC.MyVNF;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.event.GraphEvent;
import edu.uci.ics.jung.graph.util.Pair;

public class Result {
    /**結果の出力や分析のためのクラス*/
    public void write_algo1(int num,String graph,String algo){
        Parameter par = new Parameter();
        String Path = new String();
        Path = par.path+graph+algo+"fn"+num+"result.csv";
        File file = new File(Path);
        PrintWriter pw;
        try {
            FileWriter fw = new FileWriter(file, true);
            pw = new PrintWriter(fw);
            /**結果をcsvファイルに出力*/
            /**SFC数の出力*/
            pw.print("SFC数"+",");
            pw.print("ノードコスト値"+",");
            pw.print("ノード中央値"+",");
            pw.print("ノード標準偏差"+",");
            pw.print("リンクコスト値"+",");
            pw.print("リンク中央値"+",");
            pw.print("リンク標準偏差"+",");
            pw.print("エラー数"+",");
            pw.print("path_error"+",");
            pw.print("place_error"+",");
            pw.print("実行時間"+",");
            pw.println();
            pw.close();
        } catch(IOException ex){
            System.out.println("ファイルの出力に失敗しました");
            return;
        }
    }
    public void write_each_result(int node_cost,int link_cost,String graph,String algo,int num,int SFC_num,int edge_num){
        Parameter par = new Parameter();
        String Path = new String();
        Path = par.path+graph+algo+"fn"+num+"SFC_"+SFC_num+"each_result.csv";
        File file = new File(Path);
        PrintWriter pw;
        try {
            FileWriter fw = new FileWriter(file, true);
            pw = new PrintWriter(fw);
            /**結果をcsvファイルに出力*/
            /**SFC数の出力*/
            pw.print(node_cost+",");
            pw.print(link_cost+",");
            pw.println(edge_num);

            pw.close();
        } catch(IOException ex){
            System.out.println("ファイルの出力に失敗しました");
            return;
        }

    }
    public void write_algo(int sfc_num,double node_cost,double link_cost ,int medeian_node,int median_link,double std_node,double std_link,int error_num,int error_num2,int error_num3,long time,int fn,String graph,String algo){
        Parameter par = new Parameter();
        String Path = new String();
        Path = par.path+graph+algo+"fn"+fn+"result.csv";
        File file = new File(Path);
        PrintWriter pw;
        try {
            FileWriter fw = new FileWriter(file, true);
            pw = new PrintWriter(fw);
            /**結果をcsvファイルに出力*/
            /**SFC数の出力*/
            pw.print(sfc_num+",");
            pw.print(node_cost+",");
            pw.print(medeian_node+",");
            pw.print(std_node+",");
            pw.print(link_cost+",");
            pw.print(median_link+",");
            pw.print(std_link+",");
            pw.print(error_num+",");
            pw.print(error_num2+",");
            pw.print(error_num3+",");
            pw.print(time+",");
            pw.println();
            pw.close();
        } catch(IOException ex){
            System.out.println("ファイルの出力に失敗しました");
            return;
        }
    }
    public void each_placement_writer(String yn,int num,int sfc_num,int fn,String graph,String algo){
        Parameter par = new Parameter();
        String Path = new String();
        Path = par.path+graph+algo+"fn"+fn+"SFC"+sfc_num+"placement2.csv";
        File file = new File(Path);
        try {
            FileWriter fw = new FileWriter(file, true);
            PrintWriter pw = new PrintWriter(fw);
             pw.println(num+"回目"+","+yn);
            pw.close();
        } catch(IOException ex){
            System.out.println("ファイルの出力に失敗しました5");
        }
    }
    public void placement_writer_times(int num,int sfc_num,int fn,String graph,String algo){
        Parameter par = new Parameter();
        String Path = new String();
        Path = par.path+graph+algo+"fn"+fn+"SFC"+sfc_num+"placement.csv";
        File file = new File(Path);
        try {
            FileWriter fw = new FileWriter(file, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(num+"回目");
            pw.println();
            pw.close();
        } catch(IOException ex){
            System.out.println("ファイルの出力に失敗しました4");
        }
    }
    public void placement_writer(Map<MyVNF,MyNode> List,ArrayList<MyVNF> VNF_List,int sfc_num,int r_num,int sfc,int fn,String graph,String algo){
        Parameter par = new Parameter();
        String file_Path = new String();
        file_Path = par.path+graph+algo+"fn"+fn+"SFC"+sfc+"placement.csv";
        File file = new File(file_Path);

        try {
            FileWriter fw = new FileWriter(file, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.print(sfc_num+"-"+r_num+",");
            for(MyVNF t:VNF_List){
                pw.print("("+t.VNF_id+"-"+t.cap_VNF+")="+List.get(t)+",");
            }
            pw.println();
            pw.close();
        } catch(IOException ex){
            System.out.println("ファイルの出力に失敗しました0");
        }
    }
    public void graph_writer(Graph<MyNode,MyEdge> graph,Map<MyEdge,Integer> R,int num,int sfc_num,int fn,String g,String algo){
        Parameter par = new Parameter();
        String Path = new String();
        Path = par.path+g+algo+"fn"+fn+"SFC"+sfc_num+"path.csv";
        File file = new File(Path);

        try {
            FileWriter fw = new FileWriter(file, true);
            PrintWriter pw = new PrintWriter(fw);

            /**パス・グラフ構造の可視化*/
            pw.println();
            pw.println();
            pw.println(num+"回目");
            ArrayList<MyNode> V = new ArrayList<MyNode>(graph.getVertices());
            pw.print(",");
            for(MyNode v :V)pw.print(v.Node_ID+v.Node_Num+",");
            pw.println();
            for(MyNode v: V){
                pw.print(v.Node_ID+v.Node_Num+",");
                for(MyNode u:V){
                    if(graph.findEdge(v,u)!=null){
                        MyEdge e = graph.findEdge(u,v);
                        pw.print("(1-"+R.get(e)+")"+",");
                    }
                    else if(graph.findEdge(v,u)==null) pw.print("0"+",");
                }
                pw.println();
            }
            pw.println();
            pw.println();
            pw.close();
        } catch(IOException ex){
            System.out.println("ファイルの出力に失敗しました1");
        }

    }
    public void path_writer(Graph<MyNode,MyEdge> G,ArrayList<MyEdge> path,int num,int num2,int sfc_num,int fn,String graph,String algo) {
        Parameter par = new Parameter();
        String Path = new String();
        Path = par.path + graph + algo + "fn"+fn+"SFC"+sfc_num+ "path.csv";
        File file = new File(Path);
        PrintWriter pw;
        try {
            FileWriter fw = new FileWriter(file, true);
            pw = new PrintWriter(fw);
            /**csvファイルに出力*/
            /**パス作成過程の可視化*/
            pw.print("(" + num + "-" + num2 + ")"+",");
            for (MyEdge e : path) {
                Pair<MyNode> list = G.getEndpoints(find_edge(G, e));
                pw.print("(" + list.getFirst().Node_Num + "，" + list.getSecond().Node_Num + ")" + ",");
            }
            pw.println();
            pw.close();
        } catch (IOException ex) {
            System.out.println("ファイルの出力に失敗しました2");
            return;
        }

    }

    private MyEdge find_edge(Graph<MyNode,MyEdge> G,MyEdge e){
        MyEdge e2 =null;
        for(MyEdge e1:G.getEdges()) if(e1.Edge_ID==e.Edge_ID) e2 = e1;
        return e2;
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
