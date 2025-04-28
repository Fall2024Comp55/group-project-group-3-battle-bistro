package Customer;

import Character.Character;
import Food.Food;
import Food.IngredientsType;
import UI.Button;
import UI.OrderTicketUI;
import Utils.Utils;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

import java.awt.event.MouseEvent;
import java.util.EnumSet;
import java.util.Set;

public class OrderTicket extends Button {
    private final EnumSet<IngredientsType> order;
    private Set<GObject> elements;
    public static final int horizontalOffset = 5;
    public static final  int verticalOffset = 10;
    private Customer customer;


    public OrderTicket(Food pizza, Customer customer) {
//        super();
        this.customer = customer;
        order = pizza.getIngredients();
        GRect background = new GRect(0, 0, 50, 80);
        add(background);
        background.setLocation(Utils.getCenter(background.getBounds()));
        int count = 2;
        for (IngredientsType ingredient: getOrder()) {
            GLabel ingredientName = new GLabel(ingredient.toString());
            ingredientName.setLocation(background.getX() + horizontalOffset, background.getY() + verticalOffset * count);
            add(ingredientName);
            count++;
        }
    }


    public EnumSet<IngredientsType> getOrder() {
        return order;
    }

    @Override
    public void onPress(MouseEvent e) {
//        super.onPress(e);
        if (OrderTicketUI.getInstance().getSelectionMode()) {
            Food pizza = Character.getInstance().getHolding();
            customer.deliverFood(pizza);
            OrderTicketUI.getInstance().removeTicket(this);
            OrderTicketUI.getInstance().setSelectionMode(false);
            OrderTicketUI.getInstance().shiftUI();
            Character.getInstance().setHolding(null);
        }
    }
}
