package UI;

import Customer.OrderTicket;

import java.util.ArrayList;

public class OrderTicketUI extends UI {

    private static final OrderTicketUI ORDER_TICKET_UI;

    protected ArrayList<OrderTicket> tickets;
    public static final int horizontalOffset = 50;
    public static final  int verticalOffset = 150;

    static {
        try {
            ORDER_TICKET_UI = new OrderTicketUI();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating OrderTicketUI singleton instance");
        }
    }

    public static OrderTicketUI getInstance() {
        return ORDER_TICKET_UI;
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

    public void addTicket(OrderTicket ticket) {
        tickets.add(ticket);
        ticket.setLocation(horizontalOffset, verticalOffset * tickets.size());
        add(ticket);
    }

    public ArrayList<OrderTicket> getTickets() {
        return tickets;
    }
}
