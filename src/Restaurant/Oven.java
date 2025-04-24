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
    private static final String PATH2 = "/resources/restaurant/cooking.png";

    private GImage gImage;
    private GImage gImage2;
    private Food item;
    private int tick_speed = 200;

    public Oven() {
        GImage gImage = new GImage(Utils.getImage(PATH));
        GImage gImage2 = new GImage(Utils.getImage(PATH2));
        this.gImage = gImage;
        this.gImage2 = gImage2;
        gImage.setSize(100, 100);
        gImage2.setSize(100, 100);
        //gImage.setLocation(0, 0);
        add(gImage);
        tick_speed = 100;
    }

    public void interact() {
        Food pizza = Character.getInstance().getHolding();
        if (item == null) {
            if (pizza != null && !pizza.isCooked() && !pizza.isBoxed()) {
                remove(gImage);
                add(gImage2);
                item = pizza;
                Character.getInstance().setHolding(null);
                GameTick.ActionManager.addAction(tick_speed, () -> {
                    pizza.setCooked(true);
                    remove(gImage2);
                    add(gImage);
                });
            }
        } else if (item.isCooked() && pizza == null) {
            Character.getInstance().setHolding(item);
            item = null;
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
