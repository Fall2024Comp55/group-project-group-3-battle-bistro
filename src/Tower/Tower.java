package Tower;

import Enemy.Enemy;
import Utils.Hitbox;
import Utils.MouseInteract;
import Utils.Solid;
import Utils.TickListener;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GOval;
import com.sun.source.tree.Tree;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public abstract class Tower extends GCompound implements TickListener, MouseInteract {
    private static final String basePath = "resources/tower/";
    private static final String extension = ".png";
    private static final double sellModifier = 0.8;
    protected String name;
    protected GImage gImage;
    protected int cost;
    protected int level;
    protected int damage;
    protected Enemy target;
    protected Tree upgradeTree;
    protected GOval range;
    protected Hitbox hitbox;
    protected boolean placed;

    // TODO figure out what is needed

    public Tower(String name, int cost, int level, int damage) {
        this.name = name;
        this.cost = cost;
        this.level = level;
        this.damage = damage;
        this.placed = true;
//        gImage = new GImage(getImage());
//        add(gImage);
        hitbox = new Hitbox(20, this);
        add(hitbox);
    }

    public void getNextUpgrade() {
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public int getLevel() {
        return level;
    }

    public GOval getRange() {
        return getRange();
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
