package Screen;

import Character.Character;
import UI.ActionButton;
import Utils.TickListener;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import static Screen.ProgramWindow.*;

public class SummaryMenuScreen extends Screen {
    private final Set<TickListener> summaryTickListeners;
    private static final SummaryMenuScreen SUMMARY_MENU_SCREEN;
    private static final String BACKGROUND_IMAGE = "/resources/mainmenu/background.png";

    static {
        try {
            SUMMARY_MENU_SCREEN = new SummaryMenuScreen();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating SummaryMenuScreen singleton instance");
        }
    }

    private SummaryMenuScreen() {
        summaryTickListeners = Collections.synchronizedSet(new HashSet<>());
        initializeComponents();
    }

    public static SummaryMenuScreen getInstance() {
        return SUMMARY_MENU_SCREEN;
    }

    @Override
    public void initializeComponents() {
        // Background
        GImage background = new GImage(Utils.getImage(BACKGROUND_IMAGE));
        background.setSize(BASE_WIDTH, BASE_HEIGHT);
        add(background);

        // Title
        GLabel title = new GLabel("Day Summary");
        title.setFont("Arial-40");
        title.setColor(GLOBAL_COLOR);
        title.setLocation((BASE_WIDTH - title.getWidth()) / 2, BASE_HEIGHT / 4);
        add(title);

        // Money Earned Label
        int moneyEarned = Character.getInstance().getBalance();
        GLabel moneyLabel = new GLabel("Money Earned: $" + moneyEarned);
        moneyLabel.setFont("Arial-24");
        moneyLabel.setColor(GLOBAL_COLOR);
        moneyLabel.setLocation((BASE_WIDTH - moneyLabel.getWidth()) / 2, BASE_HEIGHT / 2 - 50);
        add(moneyLabel);

        // Main Menu Button
        ActionButton mainMenuButton = new ActionButton("Main Menu", () -> {
            ProgramWindow.getInstance().setScreen(CurrentScreen.MAIN_MENU);
            ProgramWindow.getInstance().remove(RestaurantScreen.getInstance());
            ProgramWindow.getInstance().remove(GardenScreen.getInstance());
            ProgramWindow.getInstance().add(MainMenuScreen.getInstance());
        });
        mainMenuButton.setLocation((BASE_WIDTH - mainMenuButton.getWidth()) / 2, BASE_HEIGHT / 2 + 50);
        add(mainMenuButton);

        // Next Day Button
        ActionButton nextDayButton = new ActionButton("Next Day", () -> {
            // Reset necessary game stats 
            Character.getInstance().getBalance(); // Reset balance or adjust as needed
            ProgramWindow.getInstance().startGame(); // Restart the game
        });
        nextDayButton.setLocation((BASE_WIDTH - nextDayButton.getWidth()) / 2, BASE_HEIGHT / 2 + 100);
        add(nextDayButton);
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
            summaryTickListeners.forEach(TickListener::onTick);
        });
    }
}