package Enemy;

import Character.Character;
import UI.GameScreen;
import Utils.GameTick;
import Utils.TickListener;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GPoint;
import acm.graphics.GRectangle;

public class Enemy extends GCompound implements TickListener {
    public static final int size = 20;
    public static final double moveRate = .1;
    private static Path path;
    private final GImage gImage;
    private final EnemyType type;
    private int health;
    private GPoint targetPoint;
    private boolean alive;
    private GRectangle bounds;
    private double pathTraversed;

    public Enemy(EnemyType type) {
        this.type = type;
        this.health = type.getHealth();
        this.alive = true;
        this.pathTraversed = 0;
        if (path == null) {
            path = GameScreen.getPath();
        }
        this.targetPoint = path.getPoint(1);
        gImage = new GImage(type.getImage());
        gImage.setSize(size, size);
        bounds = this.getBounds();
        add();
    }

    public static void removePath() {
        path = null;
    }

    public void add() {
        this.add(gImage);
        this.setLocation(Utils.getCenterOffset(path.getStart(), this.getBounds()));
        bounds = this.getBounds();
    }

    public void reachedEnd() {
        // TODO Deal damage to player
        Character.getInstance().takeDamage(type.getDamage());
        remove();
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
                    pathTraversed += distance;
                    reachedEnd();
                } else {
                    targetPoint = path.getNext(targetPoint);
                }
            } else {
                pathTraversed += type.getSpeed() * moveRate;

                this.move((dx / distance) * type.getSpeed() * moveRate, (dy / distance) * type.getSpeed() * moveRate);
            }

        }
    }

    private void remove() {
        GameTick.ActionManager.addAction(1, () -> {
            removeAll();
            GameScreen.getInstance().remove(this);
        });
    }

    public void takeDamage(int damage) {
        health -= damage;
        scale(1.05);
        if (health <= 0) {
            remove();
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

    public double getPathTraversed() {
        return pathTraversed;
    }

    @Override
    public void onTick(GameTick tick) {
        move();
        if (!alive) {
            GameTick.ActionManager.addAction(1, () -> {
                GameTick.TickManager.unregisterTickListener(this);
            });
        }
    }
}

