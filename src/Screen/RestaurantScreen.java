package Screen;

import Utils.TickListener;

import java.util.Set;

public class RestaurantScreen extends Screen {
    private volatile Set<TickListener> restaurantTickListeners;

    private static final RestaurantScreen RESTAURANT_SCREEN;

    static {
        try {
            RESTAURANT_SCREEN = new RestaurantScreen();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating RestaurantScreen singleton instance");
        }
    }

    private RestaurantScreen() {
        // Initialize the restaurant screen components here
    }

    public static RestaurantScreen getInstance() {
        return RESTAURANT_SCREEN;
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
