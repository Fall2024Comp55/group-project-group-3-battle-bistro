package Restaurant;

import Utils.Action;
import Utils.Interact;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRectangle;

public class
Assembler extends GCompound implements Action, Interact {
    // TODO find needed variables and methods
    private GImage gImage;

    @Override
    public void performAction() {

    }

    @Override
    public void interact() {

    }

    @Override
    public GRectangle getInteractHitbox() {
        return this.getBounds();
    }
}
