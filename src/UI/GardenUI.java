package UI;

import Character.Character;
import Utils.Solid;
import acm.graphics.*;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static UI.GameScreen.*;

public class GardenUI extends GCompound implements Solid {
    private Set<GObject> elements;
    private GLabel moneyLabel;
    private GRect healthBarBackground;
    private GRect healthBar;

    private static GardenUI instance;

    static {
        try {
            instance = new GardenUI();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating GardenUI singleton instance");
        }
    }

    private GardenUI() {
        elements = new HashSet<>();
        Character c = Character.getInstance();
        moneyLabel = new GLabel("Money: " + c.getBalance());
        healthBarBackground = new GRect(200, 20);
        healthBar = new GRect(200, 20);

        GRect menuBar = new GRect(WIDTH, 50);
        menuBar.setFilled(true);
        menuBar.setFillColor(Color.LIGHT_GRAY);
        add(menuBar, 0, 0);


        moneyLabel.setFont(globalFont);
        moneyLabel.setColor(globalColor);
        moneyLabel.setLocation(20, 35);
        add(moneyLabel);
        elements.add(moneyLabel);

        healthBarBackground.setFilled(true);
        healthBarBackground.setFillColor(Color.GRAY);
        healthBarBackground.setLocation(300, 15);
        add(healthBarBackground);
        elements.add(healthBarBackground);


        healthBar.setFilled(true);
        healthBar.setFillColor(Color.GREEN);
        healthBar.setLocation(300, 15);
        add(healthBar);
        elements.add(healthBar);
    }

    private void updateMoneyLabel() {
        moneyLabel.setLabel("Money: " + Character.getInstance().getBalance());
    }

    private void updateHealthBar() {
        double health = Character.getInstance().getHealth();
        double healthPercentage = health / 100.0;
        healthBar.setSize(200 * healthPercentage, 20);
        if (health > 50) {
            healthBar.setFillColor(Color.GREEN);
        } else if (health > 25) {
            healthBar.setFillColor(Color.YELLOW);
        } else {
            healthBar.setFillColor(Color.RED);
        }
    }

}
