package Customer;

import Food.IngredientsType;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

import java.util.EnumSet;
import java.util.Set;

public class OrderTicket extends GCompound {
    private final EnumSet<IngredientsType> order;
    private Set<GObject> elements;
    public static final int horizontalOffset = 5;
    public static final  int verticalOffset = 10;


    public OrderTicket() {
        order = EnumSet.noneOf(IngredientsType.class);
        order.add(IngredientsType.DOUGH);
        order.add(IngredientsType.PEPPERONI);
        GRect background = new GRect(0, 0, 50, 80);
        add(background);
        background.setLocation(Utils.getCenter(background.getBounds()));
        int count = 2;
        for (IngredientsType ingredient: getOrder()) {
            System.out.println(ingredient.toString() + "HERE");
            GLabel ingred_name = new GLabel(ingredient.toString());
            ingred_name.setLocation(background.getX() + horizontalOffset, background.getY() + verticalOffset * count);
            add(ingred_name);
            System.out.println(ingred_name.isVisible());
            count++;
        }
    }


    public EnumSet<IngredientsType> getOrder() {
        return order;
    }


}
