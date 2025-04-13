package Restaurant;

import Character.Character;
import Screen.RestaurantScreen;
import Utils.Interact;
import Utils.Solid;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRectangle;

public class TrashCan extends GCompound implements Solid, Interact {
    // TODO find needed variables and methods
    private static final String PATH = "/resources/restaurant/trash_can.png";

    private final GImage gImage;

    public TrashCan() {
        gImage = new GImage(Utils.getImage(PATH));
        add(gImage);
    }

    @Override
    public void onCollision() {
    }

    @Override
    public GRectangle getHitbox() {
        return Utils.getHitboxOffset(this.getBounds(), RestaurantScreen.getInstance().getBounds());
    }

    @Override
    public void interact() {
        Character.getInstance().setHolding(null);
    }

    @Override
    public GRectangle getInteractHitbox() {
        return Utils.getHitboxOffset(this.getBounds(), RestaurantScreen.getInstance().getBounds());
    }
}
