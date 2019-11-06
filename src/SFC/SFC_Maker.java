package SFC;

import java.util.*;
import Input.MyNode;
import Parameter.Parameter;
public class SFC_Maker {
    public ArrayList<MySFC> SFCMaker(ArrayList<MyNode> List,ArrayList<MyVNF> VNF_List,int SFC_num){
        ArrayList<MySFC> S = new ArrayList<>();
        Parameter par = new Parameter();
        Random rnd = new Random();
        for(int a=0;a<SFC_num;a++){
            /**Giving the source node and sink node*/
            Collections.shuffle(List);
            MyNode source = List.get(0);
            MyNode sink = List.get(1);
            /**Giving the demand network resource*/
            int resource = rnd.nextInt(par.SFC_resource_max-par.SFC_resource_min+1)+par.SFC_resource_min;
            /**Giving the VNF set*/
            ArrayList<MyVNF> VNF = new ArrayList<>();
            Collections.shuffle(VNF_List);
            for(int b=0;b<par.SFC_VNFnum;b++) VNF.add(VNF_List.get(b));
            Collections.sort(VNF, new Comparator<MyVNF>() {
                @Override
                public int compare(MyVNF o1, MyVNF o2) {
                    return o1.VNF_id<=o2.VNF_id ? -1:1;
                }
            });
            /**Giving the SFC set*/
            MySFC s = new MySFC(a,VNF,resource,source,sink);
            S.add(s);
        }
        return S;
    }

}
