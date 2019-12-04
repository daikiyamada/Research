package Input;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.MultiGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;

import java.util.*;

public class SINET_GraphMaker extends Value{
    public Graph<MyNode,MyEdge> Sinet_graphmaker(Graph<MyNode,MyEdge> Graph,int cost_type){
        Parameter.Parameter par = new Parameter.Parameter();
        /**各都道府県ノードの作成*/
        MyNode n1= new MyNode("hokkaido1",1);
        MyNode n2 = new MyNode("hokkaido2",2);
        MyNode n3 = new MyNode("aomori",3);
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
        MyNode n18 = new MyNode("yamanashi",18);
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
        MyNode n50 = new MyNode("Kyoto",50);
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
        Graph.addVertex(n50);
        /**Generating the node list*/
        ArrayList<MyNode> List = new ArrayList<>(Graph.getVertices());
        for(int i=0;i<List.size();i++){
            /**Generating the computing resource*/
            Random rnd = new Random();
            int r = par.node_resource_max/100;
            int resource = rnd.nextInt(r)*100+par.node_resource_min;
            int cost = rnd.nextInt(par.node_cost_max)+par.node_cost_min;
            /**Generating the unit cost of computing resource*/
            MyNode now = List.get(i);
            r_n.put(now,resource);
            c_n.put(now,cost);
        }
        /**Adding a edge to the graph, defining the edge*/
        MyEdge e1 = new MyEdge(1);
        MyEdge e2 = new MyEdge(2);
        MyEdge e3 = new MyEdge(3);
        MyEdge e4 = new MyEdge(4);
        MyEdge e5 = new MyEdge(5);
        MyEdge e6 = new MyEdge(6);
        MyEdge e7 = new MyEdge(7);
        Graph.addEdge(e1,n1,n2);
        Graph.addEdge(e2,n1,n2);
        Graph.addEdge(e3,n1,n3);
        Graph.addEdge(e4,n1,n4);
        Graph.addEdge(e5,n3,n4);
        Graph.addEdge(e6,n3,n5);
        Graph.addEdge(e7,n4,n5);
        MyEdge e = new MyEdge(8);
        Graph.addEdge(e,n4,n6);
        e = new MyEdge(9);
        Graph.addEdge(e,n5,n7);
        e = new MyEdge(10);
        Graph.addEdge(e,n6,n7);
        e = new MyEdge(11);
        Graph.addEdge(e,n6,n8);
        e = new MyEdge(12);
        Graph.addEdge(e,n7,n9);
        e = new MyEdge(13);
        Graph.addEdge(e,n9,n20);
        e = new MyEdge(14);
        Graph.addEdge(e,n8,n11);
        e = new MyEdge(15);
        Graph.addEdge(e,n9,n10);
        e = new MyEdge(16);
        Graph.addEdge(e,n11,n12);
        e = new MyEdge(17);
        Graph.addEdge(e,n11,n14);
        e = new MyEdge(18);
        Graph.addEdge(e,n10,n13);
        e = new MyEdge(19);
        Graph.addEdge(e,n12,n13);
        e = new MyEdge(20);
        Graph.addEdge(e,n12,n16);
        e = new MyEdge(21);
        Graph.addEdge(e,n12,n14);
        e = new MyEdge(22);
        Graph.addEdge(e,n14,n16);
        e = new MyEdge(23);
        Graph.addEdge(e,n13,n14);
        e = new MyEdge(24);
        Graph.addEdge(e,n13,n15);
        e = new MyEdge(25);
        Graph.addEdge(e,n14,n15);
        e = new MyEdge(26);
        Graph.addEdge(e,n14,n17);
        e = new MyEdge(27);
        Graph.addEdge(e,n15,n17);
        e = new MyEdge(28);
        Graph.addEdge(e,n10,n19);
        e = new MyEdge(29);
        Graph.addEdge(e,n15,n18);
        e = new MyEdge(30);
        Graph.addEdge(e,n17,n24);
        e = new MyEdge(31);
        Graph.addEdge(e,n19,n20);
        e = new MyEdge(32);
        Graph.addEdge(e,n18,n19);
        e = new MyEdge(33);
        Graph.addEdge(e,n18,n20);
        e = new MyEdge(34);
        Graph.addEdge(e,n18,n23);
        e = new MyEdge(35);
        Graph.addEdge(e,n19,n23);
        e = new MyEdge(36);
        Graph.addEdge(e,n20,n23);
        e = new MyEdge(37);
        Graph.addEdge(e,n24,n25);
        e = new MyEdge(38);
        Graph.addEdge(e,n23,n25);
        e = new MyEdge(39);
        Graph.addEdge(e,n20,n22);
        e = new MyEdge(40);
        Graph.addEdge(e,n21,n22);
        e = new MyEdge(41);
        Graph.addEdge(e,n23,n26);
        e = new MyEdge(42);
        Graph.addEdge(e,n25,n31);
        e = new MyEdge(43);
        Graph.addEdge(e,n21,n50);
        e = new MyEdge(44);
        Graph.addEdge(e,n26,n50);
        e = new MyEdge(45);
        Graph.addEdge(e,n26,n27);
        e = new MyEdge(46);
        Graph.addEdge(e,n28,n31);
        e = new MyEdge(47);
        Graph.addEdge(e,n21,n50);
        e = new MyEdge(48);
        Graph.addEdge(e,n21,n26);
        e = new MyEdge(49);
        Graph.addEdge(e,n21,n33);
        e = new MyEdge(50);
        Graph.addEdge(e,n50,n27);
        e = new MyEdge(51);
        Graph.addEdge(e,n27,n28);
        e = new MyEdge(52);
        Graph.addEdge(e,n28,n30);
        e = new MyEdge(53);
        Graph.addEdge(e,n27,n30);
        e = new MyEdge(54);
        Graph.addEdge(e,n29,n27);
        e = new MyEdge(55);
        Graph.addEdge(e,n27,n33);
        e = new MyEdge(56);
        Graph.addEdge(e,n29,n50);
        e = new MyEdge(57);
        Graph.addEdge(e,n32,n33);
        e = new MyEdge(58);
        Graph.addEdge(e,n29,n34);
        e = new MyEdge(59);
        Graph.addEdge(e,n30,n39);
        e = new MyEdge(60);
        Graph.addEdge(e,n32,n36);
        e = new MyEdge(61);
        Graph.addEdge(e,n36,n35);
        e = new MyEdge(62);
        Graph.addEdge(e,n34,n35);
        e = new MyEdge(63);
        Graph.addEdge(e,n35,n38);
        e = new MyEdge(64);
        Graph.addEdge(e,n37,n38);
        e = new MyEdge(65);
        Graph.addEdge(e,n37,n39);
        e = new MyEdge(66);
        Graph.addEdge(e,n38,n40);
        e = new MyEdge(67);
        Graph.addEdge(e,n39,n40);
        e = new MyEdge(68);
        Graph.addEdge(e,n36,n41);
        e = new MyEdge(69);
        Graph.addEdge(e,n38,n45);
        e = new MyEdge(70);
        Graph.addEdge(e,n41,n45);
        e = new MyEdge(71);
        Graph.addEdge(e,n45,n48);
        e = new MyEdge(72);
        Graph.addEdge(e,n41,n42);
        e = new MyEdge(73);
        Graph.addEdge(e,n42,n46);
        e = new MyEdge(74);
        Graph.addEdge(e,n45,n46);
        e = new MyEdge(75);
        Graph.addEdge(e,n48,n47);
        e = new MyEdge(76);
        Graph.addEdge(e,n46,n47);
        e = new MyEdge(77);
        Graph.addEdge(e,n42,n43);
        e = new MyEdge(78);
        Graph.addEdge(e,n43,n43);
        e = new MyEdge(79);
        Graph.addEdge(e,n44,n46);
        e = new MyEdge(80);
        Graph.addEdge(e,n42,n49);
        e = new MyEdge(81);
        Graph.addEdge(e,n14,n27);
        ArrayList<MyEdge> Edge_List = new ArrayList<>(Graph.getEdges());
        Random rnd = new Random();
        for(int i=0;i<Edge_List.size();i++){
            MyEdge now = Edge_List.get(i);
            if(Edge_List.get(i).Edge_ID!=81){
                int resource = 100;
                int cost = rnd.nextInt(par.link_cost_max)+par.link_cost_min;
                r_e.put(now,resource);
                c_e.put(now,cost);
            }
            else if(Edge_List.get(i).Edge_ID==81){
                int resource = 400;
                int cost = rnd.nextInt(par.link_cost_max)+par.link_cost_min;
                r_e.put(now,resource);
                c_e.put(now,cost);
            }
        }
        return Graph;
    }
}
