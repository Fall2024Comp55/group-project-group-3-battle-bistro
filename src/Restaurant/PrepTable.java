package Restaurant;

import Food.Food;
import Utils.Interact;
import Utils.Solid;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRectangle;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

public class PrepTable extends GCompound implements Interact, Solid {
    // TODO find needed variables and methods
    private static final String basePath = "/resources/restaurant/";
    private static final String extension = ".png";
    private String name;
    private GImage gImage;
    private ArrayList<Food> items;

    public PrepTable(String name) {
        this.name = name;
        GImage gImage = new GImage(getImage());
        this.gImage = gImage;
        //gImage.setLocation(0, 0);
        add(gImage);
    }

    @Override
    public void interact() {
        /*
        TODO: Upon pressing "e", the player will add one ingredient to the pizza. Decrement amount
         of this ingredient by one. If not holding a pizza, do nothing.
         */
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
        return null;
    }

}
