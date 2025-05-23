
package Customer;

import Food.Food;
import Food.IngredientsType;
import Screen.RestaurantScreen;
import UI.OrderTicketUI;
import Utils.TickListener;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GPoint;

import java.util.Random;

public class Customer extends GCompound implements TickListener {
    private static final int SIZE = 40;
    private static final double MOVE_RATE = 0.1;
    private static final String CUSTOMER_PATH = "/resources/character/character.png";

    private static CustomerPath path;

    private final OrderTicket orderTicket;
    private final GImage gImage;
    private final double maxWaitTick;
    private double ticksWaited;

    private GPoint targetPoint;
    private double speed = 10;

    private boolean hasOrdered;
    private boolean hasFood;
    private boolean isSatisfied;
    private boolean hasLeft;
    private boolean isMoving;

    public Customer() {
        orderTicket = new OrderTicket(generateOrder(), this);
        maxWaitTick = 1200; // 60 seconds
        isSatisfied = false;
        hasOrdered = false;
        hasFood = false;
        hasLeft = false;
        isMoving = true;
        targetPoint = path.getPoint(1);
        gImage = new GImage(Utils.getImage(CUSTOMER_PATH));
        gImage.setSize(SIZE, SIZE);
        this.rotate(90);
        gImage.setLocation(Utils.getCenter(gImage.getBounds()));
        add(gImage);
        setLocation(path.getStart());
    }

    public static Customer peekRegister() {
        return path.peekQueue(0);
    }

    public static boolean checkWaitingCustomer(Customer customer) {
        if (customer != null) {
            for (Customer c : path.getCustomersFromLine(1)) {
                System.out.println(c + " " + customer);
                if (c == customer) {
                    return true;
                }
            }
        }
        return false;
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

        boolean wantsCheese = random.nextBoolean();
        boolean wantsSauce = random.nextBoolean();

        boolean reallyWantsSauce;
        if (random.nextInt(0, 9) == 0) {
            reallyWantsSauce = true;
        } else {
            reallyWantsSauce = false;
        }

        if (wantsSauce == false && wantsCheese == false) {
            if (reallyWantsSauce) {
                order.addIngredient(IngredientsType.SAUCE);
            } else if (random.nextBoolean()) {
                if (IngredientsType.MOZZARELLA.isUnlocked()) {
                    order.addIngredient(IngredientsType.MOZZARELLA);
                } else {
                    System.out.println("Customer wants cheese, but it's not unlocked.");
                    order.addIngredient(IngredientsType.SAUCE);
                }
            } else {
                order.addIngredient(IngredientsType.SAUCE);
            }
        } else {
            if (wantsSauce || reallyWantsSauce) {
                order.addIngredient(IngredientsType.SAUCE);
            }
            if (wantsCheese) {
                if (IngredientsType.MOZZARELLA.isUnlocked()) {
                    order.addIngredient(IngredientsType.MOZZARELLA);
                } else {
                    System.out.println("Customer wants cheese, but it's not unlocked.");
                }
            }
        }

        if (!IngredientsType.getUnlockedIngredients().isEmpty()) {
            for (int i = 0; i < random.nextInt(0, IngredientsType.getUnlockedIngredients().size()); i++) {
                IngredientsType ingredient = IngredientsType.getRandomIngredient();
                if (!order.getIngredients().contains(ingredient)) {
                    order.addIngredient(ingredient);
                } else {
                    i--;
                }
            }
        }
        return order;
    }

    public static Customer dequeueRegister() {
        return path.dequeueCustomer(0);
    }

    public void startMoving() {
        isMoving = true;
    }

    public static Customer getCustomerFromTicket(OrderTicket ticket) {
        if (ticket != null) {
            for (Customer customer : path.getCustomersFromLine(2)) {
                if (customer.getOrderTicket().equals(ticket)) {
                    return customer;
                }
            }
        }
        return null;
    }

    public boolean isMoving() {
        return isMoving;
    }

  
    public OrderTicket getOrderTicket() {
        return orderTicket;
    }


    public void update() {
        if (!hasLeft) {
            if (hasOrdered) {
                ticksWaited++;
                if (ticksWaited >= maxWaitTick) {
                    isMoving = true;
                    isSatisfied = false;
                }
            }
        }
    }

    public void takeOrder() {
        hasOrdered = true;
        isMoving = true;
        if (path.getQueueSize(0) > 0) {
            System.out.println("Customer is waiting in line.");
            path.peekQueue(0).startMoving();
        }
        OrderTicketUI.getInstance().addTicket(orderTicket);
    }


    public void receivePizza(Food pizza) {
        if (pizza != null) {
            hasFood = true;
            if (matchesOrder(pizza)) {
                isSatisfied = true;
                System.out.println("Customer is satisfied with their pizza!");
            } else {
                isSatisfied = false;
                System.out.println("Customer is not satisfied with the pizza.");
            }
        }
    }


    private boolean matchesOrder(Food pizza) {
        if (!orderTicket.getOrder().equals(pizza.getIngredients())) {
            return false;
        }
//        for (IngredientsType ingredient : orderTicket.getOrder()) {
//            if (!pizza.hasIngredient(ingredient)) {
//                return false;
//            }
//        }
        return pizza.isCooked() && pizza.isBoxed();
    }


    private void leave() {
        if (isSatisfied && hasFood) {
            System.out.println("Customer left satisfied!");
        } else if (!isSatisfied && hasFood) {
            System.out.println("Customer left unsatisfied with a pizza!");
        } else {
            System.out.println("Customer left unsatisfied without a pizza!");
        }
        hasLeft = true;
    }

    public boolean isSatisfied() {
        return isSatisfied;
    }

    public boolean hasOrdered() {
        return hasOrdered;
    }

    public void deliverFood(Food food) {
        if (food != null) {
            hasFood = true;
            if (matchesOrder(food)) {
                isSatisfied = true;
                System.out.println("Customer is satisfied with their pizza!");
            } else {
                isSatisfied = false;
                System.out.println("Customer is not satisfied with the pizza.");
            }
        } else {
            isSatisfied = false;
            hasFood = false;
            System.out.println("Customer did not receive pizza and is not satisfied.");
        }
        isMoving = true;
    }

    public void move() {
        CustomerPath.PathLine line = path.getLineFromPoint(targetPoint);
        double targetX = targetPoint.getX();
        double targetY = targetPoint.getY();
        GPoint currentPos = this.getLocation();
        if (!line.isEmpty() && line.peekQueue() != this) {
            if (targetX == currentPos.getX()) {
                targetY += line.getCustomerOffset();
            } else {
                targetX += line.getCustomerOffset();
            }
        }
        double dx = targetX - currentPos.getX();
        double dy = targetY - currentPos.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < speed * MOVE_RATE) {
            isMoving = false;
            if (targetPoint.equals(path.getEnd())) {
                leave();
            } else {
                line.queueCustomer(this);
                System.out.println(path.peekQueue(0) == this);
                if (line.peekQueue() == this) {
                    targetPoint = path.getNext(targetPoint);
                }
            }
        } else {

            double moveX = (dx / distance) * speed * MOVE_RATE;
            double moveY = (dy / distance) * speed * MOVE_RATE;
            this.move(moveX, moveY);
        }
    }


    @Override
    public void onTick() {
        if (isMoving) {
            move();
        }
        update();
        if (hasLeft) {
            RestaurantScreen.getInstance().remove(this);
        }
    }
}
