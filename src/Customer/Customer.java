
package Customer;

import Food.Food;
import Food.IngredientsType;
import Screen.RestaurantScreen;
import UI.OrderTicketUI;
import Utils.GameTick;
import Utils.TickListener;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GPoint;

import java.util.Random;

public class Customer extends GCompound implements TickListener {
    private static final int SIZE = 20; 
    private static final double MOVE_RATE = 0.1; 
    private static final int PAUSE_TICKS = 5000 / GameTick.TICK_DELAY;
    private static final String KIRBY_IMAGE_PATH = "/resources/character/character.png";

    private static CustomerPath path;

    private final OrderTicket orderTicket;
    private final GImage gImage;
    private final double waitTime;
    private double timeWaited;

    private GPoint targetPoint;
    private int pauseCounter;
    private double speed = 10;

    private boolean hasOrdered;
    private boolean hasFood;
    private boolean isSatisfied;
    private boolean hasLeft;

    public Customer() {
        orderTicket = new OrderTicket(generateOrder());
        // test take order call
        takeOrder();
        waitTime = 30000;
        isSatisfied = false;
        hasOrdered = false;
        hasFood = false;
        hasLeft = false;
        timeWaited = 0;
        pauseCounter = 0;
        targetPoint = path.getPoint(1);
        // need to change image
        gImage = new GImage(Utils.getImage(KIRBY_IMAGE_PATH));
        gImage.setSize(SIZE, SIZE);
        gImage.setLocation(Utils.getCenter(gImage.getBounds()));
        add(gImage);
        setLocation(path.getStart());
    }

    public static CustomerPath getPath() {
        return path;
    }

    public static void setPath(CustomerPath path) {
        Customer.path = path;
    }

    public static void removePath() {
        path = null;
    }

    private Food generateOrder() {
        Food order = new Food();
        Random random = new Random();
        if (random.nextBoolean()) order.setCheese(true);
        if (random.nextBoolean()) order.setSauce(true);
        if (random.nextBoolean()) order.setMushroom(true);
        if (random.nextBoolean()) order.setPepperoni(true);
        hasOrdered = true;
        return order;
    }

    public void takeOrder() {
        hasOrdered = true;
        OrderTicketUI.getInstance().addTicket(orderTicket);
    }

    public void deliverFood(Food food) {
        if (food != null && matchesOrder(food)) {
            hasFood = true;
            isSatisfied = true;
            System.out.println("Customer is satisfied with their pizza!");
        } else {
            isSatisfied = false;
            System.out.println("Customer is not satisfied with the pizza.");
        }
    }

  
    public OrderTicket getOrderTicket() {
        return orderTicket;
    }


    public void update() {
        if (!isSatisfied && hasOrdered) {
            timeWaited += GameTick.TICK_DELAY;
            if (timeWaited >= waitTime) {

                leave();
            }
        }
    }


    public void move() {
        if (pauseCounter > 0) {
            pauseCounter--;
            return; 
        }

        double targetX = targetPoint.getX();
        double targetY = targetPoint.getY();
        GPoint currentPos = this.getLocation();
        double dx = targetX - currentPos.getX();
        double dy = targetY - currentPos.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < speed * MOVE_RATE) {

            this.setLocation(targetPoint);
            if (targetPoint.equals(path.getEnd())) {

            } else {

                pauseCounter = PAUSE_TICKS;
                targetPoint = path.getNext(targetPoint);
            }
        } else {

            double moveX = (dx / distance) * speed * MOVE_RATE;
            double moveY = (dy / distance) * speed * MOVE_RATE;
            this.move(moveX, moveY);
        }
    }


    public void receivePizza(Food pizza) {
        if (pizza != null && matchesOrder(pizza)) {
            isSatisfied = true;
            System.out.println("Customer is satisfied with their pizza!");
        } else {
            System.out.println("Customer is not satisfied with the pizza.");
        }
    }


    private boolean matchesOrder(Food pizza) {
        for (IngredientsType ingredient : orderTicket.getOrder()) {
            if (!pizza.hasIngredient(ingredient)) {
                return false;
            }
        }
        return pizza.isCooked() && pizza.isBoxed();
    }


    private void leave() {
        if (isSatisfied) {
            System.out.println("Customer left satisfied!");
        } else {
            System.out.println("Customer left unsatisfied!");
        }
        hasLeft = true;
    }

    public boolean isSatisfied() {
        return isSatisfied;
    }

    public boolean hasOrdered() {
        return hasOrdered;
    }

    @Override
    public void onTick() {
        move();
        update();
        if (hasLeft) {
            RestaurantScreen.getInstance().remove(this);
        }
    }
}


//package Customer;
//
//import Food.Food;
//import Food.IngredientsType;
//import acm.graphics.GCompound;
//
//import java.util.Random;
//
//public class Customer extends GCompound {
//    private final OrderTicket orderTicket;
//    private final double waitTime;
//    private boolean isSatisfied;
//    private boolean hasOrdered;
//    private double timeWaited;
//
//    public Customer() {
//        orderTicket = new OrderTicket(generateOrder());
//        waitTime = 30000; 
//        isSatisfied = false;
//        hasOrdered = false;
//        timeWaited = 0;
//    }
//
//    // Method to generate an order for now, a pizza with pepperoni 
//    private Food generateOrder() {
//        Food pizza = new Food();
//        Random random = new Random();
//        // Randomly add other ingredients
//        if (random.nextBoolean()) pizza.setCheese(true);
//        if (random.nextBoolean()) pizza.setSauce(true);
//        if (random.nextBoolean()) pizza.setMushroom(true);
//        if (random.nextBoolean()) pizza.setPepperoni(true);
//        hasOrdered = true;
//        return pizza;
//    }
//
//    // Getter for the order ticket
//    public OrderTicket getOrderTicket() {
//        return orderTicket;
//    }
//
//    // Update the customer's state called every game tick
//    public void update(double deltaTime) {
//        if (!isSatisfied && hasOrdered) {
//            timeWaited += deltaTime;
//            if (timeWaited >= waitTime) {
//                // Customer leaves if they wait too long
//                leaveUnsatisfied();
//            }
//        }
//    }
//
//    // Called when the customer receives their pizza
//    public void receivePizza(Food pizza) {
//        if (pizza != null && matchesOrder(pizza)) {
//            isSatisfied = true;
//            System.out.println("Customer is satisfied with their pizza!");
//        } else {
//            System.out.println("Customer is not satisfied with the pizza.");
//        }
//    }
//
//    // Check if the pizza matches the order
//    private boolean matchesOrder(Food pizza) {
//        for (IngredientsType ingredient : orderTicket.getOrder()) {
//            if (!pizza.hasIngredient(ingredient)) {
//                return false;
//            }
//        }
//        return pizza.isCooked() && pizza.isBoxed();
//    }
//
//    // Customer leaves if they wait too long
//    private void leaveUnsatisfied() {
//        System.out.println("Customer left because they waited too long!");
//        // remove the customer from the screen here
//        this.removeAll();
//       
//    }
//
//   
//    public boolean isSatisfied() {
//        return isSatisfied;
//    }
//
//    public boolean hasOrdered() {
//        return hasOrdered;
//    }
//}