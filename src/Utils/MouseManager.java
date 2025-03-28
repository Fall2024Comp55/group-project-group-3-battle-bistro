package Utils;

import acm.graphics.GObject;

import java.awt.*;

public class MouseManager {
    private static GObject selectedObject;
    private static Point lastClickPoint;
    private static Point lastMousePoint;

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

    public static Point getCurrentMousePoint() {
        return currentMousePoint;
    }

    public static void setCurrentMousePoint(Point point) {
        currentMousePoint = point;
    }
}
