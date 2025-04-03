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

public class Register extends GCompound implements Solid, Interact {
    private static final String PATH = "/resources/restaurant/register.png";

    private final String name;
    private final GImage gImage;
    private Food item;

    public Register(String name) {
        this.name = name;
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
        //TODO: Check if customer is present upon interaction, take order and retrieve order ticket.
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
