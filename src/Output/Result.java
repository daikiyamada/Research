package Output;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import Parameter.Parameter;


public class Result {
    /**���ʂ̏o�͂╪�͂̂��߂̃N���X*/
    public void write_algo1(String graph,String algo,int cost_type,int x,int y, int z){
        Parameter par = new Parameter();
        String Path = new String();
        if(cost_type==1)Path = par.path1+graph+algo+"_"+par.cost_type2[cost_type-1]+"("+x+","+y+","+z+")"+"���ʕ\.csv";
        else if(cost_type==2)Path = par.path2+graph+algo+"_"+par.cost_type2[cost_type-1]+"("+x+","+y+","+z+")"+"���ʕ\.csv";
        else if(cost_type==3)Path = par.path3+graph+algo+"_"+par.cost_type2[cost_type-1]+"("+x+","+y+","+z+")"+"���ʕ\.csv";
        File file = new File(Path);
        PrintWriter pw;
        try {
            FileWriter fw = new FileWriter(file, true);
            pw = new PrintWriter(fw);
            /**���ʂ�csv�t�@�C���ɏo��*/
            /**SFC���̏o��*/
            pw.print("# of Failure"+",");
            pw.print("average node cost"+",");
            pw.print("median node cost"+",");
            pw.print("std node cost"+",");
            pw.print("average link cost"+",");
            pw.print("median link cost"+",");
            pw.print("std link cost"+",");
            pw.print("# of error"+",");
            pw.print("# of path_error"+",");
            pw.print("# of place_error"+",");
            pw.print("error_rate"+",");
            pw.print("exe_time(ms)"+",");
            pw.println();
            pw.close();
        } catch(IOException ex){
            System.out.println("�t�@�C���̏o�͂Ɏ��s���܂���1");
            return;
        }
    }
    public void write_algo(double node_cost,double link_cost ,int medeian_node,int median_link,double std_node,double std_link,int error_num,int error_num2,int error_num3,double error_rate,long time,int fn,String graph,String algo,int cost_type,int x,int y,int z){
        Parameter par = new Parameter();
        String Path = new String();
        if(cost_type==1)Path = par.path1+graph+algo+"_"+par.cost_type2[cost_type-1]+"("+x+","+y+","+z+")"+"���ʕ\.csv";
        else if(cost_type==2)Path = par.path2+graph+algo+"_"+par.cost_type2[cost_type-1]+"("+x+","+y+","+z+")"+"���ʕ\.csv";
        else if(cost_type==3)Path = par.path3+graph+algo+"_"+par.cost_type2[cost_type-1]+"("+x+","+y+","+z+")"+"���ʕ\.csv";
        File file = new File(Path);
        PrintWriter pw;
        try {
            FileWriter fw = new FileWriter(file, true);
            pw = new PrintWriter(fw);
            /**���ʂ�csv�t�@�C���ɏo��*/
            /**SFC���̏o��*/
            pw.print(fn+",");
            pw.print(node_cost+",");
            pw.print(medeian_node+",");
            pw.print(std_node+",");
            pw.print(link_cost+",");
            pw.print(median_link+",");
            pw.print(std_link+",");
            pw.print(error_num+",");
            pw.print(error_num2+",");
            pw.print(error_num3+",");
            pw.print(error_rate+",");
            pw.print(time+",");
            pw.println();
            pw.close();
        } catch(IOException ex){
            System.out.println("���ʎ��s");
            return;
        }
    }
    public void write_each_result(int node_cost,int link_cost,String graph,String algo,int failure_num,int SFC_num,int cost_type,int x,int y,int z){
        Parameter par = new Parameter();
        String Path = new String();
        Path = par.path1+graph+algo+"_"+"SFC"+SFC_num+"_"+"failure"+failure_num+par.cost_type2[cost_type-1]+"("+x+","+y+","+z+")"+"_�U�z�}�f�[�^.csv";
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

}
