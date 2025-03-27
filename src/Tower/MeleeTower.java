package Tower;

import Utils.Action;
import Utils.GameTick;
import Utils.TickListener;
import acm.graphics.GObject;

public class MeleeTower extends Tower implements TickListener {
    // Might also be able to use enum for updgrade code
    // private Action variable that can store code
    private Action attack1;

    public MeleeTower() {
        super("chefkirby", 1, 1, 1, 50);
    }

    @Override
    public void attack() {
        // preform attack code
        attack1.performAction();
    }

    @Override
    public void upgrade() {
        // setting attack1 to new attack code
        if (level == 1) {
            level = 2;
            attack1 = () -> {
                System.out.println("Attack 2");
            };
        } else if (level == 2) {
            attack1 = () -> {
                System.out.println("Attack 3");
            };
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
    public void onTick(GameTick tick) {
        inRange();
    }

    @Override
    public void onCollision() {

    }

    // I am interested in using enums for this. If you want to try it out we can work on it together
    private enum TowerVersions {
        BASE {
            @Override
            void attack() {

            }
        },
        UPGRADE1 {
            @Override
            void attack() {

            }
        },
        UPGRADE2 {
            @Override
            void attack() {

            }
        },
        UPGRADE3 {
            @Override
            void attack() {

            }
        };

        abstract void attack();

        // other version specific code

    }


}

