package Utils;

import Screen.ProgramWindow;
import acm.graphics.GOval;
import acm.graphics.GPoint;

import java.awt.*;

public class Zone {
    private final GOval zone;
    private final int pathIndex;

    public Zone(double x, double y, double width, double height, int pathIndex) {
        this.zone = new GOval(x, y, width, height);
        this.pathIndex = pathIndex;
    }

    public GPoint[] getSidePoints() {
        GPoint[] points = new GPoint[4];
        points[0] = new GPoint(zone.getX(), zone.getY() + zone.getHeight() / 2);
        points[1] = new GPoint(zone.getX() + zone.getWidth(), zone.getY() + zone.getHeight() / 2);
        points[2] = new GPoint(zone.getX() + zone.getWidth() / 2, zone.getY());
        points[3] = new GPoint(zone.getX() + zone.getWidth() / 2, zone.getY() + zone.getHeight());
        return points;
    }

    public GPoint[] getSidePoints(int offsetX, int offsetY) {
        GPoint[] points = new GPoint[4];
        points[0] = new GPoint(zone.getX() + offsetX, zone.getY() + zone.getHeight() / 2 + offsetY);
        points[1] = new GPoint(zone.getX() + zone.getWidth() + offsetX, zone.getY() + zone.getHeight() / 2 + offsetY);
        points[2] = new GPoint(zone.getX() + zone.getWidth() / 2 + offsetX, zone.getY() + offsetY);
        points[3] = new GPoint(zone.getX() + zone.getWidth() / 2 + offsetX, zone.getY() + zone.getHeight() + offsetY);
        return points;
    }

    public int getPathIndex() {
        return pathIndex;
    }

    public double getX() {
        return zone.getX();
    }

    public double getY() {
        return zone.getY();
    }

    public void addZone() {
        ProgramWindow.getInstance().add(zone);

        GameTick.ActionManager.addAction(80, this::removeZone);
    }

    public void removeZone() {
        ProgramWindow.getInstance().remove(zone);
    }

    public void addPoints() {
        for (GPoint p : getSidePoints()) {
            GOval point = new GOval(p.getX(), p.getY(), 1, 1);
            point.setColor(Color.RED);
            ProgramWindow.getInstance().add(point);
        }
    }
}
