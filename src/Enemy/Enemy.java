package Enemy;

import Utils.GameTick;
import Utils.TickListener;
import Utils.Utils;
import acm.graphics.*;

public class Enemy extends GCompound implements TickListener {
    public static final int size = 20;
    public static final double moveRate = .1;

    private int health;
    private final Path path;
    private GPoint targetPoint;
    private boolean alive;
    private final GImage gImage;
    private final EnemyType type;
    private GRectangle bounds;


    public Enemy(EnemyType type, Path path) {
        this.type = type;
        this.health = type.getHealth();
        this.alive = true;
        this.path = path;
        this.targetPoint = path.getPoint(1);
        gImage = new GImage(type.getImage());
        gImage.setSize(size, size);
        bounds = this.getBounds();
        add();
    }

    public void add() {
        this.add(gImage);
        this.setLocation(Utils.getCenterOffset(path.getStart(), this.getBounds()));
        bounds = this.getBounds();
    }

    public void reachedEnd() {
        // TODO Deal damage to player
        removeAll();
        // this is better than removeused references
        this.getParent().remove(this);
        alive = false;
    }

    public void move() {

        if (alive) {
            double targetX = targetPoint.getX();
            double targetY = targetPoint.getY();

            GPoint point = Utils.getCenter(this.getLocation(), bounds);

            double dx = targetX - point.getX();
            double dy = targetY - point.getY();

            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < type.getSpeed() * moveRate) {
                this.setLocation(Utils.getCenterOffset(targetPoint, bounds));
                if (targetPoint.equals(path.getEnd())) {
                    reachedEnd();
                } else {
                    targetPoint = path.getNext(targetPoint);
                }
            } else {

                this.move((dx / distance) * type.getSpeed() * moveRate, (dy / distance) * type.getSpeed() * moveRate);
            }

        }
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            alive = false;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return type.getSpeed();
    }

    public int getDamage() {
        return type.getDamage();
    }

    @Override
    public void onTick(GameTick tick) {
        move();
        if (!alive) {
            tick.addAction(1, () -> {
                tick.unregisterTickerListener(this);;
            });
        }
    }
}

