package Tower;

import Enemy.Enemy;
import UI.GameScreen;
import Utils.GameTick;
import Utils.TickListener;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GPoint;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Projectile extends GCompound implements TickListener {
    public static final String basePath = "/resources/projectile/";
    public static final String extension = ".png";
    protected GImage gImage;
    protected GPoint targetPoint; // The target position
    private String name;
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

        GameTick.TickManager.registerTickListener(this);
    }

    public void move() {
        if (!active) return;

        double targetX = targetPoint.getX();
        double targetY = targetPoint.getY();

        GPoint point = Utils.getCenter(this.getLocation(), this.getBounds());

        double dx = targetX - point.getX();
        double dy = targetY - point.getY();

        double distance = Math.sqrt(dx * dx + dy * dy);


        if (distance < speed * moveRate) {
            this.setLocation(Utils.getCenterOffset(targetPoint, this.getBounds()));
            hitTarget();
        } else {

            this.move((dx / distance) * speed * moveRate, (dy / distance) * speed * moveRate);


            if (checkHit()) {
                hitTarget();
            }
        }
    }

    public Boolean checkHit() {
        AtomicBoolean hit = new AtomicBoolean(false);

        GameScreen.getInstance().forEach(object -> {
            if (object instanceof Enemy e) {
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
            System.out.println("Projectile hit enemy, dealt " + damage + " damage!");
        }
        active = false;
    }

    protected void remove() {
        GameScreen.getInstance().remove(this);
        removeAll();
    }


    protected abstract void setupVisuals();

    public String toString() {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public String toPath() {
        return basePath + name.toLowerCase() + extension;
    }

    public Image getImage() {
        URL resource = getClass().getResource(toPath());
        if (resource != null) {
            return new ImageIcon(resource).getImage();
        }
        throw new RuntimeException("Could not find image for path " + toPath());
    }

    @Override
    public void onTick(GameTick tick) {
        //TODO rework this
        if (!active) {
            GameTick.ActionManager.addAction(1, () -> {
                GameTick.TickManager.unregisterTickListener(this);
            });
            remove();
            return;
        }

        // TODO: check if this code is needed and if so, rework it
//        if (targetEnemy == null || !targetEnemy.isAlive()) {
//            active = false;
//            return;
//        }

        targetPoint = Utils.getCenter(targetEnemy.getLocation(), targetEnemy.getBounds());
        move();
    }
}