package UI;

import Utils.MouseInteract;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;

import java.awt.*;
import java.awt.event.MouseEvent;

import static UI.GameScreen.GLOBAL_COLOR;
import static UI.GameScreen.GLOBAL_FONT;

public class Button extends GCompound implements MouseInteract {
    protected GLabel label;
    protected GRect box;
    protected GImage gImage;
    protected boolean hovering;

    public Button(String text) {
        label = new GLabel(text);
        label.setFont(GLOBAL_FONT);
        label.setColor(GLOBAL_COLOR);
        box = new GRect(label.getWidth() + 20, label.getHeight() + 10); // Added padding
        box.setColor(GLOBAL_COLOR);
        box.setFilled(true);
        box.setFillColor(Color.WHITE);
        add(box, -box.getWidth() / 2, -box.getHeight() / 2); // Center the box
        add(label, -label.getWidth() / 2, -label.getHeight() / 2); // Center the label
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