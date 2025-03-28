package UI;

import Utils.MouseInteract;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Button extends GCompound implements MouseInteract {
    private static final String globalFont = "Arial-20";
    private static final Color globalColor = Color.BLACK;
    // TODO find needed variables and methods
    protected GLabel label;
    protected GRect box;
    protected GImage gImage;
    protected boolean hovering;

    public Button(String text) {
        label = new GLabel(text);
        label.setFont(globalFont);
        label.setColor(globalColor);
        box = new GRect(label.getWidth() + 10, label.getHeight() + 10);
        box.setColor(globalColor);
        add(box);
        add(label);
        label.setLocation(-label.getWidth() / 2, -label.getHeight() / 2);
    }

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
