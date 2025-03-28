package Utils;

import UI.GameScreen;
import acm.graphics.GObject;
import acm.graphics.GRectangle;

import java.util.concurrent.atomic.AtomicBoolean;

public interface Solid {

    void onCollision();

    GRectangle getHitbox();

    default Boolean checkCollision() {
        AtomicBoolean hit = new AtomicBoolean(false);

        GameScreen.getInstance().forEach(object -> {
            if (object instanceof Solid s && object != (GObject) this) {
                if (s.getHitbox() != null && this.getHitbox().intersects(s.getHitbox())) {
                    hit.set(true);
                }
            }
        });


        return hit.get();
    }

}
