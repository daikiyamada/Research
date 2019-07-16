package Output;
import Input.MyEdge;
import Input.MyNode;
import SFC.MySFC;
import edu.uci.ics.jung.graph.Graph;
import Input.Value;
import java.util.*;
public class Calculation extends Value{
    public double average_cal(ArrayList<Integer> list){
        double average =0;
        for(int i=0;i<list.size();i++) average+=list.get(i);
        average /=list.size();
        return average;
    }
    public double average_cal2(ArrayList<Double> list){
        double average =0;
        for(int i=0;i<list.size();i++) average+=list.get(i);
        average /=list.size();
        return average;
    }
    public Map<Integer,Double> average_cal3(ArrayList<Map<Integer,Double>> data_list){
        Map<Integer,Double> List = new HashMap<>();
        for(int i:data_list.get(0).keySet()) List.put(i,0.0);
        for(int i=0;i<data_list.size();i++){
            Map<Integer,Double> Each = data_list.get(i);
            for(int j:Each.keySet()){
                List.replace(j,List.get(j)+Each.get(j));
            }
        }
        for(int i:List.keySet()) List.replace(i,List.get(i)/data_list.size());
        return List;
    }
    public Map<Integer,Double> average_cal4(ArrayList<Map<Integer,Integer>> data_list){
        Map<Integer,Double> List = new HashMap<>();
        for(int i:data_list.get(0).keySet()) List.put(i,0.0);
        for(int i=0;i<data_list.size();i++){
            Map<Integer,Integer> Each = data_list.get(i);
            for(int j:Each.keySet()){
                List.replace(j,List.get(j)+Each.get(j));
            }
        }
        for(int i:List.keySet()) List.replace(i,List.get(i)/data_list.size());
        return List;
    }
    public int median_cal(ArrayList<Integer> List){
        if(List.size()!=0){
            int median = 0;
            Collections.sort(List);
            int middle = List.size()/2;
            median = List.get(middle);
            return median;
        }
        else return 0;

    }
    public double  standard_deviation_cal(ArrayList<Integer> List,double average){
        double SD = 0;
        double std =0;
        for(int i=0;i<List.size();i++) std+=Math.pow((List.get(i)-average),2);
        SD = Math.sqrt(std/List.size());
        return SD;
    }
    public double  standard_deviation_cal2(ArrayList<Double> List,double average){
        double SD = 0;
        double std =0;
        for(int i=0;i<List.size();i++) std+=Math.pow((List.get(i)-average),2);
        SD = Math.sqrt(std/List.size());
        return SD;
    }
    public Map<Integer,Double>  standard_deviation_cal3(ArrayList<Map<Integer,Double>> Data,Map<Integer,Double> average){
        Map<Integer,Double> List = new HashMap<>();
        for(int i:Data.get(0).keySet()) List.put(i,0.0);
        for(int i=0;i<Data.size();i++){
            for(int j:Data.get(i).keySet()){
                List.replace(j,List.get(j)+Math.pow(List.get(j)-average.get(j),2));
            }
        }
        for(int i:Data.get(0).keySet()){
            List.replace(i,Math.sqrt(List.get(i)/Data.size()));
        }
        return List;
    }
    public Map<Integer,Double>  standard_deviation_cal4(ArrayList<Map<Integer,Integer>> Data,Map<Integer,Double> average){
        Map<Integer,Double> List = new HashMap<>();
        for(int i:Data.get(0).keySet()) List.put(i,0.0);
        for(int i=0;i<Data.size();i++){
            for(int j:Data.get(i).keySet()){
                List.replace(j,List.get(j)+Math.pow(List.get(j)-average.get(j),2));
            }
        }
        for(int i:Data.get(0).keySet()){
            List.replace(i,Math.sqrt(List.get(i)/Data.size()));
        }
        return List;
    }
    public double average_path_length(Map<MySFC, ArrayList<Graph<MyNode, MyEdge>>> Path_set,ArrayList<MySFC> S){
        double average;
        int path_num=0,sum=0;
        for(MySFC s:S){
            ArrayList<Graph<MyNode,MyEdge>> path_set = new ArrayList<>(Path_set.get(s));
            for(Graph<MyNode,MyEdge>path:path_set){
                sum+=path.getEdgeCount();
                path_num++;
            }
        }
        average=(double)sum/path_num;
        return average;
    }
    public double[] average_service_node(Map<MySFC,ArrayList<Graph<MyNode,MyEdge>>> Path_set,ArrayList<MySFC> S,int used_node_num){
        double average=0,sum=0;
        double list[]= new double[2];
        int service_num=0;
        /**サービスノード数カウント*/
        for(MySFC s:S){
            ArrayList<Graph<MyNode,MyEdge>> path_set = new ArrayList<>(Path_set.get(s));
            for(Graph<MyNode,MyEdge> path:path_set){
                Collection<MyNode> Node_List1 = path.getVertices();
                ArrayList<MyNode> Node_List2 = new ArrayList<>(Node_List1);
                for(MyNode n:Node_List2){
                    if(n.Node_ID.equals("s")==true) service_num++;
                }
                sum++;
            }
        }
        list[0] = (double) used_node_num/sum;
        list[1] = (double) service_num/sum;
        return list;
    }

}
