package UI;

import Character.Character;
import Utils.Solid;
import acm.graphics.*;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static Screen.ProgramWindow.*;

public class GardenUI extends UI implements Solid {
    private static final GardenUI GARDEN_UI;

    private final Set<GObject> elements;
    private final GLabel moneyLabel;
    private final GLabel healthLabel;  
    private int starRating;
    private GCompound starDisplay;
    private GCompound notificationArea;
    private GLabel notificationLabel;
    private ScheduledExecutorService notificationExecutor;

    static {
        try {
            GARDEN_UI = new GardenUI();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating GardenUI singleton instance");
        }
    }

    private GardenUI() {
        elements = new HashSet<>();
        Character c = Character.getInstance();
        moneyLabel = new GLabel("Money: " + c.getBalance());
        healthLabel = new GLabel("Health: " + c.getHealth());

        GRect menuBar = new GRect(BASE_WIDTH, 50);
        menuBar.setFilled(true);
        menuBar.setFillColor(Color.LIGHT_GRAY);
        add(menuBar, 0, 0);
        elements.add(menuBar);

        moneyLabel.setFont(GLOBAL_FONT);
        moneyLabel.setColor(GLOBAL_COLOR);
        moneyLabel.setLocation(20, 35);
        add(moneyLabel);
        elements.add(moneyLabel);

        healthLabel.setFont(GLOBAL_FONT);  
        healthLabel.setColor(GLOBAL_COLOR);
        healthLabel.setLocation(20, 15);
        add(healthLabel);
        elements.add(healthLabel);

        NewTowerButton tower1 = new NewTowerButton("New Tower");
        add(tower1, 500, 25);
        elements.add(tower1);

        initStarRating();
        initNotificationArea();
    }

    public static GardenUI getInstance() {
        return GARDEN_UI;
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

        starDisplay.setLocation(BASE_WIDTH - starDisplay.getWidth() - 10, 15);
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
                (BASE_WIDTH - notificationLabel.getWidth()) / 2,
                BASE_HEIGHT / 2
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
        updateHealthLabel(); 
    }

    private void updateMoneyLabel() {
        moneyLabel.setLabel("Money: " + Character.getInstance().getBalance());
    }

    private void updateHealthLabel() {  
        double health = Character.getInstance().getHealth();
        healthLabel.setLabel("Health: " + (int)health);
        
        if (health > 50) {
            healthLabel.setColor(Color.GREEN);
        } else if (health > 25) {
            healthLabel.setColor(Color.YELLOW);
        } else {
            healthLabel.setColor(Color.RED);
        }
    }

    @Override
    public void onCollision() {
    }

    @Override
    public GRectangle getHitbox() {
        return getBounds();
    }
}