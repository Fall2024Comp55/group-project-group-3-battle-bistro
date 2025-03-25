package Utils;

import Tower.Tower;
import UI.GameScreen;
import acm.graphics.GCompound;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRectangle;

import java.util.concurrent.atomic.AtomicBoolean;

public class Hitbox extends GCompound implements Solid {
    private final GOval hitbox;
    private final GObject parent;


    public Hitbox(double size, GObject parent) {
        this.hitbox = new GOval(size, size);
        this.parent = parent;
        hitbox.setFilled(true);
        add(hitbox);
    }

    @Override
    public void onCollision() {

    }

    // TODO Needs work might need to remove hitbox class and make tower implement solid and then when overlap happens get the do a GetHitbox() and check if it is overlapping the hutbix.

    @Override
    public Boolean checkCollision() {
        AtomicBoolean collision = new AtomicBoolean(false);
        GameScreen.getInstance().forEach(object -> {
            if (parent != object && object instanceof Tower tower) {
                System.out.println(object + " parent " + parent + " " + (parent != object));
                if (tower.getHitbox().getBounds().intersects(hitbox.getBounds())) {
                    collision.set(true);
                }
            }
        });
        return collision.get();
    }
}
