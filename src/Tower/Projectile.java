package Tower;

import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GPoint;

public abstract class Projectile extends GCompound {
    // TODO find needed variables and methods (possible projectile type enum class?)

    private GPoint target;
    private double speed;
    private double moveRate;

    public Projectile() {

    }


    //FIXME
    public void move() {
        double targetX = target.getX();
        double targetY = target.getY();

        GPoint point = Utils.getCenter(this.getLocation(), this.getBounds());

        double dx = targetX - point.getX();
        double dy = targetY - point.getY();

        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < speed * moveRate) {
            this.setLocation(Utils.getCenterOffset(target, this.getBounds()));
            //TODO: implement projectile hit logic and collision logic
        } else {
            //TODO: implement projectile movement logic and collision detection
            this.move((dx / distance) * speed * moveRate, (dy / distance) * speed * moveRate);
        }
    }


}
