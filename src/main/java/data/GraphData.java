package data;

import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GraphData{


    private int maxX;
    private int minX;
    private int maxY;
    private int minY;
    private List<GraphLine> graphLines = new ArrayList<>();
    private Color[] colors = new Color[]{
            new Color(255,0,0),
            new Color(0,255,100),
            new Color(100,0,255),
            new Color(255,255,0),
            new Color(0,255,255),
            new Color(255,0,255),
            new Color(170,100,100),
            new Color(255,150,30),
    };

    public GraphData(HashMap<Object, List<Point>> lines) {
        build(lines, new NameMapper() {
            @Override
            public String getLineNameFromKey(Object key) {
                return key.toString();
            }
        });
    }

    public GraphData(HashMap<Object, List<Point>>lines, NameMapper nameMapper) {
        build(lines, nameMapper);
    }

    private void build(HashMap<Object, List<Point>> lines, NameMapper nameMapper){
        Iterator<Object> iter = lines.keySet().iterator();
        int i = 0;
        while(iter.hasNext()){
            Object key = iter.next();
            Color c = colors[i++];
            String name = nameMapper.getLineNameFromKey(key);
            List<Point> points = lines.get(key);
            GraphLine gl = new GraphLine(name,points,c);
            graphLines.add(gl);
        }

        minX = getLineMinX(graphLines.get(0));
        minY = getLineMinY(graphLines.get(0));
        maxX = getLineMaxX(graphLines.get(0));
        maxY = getLineMaxY(graphLines.get(0));
        for(GraphLine gl: graphLines){
            minX = Math.min(minX,getLineMinX(gl));
            minY = Math.min(minY,getLineMinY(gl));
            maxX = Math.max(maxX,getLineMaxX(gl));
            maxY = Math.max(maxY,getLineMaxY(gl));
        }
    }

    private int getLineMinX(GraphLine gl){
        if(gl.getPoints().size() == 0){
            return 0;
        }

        int min = gl.getPoints().get(0).x;
        for(Point p: gl.getPoints()){
            min = Math.min(p.x,min);
        }

        return min;
    }

    private int getLineMaxX(GraphLine gl){
        if(gl.getPoints().size() == 0){
            return 0;
        }

        int max = gl.getPoints().get(0).x;
        for(Point p: gl.getPoints()){
            max = Math.max(p.x,max);
        }

        return max;
    }

    private int getLineMinY(GraphLine gl){
        if(gl.getPoints().size() == 0){
            return 0;
        }

        int min = gl.getPoints().get(0).y;
        for(Point p: gl.getPoints()){
            min = Math.min(p.y,min);
        }

        return min;
    }

    private int getLineMaxY(GraphLine gl){
        if(gl.getPoints().size() == 0){
            return 0;
        }

        int max = gl.getPoints().get(0).y;
        for(Point p: gl.getPoints()){
            max = Math.max(p.y,max);
        }

        return max;
    }

    public List<GraphLine> getGraphLines() {
        return graphLines;
    }

    public void setGraphLines(List<GraphLine> graphLines) {
        this.graphLines = graphLines;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public int getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public abstract class NameMapper{
        public abstract String getLineNameFromKey(Object key);
    }
}
