
package UI;

import Customer.OrderTicket;
import Screen.ProgramWindow;
import acm.graphics.GRect;

import java.awt.*;
import java.util.ArrayList;

public class OrderTicketUI extends UI {
    private static final OrderTicketUI ORDER_TICKET_UI;
    public static final int HORIZONTAL_OFFSET = 30; //50
    public static final int VERTICAL_OFFSET = 50; //150
    public static final int POS_X = 736;
    public static final int POS_Y = 50;
    private boolean hidden = true;

    private static final ArrayList<OrderTicket> tickets;

    static {
        try {
            ORDER_TICKET_UI = new OrderTicketUI();
            tickets = new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating OrderTicketUI singleton instance");
        }
    }

    public static OrderTicketUI getInstance() {
        return ORDER_TICKET_UI;
    }

    public OrderTicketUI() {
//        this.setLocation(POS_X, POS_Y);
        GRect background = new GRect(64, 400);
        background.setFilled(true);
        background.setFillColor(Color.WHITE);
//        background.setLocation(POS_X, POS_Y);
        add(background);
//        GRect tab = new GRect(25, 50);
//        tab.setFilled(true);
//        tab.setFillColor(Color.RED);
//        tab.setLocation(-25,  200);
//        add(tab);
        ActionButton arrow = new ActionButton("<<", this::shiftUI);
        arrow.setLocation(-12, 240);
        arrow.sendToFront();
//        ProgramWindow.getInstance().add(arrow);
        add(arrow);
//        initializeComponents();
    }

    public void shiftUI() {
        if (hidden) {
            hidden = false;
            ProgramWindow.getInstance().animateObject(this, this.getX() + -64, 0, 500);
        } else{
            hidden = true;
            ProgramWindow.getInstance().animateObject(this, this.getX() + 64, 0, 500);
        }
    }

    @Override
    public void initializeComponents() {
//        GRect tab = new GRect(15, 50);
//        tab.setFilled(true);
//        tab.setFillColor(Color.BLUE);
//        tab.setLocation(HORIZONTAL_OFFSET, VERTICAL_OFFSET * tickets.size());
//        add(tab);
//        Food pizza = new Food();
//        pizza.addIngredient(IngredientsType.MOZZARELLA);
//        pizza.addIngredient(IngredientsType.PEPPERONI);
//        addTicket(new OrderTicket(pizza));
    }

    @Override
    public void update() {

    }

    public void addTicket(OrderTicket ticket) {
        tickets.add(ticket);
        ticket.setLocation(HORIZONTAL_OFFSET, VERTICAL_OFFSET * tickets.size());
        add(ticket);
    }

    public void clearTickets() {
        tickets.clear();
    }

    public ArrayList<OrderTicket> getTickets() {
        return tickets;
    }
}



//package UI;
//
//import Customer.Customer;
//import Customer.OrderTicket;
//
//import java.util.ArrayList;
//
//public class OrderTicketUI extends UI {
//
//    private static final OrderTicketUI ORDER_TICKET_UI;
//
//    protected ArrayList<OrderTicket> tickets;
//    public static final int horizontalOffset = 50;
//    public static final  int verticalOffset = 150;
//
//    static {
//        try {
//            ORDER_TICKET_UI = new OrderTicketUI();
//        } catch (Exception e) {
//            throw new RuntimeException("Exception occurred in creating OrderTicketUI singleton instance");
//        }
//    }
//
//    public static OrderTicketUI getInstance() {
//        return ORDER_TICKET_UI;
//    }
//
//    public OrderTicketUI() {
//        tickets = new ArrayList<>();
//        Customer c1 = new Customer();
//        Customer c2 = new Customer();
//        OrderTicket ticket1 = c1.getOrderTicket();
//        OrderTicket ticket2 = c2.getOrderTicket();
//        tickets.add(ticket1);
//        tickets.add(ticket2);
//        int count = 1;
//        for (OrderTicket ticket : tickets) {
//            ticket.setLocation(horizontalOffset, verticalOffset * count);
//            add(ticket);
//            count++;
//        }
//    }
//
//    public void addTicket(OrderTicket ticket) {
//        tickets.add(ticket);
//        ticket.setLocation(horizontalOffset, verticalOffset * tickets.size());
//        add(ticket);
//    }
//
//    public ArrayList<OrderTicket> getTickets() {
//        return tickets;
//    }
//}
