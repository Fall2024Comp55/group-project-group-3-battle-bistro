package Tower;

import Utils.GameTick;
import acm.graphics.GObject;

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

    @Override
    public void onCollision() {

    }


}

