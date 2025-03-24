package Customer;

import Food.IngredientsType;
import acm.graphics.GCompound;

import java.util.EnumSet;

public class Customer extends GCompound {
    // TODO find needed variables and methods
    private OrderTicket orderTicket;
    private double waitTime;
    // other variables needed?

    public Customer() {
        orderTicket = new OrderTicket();
        waitTime = 0;
    }


    // order randomizer in customer or in order ticket?


}
