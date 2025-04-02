package UI;

import Character.Character;
import Enemy.Enemy;
import Enemy.EnemyType;
import Enemy.Path;
import Utils.GameTick;
import Utils.MouseInteract;
import Utils.MouseManager;
import acm.graphics.GCompound;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.random.RandomGenerator;

import static Utils.Utils.easeInOutCubic;

public class GameScreen extends GraphicsProgram {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 450;
    public static final String globalFont = "Arial-16"; // Reduced font size for better fit
    public static final Color globalColor = Color.BLACK;

    private static final GameScreen instance;
    private static CurrentScreen currentScreen;
    private static Path path;

    private GameTick tick;

    static {
        try {
            instance = new GameScreen();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating GameScreen singleton instance");
        }
    }

    public static GameScreen getInstance() {
        return instance;
    }

    public static Path getPath() {
        return path;
    }

    public void init() {
        setSize(WIDTH, HEIGHT);
        this.gw.setTitle("Battle Bistro");
        this.gw.setResizable(false);
        this.gw.setLocationRelativeTo(null);
        this.requestFocus();
        this.tick = new GameTick();
        this.setAutoRepaintFlag(false);
    }

    @Override
    public void run() {
        // add and register character to tick manager
        add(Character.getInstance());
        GameTick.TickManager.registerTickListener(Character.getInstance());
        // set current screen to GARDEN
        currentScreen = CurrentScreen.GARDEN;
        // add GardenUI to screen
        add(GardenUI.getInstance());

        // create new path
        path = new Path(-10, 100, 100, 100, 100, 200, 200, 200, 200, 150, 300, 150, 300, 300, 150, 300);

        // start GameTick
        tick.start();

        addEnemy();

       /*  // Door animation test
        GameTick.ActionManager.addAction(40, this::enterDoor);
        GameTick.ActionManager.addAction(80, this::enterDoor);
*/

        Button screenSwitch = new ActionButton("Screen Switch", () -> {
            ;
            enterDoor();
        });
        add(screenSwitch);

        screenSwitch.setLocation(WIDTH - screenSwitch.getWidth(), HEIGHT - screenSwitch.getHeight());

        // Add input listeners
        addInputListeners();
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

    public void addEnemy() {
        // add 2-5 new enemies and register to tick manager
        for (int i = 0; i < RandomGenerator.getDefault().nextInt(2, 5); i++) {
            Enemy enemy = new Enemy(EnemyType.DOUGH);
            enemy.sendToBack();
            add(enemy);
            GameTick.TickManager.registerTickListener(enemy);
        }
        // every 1-10 ms run this function again
        GameTick.ActionManager.addAction(RandomGenerator.getDefault().nextInt(1, 10), () -> {
            addEnemy();
        });
    }

    public void enterDoor() {
        AtomicInteger endX = new AtomicInteger();
        // check if currentScreen is GARDEN or RESTAURANT and switch.
        if (currentScreen.equals(CurrentScreen.GARDEN)) {
            currentScreen = CurrentScreen.RESTAURANT;
            remove(GardenUI.getInstance());
            endX.set((int) (-WIDTH + ((float) WIDTH * .25)));
        } else if (currentScreen.equals(CurrentScreen.RESTAURANT)) {
            currentScreen = CurrentScreen.GARDEN;
            add(GardenUI.getInstance());
            endX.set(0);
        }

        // new executor to run the animation
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        // set the start time
        long startTime = System.currentTimeMillis();

        // setup executor to run screen shift every 10ms until done
        executor.scheduleAtFixedRate(() -> {
            boolean done = shiftScreen(gw.getGCanvas().getX(), endX.get(), startTime);
            if (done) {
                executor.shutdown();
            }
        }, 0, 10, TimeUnit.MILLISECONDS);
    }

    public boolean shiftScreen(int startX, int endX, long startTime) {
        // get animation progress
        float progress = (System.currentTimeMillis() - startTime) / 800.0f;
        // check if progress is at the end
        if (progress >= 1.0f) {
            // commented out as there was a wierd jump at the end
//            gw.getGCanvas().setLocation(endX, 0);
            //TODO remove repaint later
            repaint();
            // return true ending executor
            return true;
        } else {
            // set the location of the canvas shifted towards the endX
            gw.getGCanvas().setLocation((int) Utils.Utils.lerp(startX, endX, easeInOutCubic(progress)), 0);
            //TODO remove repaint later
            repaint();
            // return false to keep executor running
            return false;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        GObject object = getElementAt(e.getX(), e.getY());
        System.out.println(getElementCount());

        MouseManager.setLastClickPoint(e.getPoint());
        MouseManager.setLastMousePoint(e.getPoint());

        if (object != null) {
            if (object instanceof MouseInteract o) {
                MouseManager.setSelectedObject(object);
                o.onPress(e);
            } else if (object instanceof GCompound c) {
                c.forEach(o -> {
                    if (o instanceof MouseInteract m && o.contains(new GPoint(e.getX(), e.getY()))) {
                        MouseManager.setSelectedObject(o);
                        m.onPress(e);
                    }
                });
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        GObject object = MouseManager.getSelectedObject();
        MouseManager.setHoverObject(object);

        if (object != null) {
            if (object instanceof MouseInteract o) {
                o.onDrag(e);
            }
        }
        MouseManager.setLastMousePoint(e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        GObject object = MouseManager.getSelectedObject();

        if (object != null) {
            if (object instanceof MouseInteract o) {
                o.onRelease(e);
            }
        }
        MouseManager.setLastClickPoint(e.getPoint());
        MouseManager.setSelectedObject(null);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
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
                for (GObject o : c) {
                    if (o instanceof MouseInteract m && o.contains(new GPoint(e.getX(), e.getY()))) {
                        if (o != MouseManager.getHoverObject()) {
                            MouseManager.setHoverObject(o);
                            m.onHover(e);
                        }
                        found = true;
                    }
                }
                if (!found && MouseManager.getHoverObject() != null) {
                    MouseManager.setHoverObject(null);
                }
            }
        } else if (MouseManager.getHoverObject() != null) {
        }
    }

    public enum CurrentScreen {
        MAIN_MENU,
        SUMMARY,
        GARDEN,
        RESTAURANT,
        SETTINGS
    }

}