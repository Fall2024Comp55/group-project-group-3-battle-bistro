package Utils;

import java.awt.event.MouseEvent;

/**
 * The MouseInteract interface should be implemented by any class that wants to handle
 * mouse interactions such as press, drag, release, and hover events.
 */
public interface MouseInteract {

    /**
     * This method is called when a mouse press event occurs.
     *
     * @param e The MouseEvent object containing details about the mouse press event.
     */
    void onPress(MouseEvent e);

    /**
     * This method is called when a mouse drag event occurs.
     *
     * @param e The MouseEvent object containing details about the mouse drag event.
     */
    void onDrag(MouseEvent e);

    /**
     * This method is called when a mouse release event occurs.
     *
     * @param e The MouseEvent object containing details about the mouse release event.
     */
    void onRelease(MouseEvent e);

    /**
     * This method is called when a mouse hover event occurs.
     * The default method is empty if a class does not need to use this and can be optionally overridden by implementing classes.
     *
     * @param e The MouseEvent object containing details about the mouse hover event.
     */
    default void onHover(MouseEvent e) {

    }

    /**
     * This method is called to stop the hover event.
     * The default method is empty if a class does not need to use this and can be optionally overridden by implementing classes.
     */
    default void stopHover() {

    }

}