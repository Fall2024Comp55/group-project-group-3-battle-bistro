package Screen;

public class MainMenuScreen extends Screen {
    private static final MainMenuScreen MAIN_MENU_SCREEN;

    static {
        try {
            MAIN_MENU_SCREEN = new MainMenuScreen();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating MainMenuScreen singleton instance");
        }
    }

    private MainMenuScreen() {
        // Initialize the main menu screen components here
    }

    public static MainMenuScreen getInstance() {
        return MAIN_MENU_SCREEN;
    }

}
