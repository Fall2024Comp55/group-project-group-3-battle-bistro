package Restaurant;

import Character.Character;
import Customer.Customer;
import Food.Food;
import Utils.Interact;
import Utils.Solid;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRectangle;

public class OrderWindow extends GCompound implements Solid, Interact {
    // TODO find needed variables and methods
    private static final String PATH = "/resources/restaurant/prep_table.png";

    private final GImage gImage;
    protected Food item;

    public OrderWindow() {
        GImage gImage = new GImage(Utils.getImage(PATH));
        this.gImage = gImage;
//        gImage.setSize(32, 425);
        //        order_image.setLocation(490, 50);
        gImage.setSize(350, 50);
        gImage.rotate(270);
        //gImage.setLocation(0, 0);
        add(gImage);
    }

    @Override
    public void interact() {
        Food food = Character.getInstance().getHolding();
        if (food != null) {
            Customer customer = Customer.getCustomerFromTicket(food.getOrderTicket());
            if (customer != null) {
                customer.deliverFood(food);
                Character.getInstance().setHolding(null);
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
