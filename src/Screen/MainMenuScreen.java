package Screen;

import UI.ActionButton;
import UI.Button;
import Utils.TickListener;
import Utils.Utils;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static Screen.ProgramWindow.BASE_HEIGHT;
import static Screen.ProgramWindow.BASE_WIDTH;

public class MainMenuScreen extends Screen implements TickListener {
    private volatile Set<TickListener> mainMenuTickListeners;
    private static final MainMenuScreen MAIN_MENU_SCREEN;
    private static final String BACKGROUND_IMAGE = "/resources/mainmenu/background.png";

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
        mainMenuTickListeners = new HashSet<>();
        initializeComponents();
    }

    public static MainMenuScreen getInstance() {
        return MAIN_MENU_SCREEN;
    }

    @Override
    public void initializeComponents() {
        // background for the menu
        GImage background = new GImage(Utils.getImage(BACKGROUND_IMAGE));
        background.setSize(BASE_WIDTH, BASE_HEIGHT);
        add(background);

        //  title label
        GLabel title = new GLabel("Battle Bistro");
        title.setFont("Arial-40");
        title.setColor(Color.WHITE);
        title.setLocation((BASE_WIDTH - title.getWidth()) / 2, BASE_HEIGHT / 4);
        add(title);

        // start Game button
        startButton = new ActionButton("Start Game", () -> {
            // Start the game
            ProgramWindow.getInstance().startGame();
        });
        startButton.setLocation(Utils.getCenterPoint(this.getBounds()));
        startButton.move(0, -50);
        add(startButton);

        // Options button
        optionsButton = new ActionButton("Options", () -> {
            System.out.println("Options button clicked - functionality not implemented yet.");
        });
        optionsButton.setLocation(Utils.getCenterPoint(this.getBounds()));
        add(optionsButton);

        // exit button
        exitButton = new ActionButton("Exit", () -> {
            // Exit the game
            System.exit(0);
        });
        exitButton.setLocation(Utils.getCenterPoint(this.getBounds()));
        exitButton.move(0, 50);
        add(exitButton);
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