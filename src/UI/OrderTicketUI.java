package UI;

import Customer.OrderTicket;
import Utils.Interact;
import acm.graphics.GCompound;
import acm.graphics.GRectangle;

import java.util.HashSet;

public class OrderTicketUI extends GCompound implements Interact {

    private static final OrderTicketUI tab;

    protected HashSet<OrderTicket> tickets;

    static {
        try {
            tab = new OrderTicketUI();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating OrderTicketUI singleton instance");
        }
    }

    public OrderTicketUI() {
        tickets = new HashSet<>();
    }

    public static OrderTicketUI getInstance() {
        return tab;
    }

    @Override
    public void interact() {

    }

    @Override
    public GRectangle getInteractHitbox() {
        return null;
    }
}
