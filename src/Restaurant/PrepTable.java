package Restaurant;

import Character.Character;
import Food.Food;
import Screen.RestaurantScreen;
import Utils.Interact;
import Utils.Solid;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRectangle;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class PrepTable extends GCompound implements Interact, Solid {
    // TODO find needed variables and methods
    private static final String PATH = "/resources/restaurant/prep_table.png";

    private final GImage gImage;
    private Food food;

    public PrepTable() {
        GImage gImage = new GImage(Utils.getImage(PATH));
        this.gImage = gImage;
        gImage.setLocation(0, 0);
        gImage.setSize(50, 50);
        add(gImage);
        gImage.setVisible(false);
    }

    @Override
    public void interact() {
        if (food == null && Character.getInstance().getHolding() != null) {
            food = Character.getInstance().getHolding();
            food.setLocation(gImage.getLocation());
            add(food);
            Character.getInstance().setHolding(null);
        } else if (food != null && Character.getInstance().getHolding() == null) {
            Character.getInstance().setHolding(food);
            remove(food);
            food = null;
        }
    }

    public Image getImage() {
        URL resource = getClass().getResource(PATH);
        if (resource != null) {
            return new ImageIcon(resource).getImage();
        }
        throw new RuntimeException("Could not find image for path " + PATH);
    }

    @Override
    public void onCollision() {

    }

    @Override
    public GRectangle getHitbox() {
        return this.getBounds();
    }

    @Override
    public GRectangle getInteractHitbox() {
        return Utils.getHitboxOffset(this.getBounds(), RestaurantScreen.getInstance().getBounds());
    }

    public GImage getgImage() {
        return gImage;
    }
}
