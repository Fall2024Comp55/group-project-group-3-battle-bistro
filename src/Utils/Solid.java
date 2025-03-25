package Utils;

import acm.graphics.GRectangle;

public interface Solid {

    void onCollision();

    GRectangle getHitbox();

    Boolean checkCollision();

}
