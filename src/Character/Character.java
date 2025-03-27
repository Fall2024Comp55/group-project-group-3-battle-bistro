package Character;

import Food.Food;
import UI.GameScreen;
import Utils.GameTick;
import Utils.TickListener;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRect;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

public class Character extends GCompound implements KeyListener, TickListener {
    private static Character instance;

    GImage gImage;
    Food holding;
    GRect collision;
    boolean moving;
    KeyEvent action;
    int health;
    int balance;
    // health here or in other class?


    private Character() {
        URL resource = getClass().getResource("/resources/placeholder.png");
        if (resource != null) {
            gImage = new GImage(new ImageIcon(resource).getImage());
        }
        gImage.setSize(20, 20);
        collision = new GRect(0, 0, gImage.getWidth(), gImage.getHeight());
        add(gImage);
        add(collision);
        moving = false;
        health = 100;
        balance = 100;
    }

    public static Character getInstance() {
        if (instance == null) {
            instance = new Character();
        }
        return instance;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void addBalance(int balance) {
        this.balance += balance;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            // Game Over screen
            GameScreen.getInstance().removeAll();
            GameScreen.getInstance().remove(this);
        }
    }

    public void addHealth(int health) {
        this.health += health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void up() {
        System.out.println("up");
        this.move(0, -1);
        repaint();

    }

    public void down() {
        System.out.println("down");
        this.move(0, 1);

        repaint();

    }

    public void left() {
        System.out.println("left");
        this.move(-1, 0);
        repaint();

    }

    public void right() {
        System.out.println("right");
        this.move(1, 0);
        repaint();

    }

    public void move() {
        switch (action.getKeyChar()) {
            case 'w':
                System.out.println("w");
                up();
                break;
            case 's':
                System.out.println("s");
                down();
                break;
            case 'a':
                left();
                break;
            case 'd':
                right();
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
//        GameScreen.getInstance().setAutoRepaintFlag(true);
        action = e;
        moving = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        GameScreen.getInstance().setAutoRepaintFlag(false);
        action = null;
        moving = false;
    }

    @Override
    public void onTick(GameTick tick) {
        if (moving) {
            move();
        }
    }
}
