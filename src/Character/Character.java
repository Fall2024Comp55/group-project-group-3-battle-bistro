package Character;

import Food.Food;
import Food.IngredientsType;
import Screen.RestaurantScreen;
import UI.GardenUI;
import UI.RestaurantUI;
import Utils.*;
import acm.graphics.*;
import com.google.common.collect.Maps;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    public static final int ROTATE_SPEED = 12;
    private static final int SPEED = 5;
    private static final String PATH = "/resources/character/character.png";

    private static final Character CHARACTER;

    private final Set<Integer> keysHeld;
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
    private double currentTheta;
    private ScheduledExecutorService movementExecutor;
    private GImage food_image;

    static {
        try {
            CHARACTER = new Character();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating Character singleton instance");
        }
    }

    /**
     * Private constructor to enforce singleton pattern.
     */
    private Character() {
        gImage = new GImage(Utils.getImage(PATH));
        ingredients = Maps.newHashMap();
        keysHeld = new HashSet<>();
        actions = new HashSet<>();
        food_image = new GImage(Utils.getImage("/resources/enemy/pizza (2).png"));
        food_image.setLocation(-20, -60);
        food_image.setSize(40, 40);
        gImage.setSize(50, 50);
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
        return CHARACTER;
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

    /**
     * @return The food item the character is currently holding.
     */
    public Food getHolding() {
        return holding;
    }

    /**
     * Sets the food item the character is holding.
     *
     * @param holding The food item to set.
     */
    public void setHolding(Food holding) {
        this.holding = holding;
        if (this.holding != null) {
            add(food_image);
        } else {
            remove(food_image);
        }
    }

    /**
     * @return The direction the character is currently facing.
     */
    public Directions getFacing() {
        return facing;
    }

    /**
     * Sets the direction the character is facing.
     *
     * @param facing The direction to set.
     */
    public void setFacing(Directions facing) {
        this.facing = facing;
    }

    /**
     * Updates the user interfaces.
     */
    public void updateUIs() {
        GardenUI.getInstance().update();
        RestaurantUI.getInstance().update();
    }

    /**
     * Moves the character based on the current actions.
     * Updates the character's facing direction and position based on the pressed keys.
     */
    public void move() {
        for (Integer i : keysHeld) {
            if (i == Directions.UP.getKey()) {
                actions.add(Directions.UP.getKey());
            } else if (i == Directions.DOWN.getKey()) {
                actions.add(Directions.DOWN.getKey());
            } else if (i == Directions.LEFT.getKey()) {
                actions.add(Directions.LEFT.getKey());
            } else if (i == Directions.RIGHT.getKey()) {
                actions.add(Directions.RIGHT.getKey());
            }

        }
        if (checkCollision()) {
            onCollision();
        }

        if (actions.size() == 1 || (actions.size() == 2 && actions.contains(KeyEvent.VK_E))) { // if only one movement key is pressed
            if (actions.contains(Directions.UP.getKey())) {
                setFacing(Directions.UP);
            } else if (actions.contains(Directions.DOWN.getKey())) {
                setFacing(Directions.DOWN);
            } else if (actions.contains(Directions.LEFT.getKey())) {
                setFacing(Directions.LEFT);
            } else if (actions.contains(Directions.RIGHT.getKey())) {
                setFacing(Directions.RIGHT);
            }
        } else if (actions.size() == 2 || (actions.size() == 3 && actions.contains(KeyEvent.VK_E))) { // if two movement keys are pressed
            if (actions.contains(Directions.UP.getKey()) && actions.contains(Directions.LEFT.getKey())) {
                setFacing(Directions.UP_LEFT);
            } else if (actions.contains(Directions.UP.getKey()) && actions.contains(Directions.RIGHT.getKey())) {
                setFacing(Directions.UP_RIGHT);
            } else if (actions.contains(Directions.DOWN.getKey()) && actions.contains(Directions.LEFT.getKey())) {
                setFacing(Directions.DOWN_LEFT);
            } else if (actions.contains(Directions.DOWN.getKey()) && actions.contains(Directions.RIGHT.getKey())) {
                setFacing(Directions.DOWN_RIGHT);
            }
        } else {
            return;
        }

        // get desired direction
        double dx = facing.getX();
        double dy = facing.getY();

        double length = Math.sqrt(dx * dx + dy * dy); // get length of the vector

        if (length != 0) {
            // normalize the vector so the character moves at a constant speed in any direction
            dx = (dx / length) * SPEED;
            dy = (dy / length) * SPEED;
        }

        move(dx, dy); // move the character by the normalized vector

        double rotateDistance = facing.getTheta() - currentTheta; // calculate the distance to rotate

        // if the distance is greater than 180 or -180 degrees, rotate in the opposite direction
        if (rotateDistance > 180) {
            rotateDistance -= 360;
        } else if (rotateDistance < -180) {
            rotateDistance += 360;
        }

        if (ROTATE_SPEED < Math.abs(rotateDistance)) { // if the distance is greater than the speed, rotate by the speed
            this.rotate(ROTATE_SPEED * Math.signum(rotateDistance)); // rotate by the speed times the sign of the distance
            currentTheta += ROTATE_SPEED * (int) Math.signum(rotateDistance); // track the current angle
        } else { // if the distance is less than the speed, rotate by the distance
            this.rotate(rotateDistance);
            currentTheta += rotateDistance; // track the current angle
        }

        // keep the current angle between 0 and 360 degrees
        if (currentTheta > 360) {
            currentTheta -= 360;
        } else if (currentTheta < 0) {
            currentTheta += 360;
        }
    }


    public void interact() {
        if (actions.contains(KeyEvent.VK_E)) {
            interactHeld = true;
            Interact interactable = checkForInteractable();
            if (interactable != null) {
                System.out.println("interact");
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
        keysHeld.add(e.getKeyCode());
        actions.add(e.getKeyCode());
        if (!moving && !(keysHeld.size() == 1 && keysHeld.contains(KeyEvent.VK_E))) {
            moving = true;
            movementExecutor = Executors.newSingleThreadScheduledExecutor();
            movementExecutor.scheduleAtFixedRate(this::move, 0, 16, java.util.concurrent.TimeUnit.MILLISECONDS);
        }
        interact();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysHeld.remove(e.getKeyCode());
        actions.remove(e.getKeyCode());
        if (moving && ((keysHeld.size() == 1 && keysHeld.contains(KeyEvent.VK_E)) || keysHeld.isEmpty())) {
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
    public Boolean checkCollision() {
        if (actions.size() == 1 || (actions.size() == 2 && actions.contains(KeyEvent.VK_E))) { // if only one movement key is pressed
            if (actions.contains(Directions.UP.getKey())) {
                setFacing(Directions.UP);
            } else if (actions.contains(Directions.DOWN.getKey())) {
                setFacing(Directions.DOWN);
            } else if (actions.contains(Directions.LEFT.getKey())) {
                setFacing(Directions.LEFT);
            } else if (actions.contains(Directions.RIGHT.getKey())) {
                setFacing(Directions.RIGHT);
            }
        } else if (actions.size() == 2 || (actions.size() == 3 && actions.contains(KeyEvent.VK_E))) { // if two movement keys are pressed
            if (actions.contains(Directions.UP.getKey()) && actions.contains(Directions.LEFT.getKey())) {
                setFacing(Directions.UP_LEFT);
            } else if (actions.contains(Directions.UP.getKey()) && actions.contains(Directions.RIGHT.getKey())) {
                setFacing(Directions.UP_RIGHT);
            } else if (actions.contains(Directions.DOWN.getKey()) && actions.contains(Directions.LEFT.getKey())) {
                setFacing(Directions.DOWN_LEFT);
            } else if (actions.contains(Directions.DOWN.getKey()) && actions.contains(Directions.RIGHT.getKey())) {
                setFacing(Directions.DOWN_RIGHT);
            }
        }

        // get desired direction

        double rotateDistance = facing.getTheta() - currentTheta; // calculate the distance to rotate

        // if the distance is greater than 180 or -180 degrees, rotate in the opposite direction
        if (rotateDistance > 180) {
            rotateDistance -= 360;
        } else if (rotateDistance < -180) {
            rotateDistance += 360;
        }

        if (ROTATE_SPEED < Math.abs(rotateDistance)) { // if the distance is greater than the speed, rotate by the speed
            this.rotate(ROTATE_SPEED * Math.signum(rotateDistance)); // rotate by the speed times the sign of the distance
            currentTheta += ROTATE_SPEED * (int) Math.signum(rotateDistance); // track the current angle
        } else { // if the distance is less than the speed, rotate by the distance
            this.rotate(rotateDistance);
            currentTheta += rotateDistance; // track the current angle
        }

        // keep the current angle between 0 and 360 degrees
        if (currentTheta > 360) {
            currentTheta -= 360;
        } else if (currentTheta < 0) {
            currentTheta += 360;
        }
        boolean hit = false;
        if (keysHeld.contains(Directions.UP.getKey())) {
            GPoint topPoint = new GPoint(this.getBounds().getX() + this.getBounds().getWidth() / 2, this.getBounds().getY());

            GObject hitObject = RestaurantScreen.getInstance().getElementAt(topPoint.getX(), topPoint.getY());

            if (hitObject != this && hitObject instanceof Solid) {
                actions.remove(Directions.UP.getKey());
                hit = true;
                System.out.println(hitObject);
            }
        }
        if (keysHeld.contains(Directions.DOWN.getKey())) {
            GPoint bottomPoint = new GPoint(this.getBounds().getX() + this.getBounds().getWidth() / 2, this.getBounds().getY() + this.getBounds().getHeight());

            GObject hitObject = RestaurantScreen.getInstance().getElementAt(bottomPoint.getX(), bottomPoint.getY());

            if (hitObject != this && hitObject instanceof Solid) {
                actions.remove(Directions.DOWN.getKey());
                hit = true;
                System.out.println(hitObject);
            }
        }
        if (keysHeld.contains(Directions.LEFT.getKey())) {
            GPoint leftPoint = new GPoint(this.getBounds().getX(), this.getBounds().getY() + this.getBounds().getHeight() / 2);

            GObject hitObject = RestaurantScreen.getInstance().getElementAt(leftPoint.getX(), leftPoint.getY());

            if (hitObject != this && hitObject instanceof Solid) {
                actions.remove(Directions.LEFT.getKey());
                hit = true;
                System.out.println(hitObject);
            }
        }
        if (keysHeld.contains(Directions.RIGHT.getKey())) {
            GPoint rightPoint = new GPoint(this.getBounds().getX() + this.getBounds().getWidth(), this.getBounds().getY() + this.getBounds().getHeight() / 2);

            GObject hitObject = RestaurantScreen.getInstance().getElementAt(rightPoint.getX(), rightPoint.getY());

            if (hitObject != this && hitObject instanceof Solid) {
                actions.remove(Directions.RIGHT.getKey());
                hit = true;
                System.out.println(hitObject);
            }
        }
        return hit;
    }

    @Override
    public GRectangle getHitbox() {
        return null;
    }

    public GLine linetrace(double length) {
        // Get the character's current position
        GPoint p = Utils.getPointOffset(getLocation(), RestaurantScreen.getInstance().getBounds());
        double startX = p.getX();
        double startY = p.getY();

        // Calculate the end position based on the direction the character is facing
        double endX = startX - length * Math.sin(Math.toRadians(currentTheta));
        double endY = startY - length * Math.cos(Math.toRadians(currentTheta));

        // TODO figure out issues when moving out side of screens
//        ProgramWindow.getInstance().add(new GLine(startX, startY, endX, endY));

        // Add the line to the ProgramWindow
        return new GLine(startX, startY, endX, endY);
    }

    @Override
    public GRectangle getInteractHitbox() {
        GPoint p = linetrace(50).getEndPoint();
        GOval interactHitbox = new GOval(p.getX(), p.getY(), 20, 20);

        return interactHitbox.getBounds();
    }

}