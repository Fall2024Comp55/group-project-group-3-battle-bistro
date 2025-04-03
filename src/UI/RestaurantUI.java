package UI;

import Character.Character;
import Customer.Customer;
import Food.IngredientsType;
import Restaurant.Door;
import Restaurant.IngredientStation;
import Restaurant.OrderWindow;
import Restaurant.Oven;
import Restaurant.PrepTable;
import Utils.Solid;

import acm.graphics.*;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static UI.GameScreen.*;

public class RestaurantUI extends GCompound implements Solid {
    private static final RestaurantUI RESTAURANT_UI;

    private final Set<GObject> elements;
    private final GLabel moneyLabel;
    private final GRect healthBarBackground;
    private final GRect healthBar;
    private int starRating;
    private GCompound starDisplay;
    private GCompound notificationArea;
    private GLabel notificationLabel;
    private ScheduledExecutorService notificationExecutor;

    static {
        try {
            RESTAURANT_UI = new RestaurantUI();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating RestaurantUI singleton instance");
        }
    }

    private RestaurantUI() {
        elements = new HashSet<>();
        Character c = Character.getInstance();
        moneyLabel = new GLabel("Money: " + c.getBalance());
        healthBarBackground = new GRect(200, 20);
        healthBar = new GRect(200, 20);

        // Menu bar 
        GRect menuBar = new GRect(WIDTH, 50);
        menuBar.setFilled(true);
        menuBar.setFillColor(Color.LIGHT_GRAY);
        add(menuBar, 0, 0);
        elements.add(menuBar);

        // Money label 
        moneyLabel.setFont(GLOBAL_FONT);
        moneyLabel.setColor(GLOBAL_COLOR);
        moneyLabel.setLocation(20, 35);
        add(moneyLabel);
        elements.add(moneyLabel);

        // Health bar background
        healthBarBackground.setFilled(true);
        healthBarBackground.setFillColor(Color.GRAY);
        healthBarBackground.setLocation(150, 15);
        add(healthBarBackground);
        elements.add(healthBarBackground);

        // Health bar 
        healthBar.setFilled(true);
        healthBar.setFillColor(Color.GREEN);
        healthBar.setLocation(150, 15);
        add(healthBar);
        elements.add(healthBar);

        // star rating 
        initStarRating();

        // notification area
        initNotificationArea();

        // door to switch back to the Garden screen
        Door door = new Door();
        door.setLocation(50, 100);
        elements.add(door);
        add(door);

        // ingredient stations
        IngredientStation doughStation = new IngredientStation(IngredientsType.DOUGH);
        doughStation.setLocation(100, 300);
        elements.add(doughStation);
        add(doughStation);

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

        // prep table for assembling pizzas
        PrepTable prepTable = new PrepTable("Prep Table");
        prepTable.setLocation(250, 200);
        elements.add(prepTable);
        add(prepTable);
        
        // oven for cooking pizzas
        Oven oven = new Oven();
        oven.setLocation(350, 200);
        elements.add(oven);
        add(oven);

        // order window for delivering pizzas
        OrderWindow orderWindow = new OrderWindow("Order Window");
        orderWindow.setLocation(450, 200);
        elements.add(orderWindow);
        add(orderWindow);

        //customer
        Customer customer = new Customer();
        customer.setLocation(500, 100);
        elements.add(customer);
        add(customer);
    }

    public static RestaurantUI getInstance() {
        return RESTAURANT_UI;
    }

    private void initStarRating() {
        starRating = 3;
        starDisplay = new GCompound();

        for (int i = 0; i < 5; i++) {
            GLabel star = new GLabel("★");
            star.setFont("Arial-20");
            star.setColor(i < starRating ? Color.YELLOW : Color.GRAY);
            star.setLocation(i * 25, 0);
            starDisplay.add(star);
        }

        starDisplay.setLocation(WIDTH - starDisplay.getWidth() - 10, 15);
        add(starDisplay);
        elements.add(starDisplay);
    }

    public void updateStarRating(int newRating) {
        starRating = Math.max(0, Math.min(5, newRating));
        for (int i = 0; i < 5; i++) {
            GLabel star = (GLabel) starDisplay.getElement(i);
            star.setColor(i < starRating ? Color.YELLOW : Color.GRAY);
        }
    }

    private void initNotificationArea() {
        notificationArea = new GCompound();
        notificationLabel = new GLabel("");
        notificationLabel.setFont(GLOBAL_FONT);
        notificationLabel.setColor(GLOBAL_COLOR);
        notificationArea.add(notificationLabel);

        notificationArea.setLocation(
            (WIDTH - notificationLabel.getWidth()) / 2,
            HEIGHT / 2
        );
        add(notificationArea);
        elements.add(notificationArea);
    }

    public void showNotification(String message, int durationMs) {
        notificationLabel.setLabel(message);
        notificationLabel.setLocation(
            -notificationLabel.getWidth() / 2,
            -notificationLabel.getHeight() / 2
        );

        notificationLabel.setVisible(true);

        if (notificationExecutor != null) {
            notificationExecutor.shutdownNow();
        }
        notificationExecutor = Executors.newSingleThreadScheduledExecutor();

        AtomicInteger alpha = new AtomicInteger(255);
        notificationExecutor.scheduleAtFixedRate(() -> {
            alpha.set(Math.max(0, alpha.get() - 5));
            notificationLabel.setColor(new Color(0, 0, 0, alpha.get()));
            if (alpha.get() <= 0) {
                notificationLabel.setVisible(false);
                notificationExecutor.shutdown();
            }
        }, durationMs, 50, TimeUnit.MILLISECONDS);
    }

    public void update() {
        updateMoneyLabel();
        updateHealthBar();
    }

    private void updateMoneyLabel() {
        moneyLabel.setLabel("Money: " + Character.getInstance().getBalance());
    }

    private void updateHealthBar() {
        double health = Character.getInstance().getHealth();
        double healthPercentage = health / 100.0;
        healthBar.setSize(200 * healthPercentage, 20);
        healthBar.setLocation(150, 15);
        if (health > 50) {
            healthBar.setFillColor(Color.GREEN);
        } else if (health > 25) {
            healthBar.setFillColor(Color.YELLOW);
        } else {
            healthBar.setFillColor(Color.RED);
        }
    }

    @Override
    public void onCollision() {
    }

    @Override
    public GRectangle getHitbox() {
        return getBounds();
    }

    public Set<GObject> getElements() {
        return elements;
    }
}