package Screen;

import Character.Character;
import UI.GardenUI;
import UI.OrderTicketUI;
import UI.RestaurantUI;
import Utils.*;
import acm.graphics.GCompound;
import acm.graphics.GObject;
import acm.program.GraphicsProgram;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static Utils.Utils.easeInOutCubic;
import static Utils.Utils.getObjectInCompound;

public class ProgramWindow extends GraphicsProgram {
    public static final int BASE_WIDTH = 800;
    public static final int BASE_HEIGHT = 450;
    public static final String GLOBAL_FONT = "Arial-16";
    public static final Color GLOBAL_COLOR = Color.BLACK;

    private static final ProgramWindow GAME_SCREEN;

    private static CurrentScreen currentScreen;

    private boolean shifting;
    private GameTick tick;

    static {
        try {
            GAME_SCREEN = new ProgramWindow();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating GameScreen singleton instance");
        }
    }

    public static ProgramWindow getInstance() {
        return GAME_SCREEN;
    }

    public void init() {
        setSize(BASE_WIDTH, BASE_HEIGHT);
        this.gw.setTitle("Battle Bistro");
        this.gw.setResizable(false);
        this.gw.setLocationRelativeTo(null);
        this.requestFocus();
        this.tick = new GameTick();
        this.setAutoRepaintFlag(false);
        this.shifting = false;
    }

    public static CurrentScreen getCurrentScreen() {
        return currentScreen;
    }

    @Override
    public void run() {

        currentScreen = CurrentScreen.MAIN_MENU;


        add(MainMenuScreen.getInstance());


        GardenScreen.getInstance().setLocation(-BASE_WIDTH * .75, 0);
        RestaurantScreen.getInstance().setLocation(BASE_WIDTH * .25, 0);


        tick.start();

        addInputListeners();
    }

    public void setScreen(CurrentScreen newScreen) {
        currentScreen = newScreen;
    }

    public void startGame() {
        // remove the main menu screen from the window
        remove(MainMenuScreen.getInstance());

        // add the garden screen to the window
        add(GardenScreen.getInstance());

        // add the restaurant screen to the window
        add(RestaurantScreen.getInstance());

        // start in the restaurant screen by adding order ticket UI and restaurant UI
        add(RestaurantUI.getInstance());
        GardenUI.getInstance().setLocation(0, -GardenUI.getInstance().getHeight());
        add(OrderTicketUI.getInstance());
        add(GardenUI.getInstance());
        setScreen(CurrentScreen.RESTAURANT);
        RestaurantScreen.getInstance().resetDay();

        // send game screens to back to make UI is in front
        GardenScreen.getInstance().sendToBack();
        RestaurantScreen.getInstance().sendToBack();
    }

    public void endDay() {
        if (currentScreen.equals(CurrentScreen.GARDEN)) {
            enterDoor();
        }
        // Remove game screens and UI
        remove(GardenScreen.getInstance());
        remove(RestaurantScreen.getInstance());
        remove(RestaurantUI.getInstance());
        remove(GardenUI.getInstance());
        remove(OrderTicketUI.getInstance());
        // Add SummaryMenuScreen
        setScreen(CurrentScreen.SUMMARY);
        add(SummaryMenuScreen.getInstance());
    }

    public void startDay() {
        // remove the main menu screen from the window
        remove(SummaryMenuScreen.getInstance());

        // add the garden screen to the window
        add(GardenScreen.getInstance());

        // add the restaurant screen to the window
        add(RestaurantScreen.getInstance());

        // start in the restaurant screen by adding order ticket UI and restaurant UI
        add(RestaurantUI.getInstance());
        add(OrderTicketUI.getInstance());
        add(GardenUI.getInstance());
        setScreen(CurrentScreen.RESTAURANT);
        RestaurantScreen.getInstance().resetDay();

        // send game screens to back to make UI is in front
        GardenScreen.getInstance().sendToBack();
        RestaurantScreen.getInstance().sendToBack();
    }

    public void exitToMainMenu() {
        // Remove game screens and UI
        if (currentScreen.equals(CurrentScreen.SUMMARY)) {
            remove(SummaryMenuScreen.getInstance());
        } else {
            remove(GardenScreen.getInstance());
            remove(RestaurantScreen.getInstance());
            remove(RestaurantUI.getInstance());
            remove(GardenUI.getInstance());
            remove(OrderTicketUI.getInstance());
        }

        // Add MainMenuScreen
        add(MainMenuScreen.getInstance());
        setScreen(CurrentScreen.MAIN_MENU);
    }

    public void endGameOver() {
        remove(GardenScreen.getInstance());
        remove(RestaurantScreen.getInstance());
        add(SummaryMenuScreen.getInstance());
        setScreen(CurrentScreen.SUMMARY);
    }

