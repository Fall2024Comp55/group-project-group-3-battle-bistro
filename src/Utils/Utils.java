package Utils;

import acm.graphics.GPoint;
import acm.graphics.GRectangle;

public class Utils {
    //TODO work on getCenter and getCenterOffset methods
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


    /**
     * Linearly interpolates between two values.
     *
     * @param start the starting value
     * @param end   the ending value
     * @param t     the interpolation factor, typically between 0 and 1
     * @return the interpolated value
     */
    public static double lerp(double start, double end, double t) {
        return start + (end - start) * t;
    }

    /**
     * Applies an ease-in-out cubic function to the interpolation factor.
     *
     * @param t the interpolation factor, typically between 0 and 1
     * @return the eased value
     */
    public static double easeInOutCubic(double t) {
        return t < 0.5 ? 4 * t * t * t : 1 - Math.pow(-2 * t + 2, 3) / 2;
    }
}
