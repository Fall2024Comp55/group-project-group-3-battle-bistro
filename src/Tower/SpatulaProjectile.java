package Tower;

import UI.GameScreen;
import Utils.GameTick;
import Utils.Utils;
import acm.graphics.GImage;
import acm.graphics.GPoint;
import Enemy.Enemy;

import javax.swing.*;

public class SpatulaProjectile extends Projectile {
    private GImage spatulaImage;

    public SpatulaProjectile(GPoint startPoint, GPoint targetPoint, Enemy targetEnemy, double speed, double moveRate, int damage) {
        super(targetPoint, targetEnemy, speed, moveRate, damage);

      
        this.setLocation(Utils.getCenterOffset(startPoint, 20, 20)); 

     
        setupVisuals();

     
        GameScreen.getInstance().add(this);
    }

    @Override
    protected void setupVisuals() {
        // I don't know why when I add image it gives me some weird error so I'll use the placeholder
        spatulaImage = new GImage("resources/spatula.png");
        spatulaImage.setSize(20, 20);
        add(spatulaImage);

        // have to go this route
        if (spatulaImage.getImage() == null) {
            System.out.println("Spatula image not found, using placeholder.");
            spatulaImage = new GImage(new ImageIcon(getClass().getResource("/resources/placeholder.png")).getImage());
            spatulaImage.setSize(20, 20);
            add(spatulaImage);
        }
    }
}