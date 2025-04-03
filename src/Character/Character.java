package Character;

import Food.Food;
import Food.IngredientsType;
import UI.GardenUI;
import Utils.Directions;
import Utils.Interact;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import static Utils.Utils.getCenter;

/**
 * The Character class represents a character in the game. It handles movement, health,
 * balance, and interactions with food, ingredients, and customers.
 */
public class Character extends GCompound implements Solid, Interact, KeyListener, TickListener {
    private static final int speed = 5;
    private static final Character instance;

    private final Set<Integer> actions;
    private final Map<IngredientsType, AtomicInteger> ingredients;
    private final GRect collision;
    private GImage gImage;
    private Food holding;
    private boolean moving;
    private int health;
    private int balance;
    private boolean interactHeld;
    private Directions facing;
    private int currentTheta;
    private ScheduledExecutorService movementExecutor;

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
        this.setLocation(200, 200);
        System.out.println(this.getBounds() + " " + gImage.getBounds() + getCenter(gImage.getBounds()) + " " + getCenter(this.getBounds()));
        add(gImage);
        gImage.setLocation(getCenter(gImage.getBounds()));
        System.out.println(this.getBounds() + " " + gImage.getBounds() + getCenter(gImage.getBounds()) + " " + getCenter(this.getBounds()));
        moving = false;
        health = 100;
        balance = 100;
        currentTheta = 0;
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
    public Boolean removeIngredient(IngredientsType type, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (!ingredients.containsKey(type)) {
            return false;
        } else if (ingredients.get(type).get() < amount) {
            return false;
        } else {
            ingredients.get(type).addAndGet(-amount);
            return true;
        }
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

    public Food getHolding() {
        return holding;
    }

    public void setHolding(Food holding) {
        this.holding = holding;
    }

    public Directions getFacing() {
        return facing;
    }

    public void setFacing(Directions facing) {
        this.facing = facing;
    }

    /**
     * Updates the user interfaces.
     */
    public void updateUIs() {
        GardenUI.getInstance().update();
    }

    /**
     * Moves the character based on the current actions.
     */
    public void move() {
        if (actions.size() == 1 || (actions.size() == 2 && actions.contains(KeyEvent.VK_E))) {
            if (actions.contains(KeyEvent.VK_W)) {
                facing = Directions.UP;
            } else if (actions.contains(KeyEvent.VK_S)) {
                facing = Directions.DOWN;
            } else if (actions.contains(KeyEvent.VK_A)) {
                facing = Directions.LEFT;
            } else if (actions.contains(KeyEvent.VK_D)) {
                facing = Directions.RIGHT;
            }
        } else if (actions.size() == 2 || (actions.size() == 3 && actions.contains(KeyEvent.VK_E))) {
            if (actions.contains(KeyEvent.VK_W) && actions.contains(KeyEvent.VK_A)) {
                facing = Directions.UP_LEFT;
            } else if (actions.contains(KeyEvent.VK_W) && actions.contains(KeyEvent.VK_D)) {
                facing = Directions.UP_RIGHT;
            } else if (actions.contains(KeyEvent.VK_S) && actions.contains(KeyEvent.VK_A)) {
                facing = Directions.DOWN_LEFT;
            } else if (actions.contains(KeyEvent.VK_S) && actions.contains(KeyEvent.VK_D)) {
                facing = Directions.DOWN_RIGHT;
            }
        }
        // set desired direction
        double dx = facing.getX();
        double dy = facing.getY();
        // get length of the vector
        double length = Math.sqrt(dx * dx + dy * dy);
        if (length != 0) {
            // normalize the vector
            dx = (dx / length) * speed;
            dy = (dy / length) * speed;
        }
        // move the character by a total distance of the speed in the direction of the vector
        move(dx, dy);

        int rotateSpeed = 12;
        int rotateDistance = facing.getTheta() - currentTheta;
        if (rotateDistance > 180) {
            rotateDistance -= 360;
        } else if (rotateDistance < -180) {
            rotateDistance += 360;
        }
        if (rotateSpeed < Math.abs(rotateDistance)) {
            this.rotate(rotateSpeed * Math.signum(rotateDistance));
            currentTheta += rotateSpeed * (int) Math.signum(rotateDistance);
        } else {
            this.rotate(rotateDistance);
            currentTheta += rotateDistance;
        }

        if (currentTheta > 360) {
            currentTheta -= 360;
        } else if (currentTheta < 0) {
            currentTheta += 360;
        }

        System.out.println("current theta: " + currentTheta);

    }


    public void interact() {
        if (actions.contains(KeyEvent.VK_E)) {
            interactHeld = true;
            Interact interactable = checkForInteractable();
            if (interactable != null) {
                interactable.interact();
            }
        } else {
            interactHeld = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
//        GameScreen.getInstance().setAutoRepaintFlag(true);
        actions.add(e.getKeyCode());
        if (!moving && !(actions.size() == 1 && actions.contains(KeyEvent.VK_E))) {
            moving = true;
            movementExecutor = Executors.newSingleThreadScheduledExecutor();
            movementExecutor.scheduleAtFixedRate(this::move, 0, 16, java.util.concurrent.TimeUnit.MILLISECONDS);
        }
        interact();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("key released");
        actions.remove(e.getKeyCode());
        if (moving && ((actions.size() == 1 && actions.contains(KeyEvent.VK_E)) || actions.isEmpty())) {
            moving = false;
            movementExecutor.shutdown();
        }
        interact();
    }

    @Override
    public void onTick() {
    }

    @Override
    public void onCollision() {
    }

    @Override
    public GRectangle getHitbox() {
        return this.getBounds();
    }

    @Override
    public GRectangle getInteractHitbox() {


        return null;
    }

}