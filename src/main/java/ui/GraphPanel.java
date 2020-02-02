package ui;

import data.GraphData;
import data.GraphLine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Comparator;

public class GraphPanel extends JPanel {

    private GraphData data;
    private int bufferx = 30;
    private int buffery = 20;
    private int runx = 0;
    private int runy = 0;
    private int w = 500;
    private int h = 300;

    public GraphPanel(GraphData data) {
        this.data = data;
        runx = (int)Math.ceil((data.getMaxX() - data.getMinX())*1.2);
        runy = (int)Math.ceil((data.getMaxY() - data.getMinY())*1.4);
        setPreferredSize(new Dimension(runx,runy));
    }

    public void fit(int w, int h){
        this.w = w;
        this.h = h;
        repaint();
    }

    public void paintComponent(Graphics g){
        BufferedImage graphImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics graph = graphImage.getGraphics();
        graph.setColor(new Color(50,50,50));
        graph.fillRect(0,0,w,h);

        drawGrid(graph);

        for(GraphLine gl: data.getGraphLines()){
            drawLine(graph,gl);
        }

        g.drawImage(graphImage,0,0,null);
    }

    public void drawGrid(Graphics g){
        g.setColor(new Color(220,220,220,200));
        g.drawLine(0,scaleY(0),w,scaleY(0));
        g.drawLine(scaleX(0),0,scaleX(0),h);

        g.setColor(new Color(150,150,150,150));
        for(int i = data.getMinX()+Math.max(2,runx/5); i <= data.getMaxX()+Math.max(2,runx/5); i+= Math.max(2,runx/5)){
            g.drawLine(scaleX(i),0,scaleX(i),h);
            g.drawString(i+"",scaleX(i)+3,scaleY(0)+12);
        }
        for(int i = 0+Math.max(2,runy/5); i < data.getMaxY()+Math.max(2,runy/5); i+= Math.max(2,runy/5)){
            g.drawLine(0,scaleY(i),w,scaleY(i));
            g.drawString(i+"",scaleX(0)-23,scaleY(i)-2);
        }
        g.drawString("0",scaleX(0)-10,scaleY(0)+12);
    }

    public void drawLine(Graphics g, GraphLine gl){
        g.setColor(gl.getColor());
        Collections.sort(gl.getPoints(), new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return o1.x - o2.x;
            }
        });
        for(int i = 0; i < gl.getPoints().size()-1; i++){
            Point p1 = gl.getPoints().get(i);
            Point p2 = gl.getPoints().get(i+1);

            g.drawLine(scaleX(p1.x),scaleY(p1.y),scaleX(p2.x),scaleY(p2.y));
        }
    }

    private int scaleX(int x){
        return (int)(x / ((runx+0.0) / (w+0.0)))+bufferx;
    }

    private int scaleY(int y){
        return (int)(y / ((runy+0.0) / (h+0.0)))+buffery;
    }
}
