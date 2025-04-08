package Restaurant;

import Screen.ProgramWindow;
import Screen.RestaurantScreen;
import Utils.Interact;
import Utils.Solid;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRectangle;

public class Door extends GCompound implements Solid, Interact {
    // TODO find needed variables and methods
    private static final String PATH = "/resources/restaurant/door.png";

    private final GImage gImage;

    public Door() {
        gImage = new GImage(Utils.getImage(PATH));
        gImage.setLocation(0, 0);
        gImage.setSize(30, 50);
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
        ProgramWindow.getInstance().enterDoor();
    }

    @Override
    public GRectangle getInteractHitbox() {
        return Utils.shrinkBounds(this.getBounds(), 10);
    }
}
