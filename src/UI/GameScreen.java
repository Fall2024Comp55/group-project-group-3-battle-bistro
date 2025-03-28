package UI;

import Character.Character;
import Enemy.Enemy;
import Enemy.EnemyType;
import Enemy.Path;
import Tower.MeleeTower;
import Tower.Tower;
import Utils.GameTick;
import Utils.MouseInteract;
import Utils.MouseManager;
import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.program.GraphicsProgram;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.random.RandomGenerator;


public class GameScreen extends GraphicsProgram {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 450;
    private static Path path;
    private static GameScreen instance;
    // TODO FIX EVERYTHING ALL TEST STUFF
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
    }

    @Override
    public void run() {
        add(Character.getInstance());
        GameTick.TickManager.registerTickListener(Character.getInstance());


//        URL resource = getClass().getResource("/resources/enemy/dough.png");
//        if (resource != null) {
//        	System.out.println("Resource found" + resource);
//        	GImage test = new GImage(new ImageIcon(resource).getImage());
//        	add(test);
//        	test.setSize(100, 100);
//        	test.setLocation(100, 100);
//        } else {
//        	System.out.println("Resource not found" + resource);
//        }

        ArrayList<Enemy> enemies = new ArrayList<>();

        path = new Path(-10, 100, 100, 100, 100, 200, 200, 200, 200, 150, 300, 150, 300, 300, 150, 300);


        tick.start();


        for (int i = 0; i < 3; i++) {
            Enemy enemy = new Enemy(EnemyType.DOUGH);
            add(enemy);
            GameTick.TickManager.registerTickListener(enemy);
            System.out.println("Enemy added");
//            enemies.add(enemy);
        }

        GameTick.ActionManager.addAction(1, () -> {
            addEnemy();
        });

        Tower testTower;
        testTower = new MeleeTower();
        GameTick.TickManager.registerTickListener(testTower);

        add(testTower);

        Tower testTower2;
        testTower2 = new MeleeTower();
        GameTick.TickManager.registerTickListener(testTower2);

        add(testTower2);

        testTower2.sendToFront();


        addKeyListeners(Character.getInstance());
        addMouseListeners();

    }


    // !!! Testing for GameTick actions
    public void addEnemy() {
        for (int i = 0; i < RandomGenerator.getDefault().nextInt(20, 50); i++) {
            Enemy enemy = new Enemy(EnemyType.DOUGH);
            enemy.sendToBack();
            add(enemy);
            GameTick.TickManager.registerTickListener(enemy);
        }
        GameTick.ActionManager.addAction(RandomGenerator.getDefault().nextInt(1, 10), () -> {
            addEnemy();
        });
    }

    @Override
    public void mousePressed(MouseEvent e) {
        GObject object = getElementAt(e.getX(), e.getY());
        System.out.println(getElementCount());

        // set last click point in MouseManager
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
        // get selected object
        GObject object = MouseManager.getSelectedObject();

        // set last mouse point in MouseManager

        if (object != null) {
            if (object instanceof MouseInteract o) {
                o.onDrag(e);
            }
        }
        MouseManager.setLastMousePoint(e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // get selected object
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
        // set current mouse point in MouseManager
        MouseManager.setHoverPoint(e.getPoint());

        GObject object = getElementAt(e.getX(), e.getY());
        if (object != null) {
            if (object instanceof MouseInteract o && object != MouseManager.getHoverObject()) {
                o.onHover(e);
            }
        }
    }

}
