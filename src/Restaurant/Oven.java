package Restaurant;

import Food.Food;
import Food.IngredientsType;
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
    private static final String basePath = "/resources/restaurant/";
    private static final String extension = ".png";
    private String name;
    private GImage gImage;
    private Food item;


    public Oven(String name, IngredientsType ingredient) {
        this.name = name;
        GImage gImage = new GImage(getImage());
        this.gImage = gImage;
        //gImage.setLocation(0, 0);
        add(gImage);
    }

    public void interact(){
        boolean ready;

        GameTick.ActionManager.addAction(120, () -> {
            //ready = true;
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
}
