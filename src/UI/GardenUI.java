package UI;

import Character.Character;
import Utils.Solid;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.graphics.GRectangle;

import java.awt.*;

import static Screen.ProgramWindow.*;

public class GardenUI extends UI implements Solid {
    private static final GardenUI GARDEN_UI;

    private final GLabel moneyLabel;
    private final GLabel healthLabel;

    static {
        try {
            GARDEN_UI = new GardenUI();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating GardenUI singleton instance");
        }
    }

    private GardenUI() {
        moneyLabel = new GLabel("Money: " + Character.getInstance().getBalance());
        healthLabel = new GLabel("Health: " + Character.getInstance().getHealth());
        initializeComponents();
    }

    @Override
    public void initializeComponents() {
        GRect background = new GRect(BASE_WIDTH, 50);
        background.setFilled(true);
        background.setFillColor(Color.LIGHT_GRAY);
        add(background);

        moneyLabel.setFont(GLOBAL_FONT);
        moneyLabel.setColor(GLOBAL_COLOR);
        moneyLabel.setLocation(20, 35);
        add(moneyLabel);

        healthLabel.setFont(GLOBAL_FONT);
        healthLabel.setColor(GLOBAL_COLOR);
        healthLabel.setLocation(20, 15);
        add(healthLabel);
    }

    public static GardenUI getInstance() {
        return GARDEN_UI;
    }

    @Override
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
    }

    @Override
    public void onCollision() {
    }

    @Override
    public GRectangle getHitbox() {
        return getBounds();
    }
}