package Screen;

import Utils.TickListener;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SummaryMenuScreen extends Screen {
    private final Set<TickListener> summaryTickListeners;

    private static final SummaryMenuScreen SUMMARY_MENU_SCREEN;

    static {
        try {
            SUMMARY_MENU_SCREEN = new SummaryMenuScreen();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating RestaurantScreen singleton instance");
        }
    }

    private SummaryMenuScreen() {
        summaryTickListeners = Collections.synchronizedSet(new HashSet<>());
        // Initialize the summary menu screen components here
    }

    public static SummaryMenuScreen getInstance() {
        return SUMMARY_MENU_SCREEN;
    }

    @Override
    public void initializeComponents() {

    }

    @Override
    public synchronized void registerTickListener(TickListener listener) {
        summaryTickListeners.add(listener);
    }

    @Override
    public synchronized void unregisterTickListener(TickListener listener) {
        summaryTickListeners.remove(listener);
    }

    @Override
    public void unregisterAllTickListener() {
        summaryTickListeners.clear();
    }

    @Override
    public void onTick() {
        screenExecutor.submit(() -> {
            summaryTickListeners.spliterator().forEachRemaining(TickListener::onTick);
        });
    }
}
