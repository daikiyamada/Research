package Output;

import Input.MyEdge;
import Input.MyNode;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Visualization {
    public static void Layout_Graph(Graph<MyNode, MyEdge> graph, String s) {
        Layout<MyNode, MyEdge> layout = new CircleLayout<MyNode, MyEdge>(graph);
        layout.setSize(new Dimension(800, 800));
        BufferedImage image = new BufferedImage(800,800,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        BasicVisualizationServer<MyNode, MyEdge> panel = new BasicVisualizationServer<MyNode, MyEdge>(layout, new Dimension(800, 800));
        panel.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
        panel.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<MyNode>());//vertex label
        panel.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<MyEdge>());
        Date now = new Date();
        DateFormat dfYMD = new SimpleDateFormat("YYYYMMDD");
        DateFormat dfHMS = new SimpleDateFormat("hhmmss");
        JFrame frame = new JFrame("Graph View: Manual Layout"+s+dfYMD.format(now)+"_"+dfHMS.format(now));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
