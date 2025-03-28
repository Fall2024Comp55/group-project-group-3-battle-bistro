package UI;

import Utils.MouseInteract;
import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GObject;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Button extends GCompound implements MouseInteract {
    // TODO find needed variables and methods
    private GLabel label;

    @Override
    public void onPress(MouseEvent e) {

    }

    @Override
    public void onDrag(MouseEvent e) {

    }

    @Override
    public void onRelease(MouseEvent e) {

    }

    @Override
    public void onHover(MouseEvent e) {
        if(!hovering) {
            hovering = true;
            scale(1.2, 1.2);
        }
    }

    @Override
    public void stopHover() {
        if(hovering) {
            hovering = false;
            scale(1/1.2, 1/1.2);
        }

    }
}
