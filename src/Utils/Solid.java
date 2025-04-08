package Utils;

import Screen.ProgramWindow;
import acm.graphics.GCompound;
import acm.graphics.GObject;
import acm.graphics.GRectangle;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The Solid interface should be implemented by any class that represents a solid object
 * in the game. Solid objects can collide with each other and have a hitbox.
 */
public interface Solid {

    /**
     * This method is called when a collision occurs with another solid object.
     */
    void onCollision();

    /**
     * @return The GRectangle representing the hitbox of the solid object.
     */
    GRectangle getHitbox();

    /**
     * Checks if the solid object is colliding with any other solid object in the game.
     *
     * @return True if a collision is detected, false otherwise.
     */
    default Boolean checkCollision() {
        AtomicBoolean hit = new AtomicBoolean(false);

        ProgramWindow.getInstance().forEach(object -> {
            if (object instanceof Solid s && object != this) {
                if (s.getHitbox() != null && this.getHitbox().intersects(s.getHitbox())) {
                    hit.set(true);
                }
            } else if (object instanceof GCompound c) {
                for (GObject g : c) {
                    if (g instanceof Solid s && s != this) {
                        if (s.getHitbox() != null && this.getHitbox().intersects(s.getHitbox())) {
                            hit.set(true);
                        }
                    }
                }


            }
        });

        return hit.get();
    }
}