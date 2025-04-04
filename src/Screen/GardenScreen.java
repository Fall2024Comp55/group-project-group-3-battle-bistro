package Screen;

import Utils.TickListener;

public class GardenScreen extends Screen {

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
