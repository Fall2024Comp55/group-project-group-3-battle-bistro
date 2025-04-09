package Screen;

import Character.Character;
import Customer.Customer;
import Enemy.Path;
import Food.IngredientsType;
import Restaurant.*;
import UI.OrderTicketUI;
import UI.RestaurantUI;
import Utils.TickListener;

import java.util.HashSet;
import java.util.Set;

public class RestaurantScreen extends Screen {
    private volatile Set<TickListener> restaurantTickListeners;
    private static final RestaurantScreen RESTAURANT_SCREEN;
    private static CustomerPath customerPath;

    static {
        try {
            RESTAURANT_SCREEN = new RestaurantScreen();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating RestaurantScreen singleton instance");
        }
    }

    private RestaurantScreen() {
        restaurantTickListeners = new HashSet<>();
        add(Character.getInstance());
        initializeComponents();
    }

    public static RestaurantScreen getInstance() {
        return RESTAURANT_SCREEN;
    }

    @Override
    public void initializeComponents() {
        add(RestaurantUI.getInstance());
        add(OrderTicketUI.getInstance());
        elements.add(OrderTicketUI.getInstance());
        elements.add(OrderTicketUI.getInstance());

        
        Door door = new Door();
        door.setLocation(0, 50);
        elements.add(door);
        add(door);

    
        IngredientStation doughStation = new IngredientStation(IngredientsType.DOUGH);
        doughStation.setLocation(100, 300);
        elements.add(doughStation);
        add(doughStation);

        /*
        IngredientStation pepperoniStation = new IngredientStation(IngredientsType.PEPPERONI);
        pepperoniStation.setLocation(200, 300);
        elements.add(pepperoniStation);
        add(pepperoniStation);

        IngredientStation mozzarellaStation = new IngredientStation(IngredientsType.MOZZARELLA);
        mozzarellaStation.setLocation(300, 300);
        elements.add(mozzarellaStation);
        add(mozzarellaStation);

        IngredientStation mushroomStation = new IngredientStation(IngredientsType.MUSHROOM);
        mushroomStation.setLocation(400, 300);
        elements.add(mushroomStation);
        add(mushroomStation);
        */

        // Prep table for assembling pizzas
        PrepTable prepTable = new PrepTable("Prep Table");
        prepTable.setLocation(250, 200);
        elements.add(prepTable);
        add(prepTable);

        // Oven for cooking pizzas
        Oven oven = new Oven();
        oven.setLocation(350, 200);
        elements.add(oven);
        add(oven);

        // Order window for delivering pizzas
        OrderWindow orderWindow = new OrderWindow("Order Window");
        orderWindow.setLocation(450, 200);
        elements.add(orderWindow);
        add(orderWindow);


        customerPath = new CustomerPath(600, 300, 500, 300, 500, 100, 600, 100);
        customerPath.addPath(this);
        Customer.setPath(customerPath);


        Customer testCustomer = new Customer();
        elements.add(testCustomer);
        add(testCustomer);

//        add(OrderTicketUI.getInstance());
//        elements.add(OrderTicketUI.getInstance());
    }

    @Override
    public void registerTickListener(TickListener listener) {
        restaurantTickListeners.add(listener);
    }

    @Override
    public void unregisterTickListener(TickListener listener) {
        restaurantTickListeners.remove(listener);
    }

    @Override
    public void unregisterAllTickListener() {
        restaurantTickListeners.clear();
    }

    @Override
    public void onTick() {
        screenExecutor.submit(() -> {
            restaurantTickListeners.forEach(TickListener::onTick);
         
            OrderTicketUI.getInstance().updateTickets(pathCustomer);
        });
    }


    public Customer getPathCustomer() {
        return pathCustomer;
    }
}




//package Screen;
//import Character.Character;
//import Customer.Customer;
//import Food.IngredientsType;
//import Restaurant.*;
//import UI.OrderTicketUI;
//import UI.RestaurantUI;
//import Utils.TickListener;
//
//import java.util.Set;
//
//public class RestaurantScreen extends Screen {
//    private volatile Set<TickListener> restaurantTickListeners;
//
//    private static final RestaurantScreen RESTAURANT_SCREEN;
//
//    static {
//        try {
//            RESTAURANT_SCREEN = new RestaurantScreen();
//        } catch (Exception e) {
//            throw new RuntimeException("Exception occurred in creating RestaurantScreen singleton instance");
//        }
//    }
//
//    private RestaurantScreen() {
//        // Initialize the restaurant screen components here
//        add(Character.getInstance());
//        initializeComponents();
//    }
//
//    public static RestaurantScreen getInstance() {
//        return RESTAURANT_SCREEN;
//    }
//
//    @Override
//    public void initializeComponents() {
//        add(RestaurantUI.getInstance());
//        add(OrderTicketUI.getInstance());
//
//        // door to switch back to the Garden screen
//        Door door = new Door();
//        door.setLocation(0, 50);
//        elements.add(door);
//        add(door);
//
//        // ingredient stations
//        IngredientStation doughStation = new IngredientStation(IngredientsType.DOUGH);
//        doughStation.setLocation(100, 300);
//        elements.add(doughStation);
//        add(doughStation);
//
//        /*
//        IngredientStation pepperoniStation = new IngredientStation(IngredientsType.PEPPERONI);
//        pepperoniStation.setLocation(200, 300);
//        elements.add(pepperoniStation);
//        add(pepperoniStation);
//
//        IngredientStation mozzarellaStation = new IngredientStation(IngredientsType.MOZZARELLA);
//        mozzarellaStation.setLocation(300, 300);
//        elements.add(mozzarellaStation);
//        add(mozzarellaStation);
//
//        IngredientStation mushroomStation = new IngredientStation(IngredientsType.MUSHROOM);
//        mushroomStation.setLocation(400, 300);
//        elements.add(mushroomStation);
//        add(mushroomStation);
//        */
//
//        // prep table for assembling pizzas
//        PrepTable prepTable = new PrepTable("Prep Table");
//        prepTable.setLocation(250, 200);
//        elements.add(prepTable);
//        add(prepTable);
//
//        // oven for cooking pizzas
//        Oven oven = new Oven();
//        oven.setLocation(350, 200);
//        elements.add(oven);
//        add(oven);
//
//        // order window for delivering pizzas
//        OrderWindow orderWindow = new OrderWindow("Order Window");
//        orderWindow.setLocation(450, 200);
//        elements.add(orderWindow);
//        add(orderWindow);
//
//        //customer
//        Customer customer = new Customer();
//        customer.setLocation(500, 100);
//        elements.add(customer);
//        add(customer);
//
//    }
//
//    @Override
//    public void registerTickListener(TickListener listener) {
//
//    }
//
//    @Override
//    public void unregisterTickListener(TickListener listener) {
//
//    }
//
//    @Override
//    public void unregisterAllTickListener() {
//
//    }
//
//    @Override
//    public void onTick() {
//        screenExecutor.submit(() -> {
//            restaurantTickListeners.spliterator().forEachRemaining(TickListener::onTick);
//        });
//    }
//}
