package Restaurant;

import Customer.OrderTicket;
import Food.Food;
import Food.IngredientsType;
import Utils.Action;
import Utils.GameTick.ActionManager;
import Utils.Interact;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRectangle;

import java.util.LinkedList;
import java.util.Queue;

public class Assembler extends GCompound implements Action, Interact {
    private static final String ASSEMBLER_PATH = "/resources/restaurant/assembler.png";
    private GImage gImage;
    private Queue orderQueue;
    private boolean foodReady;
    private boolean onCooldown;

    public Assembler() {
        orderQueue = new LinkedList();

        gImage = new GImage(Utils.getImage(ASSEMBLER_PATH));
        gImage.setSize(100, 100);
        gImage.setLocation(50, 400);
        add(gImage);
    }

    public void giveTicket(OrderTicket orderTicket) {
        // Implement the logic to give the ticket to the assembler
        orderQueue.add(orderTicket);
        System.out.println("Assembler received an order ticket.");
    }

    public Food processOrder(OrderTicket orderTicket) {
        var food = new Food();
        for (IngredientsType ingredient : orderTicket.getOrder()) {
            food.addIngredient(ingredient);
        }
        food.setCooked(true);
        ActionManager.addAction(400, this);

        return food;
    }

    @Override
    public void performAction() {
        if (onCooldown) {
            onCooldown = false;
        } else if (!foodReady) {
            foodReady = true;
            onCooldown = true;
            ActionManager.addAction(600, this);
        }
    }

    @Override
    public void interact() {

    }

    @Override
    public GRectangle getInteractHitbox() {
        return this.getBounds();
    }
}
