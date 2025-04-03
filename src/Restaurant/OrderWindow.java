package Restaurant;

import Character.Character;
import Food.Food;
import Utils.Interact;
import Utils.Solid;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRectangle;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class OrderWindow extends GCompound implements Solid, Interact {
    // TODO find needed variables and methods
    private static final String PATH = "/resources/restaurant/order_window.png";

    private final GImage gImage;
    protected Food item;

    public OrderWindow(String name) {
        GImage gImage = new GImage(getImage());
        this.gImage = gImage;
        //gImage.setLocation(0, 0);
        add(gImage);
    }

    public Image getImage() {
        URL resource = getClass().getResource(PATH);
        if (resource != null) {
            return new ImageIcon(resource).getImage();
        }
        throw new RuntimeException("Could not find image for path " + PATH);
    }

    @Override
    public void interact() {
        Food pizza = Character.getInstance().getHolding();
        if (pizza.isCooked() && pizza.isBoxed()) {
            item = pizza;
            Character.getInstance().setHolding(null);
        }
    }

    @Override
    public GRectangle getInteractHitbox() {
        return this.getBounds();
    }

    @Override
    public void onCollision() {

    }

    @Override
    public GRectangle getHitbox() {
        return null;
    }
}
