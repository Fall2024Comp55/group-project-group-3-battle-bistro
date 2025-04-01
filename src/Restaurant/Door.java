package Restaurant;

import Utils.Solid;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRectangle;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Door extends GCompound implements Solid {
    // TODO find needed variables and methods
    private static final String basePath = "/resources/restaurant/";
    private static final String extension = ".png";
    private String name;
    private GImage gImage;

    public Door(String name) {
        this.name = name;
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
        if (checkCollision()) {
            //TODO: Remove Restaurant Screen, then add Garden Screen
        }
    }

    @Override
    public GRectangle getHitbox() {
        return null;
    }
}
