package Customer;

import Screen.Screen;
import acm.graphics.GLine;
import acm.graphics.GPoint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CustomerPath {
    private static final int SEGMENT_WIDTH = 20;

    private final List<PathLine> path;
    private final LinkedList<GPoint> points;

    public CustomerPath(int... points) {
        path = new ArrayList<>();
        this.points = new LinkedList<>();
        for (int i = 1; i < points.length; i += 2) {
            this.points.add(new GPoint(points[i - 1], points[i]));
        }
        setupPath();
    }

    public void setupPath() {
        for (int i = 1; i <= points.size() - 1; i++) {
            GPoint p1 = points.get(i - 1);
            GPoint p2 = points.get(i);
            PathLine segment = new PathLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            path.add(segment);
        }
        hidePath();
    }

    public List<PathLine> getPath() {
        return path;
    }

    public List<GPoint> getPoints() {
        return points;
    }

    public GPoint getPoint(int index) {
        return points.get(index);
    }

    public GPoint getNext(GPoint point) {
        int index = points.indexOf(point);
        if (index == -1) {
            return null;
        }
        return points.get(index + 1);
    }

    public GPoint getStart() {
        return points.getFirst();
    }

    public GPoint getEnd() {
        return points.getLast();
    }

    public void addPath(Screen screen) {
        for (PathLine line : path) {
            screen.add(line);
        }
    }

    public void removePath(Screen screen) {
        for (PathLine line : path) {
            screen.remove(line);
        }
    }

    public void showPath() {
        for (PathLine line : path) {
            line.setVisible(true);
        }
    }

    public void hidePath() {
        for (PathLine line : path) {
            line.setVisible(false);
        }
    }

    public static class PathLine extends GLine {
        public PathLine(double x0, double y0, double x1, double y1) {
            super(x0, y0, x1, y1);
        }
    }

}
