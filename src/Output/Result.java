package Output;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import Parameter.Parameter;


public class Result {
    /**���ʂ̏o�͂╪�͂̂��߂̃N���X*/
    public void write_algo1(int num,String graph,String algo,int cost_type){
        Parameter par = new Parameter();
        String Path = new String();
        if(cost_type==1)Path = par.path1+graph+algo+"fn"+num+"_"+par.cost_type2[cost_type-1]+"���ʕ\.csv";
        else if(cost_type==2)Path = par.path2+graph+algo+"fn"+num+"_"+par.cost_type2[cost_type-1]+"���ʕ\.csv";
        else if(cost_type==3)Path = par.path3+graph+algo+"fn"+num+"_"+par.cost_type2[cost_type-1]+"���ʕ\.csv";
        File file = new File(Path);
        PrintWriter pw;
        try {
            FileWriter fw = new FileWriter(file, true);
            pw = new PrintWriter(fw);
            /**���ʂ�csv�t�@�C���ɏo��*/
            /**SFC���̏o��*/
            pw.print("# of SFC"+",");
            pw.print("average node cost"+",");
            pw.print("median node cost"+",");
            pw.print("std node cost"+",");
            pw.print("average link cost"+",");
            pw.print("median link cost"+",");
            pw.print("std link cost"+",");
            pw.print("# of error"+",");
            pw.print("# of path_error"+",");
            pw.print("# of place_error"+",");
            pw.print("exe_time"+",");
            pw.println();
            pw.close();
        } catch(IOException ex){
            System.out.println("�t�@�C���̏o�͂Ɏ��s���܂���1");
            return;
        }
    }
    public void write_each_result(int node_cost,int link_cost,String graph,String algo,int num,int SFC_num,int cost_type){
        Parameter par = new Parameter();
        String Path = new String();
        if(cost_type==1)Path = par.path1+graph+algo+"fn"+num+"_"+"SFC"+SFC_num+"_"+par.cost_type2[cost_type-1]+"_�U�z�}�f�[�^.csv";
        else if(cost_type==2)Path = par.path2+graph+algo+"fn"+num+"_"+"SFC"+SFC_num+"_"+par.cost_type2[cost_type-1]+"_�U�z�}�f�[�^.csv";
        else if(cost_type==3)Path = par.path3+graph+algo+"fn"+num+"_"+"SFC"+SFC_num+"_"+par.cost_type2[cost_type-1]+"_�U�z�}�f�[�^.csv";
        File file = new File(Path);
        PrintWriter pw;
        try {
            FileWriter fw = new FileWriter(file, true);
            pw = new PrintWriter(fw);
            /**���ʂ�csv�t�@�C���ɏo��*/
            /**SFC���̏o��*/
            pw.print(node_cost+",");
            pw.print(link_cost+",");
            pw.println();
            pw.close();
        } catch(IOException ex){
            System.out.println("�U�z�}�f�[�^���s");
            return;
        }

    }
    public void write_algo(int sfc_num,double node_cost,double link_cost ,int medeian_node,int median_link,double std_node,double std_link,int error_num,int error_num2,int error_num3,long time,int fn,String graph,String algo,int cost_type){
        Parameter par = new Parameter();
        String Path = new String();
        if(cost_type==1)Path = par.path1+graph+algo+"fn"+fn+"_"+par.cost_type2[cost_type-1]+"���ʕ\.csv";
        else if(cost_type==2)Path = par.path2+graph+algo+"fn"+fn+"_"+par.cost_type2[cost_type-1]+"���ʕ\.csv";
        else if(cost_type==3)Path = par.path3+graph+algo+"fn"+fn+"_"+par.cost_type2[cost_type-1]+"���ʕ\.csv";
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
            System.out.println("���ʎ��s");
            return;
        }
    }
    public void edge_info_writer(Map<Integer,Double> Ave1,Map<Integer,Double> Std1,Map<Integer,Double> Ave2,Map<Integer,Double> Std2,String graph,String algo,int fn,int sfc_num,int cost_type){
        Parameter par = new Parameter();
        String Path = new String();
        if(cost_type==1) Path = par.path1 + graph + algo + "fn"+fn+"_"+par.cost_type2[cost_type-1]+ "_�ӏ��.csv";
        else if(cost_type==2) Path = par.path2 + graph + algo + "fn"+fn+"_"+par.cost_type2[cost_type-1]+ "_�ӏ��.csv";
        else if(cost_type==3) Path = par.path3 + graph + algo + "fn"+fn+"_"+par.cost_type2[cost_type-1]+ "_�ӏ��.csv";
        File file = new File(Path);
        PrintWriter pw;
        try{
            FileWriter fw = new FileWriter(file,true);
            pw = new PrintWriter(fw);
            /**�Ӑ��̕\��*/
            pw.print(sfc_num+",");
            for(int i:Ave1.keySet()) pw.print(Ave1.get(i)+",");
            pw.print(",");
            for(int i:Ave1.keySet()) pw.print(Std1.get(i)+",");
            pw.print(",");
            pw.print(",");
            for(int i:Ave2.keySet()) pw.print(Ave2.get(i)+",");
            pw.print(",");
            for(int i:Ave2.keySet()) pw.print(Std2.get(i)+",");
            pw.println();
            pw.close();
        }
        catch(IOException ex){
            System.out.println("�ӏ�񎸔s");
            return;
        }

    }
    public void edge_utilization_writer(Map<Integer,Double> Ave,Map<Integer,Double> Std,String graph,String algo,int fn,int sfc_num,int cost_type){
        Parameter par = new Parameter();
        String Path = new String();
        if(cost_type==1)Path = par.path1 + graph + algo + "fn"+fn+"_"+par.cost_type2[cost_type-1]+ "_�ӎg�p��.csv";
        else if(cost_type==2)Path = par.path2 + graph + algo + "fn"+fn+"_"+par.cost_type2[cost_type-1]+ "_�ӎg�p��.csv";
        else if(cost_type==3)Path = par.path3 + graph + algo + "fn"+fn+"_"+par.cost_type2[cost_type-1]+ "_�ӎg�p��.csv";
        File file = new File(Path);
        PrintWriter pw;
        try{
            FileWriter fw = new FileWriter(file,true);
            pw = new PrintWriter(fw);
             /**�g�p���̕\��*/
             pw.print(sfc_num+",");
             for(int i:Ave.keySet()) pw.print(Ave.get(i)+",");
            pw.print(",");
             for(int i:Ave.keySet()) pw.print(Std.get(i)+",");
             pw.println();
             pw.close();
        }
        catch(IOException ex){
            System.out.println("�ӎg�p�����s");
            return;
        }

    }
    public void node_info_writer(Map<Integer,Double> Ave1,Map<Integer,Double> Std1,Map<Integer,Double> Ave2,Map<Integer,Double> Std2,String graph,String algo,int fn,int sfc_num,int cost_type){
        Parameter par = new Parameter();
        String Path = new String();
        if(cost_type==1) Path = par.path1 + graph + algo + "fn"+fn+"_"+par.cost_type2[cost_type-1]+ "_���_���.csv";
        else if(cost_type==2) Path = par.path2 + graph + algo + "fn"+fn+"_"+par.cost_type2[cost_type-1]+ "_���_���.csv";
        else if(cost_type==3) Path = par.path3 + graph + algo + "fn"+fn+"_"+par.cost_type2[cost_type-1]+ "_���_���.csv";
        File file = new File(Path);
        PrintWriter pw;
        try{
            FileWriter fw = new FileWriter(file,true);
            pw = new PrintWriter(fw);
            pw.print(sfc_num+",");
            for(int i:Ave1.keySet()) pw.print(Ave1.get(i)+",");
            pw.print(",");
            for(int i:Ave1.keySet()) pw.print(Std1.get(i)+",");
            pw.print(",");
            pw.print(",");
            for(int i:Ave2.keySet()) pw.print(Ave2.get(i)+",");
            pw.print(",");
            for(int i:Ave2.keySet()) pw.print(Std2.get(i)+",");
            pw.println();
            pw.close();
        }
        catch(IOException ex){
            System.out.println("���_��񎸔s");
            return;
        }

    }
    public void node_utilization_writer(Map<Integer,Double> Ave,Map<Integer,Double> Std,String graph,String algo,int fn,int sfc_num,int cost_type){
        Parameter par = new Parameter();
        String Path = new String();
        if(cost_type==1) Path = par.path1 + graph + algo + "fn"+fn+"_"+par.cost_type2[cost_type-1]+ "_���_�g�p��.csv";
        else if(cost_type==2) Path = par.path2 + graph + algo + "fn"+fn+"_"+par.cost_type2[cost_type-1]+ "_���_�g�p��.csv";
        else if(cost_type==3) Path = par.path3 + graph + algo + "fn"+fn+"_"+par.cost_type2[cost_type-1]+ "_���_�g�p��.csv";
        File file = new File(Path);
        PrintWriter pw;
        try{
            FileWriter fw = new FileWriter(file,true);
            pw = new PrintWriter(fw);
             /**�g�p���̕\��*/
            pw.print(sfc_num+",");
            for(int i:Ave.keySet()) pw.print(Ave.get(i)+",");
            pw.print(",");
            for(int i:Ave.keySet()) pw.print(Std.get(i)+",");
            pw.println();
            pw.close();
        }
        catch(IOException ex){
            System.out.println("error node_utilization3");
            return;
        }

    }
    public void path_length_writer(double ave,double std,String graph,String algo,int fn,int sfc_num,int cost_type){
        Parameter par = new Parameter();
        String Path = new String();
        if(cost_type==1)Path = par.path1 + graph + algo + "fn"+fn+"_"+par.cost_type2[cost_type-1]+ "_�p�X�̒���.csv";
        else if(cost_type==2)Path = par.path2 + graph + algo + "fn"+fn+"_"+par.cost_type2[cost_type-1]+ "_�p�X�̒���.csv";
        else if(cost_type==3)Path = par.path3 + graph + algo + "fn"+fn+"_"+par.cost_type2[cost_type-1]+ "_�p�X�̒���.csv";
        File file = new File(Path);
        PrintWriter pw;
        try{
            FileWriter fw = new FileWriter(file,true);
            pw = new PrintWriter(fw);
            /**�p�X�̒���*/
            pw.println(sfc_num+","+ave+","+std);
            pw.close();
        }
        catch(IOException ex){
            System.out.println("error path_length");
            return;
        }
    }

}
