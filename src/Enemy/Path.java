package Enemy;

import UI.GameScreen;
import Utils.Solid;
import acm.graphics.GCompound;
import acm.graphics.GLine;
import acm.graphics.GPoint;
import acm.graphics.GRectangle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Path extends GCompound {
    List<PathLine> path;
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
            PathLine segment = new PathLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            GameScreen.getInstance().add(segment);
            path.add(segment);
        }
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


    public class PathLine extends GLine implements Solid {

        public PathLine(double x0, double y0, double x1, double y1) {
            super(x0, y0, x1, y1);
        }

        @Override
        public void onCollision() {

        }

        @Override
        public GRectangle getHitbox() {
            System.out.println("PathLine hitbox");
            return getBounds();
        }

        @Override
        public Boolean checkCollision() {
            return null;
        }
    }

}
