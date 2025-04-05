package Screen;

import Utils.TickListener;

import java.util.Set;

public class GardenScreen extends Screen {
    private volatile Set<TickListener> enemyTickListeners;
    private volatile Set<TickListener> towerTickListeners;
    private volatile Set<TickListener> projectileTickListeners;


    private static final GardenScreen GARDEN_SCREEN;

    static {
        try {
            GARDEN_SCREEN = new GardenScreen();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating GardenScreen singleton instance");
        }
    }

    private GardenScreen() {
        // Initialize the garden screen components here
    }

    public static GardenScreen getInstance() {
        return GARDEN_SCREEN;
    }

    @Override
    public void initializeComponents() {

    }

    @Override
    public void registerTickListener(TickListener listener) {

    }

    @Override
    public void unregisterTickListener(TickListener listener) {

    }

    @Override
    public void unregisterAllTickListener() {

    }
}
