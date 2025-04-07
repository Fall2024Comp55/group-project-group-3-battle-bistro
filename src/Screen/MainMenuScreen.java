package Screen;

import Utils.TickListener;

import java.util.Set;

public class MainMenuScreen extends Screen {
    private volatile Set<TickListener> mainMenuTickListeners;
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

    @Override
    public void onTick() {
        screenExecutor.submit(() -> {
            mainMenuTickListeners.spliterator().forEachRemaining(TickListener::onTick);
        });
    }
}
