package Screen;

import Character.Character;
import UI.ActionButton;
import UI.Button;
import Utils.GameTick;
import Utils.MouseInteract;
import Utils.MouseManager;
import Utils.Utils;
import acm.graphics.GCompound;
import acm.graphics.GObject;
import acm.program.GraphicsProgram;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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

    @Override
    public void run() {
        
        currentScreen = CurrentScreen.MAIN_MENU;


        add(MainMenuScreen.getInstance());

      
        MainMenuScreen.getInstance().setLocation(0, 0);
        RestaurantScreen.getInstance().setLocation(BASE_WIDTH, 0);

      
        tick.start();
  
        addInputListeners();
    }

    public void setScreen(CurrentScreen newScreen) {
        currentScreen = newScreen;
    }

    public void startGame() {
        setScreen(CurrentScreen.GARDEN);
        Button screenSwitch = new ActionButton("Screen Switch", () -> {
            enterDoor();
        });
        add(screenSwitch);
        screenSwitch.setLocation(BASE_WIDTH - screenSwitch.getWidth(), BASE_HEIGHT - screenSwitch.getHeight());

        remove(MainMenuScreen.getInstance());
        add(GardenScreen.getInstance());
        add(RestaurantScreen.getInstance());
        GardenScreen.getInstance().sendToBack();
        RestaurantScreen.getInstance().sendToBack();
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

    public void enterDoor() {
        AtomicInteger endX = new AtomicInteger();
        if (shifting) {
            return;
        }

        if (currentScreen.equals(CurrentScreen.GARDEN)) {
            currentScreen = CurrentScreen.RESTAURANT;
            endX.set((int) (-BASE_WIDTH + ((float) BASE_WIDTH * .25)));
        } else if (currentScreen.equals(CurrentScreen.RESTAURANT)) {
            currentScreen = CurrentScreen.GARDEN;
            endX.set(0);
        }

        shifting = true;

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        long startTime = System.currentTimeMillis();

        executor.scheduleAtFixedRate(() -> {
            boolean done = shiftScreen(endX.get(), startTime);
            if (done) {
                shifting = false;
                executor.shutdown();
            }
        }, 0, 10, TimeUnit.MILLISECONDS);
    }

    public boolean shiftScreen(int endX, long startTime) {

        float progress = (System.currentTimeMillis() - startTime) / 800.0f;

        if (progress >= 1.0f) {
            GardenScreen.getInstance().setLocation(endX, 0);
            RestaurantScreen.getInstance().setLocation(BASE_WIDTH + endX, 0);
            CurrentScreen.GARDEN.setX(endX);
            CurrentScreen.RESTAURANT.setX(BASE_WIDTH + endX);
            repaint();
            return true;
        } else {
            GardenScreen.getInstance().setLocation(Utils.lerp(CurrentScreen.GARDEN.getX(), endX, easeInOutCubic(progress)), 0);
            RestaurantScreen.getInstance().setLocation(Utils.lerp(CurrentScreen.RESTAURANT.getX(), BASE_WIDTH + endX, easeInOutCubic(progress)), 0);
            repaint();
            return false;
        }
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
        GARDEN(0),
        RESTAURANT(BASE_WIDTH),
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