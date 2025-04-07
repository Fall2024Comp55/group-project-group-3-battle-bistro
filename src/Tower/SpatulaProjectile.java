package Tower;

import Enemy.Enemy;
import Screen.ProgramWindow;
import acm.graphics.GPoint;

public class SpatulaProjectile extends Projectile {

    public SpatulaProjectile(GPoint startPoint, GPoint targetPoint, Enemy targetEnemy, double speed, double moveRate, int damage) {
        super("spatula", targetPoint, targetEnemy, speed, moveRate, damage);
        add(gImage);
        gImage.setSize(20, 50);
        ProgramWindow.getInstance().add(this);
    }
}