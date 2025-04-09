
package UI;

import Customer.Customer;
import Customer.OrderTicket;

import java.util.ArrayList;

public class OrderTicketUI extends UI {
    private static final OrderTicketUI ORDER_TICKET_UI;
    protected ArrayList<OrderTicket> tickets;
    public static final int horizontalOffset = 50;
    public static final int verticalOffset = 150;

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
  
    }

    public void addTicket(OrderTicket ticket) {
        tickets.add(ticket);
        int count = tickets.size();
        ticket.setLocation(horizontalOffset, verticalOffset * count);
        add(ticket);
    }

    public void clearTickets() {
        for (OrderTicket ticket : tickets) {
            remove(ticket);
        }
        tickets.clear();
    }

    public ArrayList<OrderTicket> getTickets() {
        return tickets;
    }

 
    public void updateTickets(Customer customer) {
        clearTickets();
        if (customer != null && customer.hasOrdered()) {
            addTicket(customer.getOrderTicket());
        }
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
