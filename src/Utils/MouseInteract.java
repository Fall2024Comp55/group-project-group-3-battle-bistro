package Utils;


import java.awt.event.MouseEvent;

public interface MouseInteract {

    void onPress(MouseEvent e);

    void onDrag(MouseEvent e);

    void onRelease(MouseEvent e);

    default void onHover(MouseEvent e) {

    }

    default void stopHover() {

    }

}
