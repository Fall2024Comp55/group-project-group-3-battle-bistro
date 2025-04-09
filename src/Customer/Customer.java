
package Customer;

import Enemy.Path;
import Food.Food;
import Food.IngredientsType;
import Screen.RestaurantScreen;
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
    private static final String KIRBY_IMAGE_PATH = "/resources/enemies/kirby.png"; 

    private final OrderTicket orderTicket;
    private final double waitTime;
    private boolean isSatisfied;
    private boolean hasOrdered;
    private double timeWaited;
    private final GImage gImage;
    private final Path path;
    private GPoint targetPoint;
    private boolean alive;
    private int pauseCounter;
    private double speed = 10; 
    private int currentPointIndex;

    public Customer(Path path) {
        this.path = path;
        orderTicket = new OrderTicket(generateOrder());
        waitTime = 30000;
        isSatisfied = false;
        hasOrdered = false;
        timeWaited = 0;
        alive = true;
        pauseCounter = 0;
        currentPointIndex = 0;
        targetPoint = path.getPoint(1); 
        gImage = new GImage(Utils.getImage(KIRBY_IMAGE_PATH));
        gImage.setSize(SIZE, SIZE);
        gImage.setLocation(Utils.getCenter(gImage.getBounds()));
        add(gImage);
        setLocation(path.getStart());
    }

  
    private Food generateOrder() {
        Food pizza = new Food();
        Random random = new Random();
        
        if (random.nextBoolean()) pizza.setCheese(true);
        if (random.nextBoolean()) pizza.setSauce(true);
        if (random.nextBoolean()) pizza.setMushroom(true);
        if (random.nextBoolean()) pizza.setPepperoni(true);
        hasOrdered = true;
        return pizza;
    }

  
    public OrderTicket getOrderTicket() {
        return orderTicket;
    }

 
    public void update(double deltaTime) {
        if (!isSatisfied && hasOrdered) {
            timeWaited += deltaTime;
            if (timeWaited >= waitTime) {
                
                leaveUnsatisfied();
            }
        }
    }


    public void move() {
        if (!alive) return;

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
            currentPointIndex++;
            if (currentPointIndex >= path.getPoints().size() - 1) {

                despawn();
            } else {

                pauseCounter = PAUSE_TICKS;
                targetPoint = path.getPoint(currentPointIndex + 1);
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


    private void leaveUnsatisfied() {
        System.out.println("Customer left because they waited too long!");
        despawn();
    }

 
    private void despawn() {
        alive = false;
        GameTick.ActionManager.addAction(1, () -> {
            removeAll();
            RestaurantScreen.getInstance().remove(this);
        });
    }

    public boolean isSatisfied() {
        return isSatisfied;
    }

    public boolean hasOrdered() {
        return hasOrdered;
    }

    public boolean isAlive() {
        return alive;
    }

    @Override
    public void onTick() {
        move(); 
        update(GameTick.TICK_DELAY); 
        if (!alive) {
            RestaurantScreen.getInstance().unregisterTickListener(this);
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