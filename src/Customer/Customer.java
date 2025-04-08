package Customer;

import Food.Food;
import Food.IngredientsType;
import acm.graphics.GCompound;

import java.util.Random;

public class Customer extends GCompound {
    private final OrderTicket orderTicket;
    private final double waitTime;
    private boolean isSatisfied;
    private boolean hasOrdered;
    private double timeWaited;

    public Customer() {
        orderTicket = new OrderTicket(generateOrder());
        waitTime = 30000; 
        isSatisfied = false;
        hasOrdered = false;
        timeWaited = 0;
    }

    // Method to generate an order for now, a pizza with pepperoni 
    private Food generateOrder() {
        Food pizza = new Food();
        Random random = new Random();
        // Randomly add other ingredients
        if (random.nextBoolean()) pizza.setCheese(true);
        if (random.nextBoolean()) pizza.setSauce(true);
        if (random.nextBoolean()) pizza.setMushroom(true);
        if (random.nextBoolean()) pizza.setPepperoni(true);
        hasOrdered = true;
        return pizza;
    }

    // Getter for the order ticket
    public OrderTicket getOrderTicket() {
        return orderTicket;
    }

    // Update the customer's state called every game tick
    public void update(double deltaTime) {
        if (!isSatisfied && hasOrdered) {
            timeWaited += deltaTime;
            if (timeWaited >= waitTime) {
                // Customer leaves if they wait too long
                leaveUnsatisfied();
            }
        }
    }

    // Called when the customer receives their pizza
    public void receivePizza(Food pizza) {
        if (pizza != null && matchesOrder(pizza)) {
            isSatisfied = true;
            System.out.println("Customer is satisfied with their pizza!");
        } else {
            System.out.println("Customer is not satisfied with the pizza.");
        }
    }

    // Check if the pizza matches the order
    private boolean matchesOrder(Food pizza) {
        for (IngredientsType ingredient : orderTicket.getOrder()) {
            if (!pizza.hasIngredient(ingredient)) {
                return false;
            }
        }
        return pizza.isCooked() && pizza.isBoxed();
    }

    // Customer leaves if they wait too long
    private void leaveUnsatisfied() {
        System.out.println("Customer left because they waited too long!");
        // remove the customer from the screen here
        this.removeAll();
       
    }

   
    public boolean isSatisfied() {
        return isSatisfied;
    }

    public boolean hasOrdered() {
        return hasOrdered;
    }
}