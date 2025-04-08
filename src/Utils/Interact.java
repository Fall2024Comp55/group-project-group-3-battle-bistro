package Utils;

import Screen.RestaurantScreen;
import acm.graphics.GObject;
import acm.graphics.GRectangle;

/**
 * The Interact interface should be implemented by any class that wants to handle
 * interaction events.
 */
public interface Interact {

    /**
     * This method is called to perform an interaction.
     */
    void interact();

    /**
     * @return The GRectangle representing the hitbox.
     */
    GRectangle getInteractHitbox();

    /**
     * Checks if an interactable object's hitbox intersects with this object's hitbox.
     *
     * @return The Interact object that intersects with this object's hitbox, or null if none found.
     */
    default Interact checkForInteractable() {

        for (GObject object : RestaurantScreen.getInstance().getElements()) {
            if (object instanceof Interact i && object != this) {
                if (i.getInteractHitbox() != null && this.getInteractHitbox().intersects(i.getInteractHitbox())) {
                    return i;
                }
            }
        }
        return null;
    }

}