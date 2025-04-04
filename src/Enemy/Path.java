package Enemy;

import Screen.ProgramWindow;
import Utils.MouseInteract;
import Utils.Solid;
import acm.graphics.GLine;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.graphics.GRectangle;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Path {
    private static final int SEGMENT_WIDTH = 20;

    private final List<PathLine> path;
    private final LinkedList<GPoint> points;

    public Path(int... points) {
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
            ProgramWindow.getInstance().add(segment);
            path.add(segment);
            ProgramWindow.getInstance().add(new GRect(segment.getHitbox().getX(), segment.getHitbox().getY(), segment.getHitbox().getWidth(), segment.getHitbox().getHeight()));
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


    public static class PathLine extends GLine implements Solid, MouseInteract {

        public PathLine(double x0, double y0, double x1, double y1) {
            super(x0, y0, x1, y1);
        }

        @Override
        public void onCollision() {

        }

        @Override
        public GRectangle getHitbox() {
            GRectangle hitbox = getBounds();

            if (hitbox.getWidth() == 0) {
                hitbox.setBounds(hitbox.getX() - SEGMENT_WIDTH / 2, hitbox.getY() - SEGMENT_WIDTH / 2, SEGMENT_WIDTH, hitbox.getHeight() + SEGMENT_WIDTH);
            } else {
                hitbox.setBounds(hitbox.getX() - SEGMENT_WIDTH / 2, hitbox.getY() - SEGMENT_WIDTH / 2, hitbox.getWidth() + SEGMENT_WIDTH, SEGMENT_WIDTH);
            }

            return hitbox;
        }

        @Override
        public Boolean checkCollision() {
            return null;
        }

        @Override
        public void onPress(MouseEvent e) {
            System.out.println(this);
            System.out.println(this.getBounds());
        }

        @Override
        public void onDrag(MouseEvent e) {

        }

        @Override
        public void onRelease(MouseEvent e) {

        }
    }

}
