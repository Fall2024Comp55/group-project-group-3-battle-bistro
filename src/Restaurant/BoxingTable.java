package Restaurant;

import Character.Character;
import Food.Food;
import Utils.Interact;
import Utils.Solid;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRectangle;

public class BoxingTable extends GCompound implements Solid, Interact {
    private static final String BOX_PATH = "/resources/restaurant/pizza_box_left.png";

    private final GImage gImage;

    public BoxingTable() {
        GImage gImage = new GImage(Utils.getImage(BOX_PATH));
        this.gImage = gImage;
        gImage.setSize(45, 45);
        add(gImage);
    }

    @Override
    public void interact() {
        Food pizza = Character.getInstance().getHolding();
        if (Character.getInstance().getHolding() != null) {
            if (pizza != null) {
                if (!pizza.isBoxed()) {
                    pizza.box();
                } else {
                    pizza.unbox();
                }
            }
        }
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
