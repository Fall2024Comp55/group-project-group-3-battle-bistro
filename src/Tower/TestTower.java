package Tower;

import Utils.GameTick;
import Utils.Utils;
import Utils.MouseManager;
import acm.graphics.GObject;
import acm.graphics.GPoint;

import java.awt.*;
import java.awt.event.MouseEvent;

public class TestTower extends Tower {

    public TestTower() {
        super("Test", 1, 1, 1);
    }

    @Override
    public void attack() {
        System.out.println("Attacking");
    }

    @Override
    public void upgrade() {
        System.out.println("Upgrading");
    }

    @Override
    public void sell() {
        System.out.println("Selling");
    }

    @Override
    public void move() {

    }

    @Override
    public void setTarget(GObject target) {

    }

    @Override
    public void onTick(GameTick tick) {

    }

    // TODO code does not work
    private Tower selectedTower;

    @Override
    public void onPress(MouseEvent e) {
        this.sendToFront();
    }

    @Override
    public void onDrag(MouseEvent e) {
        MouseManager.getSelectedObject().setLocation(e.getX() - this.getWidth() / 2, e.getY() - this.getHeight() / 2);
        repaint();
        if (hitbox.checkCollision()) {
            System.out.println("collison");
        }
    }

    @Override
    public void onRelease(MouseEvent e) {

    }
}

