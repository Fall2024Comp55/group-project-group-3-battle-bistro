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

    public static Character getInstance() {
        return instance;
    }

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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
        updateUIs();
    }

    public void addMoney(int balance) {
        this.balance += balance;
        updateUIs();
    }

    public void takeDamage(int damage) {
        health -= damage;
        updateUIs();
        if (health <= 0) {
            // Game Over screen
//            GameScreen.getInstance().removeAll();
//            GameScreen.getInstance().remove(this);
//            System.out.println("Game Over");
        }
    }

    public void addHealth(int health) {
        if (health < 0) {
            throw new IllegalArgumentException("Health cannot be negative");
        }
        this.health += health;
        updateUIs();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        updateUIs();
    }

    public void updateUIs() {
        GardenUI.getInstance().update();
    }

    public void up() {
        System.out.println("up");
        this.move(0, lerp(0, -speed, .5));
        repaint();

    }

    public void down() {
        System.out.println("down");
        this.move(0, lerp(0, speed, .5));

        repaint();

    }

    public void left() {
        System.out.println("left");
        this.move(lerp(0, -speed, .5), 0);
        repaint();

    }

    public void right() {
        System.out.println("right");
        this.move(lerp(0, speed, 0.5), 0);
        repaint();

    }

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
