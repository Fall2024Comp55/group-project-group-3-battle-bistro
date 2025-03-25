package Utils;


import acm.graphics.GObject;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface MouseInteract {

    void onPress(MouseEvent e);

    void onDrag(MouseEvent e);

    void onRelease(MouseEvent e);

}
