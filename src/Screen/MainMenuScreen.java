package Screen;

import UI.Button;
import Utils.TickListener;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static Screen.ProgramWindow.*;
import static Utils.Utils.getCenter;

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
        GRect background = new GRect(WIDTH, HEIGHT);
        background.setFilled(true);
        background.setFillColor(Color.DARK_GRAY);
        add(background, 0, 0);
        elements.add(background);

        //  title label
        GLabel title = new GLabel("Battle Bistro");
        title.setFont("Arial-40");
        title.setColor(Color.WHITE);
        title.setLocation((WIDTH - title.getWidth()) / 2, HEIGHT / 4);
        add(title);
        elements.add(title);

        // start Game button
        startButton = new Button("Start Game") {
            @Override
            public void onRelease(MouseEvent e) {
                // transition to the GardenScreen
                ProgramWindow.getInstance().setScreen(CurrentScreen.GARDEN);
                ProgramWindow.getInstance().add(GardenScreen.getInstance());
                ProgramWindow.getInstance().add(RestaurantScreen.getInstance());
                ProgramWindow.getInstance().remove(this);
            }
        };
        startButton.setLocation((WIDTH - startButton.getWidth()) / 2, HEIGHT / 2 - 50);
        add(startButton);
        elements.add(startButton);

        // Options button
        optionsButton = new Button("Options") {
            @Override
            public void onRelease(MouseEvent e) {
                //
                System.out.println("Options button clicked - functionality not implemented yet.");
            }
        };
        optionsButton.setLocation((WIDTH - optionsButton.getWidth()) / 2, HEIGHT / 2);
        add(optionsButton);
        elements.add(optionsButton);

        // exit button
        exitButton = new Button("Exit") {
            @Override
            public void onRelease(MouseEvent e) {
                // Exit the game
                System.exit(0);
            }
        };
        exitButton.setLocation((WIDTH - exitButton.getWidth()) / 2, HEIGHT / 2 + 50);
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