package Customer;

import Food.IngredientsType;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GObject;
import acm.graphics.GRect;

import java.util.EnumSet;
import java.util.Set;

public class OrderTicket extends GCompound {
    private final EnumSet<IngredientsType> order;
    private Set<GObject> elements;


    public OrderTicket() {
        order = EnumSet.noneOf(IngredientsType.class);
        GRect background = new GRect(0, 0, 50, 80);
        add(background);
        background.setLocation(Utils.getCenter(background.getBounds()));
    }


    public EnumSet<IngredientsType> getOrder() {
        return order;
    }


}
