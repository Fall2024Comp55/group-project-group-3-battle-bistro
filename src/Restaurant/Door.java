package Restaurant;

import Screen.ProgramWindow;
import Screen.RestaurantScreen;
import Utils.Interact;
import Utils.Solid;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRect;
import acm.graphics.GRectangle;

public class Door extends GCompound implements Solid, Interact {
    // TODO find needed variables and methods
    private static final String PATH = "/resources/restaurant/door.png";

    private final GImage gImage;
    private GRect interact_hitbox;

    public Door() {
        gImage = new GImage(Utils.getImage(PATH));
        gImage.setSize(30, 50);
        add(gImage);
//        interact_hitbox = new GRect(0, 0, 30, 50);
//        add(interact_hitbox);
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
        System.out.println("interact");
        ProgramWindow.getInstance().enterDoor();
    }

    @Override
    public GRectangle getInteractHitbox() {
        return Utils.getHitboxOffset(this.getBounds(), RestaurantScreen.getInstance().getBounds());
    }
}
