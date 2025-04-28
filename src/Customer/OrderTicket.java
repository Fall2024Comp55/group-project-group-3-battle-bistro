package Customer;

import Character.Character;
import Food.Food;
import Food.IngredientsType;
import Restaurant.Assembler;
import Restaurant.ServingWindow;
import UI.Button;
import UI.OrderTicketUI;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GRect;

import java.awt.event.MouseEvent;
import java.util.EnumSet;

public class OrderTicket extends Button {
    private final EnumSet<IngredientsType> order;
    private static GCompound orderTicketGrabber;
    private static boolean isGrabbing;
    public static final int horizontalOffset = 5;
    public static final  int verticalOffset = 10;
    private Customer customer;


    public OrderTicket(Food pizza, Customer customer) {
        this.customer = customer;
        order = pizza.getIngredients();
        isGrabbing = false;
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

    public static void setOrderTicketGrabber(GCompound orderTicketGrabber) {
        if (orderTicketGrabber instanceof ServingWindow servingWindow) {
            orderTicketGrabber = servingWindow;
        } else if (orderTicketGrabber instanceof Assembler assembler) {
            orderTicketGrabber = assembler;
        } else {
            orderTicketGrabber = null;
        }
        if (orderTicketGrabber != null) {
            isGrabbing = true;
            if (OrderTicketUI.getInstance().isHidden()) {
                OrderTicketUI.getInstance().shiftUI();
            }
        } else {
            isGrabbing = false;
            if (!OrderTicketUI.getInstance().isHidden()) {
                OrderTicketUI.getInstance().shiftUI();
            }
        }

        OrderTicket.orderTicketGrabber = orderTicketGrabber;
    }

    public static boolean isGrabbing() {
        return isGrabbing;
    }




    public EnumSet<IngredientsType> getOrder() {
        return order;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public void onPress(MouseEvent e) {
        if (isGrabbing) {
            if (orderTicketGrabber instanceof ServingWindow servingWindow) {
                if (Customer.checkWaitingCustomer(customer)) {
                    Food pizza = Character.getInstance().getHolding();
                    pizza.setOrderTicket(this);
                    servingWindow.deliverFood(pizza);
                    OrderTicketUI.getInstance().removeTicket(this);
                }
            } else if (orderTicketGrabber instanceof Assembler assembler) {
                assembler.giveTicket(this);
                OrderTicketUI.getInstance().removeTicket(this);
            }
        }
    }

    @Override
    public void stopHover() {
        if (isGrabbing) {
            super.stopHover();
        }
    }

    @Override
    public void onHover(MouseEvent e) {
        if (isGrabbing) {
            super.onHover(e);
        }
    }
}
