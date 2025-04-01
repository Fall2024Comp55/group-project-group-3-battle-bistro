package Tower;

import Enemy.Enemy;
import Utils.Action;
import Utils.TickListener;
import acm.graphics.GObject;

public class MeleeTower extends Tower implements TickListener {
    private Action attack1;
    private Upgrade_Tree state;

    public MeleeTower() {
        super("chefkirby", 10, 1, 5, 120);
        state = Upgrade_Tree.BASE;
    }

    @Override
    public void attack() {
        if (placed) {
            // preform attack code
            state.attack(attackTarget);
            if (!attackTarget.isAlive()) {
                attackTarget = null;
            }
        }
    }

    @Override
    public void upgrade() {
        // setting attack1 to new attack code
        if (level == 1) {
            level = 2;
            state = Upgrade_Tree.UPGRADE1;
        } else if (level == 2) {
            state = Upgrade_Tree.UPGRADE2;
        }
        System.out.println("Upgrading");
    }

    @Override
    public void sell() {
        System.out.println("Selling");
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

    // I am interested in using enums for this. If you want to try it out we can work on it together
    private enum Upgrade_Tree {
        BASE {
            @Override
            void attack(Enemy target) {
                target.takeDamage(50);
            }
        },
        UPGRADE1 {
            @Override
            void attack(Enemy target) {

            }
        },
        UPGRADE2 {
            @Override
            void attack(Enemy target) {

            }
        },
        UPGRADE3 {
            @Override
            void attack(Enemy target) {

            }
        };

        abstract void attack(Enemy target);

        // other version specific code

    }
}

