package Customer;

import Food.IngredientsType;
import acm.graphics.GCompound;

import java.util.EnumSet;

public class OrderTicket extends GCompound {
    private final EnumSet<IngredientsType> order;

    public OrderTicket() {
        order = EnumSet.noneOf(IngredientsType.class);
    }

    public EnumSet<IngredientsType> getOrder() {
        return order;
    }


}
