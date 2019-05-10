package SFC;

import java.util.*;
import Input.MyNode;
import Parameter.Parameter;
public class SFC_Maker {
    public ArrayList<MySFC> SFCMaker(ArrayList<MyNode> List,ArrayList<MyVNF> VNF_List,int SFC_num){
        ArrayList<MySFC> S = new ArrayList<>();
        Parameter par = new Parameter();
        ArrayList<MyNode> t_list = new ArrayList<>();
        Random rnd = new Random();
        for(int b=0;b<List.size();b++) if(List.get(b).Node_ID=="t") t_list.add(List.get(b));
        for(int a=0;a<SFC_num;a++){
            Collections.shuffle(t_list);
            /**始点・終点*/
            MyNode source = t_list.get(0);
            MyNode sink = t_list.get(1);
            /**要求リンクリソース*/
            int resource = rnd.nextInt(par.SFC_resource_max-par.SFC_resource_min)+par.SFC_resource_min;
            /**VNF集合の決定*/
            Collections.shuffle(VNF_List);
            ArrayList<MyVNF> VNF = new ArrayList<>();
            for(int b=0;b<par.SFC_VNFnum;b++) VNF.add(VNF_List.get(b));
            Collections.sort(VNF, new Comparator<MyVNF>() {
                @Override
                public int compare(MyVNF o1, MyVNF o2) {
                    return o1.VNF_id<o2.VNF_id ? -1:1;
                }
            });
            /**SFCの決定*/
            MySFC s = new MySFC(a,VNF,resource,source,sink);
            S.add(s);
        }
        return S;
    }

}
