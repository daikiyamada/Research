package Input;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

import java.util.*;
public class SINET_GraphMaker {
    public Graph<MyNode,MyEdge> Sinet_graphmaker(Graph<MyNode,MyEdge> G){
        Graph<MyNode,MyEdge> Graph = new UndirectedSparseGraph<>();
        /**各都道府県ノードの作成*/
        MyNode n1= new MyNode("hokkaido1",1);
        MyNode n2 = new MyNode("hokkaido2",2);
        MyNode n3 = new MyNode("amori",3);
        MyNode n4 = new MyNode("iwate",4);
        MyNode n5 = new MyNode("akita",5);
        MyNode n6 = new MyNode("miyagi",6);
        MyNode n7 = new MyNode("yamagata",7);
        MyNode n8 = new MyNode("hukushima",8);
        MyNode n9= new MyNode("nigata",9);
        MyNode n10 = new MyNode("gunma",10);
        MyNode n11 = new MyNode("tochigi",11);
        MyNode n12 = new MyNode("ibaraki",12);
        MyNode n13 = new MyNode("saitama",13);
        MyNode n14 = new MyNode("tokyo1",14);
        MyNode n15 = new MyNode("tokyo2",15);
        MyNode n16 = new MyNode("tiba",16);
        MyNode n17 = new MyNode("kanagawa",17);
        MyNode n18 = new MyNode("yamanachi",18);
        MyNode n19 = new MyNode("nagano",19);
        MyNode n20 = new MyNode("toyama",20);
        MyNode n21 = new MyNode("hukui",21);
        MyNode n22 = new MyNode("ishikawa",22);
        MyNode n23 = new MyNode("gihu",23);
        MyNode n24 = new MyNode("shizuoka",24);
        MyNode n25 = new MyNode("aichi",25);
        MyNode n26 = new MyNode("shiga",26);
        MyNode n27 = new MyNode("osaka",27);
        MyNode n28 = new MyNode("nara",28);
        MyNode n29 = new MyNode("hyogo",29);
        MyNode n30 = new MyNode("wakayama",30);
        MyNode n31 = new MyNode("mie",31);
        MyNode n32 = new MyNode("shimane",32);
        MyNode n33 = new MyNode("tottori",33);
        MyNode n34 = new MyNode("okayama",34);
        MyNode n35 = new MyNode("hiroshima",35);
        MyNode n36 = new MyNode("yamaguchi",36);
        MyNode n37 = new MyNode("kagawa",37);
        MyNode n38 = new MyNode("ehime",38);
        MyNode n39 = new MyNode("tokushima",39);
        MyNode n40 = new MyNode("kochi",40);
        MyNode n41 = new MyNode("hukuoka1",41);
        MyNode n42 = new MyNode("hukuoka2",42);
        MyNode n43 = new MyNode("saga",43);
        MyNode n44 = new MyNode("nagasaki",44);
        MyNode n45 = new MyNode("oita",45);
        MyNode n46 = new MyNode("kumamoto",46);
        MyNode n47 = new MyNode("kagoshima",47);
        MyNode n48 = new MyNode("miyazaki",48);
        MyNode n49 = new MyNode("okinawa",49);
        /**頂点追加*/
        Graph.addVertex(n1);
        Graph.addVertex(n2);
        Graph.addVertex(n3);
        Graph.addVertex(n4);
        Graph.addVertex(n5);
        Graph.addVertex(n6);
        Graph.addVertex(n7);
        Graph.addVertex(n8);
        Graph.addVertex(n9);
        Graph.addVertex(n10);
        Graph.addVertex(n11);
        Graph.addVertex(n12);
        Graph.addVertex(n13);
        Graph.addVertex(n14);
        Graph.addVertex(n15);
        Graph.addVertex(n16);
        Graph.addVertex(n17);
        Graph.addVertex(n18);
        Graph.addVertex(n19);
        Graph.addVertex(n20);
        Graph.addVertex(n21);
        Graph.addVertex(n22);
        Graph.addVertex(n23);
        Graph.addVertex(n24);
        Graph.addVertex(n25);
        Graph.addVertex(n26);
        Graph.addVertex(n27);
        Graph.addVertex(n28);
        Graph.addVertex(n29);
        Graph.addVertex(n30);
        Graph.addVertex(n31);
        Graph.addVertex(n32);
        Graph.addVertex(n33);
        Graph.addVertex(n34);
        Graph.addVertex(n35);
        Graph.addVertex(n36);
        Graph.addVertex(n37);
        Graph.addVertex(n38);
        Graph.addVertex(n39);
        Graph.addVertex(n40);
        Graph.addVertex(n41);
        Graph.addVertex(n42);
        Graph.addVertex(n43);
        Graph.addVertex(n44);
        Graph.addVertex(n45);
        Graph.addVertex(n46);
        Graph.addVertex(n47);
        Graph.addVertex(n48);
        Graph.addVertex(n49);
        /**頂点情報追加*/

        /**各辺の定義・グラフへの追加*/

        return G;
    }
}
