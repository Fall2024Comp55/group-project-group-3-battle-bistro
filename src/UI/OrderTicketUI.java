
package UI;

import Customer.OrderTicket;

import java.util.ArrayList;

public class OrderTicketUI extends UI {
    private static final OrderTicketUI ORDER_TICKET_UI;
    public static final int HORIZONTAL_OFFSET = 50;
    public static final int VERTICAL_OFFSET = 150;

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
