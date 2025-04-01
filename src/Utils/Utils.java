package Utils;

import acm.graphics.GPoint;
import acm.graphics.GRectangle;

public class Utils {
    public static GPoint getCenter(GPoint p, double width, double height) {
        return new GPoint(p.getX() + (width / 2), p.getY() + (height / 2));
    }

    public static GPoint getCenter(GPoint p, GRectangle bounds) {
        return new GPoint(p.getX() + (bounds.getWidth() / 2), p.getY() + (bounds.getHeight() / 2));
    }

    public static GPoint getCenter(int x, int y, GRectangle bounds) {
        return new GPoint(x + (bounds.getWidth() / 2), y + (bounds.getHeight() / 2));
    }

    public static GPoint getCenter(int x, int y, double width, double height) {
        return new GPoint(x + (width / 2), y + (height / 2));
    }

    public static GPoint getCenterOffset(GPoint p, double width, double height) {
        return new GPoint(p.getX() - (width / 2), p.getY() - (height / 2));
    }

    public static GPoint getCenterOffset(GPoint p, GRectangle bounds) {
        return new GPoint(p.getX() - (bounds.getWidth() / 2), p.getY() - (bounds.getHeight() / 2));
    }

    public static double lerp(double start, double end, double t) {
        return start + (end - start) * t;
    }

    public static double easeInOutCubic(double t) {
        return t < 0.5 ? 4 * t * t * t : 1 - (double) Math.pow(-2 * t + 2, 3) / 2;
    }

}
