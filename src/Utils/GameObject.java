package Utils;

import UI.GameScreen;
import acm.graphics.GCompound;

public class GameObject extends GCompound {
    protected final GameScreen mainScreen;

    public GameObject() {
        this.mainScreen = GameScreen.getInstance();
    }
}
