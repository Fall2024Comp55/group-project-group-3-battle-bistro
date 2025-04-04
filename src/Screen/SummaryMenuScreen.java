package Screen;

public class SummaryMenuScreen extends Screen {
    private static final SummaryMenuScreen SUMMARY_MENU_SCREEN;

    static {
        try {
            SUMMARY_MENU_SCREEN = new SummaryMenuScreen();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating RestaurantScreen singleton instance");
        }
    }

    private SummaryMenuScreen() {
        // Initialize the summary menu screen components here
    }

    public static SummaryMenuScreen getInstance() {
        return SUMMARY_MENU_SCREEN;
    }

}