    public void enterDoor() {
        int screenShift = 0;
        int UIShift = ((int) (GardenUI.getInstance().getHeight()));
        if (!shifting) {
            if (currentScreen.equals(CurrentScreen.GARDEN)) {
                currentScreen = CurrentScreen.RESTAURANT;
                screenShift = ((int) (-BASE_WIDTH + ((float) BASE_WIDTH * .25)));
                animateObject(GardenUI.getInstance(), 0, -GardenUI.getInstance().getHeight(), 800, null);
            } else if (currentScreen.equals(CurrentScreen.RESTAURANT)) {
                currentScreen = CurrentScreen.GARDEN;
                animateObject(GardenUI.getInstance(), 0, 0, 800, null);
                // keep endX 0
            }

            shifting = true;

            animateObject(RestaurantScreen.getInstance(), BASE_WIDTH + screenShift, 0, 800, null);
            animateObject(GardenScreen.getInstance(), screenShift, 0, 800, () -> {
                shifting = false;
            });
        }
    }

    public void animateObject(GObject object, final double endX, final double endY, long duration) {
        animateObject(object, endX, endY, duration, null);
    }

    public void animateObject(GObject object, final double endX, final double endY, long duration, Action action) {
        long startTime = System.currentTimeMillis();
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        final double startX = object.getX();
        final double startY = object.getY();

        executor.scheduleAtFixedRate(() -> {
            float progress = (System.currentTimeMillis() - startTime) / (float) duration;

            if (progress >= 1.0f) {
                object.setLocation(endX, endY);
                repaint();
                if (action != null) {
                    action.performAction();
                }
                executor.shutdown();
            } else {
                object.setLocation(Utils.lerp(startX, endX, easeInOutCubic(progress)), Utils.lerp(startY, endY, easeInOutCubic(progress)));
                repaint();
            }
        }, 0, 10, TimeUnit.MILLISECONDS);
    }

    public void addInputListeners() {
        gw.getGCanvas().addKeyListener(Character.getInstance());
        gw.getGCanvas().addMouseListener(this);
        gw.getGCanvas().addMouseMotionListener(this);
    }

    public void removeInputListeners() {
        gw.getGCanvas().removeKeyListener(Character.getInstance());
        gw.getGCanvas().removeMouseListener(this);
        gw.getGCanvas().removeMouseMotionListener(this);
    }

    private void pressed(MouseEvent e) {
        GObject object = getElementAt(e.getX(), e.getY());

        MouseManager.setLastClickPoint(e.getPoint());
        MouseManager.setLastMousePoint(e.getPoint());

        if (object != null) {
            if (object instanceof MouseInteract o) {
                MouseManager.setSelectedObject(object);
                o.onPress(e);
            } else if (object instanceof GCompound c) {
                object = getObjectInCompound(c, e.getPoint());
                if (object instanceof MouseInteract m) {
                    MouseManager.setSelectedObject(object);
                    m.onPress(e);
                }
            }
        }
    }

    private void dragged(MouseEvent e) {
        GObject object = MouseManager.getSelectedObject();
        MouseManager.setHoverObject(object);

        if (object != null) {
            if (object instanceof MouseInteract o) {
                o.onDrag(e);
            } else if (object instanceof GCompound c) {
                object = getObjectInCompound(c, e.getPoint());
                if (object instanceof MouseInteract m) {
                    MouseManager.setHoverObject(object);
                    m.onDrag(e);
                }
            }
        }
        MouseManager.setLastMousePoint(e.getPoint());
    }

    private void released(MouseEvent e) {
        GObject object = MouseManager.getSelectedObject();

        if (object != null) {
            if (object instanceof MouseInteract o) {
                o.onRelease(e);
            } else if (object instanceof GCompound c) {
                object = getObjectInCompound(c, e.getPoint());
                if (object instanceof MouseInteract m) {
                    MouseManager.setSelectedObject(object);
                    m.onRelease(e);
                }
            }
        }
        MouseManager.setLastClickPoint(e.getPoint());
        MouseManager.setSelectedObject(null);
    }

    private void moved(MouseEvent e) {
        MouseManager.setHoverPoint(e.getPoint());

        GObject object = getElementAt(e.getX(), e.getY());
        if (object != null) {
            if (object instanceof MouseInteract o) {
                if (object != MouseManager.getHoverObject()) {
                    MouseManager.setHoverObject(object);
                    o.onHover(e);
                }
            } else if (object instanceof GCompound c) {
                boolean found = false;
                object = getObjectInCompound(c, e.getPoint());
                if (object instanceof MouseInteract m) {
                    if (object != MouseManager.getHoverObject()) {
                        MouseManager.setHoverObject(object);
                        m.onHover(e);
                    }
                    found = true;
                }
                if (!found && MouseManager.getHoverObject() != null) {
                    MouseManager.setHoverObject(null);
                }
            }
        } else if (MouseManager.getHoverObject() != null) {
            MouseManager.setHoverObject(null);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        dragged(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        released(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        moved(e);
    }

    public enum CurrentScreen {
        MAIN_MENU(0),
        SUMMARY(0),
        GARDEN(BASE_WIDTH),
        RESTAURANT(0),
        SETTINGS(0);

        private double x;

        CurrentScreen(double x) {
            this.x = x;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }
}