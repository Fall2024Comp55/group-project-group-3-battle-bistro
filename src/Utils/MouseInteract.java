package Utils;


import java.awt.*;
import java.awt.event.MouseEvent;

public interface MouseInteract {

    void onPress(MouseEvent e, Point lastClickPoint);

    void onDrag(MouseEvent e, Point lastClickPoint, Point lastMousePoint);

    void onRelease(MouseEvent e, Point lastClickPoint, Point lastMousePoint);

}
