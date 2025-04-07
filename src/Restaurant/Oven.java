package Restaurant;

import Character.Character;
import Food.Food;
import Screen.RestaurantScreen;
import Utils.Action;
import Utils.GameTick;
import Utils.Interact;
import Utils.Solid;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRectangle;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Oven extends GCompound implements Action, Solid, Interact {
    // TODO find needed variables and methods
    private static final String PATH = "/resources/restaurant/oven.png";

    private final GImage gImage;
    private Food item;
    private int tick_speed = 200;

    public Oven() {
        GImage gImage = new GImage(getImage());
        this.gImage = gImage;
        //gImage.setLocation(0, 0);
        add(gImage);
        tick_speed = 100;
    }

    public void interact() {
        Food pizza = Character.getInstance().getHolding();
        if (pizza != null && !pizza.isCooked()) {
            GameTick.ActionManager.addAction(tick_speed, () -> {
                pizza.setCooked(true);
            });
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
    public void performAction() {

    }

    @Override
    public void onCollision() {

    }

    @Override
    public GRectangle getHitbox() {
        return Utils.Utils.getHitboxOffset(this.getBounds(), RestaurantScreen.getInstance().getBounds());
    }

    @Override
    public GRectangle getInteractHitbox() {
        return null;
    }
}
