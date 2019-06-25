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
    /**���ʂ̏o�͂╪�͂̂��߂̃N���X*/
    public void write_algo1(int num,String graph,String algo){
        Parameter par = new Parameter();
        String Path = new String();
        Path = par.path+graph+algo+"fn"+num+"result.csv";
        File file = new File(Path);
        PrintWriter pw;
        try {
            FileWriter fw = new FileWriter(file, true);
            pw = new PrintWriter(fw);
            /**���ʂ�csv�t�@�C���ɏo��*/
            /**SFC���̏o��*/
            pw.print("SFC��"+",");
            pw.print("�m�[�h�R�X�g�l"+",");
            pw.print("�m�[�h�����l"+",");
            pw.print("�m�[�h�W���΍�"+",");
            pw.print("�����N�R�X�g�l"+",");
            pw.print("�����N�����l"+",");
            pw.print("�����N�W���΍�"+",");
            pw.print("�G���[��"+",");
            pw.print("path_error"+",");
            pw.print("place_error"+",");
            pw.print("���s����"+",");
            pw.println();
            pw.close();
        } catch(IOException ex){
            System.out.println("�t�@�C���̏o�͂Ɏ��s���܂���");
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
            /**���ʂ�csv�t�@�C���ɏo��*/
            /**SFC���̏o��*/
            pw.print(node_cost+",");
            pw.print(link_cost+",");
            pw.println(edge_num);

            pw.close();
        } catch(IOException ex){
            System.out.println("�t�@�C���̏o�͂Ɏ��s���܂���");
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
            /**���ʂ�csv�t�@�C���ɏo��*/
            /**SFC���̏o��*/
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
            System.out.println("�t�@�C���̏o�͂Ɏ��s���܂���");
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
             pw.println(num+"���"+","+yn);
            pw.close();
        } catch(IOException ex){
            System.out.println("�t�@�C���̏o�͂Ɏ��s���܂���5");
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
            pw.println(num+"���");
            pw.println();
            pw.close();
        } catch(IOException ex){
            System.out.println("�t�@�C���̏o�͂Ɏ��s���܂���4");
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
            System.out.println("�t�@�C���̏o�͂Ɏ��s���܂���0");
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

            /**�p�X�E�O���t�\���̉���*/
            pw.println();
            pw.println();
            pw.println(num+"���");
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
            System.out.println("�t�@�C���̏o�͂Ɏ��s���܂���1");
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
            /**csv�t�@�C���ɏo��*/
            /**�p�X�쐬�ߒ��̉���*/
            pw.print("(" + num + "-" + num2 + ")"+",");
            for (MyEdge e : path) {
                Pair<MyNode> list = G.getEndpoints(find_edge(G, e));
                pw.print("(" + list.getFirst().Node_Num + "�C" + list.getSecond().Node_Num + ")" + ",");
            }
            pw.println();
            pw.close();
        } catch (IOException ex) {
            System.out.println("�t�@�C���̏o�͂Ɏ��s���܂���2");
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
        /**�z�u�g�ݍ��킹���Z�o*/
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
    /**�o�O�`�F�b�N�o�͊֐�*/
    public int Total_Placement_Calculator2(Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> Path_List,ArrayList<MySFC> S){
        int num=1;
        /***/
        for(int i =0;i<Path_List.size();i++){
            ArrayList<Graph<MyNode,MyEdge>> graph = new ArrayList<>(Path_List.get(S.get(i)));
            for(int j=0;j<graph.size();j++){
                /**�P�̃p�X�ł̔z�u��*/
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
