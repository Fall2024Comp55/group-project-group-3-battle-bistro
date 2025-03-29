package Tower;

import Enemy.Enemy;
import UI.GameScreen;
import Utils.GameTick;
import Utils.TickListener;
import Utils.Utils;
import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GPoint;

import javax.swing.*;

public class RangedTower extends Tower implements TickListener {
    private static final int ATTACK_COOLDOWN = 20; 
    private int cooldownTimer;
    private UpgradeTree state;

    public RangedTower() {
        super("rangedkirby", 2, 1, 1, 150); 
        state = UpgradeTree.BASE;
        cooldownTimer = 0;
        setupVisuals();
        GameTick.TickManager.registerTickListener(this);
    }

    private void setupVisuals() {
        
        GImage towerImage = new GImage("resources/chefkirby.png");
        towerImage.setSize(40, 40);
        add(towerImage);
    }

    @Override
    public void attack() {
        if (cooldownTimer > 0) {
            cooldownTimer--;
            return;
        }

        if (attackTarget != null && attackTarget.isAlive()) {
          
            GPoint startPoint = Utils.getCenter(this.getLocation(), this.getBounds());
            GPoint targetPoint = Utils.getCenter(attackTarget.getLocation(), attackTarget.getBounds());
            new SpatulaProjectile(startPoint, targetPoint, attackTarget, 5, 0.1, state.getDamage());
            cooldownTimer = ATTACK_COOLDOWN;
            System.out.println("RangedTower fired a spatula at enemy!");
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
        System.out.println("Selling RangedTower");
        GameTick.TickManager.unregisterTickListener(this);
        GameScreen.getInstance().remove(this);
    }

    @Override
    public void move() {
      
    }

    @Override
    public void setTarget(GObject target) {
        if (target instanceof Enemy enemy) {
            attackTarget = enemy;
        }
    }

    @Override
    public void onTick(GameTick tick) {
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