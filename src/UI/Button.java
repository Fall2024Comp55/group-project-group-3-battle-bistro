package UI;

import Utils.MouseInteract;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;

import java.awt.*;
import java.awt.event.MouseEvent;

import static Screen.ProgramWindow.GLOBAL_COLOR;
import static Screen.ProgramWindow.GLOBAL_FONT;

public class Button extends GCompound implements MouseInteract {
    protected GLabel label;
    protected GRect box;
    protected GImage gImage;
    protected boolean hovering;

    Button() {

    }

    Button(String text, boolean outline) {
        label = new GLabel(text);
        label.setFont(GLOBAL_FONT);
        label.setColor(GLOBAL_COLOR);
        add(label, Utils.getCenter(label.getBounds()));
        if (outline) {
            box = new GRect(label.getWidth() + 20, label.getHeight() + 10);
            box.setColor(GLOBAL_COLOR);
            box.setFilled(true);
            box.setFillColor(Color.WHITE);
            add(box, Utils.getCenter(box.getBounds()));
            box.sendToBack();
        }
    }

    Button(Image image, int size) {
        gImage = new GImage(image);
        gImage.setSize(size, size);
        gImage.setLocation(Utils.getCenter(gImage.getBounds()));
        add(gImage);
        box = new GRect(gImage.getWidth(), gImage.getHeight());
        box.setColor(GLOBAL_COLOR);
        box.setFilled(true);
        box.setFillColor(Color.WHITE);
        add(box, Utils.getCenter(box.getBounds())); // add and center the box
        box.sendToBack();
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
        if (!hovering) {
            hovering = true;
            scale(1.2, 1.2);
        }
    }

    @Override
    public void stopHover() {
        if (hovering) {
            hovering = false;
            scale(1 / 1.2, 1 / 1.2);
        }
    }
}