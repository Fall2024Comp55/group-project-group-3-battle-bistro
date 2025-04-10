package Screen;

import UI.ActionButton;
import UI.Button;
import Utils.TickListener;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static Screen.ProgramWindow.BASE_HEIGHT;
import static Screen.ProgramWindow.BASE_WIDTH;

public class MainMenuScreen extends Screen implements TickListener {
    private volatile Set<TickListener> mainMenuTickListeners;
    private static final MainMenuScreen MAIN_MENU_SCREEN;

    private final Set<GObject> elements;
    private Button startButton;
    private Button optionsButton;
    private Button exitButton;

    static {
        try {
            MAIN_MENU_SCREEN = new MainMenuScreen();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating MainMenuScreen singleton instance");
        }
    }

    private MainMenuScreen() {
        elements = new HashSet<>();
        mainMenuTickListeners = new HashSet<>();
        initializeComponents();
    }

    public static MainMenuScreen getInstance() {
        return MAIN_MENU_SCREEN;
    }

    @Override
    public void initializeComponents() {
        // background for the menu
        GRect background = new GRect(BASE_WIDTH, BASE_HEIGHT);
        background.setFilled(true);
        background.setFillColor(Color.DARK_GRAY);
        add(background, 0, 0);
        elements.add(background);

        //  title label
        GLabel title = new GLabel("Battle Bistro");
        title.setFont("Arial-40");
        title.setColor(Color.WHITE);
        title.setLocation((BASE_WIDTH - title.getWidth()) / 2, BASE_HEIGHT / 4);
        add(title);
        elements.add(title);

        // start Game button
        startButton = new ActionButton("Start Game", () -> {
            // Start the game
            ProgramWindow.getInstance().startGame();
        });
        startButton.setLocation((BASE_WIDTH - startButton.getWidth()) / 2, BASE_HEIGHT / 2 - 50);
        add(startButton);
        elements.add(startButton);

        // Options button
        optionsButton = new ActionButton("Options", () -> {
            System.out.println("Options button clicked - functionality not implemented yet.");
        });
        optionsButton.setLocation((BASE_WIDTH - optionsButton.getWidth()) / 2, BASE_HEIGHT / 2);
        add(optionsButton);
        elements.add(optionsButton);

        // exit button
        exitButton = new ActionButton("Exit", () -> {
            // Exit the game
            System.exit(0);
        });
        exitButton.setLocation((BASE_WIDTH - exitButton.getWidth()) / 2, BASE_HEIGHT / 2 + 50);
        add(exitButton);
        elements.add(exitButton);
    }

    @Override
    public void registerTickListener(TickListener listener) {
        mainMenuTickListeners.add(listener);
    }

    @Override
    public void unregisterTickListener(TickListener listener) {
        mainMenuTickListeners.remove(listener);
    }

    @Override
    public void unregisterAllTickListener() {
        mainMenuTickListeners.clear();
    }

    @Override
    public void onTick() {
        screenExecutor.submit(() -> {
            mainMenuTickListeners.spliterator().forEachRemaining(TickListener::onTick);
        });
    }

    @Override
    public Set<GObject> getElements() {
        return elements;
    }
}