package Tower;

import Enemy.Enemy;
import Screen.GardenScreen;
import Utils.TickListener;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GPoint;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Projectile extends GCompound implements TickListener {
    public static final String BASE_PATH = "/resources/projectile/";
    public static final String EXTENSION = ".png";

    private final String name;

    protected GImage gImage;
    protected GPoint targetPoint; // The target position
    protected Enemy targetEnemy;  // The target enemy
    protected double speed;       // Speed of the projectile
    protected double moveRate;    // Movement rate
    protected int damage;         // Damage dealt to the enemy on hit
    protected boolean active;     // Whether the projectile is still active

    public Projectile(String name, GPoint targetPoint, Enemy targetEnemy, double speed, double moveRate, int damage) {
        this.name = name;
        this.targetPoint = targetPoint;
        this.targetEnemy = targetEnemy;
        this.speed = speed;
        this.moveRate = moveRate;
        this.damage = damage;
        this.active = true;
        this.gImage = new GImage(Utils.getImage(toPath()));
        gImage.setLocation(Utils.getCenter(this.getBounds()));
        add(gImage);
    }

    public void move() {
        if (!active) return;

        double targetX = targetPoint.getX();
        double targetY = targetPoint.getY();

        GPoint point = this.getLocation();

        double dx = targetX - point.getX();
        double dy = targetY - point.getY();

        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < speed * moveRate) {
            this.setLocation(Utils.getCenterOffset(targetPoint, this.getBounds()));
            if (checkHit()) {
                hitTarget();
            } else {
                active = false;
            }
        } else {
            this.move((dx / distance) * speed * moveRate, (dy / distance) * speed * moveRate);

            if (checkHit()) {
                hitTarget();
            }
        }
    }

    public Boolean checkHit() {
        AtomicBoolean hit = new AtomicBoolean(false);

        GardenScreen.getInstance().getEnemyTickListeners().spliterator().forEachRemaining(enemy -> {
            if (enemy instanceof Enemy e) {
                if (e.getBounds() != null && this.getBounds().intersects(e.getBounds())) {
                    hit.set(true);
                }
            }
        });


        return hit.get();
    }

    protected void hitTarget() {
        if (targetEnemy != null && targetEnemy.isAlive()) {
            targetEnemy.takeDamage(damage);
        }
        active = false;
    }

    public String toString() {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public String toPath() {
        return BASE_PATH + name.toLowerCase() + EXTENSION;
    }

    @Override
    public void onTick() {
        if (active) {
            targetPoint = targetEnemy.getLocation();
            move();
        } else {
            GardenScreen.getEnemyLayer().remove(this);
        }
    }
}