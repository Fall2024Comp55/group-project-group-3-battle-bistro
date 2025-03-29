package Tower;

import Enemy.Enemy;
import UI.GameScreen;
import Utils.GameTick;
import Utils.Solid;
import Utils.TickListener;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GPoint;
import acm.graphics.GRectangle;

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

    public Projectile(GPoint targetPoint, Enemy targetEnemy, double speed, double moveRate, int damage) {
        this.targetPoint = targetPoint;
        this.targetEnemy = targetEnemy;
        this.speed = speed;
        this.moveRate = moveRate;
        this.damage = damage;
        this.active = true;

    
        GameTick.TickManager.registerTickListener(this);
    }

    @Override
    public void onTick(GameTick tick) {
        if (!active) {
            removeSelf();
            return;
        }

      
        if (targetEnemy == null || !targetEnemy.isAlive()) {
            active = false;
            removeSelf();
            return;
        }

      
        targetPoint = Utils.getCenter(targetEnemy.getLocation(), targetEnemy.getBounds());
        move();
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

           
            if (checkCollision()) {
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
        removeSelf();
    }

    protected void removeSelf() {
        GameTick.TickManager.unregisterTickListener(this);
        GameScreen.getInstance().remove(this);
        removeAll();
    }

    @Override
    public void onCollision() {
     
    }

    @Override
    public GRectangle getHitbox() {
        return getBounds();
    }

  
    protected abstract void setupVisuals();
}