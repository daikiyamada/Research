package SFC;

import Input.MyNode;

import java.util.ArrayList;

public class MySFC {
    public int SFC_num;
    public ArrayList<MyVNF> VNF = new ArrayList<MyVNF>(); /**SFCに含まれるVNF*/
    public int Demand_Link_Resource;
    public int Node_Cost; /**配置コスト*/
    public int Link_Cost; /**Link resource*/
    public MyNode source;
    public MyNode sink;

    public MySFC(int SFC_num,ArrayList<MyVNF>VNF,int Node_Cost,int Link_Cost,int Demand_Link_Resource,MyNode source,MyNode sink){
        this.SFC_num=SFC_num;
        this.VNF=VNF;
        this.Node_Cost = Node_Cost;
        this.Link_Cost = Link_Cost;
        this.Demand_Link_Resource = Demand_Link_Resource;
        this.source =source;
        this.sink = sink;
    }
}

