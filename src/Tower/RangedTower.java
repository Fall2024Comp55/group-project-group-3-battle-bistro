package Tower;

import Screen.GardenScreen;
import Utils.GameTick.ActionManager;
import Utils.TickListener;
import acm.graphics.GObject;
import acm.graphics.GPoint;

public class RangedTower extends Tower implements TickListener {
    // TODO: look into reworking cooldown and other code
    private static final int ATTACK_COOLDOWN = 20;

    private boolean onCooldown;
    private UpgradeTree state;

    public RangedTower() {
        super("chefkirby", 2, 1, 1, 150);
        state = UpgradeTree.BASE;
        onCooldown = false;
    }

    @Override
    public void attack() {
        if (placed && !onCooldown) {
            if (attackTarget != null && attackTarget.isAlive() && getBounds().intersects(attackTarget.getBounds())) {

                // Get the start point (tower's location)
                GPoint startPoint = this.getLocation();

                // Get the target point (enemy's location)
                GPoint targetPoint = attackTarget.getLocation();

                // Calculate the direction vector from the tower to the enemy
                double dx = targetPoint.getX() - startPoint.getX();
                double dy = targetPoint.getY() - startPoint.getY();

                // Normalize the direction vector
                double length = Math.sqrt(dx * dx + dy * dy);
                dx = dx / length;
                dy = dy / length;

                // Calculate the end point at the edge of the tower's range
                double range = this.getRange().getWidth() / 2; // Assuming range is a circle
                double endX = startPoint.getX() + dx * range;
                double endY = startPoint.getY() + dy * range;
                GPoint endPoint = new GPoint(endX, endY);

                // Test visual line
//                ProgramWindow.getInstance().add(new GLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY()));

                Projectile p = new SpatulaProjectile(endPoint, attackTarget, 20, .5, state.getDamage());
                p.setLocation(this.getLocation());
                p.rotate(currentTheta);
                GardenScreen.getInstance().add(p);
                onCooldown = true;
                ActionManager.addAction(8, () -> {
                    onCooldown = false;
                });
            }

        }
    }

    @Override
    public void upgrade() {
        if (level == 1) {
            level = 2;
            state = UpgradeTree.UPGRADE1;
        } else if (level == 2) {
            state = UpgradeTree.UPGRADE2;
        }
        System.out.println("RangedTower upgraded to level " + level);
    }

    @Override
    public void sell() {
    }

    @Override
    public void move() {

    }

    @Override
    public void setTarget(GObject target) {
    }

    @Override
    public void onTick() {
        if (!onCooldown && inRange()) {
            attack();
        }
    }

    @Override
    public void onCollision() {

    }

    private enum UpgradeTree {
        BASE {
            @Override
            int getDamage() {
                return 30;
            }
        },
        UPGRADE1 {
            @Override
            int getDamage() {
                return 50;
            }
        },
        UPGRADE2 {
            @Override
            int getDamage() {
                return 70;
            }
        };

        abstract int getDamage();
    }
}