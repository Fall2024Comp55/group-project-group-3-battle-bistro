package Utils;

import Screen.Screen;
import UI.UI;
import acm.graphics.GCompound;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.graphics.GRectangle;

import java.awt.*;

public class Utils {
    //TODO work on getCenter and getCenterOffset methods
    public static GPoint getCenter(double width, double height) {
        return new GPoint(-width / 2, -height / 2);
    }

    public static GPoint getCenter(GRectangle bounds) {
        return new GPoint(-bounds.getWidth() / 2, -bounds.getHeight() / 2);
    }

    public static GPoint getCenterOffset(GPoint p, double width, double height) {
        return new GPoint(p.getX() - (width / 2), p.getY() - (height / 2));
    }

    public static GPoint getCenterOffset(GPoint p, GRectangle bounds) {
        return new GPoint(p.getX() - (bounds.getWidth() / 2), p.getY() - (bounds.getHeight() / 2));
    }

/**
 * Retrieves the object at the specified point within the given compound.
 *
 * @param c the compound to search within
 * @param p the point at which to look for an object
 * @return the object at the specified point, or null if no object is found
 */
public static GObject getObjectInCompound(GCompound c, Point p) {
    GObject object = c.getElementAt(p.getX(), p.getY());
    if (object instanceof UI ui) {
        object = getObjectInCompound(ui, p);
    } else if (object instanceof Screen screen) {
        object = getObjectInCompound(screen, p);
    }
    return object;
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
