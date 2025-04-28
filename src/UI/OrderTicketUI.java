
package UI;

import Customer.OrderTicket;
import Screen.ProgramWindow;
import acm.graphics.GRect;

import java.awt.*;
import java.util.ArrayList;

public class OrderTicketUI extends UI {
    private static final OrderTicketUI ORDER_TICKET_UI;
    public static final int HORIZONTAL_OFFSET = 30; //50
    public static final int VERTICAL_OFFSET = 10; //150
    private boolean hidden = true;
    private boolean selection_mode = false;

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
        initializeComponents();
    }

    public void shiftUI() {
        if (hidden) {
            hidden = false;
            ProgramWindow.getInstance().animateObject(this, this.getX() - 64, this.getY(), 500);
        } else{
            hidden = true;
            ProgramWindow.getInstance().animateObject(this, this.getX() + 64, this.getY(), 500);
        }
    }

    @Override
    public void initializeComponents() {
        GRect background = new GRect(64, 400);
        background.setFilled(true);
        background.setFillColor(Color.WHITE);
        add(background);
        GRect tab = new GRect(25, 50);
        tab.setFilled(true);
        tab.setFillColor(Color.RED);
        tab.setLocation(-25, 200);
        add(tab);
        ActionButton arrow = new ActionButton("<<", this::shiftUI);
        arrow.setLocation(-12, 240);
        arrow.sendToFront();
        add(arrow);
    }

    @Override
    public void update() {

    }

    public void addTicket(OrderTicket ticket) {
        tickets.add(ticket);
        ticket.setLocation(HORIZONTAL_OFFSET, (VERTICAL_OFFSET * tickets.size() + (tickets.size() - 1) * 80) + 40);
        add(ticket);
    }

    public void rearrangeTickets() {
        for (OrderTicket ticket : tickets) {
            ticket.setLocation(HORIZONTAL_OFFSET, (VERTICAL_OFFSET * tickets.size() + (tickets.size() - 1) * 80) + 40);
        }
    }

    public void removeTicket(OrderTicket ticket) {
        tickets.remove(ticket);
        remove(ticket);
        rearrangeTickets();
    }

    public void clearTickets() {
        tickets.clear();
    }

    public ArrayList<OrderTicket> getTickets() {
        return tickets;
    }

    public void setSelectionMode(boolean selection_mode) {
        this.selection_mode = selection_mode;
    }

    public boolean getSelectionMode() {
        return this.selection_mode;
    }

    public boolean getHidden() {
        return this.hidden;
    }

    public void setHidden(boolean hide) {
        this.hidden = hide;
    }
}