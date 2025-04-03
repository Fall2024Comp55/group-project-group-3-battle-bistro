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
    private static final String basePath = "/resources/OrderWindow/";
    private static final String extension = ".png";

    private String name;
    private GImage gImage;
    protected Food item;

    public OrderWindow(String name) {
        this.name = name;
        GImage gImage = new GImage(getImage());
        this.gImage = gImage;
        //gImage.setLocation(0, 0);
        add(gImage);
    }

    public String toPath() {
        return basePath + name.toLowerCase() + extension;
    }

    public Image getImage() {
        URL resource = getClass().getResource(toPath());
        if (resource != null) {
            return new ImageIcon(resource).getImage();
        }
        throw new RuntimeException("Could not find image for path " + toPath());
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
