package Enemy;

import Utils.Solid;
import acm.graphics.GCompound;
import acm.graphics.GLine;
import acm.graphics.GPoint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Path extends GCompound implements Solid {
    List<GLine> path;
    LinkedList<GPoint> points;

    public Path(int... points) {
        path = new ArrayList<>();
        this.points = new LinkedList<>();
        for (int i = 1; i < points.length; i += 2) {
            this.points.add(new GPoint(points[i - 1], points[i]));
        }
        setupPath();
    }

    public void setupPath () {
        for (int i = 1; i <= points.size() - 1; i++) {
            GPoint p1 = points.get(i - 1);
            GPoint p2 = points.get(i);
            GLine segment = new GLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            path.add(segment);
            add(segment);
        }
    }

    public List<GLine> getPath() {
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


    @Override
    public void onCollision() {

    }
}
