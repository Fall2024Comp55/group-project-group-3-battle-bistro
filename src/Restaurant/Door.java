package Restaurant;

import UI.GameScreen;
import Utils.Interact;
import Utils.Solid;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRectangle;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Door extends GCompound implements Solid, Interact {
    // TODO find needed variables and methods
    private static final String basePath = "/resources/restaurant/";
    private static final String extension = ".png";
    private static final String name = "door";

    private final GImage gImage;

    public Door() {
        GImage gImage = new GImage(getImage());
        this.gImage = gImage;
        gImage.setLocation(0, 0);
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
    public void onCollision() {
    }

    @Override
    public GRectangle getHitbox() {
        return this.getBounds();
    }

    @Override
    public void interact() {
        GameScreen.getInstance().enterDoor();
    }

    @Override
    public GRectangle getInteractHitbox() {
        return null;
    }
}
