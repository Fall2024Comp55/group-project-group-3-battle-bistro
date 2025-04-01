package Character;

import Food.Food;
import Food.IngredientsType;
import UI.GameScreen;
import UI.GardenUI;
import Utils.Solid;
import Utils.TickListener;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRect;
import acm.graphics.GRectangle;
import com.google.common.collect.Maps;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static Utils.Utils.lerp;

/**
 * The Character class represents a character in the game. It handles movement, health,
 * balance, and interactions with food, ingredients, and customers.
 */
public class Character extends GCompound implements Solid, KeyListener, TickListener {
    private static final int speed = 5;
    private static final Character instance;

    private GImage gImage;
    private Food holding;
    private final GRect collision;
    private boolean moving;
    private final Set<Integer> actions;
    private int health;
    private int balance;
    private final Map<IngredientsType, AtomicInteger> ingredients;

    static {
        try {
            instance = new Character();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating Character singleton instance");
        }
    }

    /**
     * Private constructor to enforce singleton pattern.
     */
    private Character() {
        URL resource = getClass().getResource("/resources/placeholder.png");
        if (resource != null) {
            gImage = new GImage(new ImageIcon(resource).getImage());
        }
        ingredients = Maps.newHashMap();
        actions = new HashSet<>();
        gImage.setSize(20, 20);
        collision = new GRect(0, 0, gImage.getWidth(), gImage.getHeight());
        add(gImage);
        add(collision);
        moving = false;
        health = 100;
        balance = 100;
    }

    /**
     * @return The singleton instance of the Character.
     */
    public static Character getInstance() {
        return instance;
    }

    /**
     * Adds an ingredient to the character's list of available ingredients.
     *
     * @param type   The type of ingredient to add.
     * @param amount The amount of the ingredient to add.
     * @throws IllegalArgumentException if the amount is negative.
     */
    public void addIngredient(IngredientsType type, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (!ingredients.containsKey(type)) {
            ingredients.put(type, new AtomicInteger(amount));
        } else {
            ingredients.get(type).addAndGet(amount);
        }
    }

    /**
     * Removes an ingredient from the character's list of available ingredients.
     *
     * @param type   The type of ingredient to remove.
     * @param amount The amount of the ingredient to remove.
     * @return True if the ingredient was successfully removed, false otherwise.
     * @throws IllegalArgumentException if the amount is negative.
     */
    public Boolean removeIngredient(String type, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (!ingredients.containsKey(type)) {
            return false;
        } else {
            ingredients.get(type).addAndGet(-amount);
        }
        return true;
    }

    /**
     * @return The character's balance.
     */
    public int getBalance() {
        return balance;
    }


    /**
     * Subtracts the specified amount from the character's balance if sufficient funds are available.
     *
     * @param amount The amount to subtract from the balance.
     * @return True if the balance was successfully subtracted, false otherwise.
     * @throws IllegalArgumentException if the amount is negative.
     */
    public boolean subtractBalance(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (balance - amount < 0) {
            return false;
        }
        balance -= amount;
        updateUIs();
        return true;
    }

    /**
     * Sets the character's balance.
     *
     * @param balance The new balance to set.
     */
    public void setBalance(int balance) {
        this.balance = balance;
        updateUIs();
    }

    /**
     * Adds money to the character's balance. The amount can be positive or negative.
     *
     * @param balance The amount (+/-) of money to add.
     */
    public void addMoney(int balance) {
        this.balance += balance;
        updateUIs();
    }

    /**
     * Reduces the character's health by the specified damage amount.
     *
     * @param damage The amount of damage to take.
     * @throws IllegalArgumentException if the damage is negative.
     */
    public void takeDamage(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }
        health -= damage;
        updateUIs();
        if (health <= 0) {
            // TODO handle game over
//            GameScreen.getInstance().removeAll();
//            GameScreen.getInstance().remove(this);
//            System.out.println("Game Over");
        }
    }

    /**
     * Adds health to the character.
     *
     * @param health The amount of health to add.
     * @throws IllegalArgumentException if the health amount is negative.
     */
    public void addHealth(int health) {
        if (health < 0) {
            throw new IllegalArgumentException("Health cannot be negative");
        }
        this.health += health;
        updateUIs();
    }

    /**
     * @return The character's health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the character's health.
     *
     * @param health The new health to set.
     */
    public void setHealth(int health) {
        this.health = health;
        updateUIs();
    }

    /**
     * Updates the user interfaces.
     */
    public void updateUIs() {
        GardenUI.getInstance().update();
    }

    /**
     * Moves the character up.
     */
    public void up() {
        System.out.println("up");
        this.move(0, lerp(0, -speed, .5));
        repaint();
    }

    /**
     * Moves the character down.
     */
    public void down() {
        System.out.println("down");
        this.move(0, lerp(0, speed, .5));
        repaint();
    }

    /**
     * Moves the character left.
     */
    public void left() {
        System.out.println("left");
        this.move(lerp(0, -speed, .5), 0);
        repaint();
    }

    /**
     * Moves the character right.
     */
    public void right() {
        System.out.println("right");
        this.move(lerp(0, speed, 0.5), 0);
        repaint();
    }

    /**
     * Moves the character based on the current actions.
     */
    public void move() {
        if (actions.contains(KeyEvent.VK_W)) {
            up();
        }
        if (actions.contains(KeyEvent.VK_S)) {
            down();
        }
        if (actions.contains(KeyEvent.VK_A)) {
            left();
        }
        if (actions.contains(KeyEvent.VK_D)) {
            right();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
//        GameScreen.getInstance().setAutoRepaintFlag(true);
        actions.add(e.getKeyCode());
        moving = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("key released");
        GameScreen.getInstance().setAutoRepaintFlag(false);
        actions.remove(e.getKeyCode());
        moving = false;
    }

    @Override
    public void onTick() {
        if (!actions.isEmpty()) {
            move();
        }
    }

    @Override
    public void onCollision() {
    }

    @Override
    public GRectangle getHitbox() {
        return null;
    }
}