package Screen;

public class RestaurantScreen extends Screen {
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
}
