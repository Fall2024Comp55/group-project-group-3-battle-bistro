package Restaurant;

import Utils.Interact;
import Utils.Solid;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRectangle;

public class Register extends GCompound implements Solid, Interact {
    private static final String PATH = "/resources/restaurant/register.png";

    private final GImage gImage;

    public Register() {
        GImage gImage = new GImage(Utils.getImage(PATH));
        this.gImage = gImage;
        gImage.setSize(35, 35);
        add(gImage);
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
