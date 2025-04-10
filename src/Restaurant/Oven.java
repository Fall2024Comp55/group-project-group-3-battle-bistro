package Restaurant;

import Character.Character;
import Food.Food;
import Screen.RestaurantScreen;
import Utils.*;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRectangle;

public class Oven extends GCompound implements Action, Solid, Interact {
    // TODO find needed variables and methods
    private static final String PATH = "/resources/restaurant/oven.png";

    private final GImage gImage;
    private Food item;
    private int tick_speed = 200;

    public Oven() {
        GImage gImage = new GImage(Utils.getImage(PATH));
        this.gImage = gImage;
        gImage.setSize(20, 50);
        //gImage.setLocation(0, 0);
        add(gImage);
        tick_speed = 100;
    }

    public void interact() {
        Food pizza = Character.getInstance().getHolding();
        if (pizza != null && !pizza.isCooked()) {
            GameTick.ActionManager.addAction(tick_speed, () -> {
                pizza.setCooked(true);
            });
        }
    }

    @Override
    public void performAction() {

    }

    @Override
    public void onCollision() {

    }

    @Override
    public GRectangle getHitbox() {
        return Utils.getHitboxOffset(this.getBounds(), RestaurantScreen.getInstance().getBounds());
    }

    @Override
    public GRectangle getInteractHitbox() {
        return null;
    }
}
