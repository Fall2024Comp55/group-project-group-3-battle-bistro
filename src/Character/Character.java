package Character;

import Food.Food;
import UI.GameScreen;
import Utils.GameTick;
import Utils.TickListener;
import acm.graphics.*;

import javax.swing.*;
import java.awt.event.*;
import java.net.URL;

public class Character extends GCompound implements KeyListener, TickListener {
    GImage gImage;
    Food holding;
    GRect collision;
    boolean moving;
    KeyEvent action;
    // health here or in other class?


    public Character() {
        URL resource = getClass().getResource("/resources/placeholder.png");
        if (resource != null) {
            gImage = new GImage(new ImageIcon(resource).getImage());
        }
        gImage.setSize(20, 20);
        collision = new GRect(0, 0, gImage.getWidth(), gImage.getHeight());
        add(gImage);
        add(collision);
        moving = false;
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
        action = e;
        moving = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
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
