package UI;

import Character.Character;
import Character.Player;
import Enemy.Enemy;
import Enemy.EnemyType;
import Enemy.Path;
import Tower.SpatulaProjectile;
import Utils.GameTick;
import Utils.MouseInteract;
import Utils.MouseManager;
import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.random.RandomGenerator;

public class GameScreen extends GraphicsProgram {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 450;
    private static Path path;
    private static GameScreen instance;
    private GImage background;
    private GameTick tick;

    public static GameScreen getInstance() {
        if (instance == null) {
            instance = new GameScreen();
        }
        return instance;
    }

    public void setInstance(GameScreen gameScreen) {
        instance = gameScreen;
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

       
        GRect menuBar = new GRect(WIDTH, 50);
        menuBar.setFilled(true);
        menuBar.setFillColor(Color.LIGHT_GRAY);
        add(menuBar, 0, 0);

       
        Player player = Player.getInstance();

      
        NewTowerButton newTowerButton = new NewTowerButton("New Tower");
        add(newTowerButton, 650, 25); // Moved to the right side
    }

    @Override
    public void run() {
        add(Character.getInstance());
        GameTick.TickManager.registerTickListener(Character.getInstance());

        ArrayList<Enemy> enemies = new ArrayList<>();

        path = new Path(-10, 100, 100, 100, 100, 200, 200, 200, 200, 150, 300, 150, 300, 300, 150, 300);

        tick.start();

        for (int i = 0; i < 3; i++) {
            Enemy enemy = new Enemy(EnemyType.DOUGH);
            add(enemy);
            GameTick.TickManager.registerTickListener(enemy);
            System.out.println("Enemy added");
        }

        GameTick.ActionManager.addAction(1, () -> {
            addEnemy();
        });
//
//        Tower testTower;
//        testTower = new MeleeTower();
//        GameTick.TickManager.registerTickListener(testTower);
//        add(testTower);
//
//        Tower testTower2;
//        testTower2 = new MeleeTower();
//        GameTick.TickManager.registerTickListener(testTower2);
//        add(testTower2);
//
//        testTower2.sendToFront();

        Enemy enemy = new Enemy(EnemyType.DOUGH);
        SpatulaProjectile spatulaProjectile = new SpatulaProjectile(new GPoint(7, 7), new GPoint(100, 100), enemy, 1, 1, 10);
        add(spatulaProjectile, 500, 500);

        addKeyListeners(Character.getInstance());
        addMouseListeners();
        GameTick.ActionManager.addAction(40, () -> {
            enterDoor();
        });

        GameTick.ActionManager.addAction(80, () -> {
            enterDoor();
        });

    }

    public void addEnemy() {
        for (int i = 0; i < RandomGenerator.getDefault().nextInt(2, 5); i++) {
            Enemy enemy = new Enemy(EnemyType.DOUGH);
            enemy.sendToBack();
            add(enemy);
            GameTick.TickManager.registerTickListener(enemy);
        }
        GameTick.ActionManager.addAction(RandomGenerator.getDefault().nextInt(1, 10), () -> {
            addEnemy();
        });
    }

    public void enterDoor () {
        AtomicInteger endX = new AtomicInteger();
        if (currentScreen.equals(CurrentScreen.GARDEN)) {
            currentScreen = CurrentScreen.RESTAURANT;
            endX.set((int) (-WIDTH + ((float) WIDTH * .25)));
        } else if (currentScreen.equals(CurrentScreen.RESTAURANT)) {
            currentScreen = CurrentScreen.GARDEN;
            endX.set(0);
        }
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        long startTime = System.currentTimeMillis();

        executor.scheduleAtFixedRate(() -> {
            boolean done = shiftScreen(gw.getGCanvas().getX(), endX.get(), startTime);
            if (done) {
                executor.shutdown();
            }
        }, 0, 10, TimeUnit.MILLISECONDS);
    }

    public boolean shiftScreen(int startX, int endX, long startTime) {
        float progress = (System.currentTimeMillis() - startTime) / 800.0f;
        if (progress >= 1.0f) {
//            gw.getGCanvas().setLocation(endX, 0);
            repaint();
            return true;
        } else {

            gw.getGCanvas().setLocation((int) Utils.Utils.lerp(startX, endX, easeInOutCubic(progress)), 0);
            repaint();
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
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        GObject object = MouseManager.getSelectedObject();

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
            if (object instanceof MouseInteract o && object != MouseManager.getHoverObject()) {
                MouseManager.setHoverObject(object);
                o.onHover(e);
            } else {
                MouseManager.setHoverObject(object);
            }
        } else {
            MouseManager.setHoverObject(object);
        }
    }
}