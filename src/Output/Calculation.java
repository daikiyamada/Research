package Output;
import java.util.*;
public class Calculation {
    public double average_cal(ArrayList<Integer> list){
        double average =0;
        for(int i=0;i<list.size();i++) average+=list.get(i);
        average /=list.size();
        return average;
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
}
