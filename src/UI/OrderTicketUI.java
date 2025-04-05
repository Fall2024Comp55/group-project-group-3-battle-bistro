package UI;

import Customer.OrderTicket;

import java.util.ArrayList;

public class OrderTicketUI extends UI {

    private static final OrderTicketUI tab;

    protected ArrayList<OrderTicket> tickets;
    public static final int horizontalOffset = 50;
    public static final  int verticalOffset = 50;

    static {
        try {
            tab = new OrderTicketUI();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating OrderTicketUI singleton instance");
        }
    }

    public OrderTicketUI() {
        tickets = new ArrayList<>();
        OrderTicket ticket1 = new OrderTicket();
        OrderTicket ticket2 = new OrderTicket();
        tickets.add(ticket1);
        tickets.add(ticket2);
        int count = 1;
        for (OrderTicket ticket : tickets) {
            ticket.setLocation(horizontalOffset, verticalOffset * count);
            add(ticket);
            count++;
        }
    }


    public static OrderTicketUI getInstance() {
        return tab;
    }

}
