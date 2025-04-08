package Tower;

import Character.Character;
import Enemy.Enemy;
import Screen.GardenScreen;
import Utils.GameTick.ActionManager;
import Utils.*;
import acm.graphics.*;
import com.sun.source.tree.Tree;

import java.awt.event.MouseEvent;

public abstract class Tower extends GCompound implements TickListener, MouseInteract, Solid {
    private static final String BASE_PATH = "/resources/tower/";
    private static final String EXTENSION = ".png";
    private static final double SELL_MODIFIER = 0.8;

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
        gImage = new GImage(Utils.getImage(toPath()));
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
        this(name, cost, level, damage, range);
        this.projectile = projectile;
    }

    protected double currentTheta;

    public boolean inRange() {
        if (placed) {
            enemyFound = false;
            attackTarget = null;
            GardenScreen.getInstance().getEnemyTickListeners().forEach(enemy -> {
                if (enemy instanceof Enemy e) {
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

            if (enemyFound && attackTarget != null) {
                // Calculate the angle to face the enemy
                double deltaX = attackTarget.getX() - this.getX();
                double deltaY = attackTarget.getY() - this.getY();
                double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));

                // Rotate the tower to face the enemy
                double rotateDistance = angle - currentTheta; // calculate the distance to rotate

                // if the distance is greater than 180 or -180 degrees, rotate in the opposite direction
                if (rotateDistance > 180) {
                    rotateDistance -= 360;
                } else if (rotateDistance < -180) {
                    rotateDistance += 360;
                }
                // if the distance is less than the speed, rotate by the distance
                this.rotate(rotateDistance);
                currentTheta += rotateDistance; // track the current angle

                // keep the current angle between 0 and 360 degrees
                if (currentTheta > 360) {
                    currentTheta -= 360;
                } else if (currentTheta < 0) {
                    currentTheta += 360;
                }
            }

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
        return Math.round((float) (cost * SELL_MODIFIER));
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
        return name.substring(0, 1).toUpperCase() + name.substring(1) + "[" + this.hashCode() + "]";
    }

    public String toPath() {
        return BASE_PATH + name.toLowerCase() + EXTENSION;
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
//        GardenScreen.getInstance().unregisterTickListener(this);
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
            GardenScreen.getInstance().registerTickListener(this);
        } else {
            if (placedLocation.getX() == 0 && placedLocation.getY() == 0) {
                Character.getInstance().addMoney(cost);
                GardenScreen.getInstance().remove(this);
            } else {
                placed = true;
                this.setLocation(placedLocation);
                ActionManager.addAction(1, () -> {
                    GardenScreen.getInstance().registerTickListener(this);
                });
            }
        }
        if (hitbox.isFilled()) {
            hitbox.setFilled(false);
        }
    }
}
