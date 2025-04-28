package Restaurant;

import Character.Character;
import Food.Food;
import Utils.Interact;
import Utils.Solid;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRectangle;

public class Box extends GCompound implements Solid, Interact {
    private static final String PATH = "/resources/restaurant/Pizza_Box.png";

    private final GImage gImage;

    public Box() {
        GImage gImage = new GImage(Utils.getImage(PATH));
        this.gImage = gImage;
        gImage.setSize(45, 45);
        add(gImage);
    }

    @Override
    public void interact() {
        System.out.println("Boxing");
        Food pizza = Character.getInstance().getHolding();
        if (pizza != null) {
            pizza.box();
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
