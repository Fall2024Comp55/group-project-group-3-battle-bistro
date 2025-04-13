package Utils;

import Screen.Screen;
import UI.UI;
import acm.graphics.GCompound;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.graphics.GRectangle;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Utils {
    public static final URL MISSING_TEXTURE = Utils.class.getResource("/resources/missingtexture.png");
    //TODO work on getCenter and getCenterOffset methods
    public static GPoint getCenter(double width, double height) {
        return new GPoint(-width / 2, -height / 2);
    }

    public static GPoint getCenter(GRectangle bounds) {
        return new GPoint(-bounds.getWidth() / 2, -bounds.getHeight() / 2);
    }

    public static GPoint getCenterPoint(GRectangle bounds) {
        return new GPoint(bounds.getWidth() / 2, bounds.getHeight() / 2);
    }

    public static GPoint getCenterOffset(GPoint p, double width, double height) {
        return new GPoint(p.getX() - (width / 2), p.getY() - (height / 2));
    }

    public static GPoint getCenterOffset(GPoint p, GRectangle bounds) {
        return new GPoint(p.getX() - (bounds.getWidth() / 2), p.getY() - (bounds.getHeight() / 2));
    }

    public static GPoint getPointOffset(GPoint p, GRectangle bounds) {
        return new GPoint(p.getX() + bounds.getX(), p.getY() + bounds.getY());
    }

    /**
     * Shrinks the bounds of a rectangle by a specified number of pixels.
     *
     * @param bounds      the original bounds
     * @param pixelShrink the number of pixels to shrink the bounds by
     * @return a new `GRectangle` with the shrunk bounds
     */
    public static GRectangle shrinkBounds(GRectangle bounds, int pixelShrink) {
        return new GRectangle(bounds.getX() + pixelShrink, bounds.getY() + pixelShrink, bounds.getWidth() - (pixelShrink), bounds.getHeight() - (pixelShrink));
    }

    /**
     * Retrieves an image from the resources folder. If the image is not found, return MISSING_TEXTURE.
     *
     * @param path the path to the image file
     * @return the image, or a default missing texture if the image is not found
     */
    public static Image getImage(String path) {
        URL resource = Utils.class.getResource(path);
        if (resource != null) {
            return new ImageIcon(resource).getImage();
        } else {
            System.out.println("Could not find image for path " + path);
            Thread.dumpStack();
            return new ImageIcon(MISSING_TEXTURE).getImage();
        }
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
     * Calculates the offset of a hitbox within a parent hitbox.
     *
     * @param bounds       the bounds of the hitbox
     * @param parentBounds the bounds of the parent hitbox
     * @return a new `GRectangle` representing the offset hitbox
     */
    public static GRectangle getHitboxOffset(GRectangle bounds, GRectangle parentBounds) {
        return new GRectangle(bounds.getX() + parentBounds.getX(), bounds.getY() + parentBounds.getY(), bounds.getWidth(), bounds.getHeight());
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
