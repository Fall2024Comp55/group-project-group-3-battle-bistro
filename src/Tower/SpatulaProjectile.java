package Tower;

import Enemy.Enemy;
import acm.graphics.GPoint;

public class SpatulaProjectile extends Projectile {

    public SpatulaProjectile(GPoint startPoint, GPoint targetPoint, Enemy targetEnemy, double speed, double moveRate, int damage) {
        super("spatula", targetPoint, targetEnemy, speed, moveRate, damage);
        gImage.setSize(10, 25);
    }
}