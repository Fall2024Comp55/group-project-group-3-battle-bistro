package Tower;

import Enemy.Enemy;
import UI.GameScreen;
import Utils.*;
import acm.graphics.*;
import com.sun.source.tree.Tree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.net.URL;

public abstract class Tower extends GCompound implements TickListener, MouseInteract, Solid {
    private static final String basePath = "/resources/tower/";
    private static final String extension = ".png";
    private static final double sellModifier = 0.8;
    protected Enemy attackTarget;
    protected String name;
    protected GImage gImage;
    protected int cost;
    protected int level;
    protected int damage;
    protected Enemy target;
    protected Tree upgradeTree;
    protected GOval range;
    protected GOval hitbox;
    protected boolean placed;
    protected GPoint placedLocation;
    protected Projectile projectile;
    protected boolean enemyFound;

    // TODO figure out what is needed

    public Tower(String name, int cost, int level, int damage, int range) {
        this.name = name;
        this.cost = cost;
        this.level = level;
        this.damage = damage;
        this.placed = true;
        this.placedLocation = this.getLocation();
        gImage = new GImage(getImage());
        gImage.setSize(20, 20);
        gImage.setLocation(-gImage.getWidth() / 2, -gImage.getHeight() / 2);
        add(gImage);
        hitbox = new GOval(20, 20);
        hitbox.setLocation(-hitbox.getWidth() / 2, -hitbox.getHeight() / 2);
        add(hitbox);
        this.range = new GOval(range, range);
        this.range.setLocation(-this.range.getWidth() / 2, -this.range.getHeight() / 2);
        add(this.range);
    }

    public Tower(String name, int cost, int level, int damage, Projectile projectile, int range) {
        this.name = name;
        this.cost = cost;
        this.level = level;
        this.damage = damage;
        this.placed = true;
        this.placedLocation = this.getLocation();
        gImage = new GImage(getImage());
        gImage.setSize(20, 20);
        add(gImage);
        hitbox = new GOval(20, 20);
        add(hitbox);
        this.projectile = projectile;
        this.range = new GOval(range, range);
        this.range.setLocation(Utils.getCenter(this.getLocation(), this.range.getBounds()));
        add(this.range);
    }

    public boolean inRange() {
        enemyFound = false;
        GameScreen.getInstance().forEach(object -> {
            if (object instanceof Enemy e) {
                if (e.isAlive() && this.getBounds().intersects(e.getBounds())) {
                    if (attackTarget == null) {
                        enemyFound = true;
                        attackTarget = e;
                    }
                    if (e.getPathTraversed() > attackTarget.getPathTraversed()) {
                        attackTarget = e;
                    }
                }
            }
        });
        if (!enemyFound) {
            attackTarget = null;
        }
        return enemyFound;
    }

    public void getNextUpgrade() {
    }

    public int getLevel() {
        return level;
    }

    public GOval getRange() {
        return range;
    }

    public double getDamage() {
        return damage;
    }

    public int getSellCost() {
        return Math.round((float) (cost * sellModifier));
    }

    public int getUpgradeCost() {
        return 0;
    }

    public int getCost() {
        return cost;
    }

    public abstract void attack();

    public abstract void upgrade();

    public abstract void sell();

    public abstract void move();

    public abstract void setTarget(GObject target);

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
    public GRectangle getHitbox() {
        return new GRectangle(getX() + hitbox.getX(), getY() + hitbox.getY(), hitbox.getWidth(), hitbox.getHeight());
    }

    @Override
    public void onPress(MouseEvent e) {
        this.sendToFront();
    }

    @Override
    public void onDrag(MouseEvent e) {
        placed = false;
        this.move(e.getX() - MouseManager.getLastMousePoint().getX(), e.getY() - MouseManager.getLastMousePoint().getY());
        if (checkCollision()) {
            if (!hitbox.isFilled()) {
                hitbox.setFilled(true);
            }
        } else {
            if (hitbox.isFilled()) {
                hitbox.setFilled(false);
            }
        }
    }

    @Override
    public void onRelease(MouseEvent e) {
        if (!checkCollision()) {
            placed = true;
            placedLocation = this.getLocation();
        } else {
            this.setLocation(placedLocation);
        }
        if (hitbox.isFilled()) {
            hitbox.setFilled(false);
        }

    }


    // Possible methods to implement
    /*
    public abstract void attack();
    public abstract void upgrade();
    public abstract void sell();
    public abstract void setTarget(GObject target);
    public abstract void setX(double x);
    public abstract void setY(double y);
    public abstract double getX();
    public abstract double getY();
    public abstract double getRange();
    public abstract double getDamage();
    public abstract double getCost();
    public abstract int getLevel();
    public abstract int getUpgradeCost();
    public abstract int getSellCost();
    public abstract boolean isInRange(GObject target);
    public abstract boolean isTargetDead();
    public abstract boolean isTargetInRange();
    public abstract boolean isTargetNull();
    public abstract boolean isTargetValid();
    public abstract boolean isUpgradable();
    public abstract boolean isSellable();
    public abstract boolean isUpgraded();
    public abstract boolean isAttacking();
    public abstract boolean isPlaced();
    public abstract boolean isTargetable();
    public abstract boolean isTargeted();
    public abstract boolean isTargetInRange(GObject target);
    public abstract boolean isTargetValid(GObject target);
    public abstract boolean isTargetNull(GObject target);
    public abstract boolean isTargetDead(GObject target);
    public abstract boolean isTargeted(GObject target);
    public abstract void setPlaced(boolean placed);
    public abstract void setTargetable(boolean targetable);
    public abstract void setTargeted(boolean targeted);
    public abstract void setAttacking(boolean attacking);
    public abstract void setUpgraded(boolean upgraded);
    public abstract void setUpgradable(boolean upgradable);
    public abstract void setSellable(boolean sellable);
    public abstract void setTargetNull(boolean targetNull);
    public abstract void setTargetValid(boolean targetValid);
    public abstract void setTargeted(GObject target, boolean targeted);
    public abstract void setTargetNull(GObject target, boolean targetNull);
    public abstract void setTargetValid(GObject target, boolean targetValid);
    public abstract void setTargetNull(boolean targetNull);
    public abstract void setTargetValid(boolean targetValid);
     */


}
