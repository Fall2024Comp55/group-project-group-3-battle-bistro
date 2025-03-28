package Utils;

import acm.graphics.GObject;

import java.awt.*;

public class MouseManager {
    private static GObject selectedObject;
    private static Point lastClickPoint;
    private static Point lastMousePoint;
    private static Point hoverPoint;
    private static GObject hoverObject;

    public static GObject getSelectedObject() {
        return selectedObject;
    }

    public static void setSelectedObject(GObject object) {
        selectedObject = object;
    }

    public static Point getLastClickPoint() {
        return lastClickPoint;
    }

    public static void setLastClickPoint(Point point) {
        lastClickPoint = point;
    }

    public static Point getLastMousePoint() {
        return lastMousePoint;
    }

    public static void setLastMousePoint(Point point) {
        lastMousePoint = point;
    }

    public static Point getHoverPoint() {
        return hoverPoint;
    }

    public static void setHoverPoint(Point point) {
        hoverPoint = point;
    }

    public static GObject getHoverObject() {
        return hoverObject;
    }

    public static void setHoverObject(GObject object) {
        if (hoverObject instanceof MouseInteract o && hoverObject != object) {
            o.stopHover();
        }
        hoverObject = object;
    }
}
