package UI;

import Character.Character;
import Enemy.Enemy;
import Enemy.EnemyType;
import Enemy.Path;
import Tower.TestTower;
import Tower.Tower;
import Utils.MouseInteract;
import Utils.GameTick;
import acm.graphics.*;
import acm.program.GraphicsProgram;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.random.RandomGenerator;


public class GameScreen extends GraphicsProgram {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 450;

    // TODO FIX EVERYTHING ALL TEST STUFF
    GImage background;
    public GCompound garden;
    public GameTick tick;
    public Point lastClickPoint;
    public Point lastMousePoint;
    public Path path;
    private Character character;
    private static GameScreen instance;


    public void init() {
        setSize(WIDTH, HEIGHT);
        this.gw.setTitle("Battle Bistro");
        this.gw.setResizable(false);
        this.gw.setLocationRelativeTo(null);
        this.requestFocus();
        garden = new GCompound();
        tick = new GameTick(this);
        this.setAutoRepaintFlag(false);
        this.character = new Character();
    }

    public void setInstance(GameScreen gameScreen) {
        instance = gameScreen;
    }

    public static GameScreen getInstance() {
        if (instance == null) {
            instance = new GameScreen();
        }
        return instance;
    }


    public GCompound getGarden() {
        return garden;
    }

    @Override
    public void run() {
        add(garden);
        add(character);
        tick.registerTickListener(character);
        
        
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

        add(path);


        tick.start();

        GCompound test;
        test = new GCompound();
        garden.add(test);


        for (int i = 0; i < 3; i++) {
            Enemy enemy = new Enemy(EnemyType.DOUGH, path);
            add(enemy);
            tick.registerTickListener(enemy);
            System.out.println("Enemy added");
            enemies.add(enemy);
        }

        tick.addAction(1, () -> {;
            addEnemy();
        });

        Tower testTower;
        testTower = new TestTower();

        add(testTower);

        Tower testTower2;
        testTower2 = new TestTower();

        add(testTower2, 100, 100);

        testTower2.sendToFront();





        addKeyListeners(character);
        addMouseListeners();

    }


    // !!! Testing for GameTick actions
    public void addEnemy() {
        for (int i = 0; i < RandomGenerator.getDefault().nextInt(20, 50); i++) {
            Enemy enemy = new Enemy(EnemyType.DOUGH, path);
            enemy.sendToBack();
            add(enemy);
            tick.registerTickListener(enemy);
        }
        tick.addAction(RandomGenerator.getDefault().nextInt(1, 10), () -> {
            addEnemy();
        });
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO possible code for mouse press need to work on dragging
        GObject object = getElementAt(e.getX(), e.getY());
        lastClickPoint = e.getPoint();
        if (object != null) {
            if (object instanceof MouseInteract o) {
                o.onPress(e, lastClickPoint);
            }

        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO possible code for mouse drag need to work on dragging
        GObject object = getElementAt(e.getX(), e.getY());
        lastMousePoint = e.getPoint();
        if (object != null) {
            if (object instanceof MouseInteract o) {
                o.onDrag(e, lastClickPoint, lastMousePoint);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO possible code for mouse release need to work on dragging
        GObject object = getElementAt(e.getX(), e.getY());
        if (object != null) {
            if (object instanceof MouseInteract o) {
                o.onRelease(e, lastClickPoint, lastMousePoint);
            }
        }
        lastMousePoint = e.getPoint();
        lastClickPoint = null;
    }

}
