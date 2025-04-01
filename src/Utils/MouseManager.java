package Utils;

import acm.graphics.GObject;

import java.awt.*;

/**
 * The MouseManager class manages mouse interactions and keeps track of the selected object,
 * last click point, last mouse point, hover point, and hover object.
 */
public class MouseManager {
    private static GObject selectedObject;
    private static Point lastClickPoint;
    private static Point lastMousePoint;
    private static Point hoverPoint;
    private static GObject hoverObject;

    /**
     * @return The currently selected GObject.
     */
    public static GObject getSelectedObject() {
        return selectedObject;
    }

    /**
     * Sets the currently selected object.
     *
     * @param object The GObject to be set as selected.
     */
    public static void setSelectedObject(GObject object) {
        selectedObject = object;
    }

    /**
     * @return The Point of the last mouse click.
     */
    public static Point getLastClickPoint() {
        return lastClickPoint;
    }

    /**
     * Sets the point of the last mouse click.
     *
     * @param point The Point to be set as the last click point.
     */
    public static void setLastClickPoint(Point point) {
        lastClickPoint = point;
    }

    /**
     * @return The Point of the last mouse movement.
     */
    public static Point getLastMousePoint() {
        return lastMousePoint;
    }

    /**
     * Sets the point of the last mouse movement.
     *
     * @param point The Point to be set as the last mouse point.
     */
    public static void setLastMousePoint(Point point) {
        lastMousePoint = point;
    }

    /**
     * @return The Point where the mouse is currently hovering.
     */
    public static Point getHoverPoint() {
        return hoverPoint;
    }

    /**
     * Sets the point where the mouse is currently hovering.
     *
     * @param point The Point to be set as the hover point.
     */
    public static void setHoverPoint(Point point) {
        hoverPoint = point;
    }

    /**
     * @return The GObject that the mouse is currently hovering over.
     */
    public static GObject getHoverObject() {
        return hoverObject;
    }

    /**
     * Sets the object that the mouse is currently hovering over.
     * If the previous hover object implements MouseInteract and is not the given object, its stopHover method is called.
     *
     * @param object The GObject to be set as the hover object.
     */
    public static void setHoverObject(GObject object) {
        if (hoverObject instanceof MouseInteract o && hoverObject != object) {
            o.stopHover();
        }
        hoverObject = object;
    }
}