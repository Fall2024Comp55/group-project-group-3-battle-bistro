package Tower;

import Character.Character;
import Enemy.Enemy;
import UI.GameScreen;
import Utils.*;
import Utils.GameTick.ActionManager;
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
        if (placed) {
            enemyFound = false;
            attackTarget = null;
            GameScreen.getInstance().forEach(object -> {
                if (object instanceof Enemy e) {
                    if (e.isAlive() && this.getBounds().intersects(e.getBounds())) {
                        enemyFound = true;
                        if (attackTarget == null) {
                            attackTarget = e;
                        }
                        if (e.getPathTraversed() > attackTarget.getPathTraversed()) {
                            attackTarget = e;
                        }
                    }
                }
            });
            return enemyFound;
        } else {
            return false;
        }
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

    private void remove() {
        removeAll();
        GameScreen.getInstance().remove(this);
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
        ActionManager.addAction(1, () -> {
            GameTick.TickManager.unregisterTickListener(this);
        });
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
            ActionManager.addAction(1, () -> {
                GameTick.TickManager.registerTickListener(this);
            });
        } else {
            if (placedLocation.getX() == 0 && placedLocation.getY() == 0) {
                Character.getInstance().addMoney(cost);
                remove();
            } else {
                placed = true;
                this.setLocation(placedLocation);
                ActionManager.addAction(1, () -> {
                    GameTick.TickManager.registerTickListener(this);
                });
            }
        }
        if (hitbox.isFilled()) {
            hitbox.setFilled(false);
        }
    }
}
