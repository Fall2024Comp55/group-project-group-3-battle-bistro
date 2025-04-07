package Enemy;

import Screen.GardenScreen;
import Screen.Screen;
import Utils.MouseInteract;
import Utils.Solid;
import Utils.Utils;
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

    public void addPathHitbox(Screen screen) {
        for (PathLine line : path) {
            screen.add(line.getVisualHitbox());
        }
    }

    public void removePathHitbox(Screen screen) {
        for (PathLine line : path) {
            screen.remove(line.getVisualHitbox());
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

    public void showPathHitbox() {
        for (PathLine line : path) {
            line.getVisualHitbox().setVisible(true);
        }
    }

    public void hidePathHitbox() {
        for (PathLine line : path) {
            line.getVisualHitbox().setVisible(false);
        }
    }


    public static class PathLine extends GLine implements Solid, MouseInteract {
        private GRect hitbox;

        public PathLine(double x0, double y0, double x1, double y1) {
            super(x0, y0, x1, y1);
            if (getWidth() == 0) {
                hitbox = new GRect(getBounds().getX() - SEGMENT_WIDTH / 2, getBounds().getY() - SEGMENT_WIDTH / 2, SEGMENT_WIDTH, getBounds().getHeight() + SEGMENT_WIDTH);
            } else {
                hitbox = new GRect(getBounds().getX() - SEGMENT_WIDTH / 2, getBounds().getY() - SEGMENT_WIDTH / 2, getBounds().getWidth() + SEGMENT_WIDTH, SEGMENT_WIDTH);
            }
        }

        public GRect getVisualHitbox() {
            return hitbox;
        }

        @Override
        public void onCollision() {

        }

        @Override
        public GRectangle getHitbox() {
            return Utils.getHitboxOffset(hitbox.getBounds(), GardenScreen.getInstance().getBounds());
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
