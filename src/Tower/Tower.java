package Tower;

import Character.Character;
import Enemy.Enemy;
import Enemy.EnemyPath;
import Screen.GardenScreen;
import Utils.GameTick.ActionManager;
import Utils.*;
import acm.graphics.*;
import com.sun.source.tree.Tree;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public abstract class Tower extends GCompound implements TickListener, MouseInteract, Solid {
    private static final String BASE_PATH = "/resources/tower/";
    private static final String EXTENSION = ".png";
    private static final double SELL_MODIFIER = 0.8;

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
    protected double currentTheta;
    protected boolean unlocked;
    protected Enemy attackTarget;
    protected LinkedList<Zone> attackZones;
    protected boolean inRange;

    Tower(String name, int cost, int level, int damage, int range) {
        this.name = name;
        this.cost = cost;
        this.level = level;
        this.damage = damage;
        this.placed = true;
        this.placedLocation = this.getLocation();
        this.attackZones = new LinkedList<>();
        this.gImage = new GImage(Utils.getImage(toPath()));
        gImage.setSize(20, 20);
        gImage.setLocation(Utils.getCenter(gImage.getBounds()));
        add(gImage);
        hitbox = new GOval(20, 20);
        hitbox.setLocation(Utils.getCenter(hitbox.getBounds()));
        add(hitbox);
        hitbox.setVisible(false);
        this.range = new GOval(range, range);
        this.range.setLocation(Utils.getCenter(this.range.getBounds()));
        add(this.range);
        this.range.setVisible(false);
        this.range.setFillColor(new Color(220, 20, 60, 150));
    }

    public Tower(String name, int cost, int level, int damage, Projectile projectile, int range) {
        this(name, cost, level, damage, range);
        this.projectile = projectile;
    }

    public boolean inRange() {
        if (!attackZones.isEmpty()) {
            for (GPoint p : attackZones.getFirst().getSidePoints()) {
                Object enemy = Utils.getObjectInCompound(GardenScreen.getInstance(), p);
                if (enemy instanceof Enemy e) {
                    if (e.isAlive()) {
                        return true;
                    }
                }
            }
            for (GPoint p : attackZones.get(attackZones.size() / 2).getSidePoints()) {
                Object enemy = Utils.getObjectInCompound(GardenScreen.getInstance(), p);
                if (enemy instanceof Enemy e) {
                    if (e.isAlive()) {
                        return true;
                    }
                }
            }
            for (GPoint p : attackZones.getLast().getSidePoints()) {
                Object enemy = Utils.getObjectInCompound(GardenScreen.getInstance(), p);
                if (enemy instanceof Enemy e) {
                    if (e.isAlive()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean findTarget() {
        enemyFound = false;
        attackTarget = null;
        for (Zone zone : attackZones) {
            for (GPoint p : zone.getSidePoints()) {
                GObject enemy = Utils.getObjectInCompound(GardenScreen.getInstance(), p);
                if (enemy instanceof Enemy e) {
                    if (e.isAlive()) {
                        enemyFound = true;
                        if (attackTarget == null) {
                            attackTarget = e;
                        }
                        if (e.getPathTraversed() > attackTarget.getPathTraversed()) {
                            attackTarget = e;
                        }
                    }
                }
                if (enemyFound) {
                    break;
                }
            }
        }
        if (enemyFound && attackTarget != null) {
            // Calculate the angle to face the enemy
            double dx = attackTarget.getX() - this.getX();
            double dy = attackTarget.getY() - this.getY();
            double angle = Math.toDegrees(Math.atan2(dx, dy));

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
    }

    public void initAttackLines() {
        attackZones.clear();
        for (int i = 0; i < 360; i++) {
            GLine line = linetrace(range.getWidth() / 2, i);
            for (EnemyPath.PathLine pathLine : GardenScreen.getEnemyPath().getPath()) {
                if (line.getBounds().intersects(pathLine.getBounds())) {
                    line.setColor(new Color(50, 50, 50, 100));
                    GRectangle bounds = line.getBounds().intersection(pathLine.getHitbox());
                    Zone zone = new Zone(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight(), pathLine.getIndex());
                    attackZones.add(zone);
                }
            }
        }
        attackZones.sort((o1, o2) -> {
            if (o1.getPathIndex() != o2.getPathIndex()) {
                return Integer.compare(o2.getPathIndex(), o1.getPathIndex());
            } else {
                double distance1 = Math.hypot(o1.getX() - GardenScreen.getEnemyPath().getPoint(o1.getPathIndex()).getX(),
                        o1.getY() - GardenScreen.getEnemyPath().getPoint(o1.getPathIndex()).getY());
                double distance2 = Math.hypot(o2.getX() - GardenScreen.getEnemyPath().getPoint(o2.getPathIndex()).getX(),
                        o2.getY() - GardenScreen.getEnemyPath().getPoint(o2.getPathIndex()).getY());
                return Double.compare(distance2, distance1);
            }
        });
        LinkedList<Zone> filteredZones = new LinkedList<>();
        for (int i = 0; i < attackZones.size(); i++) {
            if (i == 0 || i == attackZones.size() / 2 || i == attackZones.size() - 1 || i % 10 == 0) {
                filteredZones.add(attackZones.get(i));
                attackZones.get(i).addZone();
                attackZones.get(i).addPoints();
            }
        }
        attackZones = filteredZones;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean locked) {
        this.unlocked = locked;
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

    public double getCurrentTheta() {
        return currentTheta;
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

    public GImage getgImage() {
        return gImage;
    }

    @Override
    public GRectangle getHitbox() {
        return new GRectangle(getX() + hitbox.getX(), getY() + hitbox.getY(), hitbox.getWidth(), hitbox.getHeight());
    }

    @Override
    public void onPress(MouseEvent e) {
        if (!range.isVisible()) {
            add(range);
            range.setVisible(true);
        }
        this.sendToFront();
    }

    @Override
    public void onDrag(MouseEvent e) {
        placed = false;
        this.move(e.getX() - MouseManager.getLastMousePoint().getX(), e.getY() - MouseManager.getLastMousePoint().getY());
        if (!range.isVisible()) {
            add(range);
            range.setVisible(true);
        }
        if (checkCollision()) {
            if (!range.isFilled()) {
                range.setFilled(true);
            }
        } else {
            if (range.isFilled()) {
                range.setFilled(false);
            }
        }
    }

    @Override
    public void onRelease(MouseEvent e) {
        if (!checkCollision()) {
            placed = true;
            initAttackLines();
            placedLocation = this.getLocation();
            ActionManager.addAction(1, () -> {
                GardenScreen.getInstance().registerTickListener(this);
            });
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
        this.sendToBack();
        this.sendForward();
        if (range.isVisible()) {
            range.setVisible(false);
            remove(range);
        }
        if (range.isFilled()) {
            range.setFilled(false);
        }
    }

    public GLine linetrace(double length, int degree) {
        // Get the character's current position
//        GPoint p = Utils.getPointOffset(getLocation(), RestaurantScreen.getInstance().getBounds()); // ProgramWindow Relative
        GPoint p = getLocation();
        double startX = p.getX();
        double startY = p.getY();

        // Calculate the end position based on the direction the character is facing
        double endX = startX - length * Math.sin(Math.toRadians(degree));
        double endY = startY - length * Math.cos(Math.toRadians(degree));

        // TODO figure out issues when moving out side of screens
//        ProgramWindow.getInstance().add(new GLine(startX, startY, endX, endY));

        // Add the line to the ProgramWindow
        return new GLine(startX, startY, endX, endY);
    }

}
