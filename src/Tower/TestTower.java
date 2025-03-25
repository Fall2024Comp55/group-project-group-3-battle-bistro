package Tower;

import UI.GameScreen;
import Utils.GameTick;
import Utils.Solid;
import Utils.MouseManager;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.graphics.GRectangle;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicBoolean;

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
    public void onCollision() {

    }


}

