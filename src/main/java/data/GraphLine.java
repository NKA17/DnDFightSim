package data;


import javafx.util.Pair;

import java.awt.*;
import java.util.List;

public class GraphLine {
    private Color color;
    private List<Point> points;
    private String lineName;

    public GraphLine(String name, List<Point> points, Color color) {
        this.color = color;
        this.points = points;
        this.lineName = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }
}
