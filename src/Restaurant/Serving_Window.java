package Restaurant;

import Food.Food;
import UI.OrderTicketUI;
import Utils.Interact;
import Utils.Solid;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRectangle;

public class Serving_Window extends GCompound implements Solid, Interact {
    // TODO find needed variables and methods
    private static final String PATH = "/resources/restaurant/serve.png";

    private final GImage gImage;
    protected Food item;

    public Serving_Window() {
        GImage gImage = new GImage(Utils.getImage(PATH));
        this.gImage = gImage;
//        gImage.setSize(32, 425);
        //        order_image.setLocation(490, 50);
        gImage.setSize(45, 64);
        //gImage.rotate(270);
        //gImage.setLocation(0, 0);
        add(gImage);
    }

    @Override
    public void interact() {
        if (OrderTicketUI.getInstance().getHidden()) {
            OrderTicketUI.getInstance().shiftUI();
        }
        OrderTicketUI.getInstance().setSelectionMode(true);
//        Food food = Character.getInstance().getHolding();
//        if (food != null) {
//            Customer customer = Customer.getCustomerFromTicket(food.getOrderTicket());
//            if (customer != null) {
//                customer.deliverFood(food);
//                Character.getInstance().setHolding(null);
//            }
//        }
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
