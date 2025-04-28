package Screen;

import Character.Character;
import Customer.Customer;
import Customer.CustomerPath;
import Food.IngredientsType;
import Restaurant.*;
import Utils.GameTick;
import Utils.TickListener;
import Utils.Utils;
import acm.graphics.GImage;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RestaurantScreen extends Screen {
    private volatile Set<TickListener> restaurantTickListeners;
    private static final RestaurantScreen RESTAURANT_SCREEN;
    public static final String PREP_TABLE_PATH = "/resources/restaurant/prep_table.png";
    public static final String WALL_PATH = "/resources/restaurant/wall.png";
    private static final String FLOOR_PATH = "/resources/restaurant/floor.jpg";
    private static final int dayLength = 1200000; // in ticks

    private static CustomerPath customerPath;

    private static long dayTick;
    private static boolean dayStarted;

    static {
        try {
            RESTAURANT_SCREEN = new RestaurantScreen();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating RestaurantScreen singleton instance");
        }
    }

    private RestaurantScreen() {
        restaurantTickListeners = Collections.synchronizedSet(new HashSet<>());
        add(Character.getInstance());
        initializeComponents();
    }

    public static RestaurantScreen getInstance() {
        return RESTAURANT_SCREEN;
    }

    @Override
    public void initializeComponents() {
        // Background image
        GImage wall_image = new GImage(Utils.getImage(WALL_PATH));
        wall_image.setLocation(0, 0);
        wall_image.setSize(800, 100);
        add(wall_image);

        GImage background = new GImage(Utils.getImage(FLOOR_PATH));
        add(background);
        background.sendToBack();

        // Long prep table image
        GImage prep_table_image = new GImage(Utils.getImage(PREP_TABLE_PATH));
        prep_table_image.setLocation(0, 450);
        prep_table_image.setSize(250, 50);
        prep_table_image.rotate(90);
        add(prep_table_image);

        // Door for entering and exiting the restaurant
        Door door = new Door();
        door.setLocation(0, 50);
        add(door);

        // Trash can for disposing of items
        TrashCan trashCan = new TrashCan();
        trashCan.setLocation(50, 50);
        add(trashCan);

        // Order window for taking orders
        OrderWindow orderWindow = new OrderWindow();
        orderWindow.setLocation(490, 100);
        add(orderWindow);

//        GImage order_image = new GImage(Utils.getImage(PREP_TABLE_PATH));
//        order_image.setLocation(490, 50);
//        order_image.setSize(250, 50);
//        order_image.rotate(270);
//        add(order_image);

        Serving_Window mat = new Serving_Window();
        mat.setLocation(445, 150);
        add(mat);

        Register register = new Register();
        register.setLocation(445, 265);
        add(register);

        BoxingTable box = new BoxingTable();
        box.setLocation(445, 350);
        add(box);

        // Oven for cooking pizzas
        Oven oven = new Oven();
        oven.setLocation(200, 20);
        add(oven);

        // Ingredient stations for pizza making
        {
            IngredientStation doughStation = new IngredientStation(IngredientsType.DOUGH);
            doughStation.setLocation(225, 200);
            add(doughStation);

            IngredientStation pepperoniStation = new IngredientStation(IngredientsType.PEPPERONI);
            pepperoniStation.setLocation(315, 275);
            add(pepperoniStation);

            IngredientStation mozzarellaStation = new IngredientStation(IngredientsType.MOZZARELLA);
            mozzarellaStation.setLocation(135, 275);
            add(mozzarellaStation);

            IngredientStation mushroomStation = new IngredientStation(IngredientsType.MUSHROOM);
            mushroomStation.setLocation(285, 200);
            add(mushroomStation);

            IngredientStation sauceStation = new IngredientStation(IngredientsType.SAUCE);
            sauceStation.setLocation(165, 200);
            add(sauceStation);
        }

        // Prep table for preparing ingredients
        {
            PrepTable prepTable0 = new PrepTable();
            prepTable0.setLocation(0, 250);
            prepTable0.rotate(90);
            prepTable0.sendToFront();
            add(prepTable0);

            // Prep table for assembling pizzas
            PrepTable prepTable1 = new PrepTable();
            prepTable1.setLocation(0, 300);
            prepTable1.rotate(90);
            prepTable1.sendToFront();
            add(prepTable1);

            PrepTable prepTable2 = new PrepTable();
            prepTable2.setLocation(0, 350);
            prepTable2.rotate(90);
            prepTable2.sendToFront();
            add(prepTable2);

            PrepTable prepTable3 = new PrepTable();
            prepTable3.setLocation(0, 400);
            prepTable3.rotate(90);
            prepTable3.sendToFront();
            add(prepTable3);

            PrepTable prepTable4 = new PrepTable();
            prepTable4.setLocation(0, 450);
            prepTable4.rotate(90);
            prepTable4.sendToFront();
            add(prepTable4);
        }

        customerPath = new CustomerPath(600, 300, 500, 300, 500, 100, 600, 100);
        customerPath.addPath(this);
        Customer.setPath(customerPath);

        // Add a test customer
        Customer testCustomer = new Customer();
        add(testCustomer);

        GameTick.ActionManager.addAction(100, () -> {
            Customer test2Customer = new Customer();
            add(test2Customer);
        });
    }

    public static void resetDay() {
        dayStarted = true;
        dayTick = 0;
    }

    public static void incrementDayTick() {
        dayTick++;
        if (dayTick >= dayLength) {
            dayTick = 0;
            dayStarted = false;
            ProgramWindow.getInstance().endDay();
        }
    }

    public static Boolean isDayStarted() {
        return dayStarted;
    }


    @Override
    public synchronized void registerTickListener(TickListener listener) {
        restaurantTickListeners.add(listener);
    }

    @Override
    public synchronized void unregisterTickListener(TickListener listener) {
        restaurantTickListeners.remove(listener);
    }

    @Override
    public void unregisterAllTickListener() {
        restaurantTickListeners.clear();
    }

    @Override
    public void update() {

    }

    @Override
    public void onTick() {
        if (dayStarted) {
            screenExecutor.submit(() -> {
                restaurantTickListeners.forEach(TickListener::onTick);
            });
            incrementDayTick();
        }
    }
}