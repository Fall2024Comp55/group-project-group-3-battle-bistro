package Tower;

import Character.Character; 
import Screen.GardenScreen;
import Utils.GameTick.ActionManager;
import Utils.TickListener;
import acm.graphics.GObject;
import acm.graphics.GPoint;

public class RangedTower extends Tower implements TickListener {
    private static final int ATTACK_COOLDOWN = 20;
    private static final int[] UPGRADE_COSTS = {50, 100}; // Costs for upgrading to level 2 and 3

    private boolean onCooldown;
    private UpgradeTree state;

    public RangedTower() {
        super("chefkirby", 2, 1, 1, 150);
        state = UpgradeTree.BASE;
        onCooldown = false;
    }

    @Override
    public void attack() {
        findTarget();
        if (attackTarget != null && attackTarget.isAlive()) {
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
//             ProgramWindow.getInstance().add(new GLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY()));

            Projectile p = new SpatulaProjectile(endPoint, attackTarget, 20, .5, state.getDamage());
            p.setLocation(this.getLocation());
            p.rotate(currentTheta);
            GardenScreen.getEnemyLayer().add(p);
            onCooldown = true;
            ActionManager.addAction(1, () -> {
                onCooldown = false;
            });
        }
    }

    @Override
    public void upgrade() {
        if (level == 1 && Character.getInstance().subtractBalance(UPGRADE_COSTS[0])) {
            level = 2;
            state = UpgradeTree.UPGRADE1;
        } else if (level == 2 && Character.getInstance().subtractBalance(UPGRADE_COSTS[1])) {
            level = 3;
            state = UpgradeTree.UPGRADE2;
        }
        
    }

    public int getUpgradeCost() {
        if (level == 1) return UPGRADE_COSTS[0];
        if (level == 2) return UPGRADE_COSTS[1];
        return 0; // No further upgrades
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
        if (placed && !onCooldown && inRange()) {
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
                return 5; // Initial damage
            }
        },
        UPGRADE1 {
            @Override
            int getDamage() {
                return 10; // Upgraded damage
            }
        },
        UPGRADE2 {
            @Override
            int getDamage() {
                return 15; // Further upgraded damage
            }
        };

        abstract int getDamage();
    }
}