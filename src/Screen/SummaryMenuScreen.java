package Screen;

import Utils.TickListener;

import java.util.Set;

public class SummaryMenuScreen extends Screen {
    private volatile Set<TickListener> summaryTickListeners;

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
            summaryTickListeners.spliterator().forEachRemaining(TickListener::onTick);
        });
    }
}
