package Restaurant;

import Food.Food;
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
import java.util.concurrent.atomic.AtomicBoolean;

public class Oven extends GCompound implements Action, Solid, Interact {
    // TODO find needed variables and methods
    private static final String basePath = "/resources/restaurant/";
    private static final String extension = ".png";
    private static final String name = "oven";

    private final GImage gImage;
    private Food item;
    private int tick_speed;

    public Oven() {
        GImage gImage = new GImage(getImage());
        this.gImage = gImage;
        //gImage.setLocation(0, 0);
        add(gImage);
        tick_speed = 100;
    }

    public void interact() {
        AtomicBoolean ready = new AtomicBoolean(false);

        GameTick.ActionManager.addAction(tick_speed, () -> {
            ready.set(true);
        });
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
    public void performAction() {

    }

    @Override
    public void onCollision() {

    }

    @Override
    public GRectangle getHitbox() {
        return null;
    }

    @Override
    public GRectangle getInteractHitbox() {
        return null;
    }
}
