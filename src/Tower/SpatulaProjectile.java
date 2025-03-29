package Tower;

import Enemy.Enemy;
import UI.GameScreen;
import acm.graphics.GImage;
import acm.graphics.GPoint;

public class SpatulaProjectile extends Projectile {

    public SpatulaProjectile(GPoint startPoint, GPoint targetPoint, Enemy targetEnemy, double speed, double moveRate, int damage) {
        super("spatula", targetPoint, targetEnemy, speed, moveRate, damage);

        gImage = new GImage(getImage());

        add(gImage);
        gImage.setSize(20, 50);

     
        setupVisuals();

     
        GameScreen.getInstance().add(this);
    }

    @Override
    protected void setupVisuals() {

    }
}