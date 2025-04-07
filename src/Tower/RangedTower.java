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

                GPoint startPoint = this.getLocation();
                GPoint targetPoint = attackTarget.getLocation();
                Projectile p = new SpatulaProjectile(startPoint, targetPoint, attackTarget, 20, .5, state.getDamage());
                p.setLocation(this.getLocation());
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
        if (inRange()) {
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