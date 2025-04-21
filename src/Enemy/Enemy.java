package Enemy;

import Character.Character;
import Screen.GardenScreen;
import Screen.ProgramWindow;
import Utils.GameTick;
import Utils.TickListener;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GPoint;
import acm.graphics.GRectangle;

public class Enemy extends GCompound implements TickListener {
    public static final int SIZE = 20;
    public static final double MOVE_RATE = .1;

    private static EnemyPath enemyPath;

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
        this.targetPoint = enemyPath.getPoint(1);
        gImage = new GImage(Utils.getImage(type.toPath()));
        gImage.setSize(SIZE, SIZE);
        gImage.setLocation(Utils.getCenter(gImage.getBounds()));
        bounds = this.getBounds();
        bounds.setLocation(Utils.getCenter(bounds));
        add();
    }

    public static EnemyPath getPath() {
        return enemyPath;
    }

    public static void setPath(EnemyPath path) {
        enemyPath = path;
    }

    public static void removePath() {
        enemyPath = null;
    }

    public void add() {
        this.add(gImage);
        this.setLocation(enemyPath.getStart());
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

            GPoint point = this.getLocation();

            double dx = targetX - point.getX();
            double dy = targetY - point.getY();

            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < type.getSpeed() * MOVE_RATE) {
                this.setLocation(targetPoint);
                if (targetPoint.equals(enemyPath.getEnd())) {
                    pathTraversed += distance;
                    reachedEnd();
                } else {
                    targetPoint = enemyPath.getNext(targetPoint);
                }
            } else {
                pathTraversed += type.getSpeed() * MOVE_RATE;

                this.move((dx / distance) * type.getSpeed() * MOVE_RATE, (dy / distance) * type.getSpeed() * MOVE_RATE);
            }

        }
    }

    private void remove() {
        GameTick.ActionManager.addAction(1, () -> {
            removeAll();
            ProgramWindow.getInstance().remove(this);
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
    public void onTick() {
        move();
        sendToBack();
        sendForward();
        sendForward();
        if (!alive) {
            GardenScreen.getInstance().remove(this);
        }
    }
}

