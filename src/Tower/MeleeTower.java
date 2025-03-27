package Tower;

import Utils.GameTick;
import Utils.TickListener;
import acm.graphics.GObject;

public class MeleeTower extends Tower implements TickListener {

    public MeleeTower() {
        super("chefkirby", 1, 1, 1, 50);
    }

    @Override
    public void attack() {
        //No
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
        inRange();
    }

    @Override
    public void onCollision() {

    }


}

