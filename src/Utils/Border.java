package Utils;

import acm.graphics.GCompound;
import acm.graphics.GRect;
import acm.graphics.GRectangle;

import java.awt.*;

public class Border extends GRect implements Solid {
    private final GCompound parent;

    public Border(double x0, double y0, double x1, double y1, GCompound parent) {
        super(0, 0, x1, y1);
        if (parent != null) {
            this.parent = parent;
        } else {
            throw new IllegalArgumentException("Parent cannot be null");
        }
        setFilled(true);
        setFillColor(Color.RED);
    }


    public void show() {
        setVisible(true);
    }

    public void hide() {
        setVisible(false);
    }

    @Override
    public void onCollision() {

    }

    @Override
    public GRectangle getHitbox() {
        return Utils.getHitboxOffset(this.getBounds(), parent.getBounds());
    }
}
