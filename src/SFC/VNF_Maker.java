package SFC;

import java.util.*;
import Parameter.Parameter;
public class VNF_Maker {
    public ArrayList<MyVNF> VNF_Maker(){
        Parameter par = new Parameter();
        Random rnd = new Random();
        /**VNFtypeList*/
        ArrayList<MyVNF> VNFtype_List = new ArrayList<>();
        /**VNFの決定*/
        for(int a=0;a<par.VNF_num;a++){
            int vnf_cap = rnd.nextInt(par.VNF_resource_max-par.VNF_resource_min)+par.VNF_resource_min;
            MyVNF F = new MyVNF(a,vnf_cap);
            VNFtype_List.add(F);
        }
        return VNFtype_List;
    }

}
