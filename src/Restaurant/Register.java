package Restaurant;

import Character.Character;
import Customer.Customer;
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
        gImage.setSize(45, 64);
        add(gImage);
    }

    @Override
    public void interact() {
        if (Character.getInstance().getHolding() == null &&
                Customer.peekRegister() != null && !Customer.peekRegister().isMoving()) {
            Customer customer = Customer.dequeueRegister();
            System.out.println(customer);
            customer.takeOrder();
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
