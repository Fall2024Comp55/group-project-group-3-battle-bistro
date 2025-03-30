package Character;

import UI.GameScreen;
import acm.graphics.GLabel;
import acm.graphics.GRect;

import java.awt.*;

public class Player {
    private static Player instance;
    private int money;
    private int health;
    private GLabel moneyLabel;
    private GRect healthBar;
    private GRect healthBarBackground;

    private Player() {
        this.money = 50; 
        this.health = 100; 
        setupUI();
    }

    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    private void setupUI() {
        
        moneyLabel = new GLabel("Money: " + money);
        moneyLabel.setFont("Arial-16");
        moneyLabel.setColor(Color.BLACK);
        moneyLabel.setLocation(20, 35); 
        GameScreen.getInstance().add(moneyLabel);


        healthBarBackground = new GRect(200, 20); 
        healthBarBackground.setFilled(true);
        healthBarBackground.setFillColor(Color.GRAY);
        healthBarBackground.setLocation(300, 15); 
        GameScreen.getInstance().add(healthBarBackground);


        healthBar = new GRect(200, 20);
        healthBar.setFilled(true);
        healthBar.setFillColor(Color.GREEN);
        healthBar.setLocation(300, 15); 
        GameScreen.getInstance().add(healthBar);
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int amount) {
        money += amount;
        updateMoneyLabel();
    }

    public void subtractMoney(int amount) {
        money -= amount;
        updateMoneyLabel();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(100, health)); 
        updateHealthBar();
    }

    private void updateMoneyLabel() {
        moneyLabel.setLabel("Money: " + money);
    }

    private void updateHealthBar() {
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