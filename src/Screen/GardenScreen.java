package Screen;

import Enemy.Enemy;
import Enemy.EnemyPath;
import Enemy.EnemyType;
import Tower.Projectile;
import Tower.Tower;
import Utils.LayerCompound;
import Utils.TickListener;
import Utils.Utils;
import acm.graphics.GImage;
import acm.graphics.GObject;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.random.RandomGenerator;

public class GardenScreen extends Screen {
    private final Set<TickListener> enemyTickListeners;
    private final Set<TickListener> towerTickListeners;
    private final Set<TickListener> projectileTickListeners;
    private static final String FLOOR_PATH = "/resources/garden/grass.jpg";

    private static final GardenScreen GARDEN_SCREEN;

    private static EnemyPath enemyPath;
    private static GImage background;
    private static LayerCompound enemyLayer;
    private static LayerCompound towerLayer;
    private static LayerCompound projectileLayer;

    static {
        try {
            GARDEN_SCREEN = new GardenScreen();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating GardenScreen singleton instance");
        }
    }

    private GardenScreen() {
        enemyTickListeners = Collections.synchronizedSet(new HashSet<>());
        towerTickListeners = Collections.synchronizedSet(new HashSet<>());
        projectileTickListeners = Collections.synchronizedSet(new HashSet<>());
        enemyLayer = new LayerCompound(this);
        towerLayer = new LayerCompound(this);
        projectileLayer = new LayerCompound(this);

        initializeComponents();
    }

    public void reset() {
        enemyTickListeners.clear();
        towerTickListeners.clear();
        projectileTickListeners.clear();
        removeAll();
        initializeComponents();
    }

    public static GardenScreen getInstance() {
        return GARDEN_SCREEN;
    }

    public static EnemyPath getEnemyPath() {
        return enemyPath;
    }

    public static LayerCompound getEnemyLayer() {
        return enemyLayer;
    }

    @Override
    public void initializeComponents() {
        background = new GImage(Utils.getImage(FLOOR_PATH));
        add(background);
        enemyPath = new EnemyPath(
                -10, 400,   
                200, 400,   
                200, 300,   
                100, 300,   
                100, 100,   
                300, 100,   
                300, 350,   
                500, 350,   
                500, 200,   
                400, 200,
                400, 80,
                600, 80,
                600, 150,
                800, 150,
                800, 100
        );

        Enemy.setPath(enemyPath);
        enemyPath.addPathHitbox(this);
        enemyPath.showPathHitbox();
        enemyPath.addPath(this);

        add(projectileLayer);
        add(towerLayer);
        add(enemyLayer);
    }

    public void addEnemy() {
        Random random = new Random();
        if (random.nextBoolean()) {
            var enemies = EnemyType.getUnlockedEnemies();
            for (int i = 0; i < RandomGenerator.getDefault().nextInt(0, 3); i++) {
                EnemyType enemyType = enemies.get(random.nextInt(0, enemies.size()));
                Enemy enemy = new Enemy(enemyType);
                enemy.sendToBack();
                enemyLayer.add(enemy);
            }
        }

        for (int i = 0; i < RandomGenerator.getDefault().nextInt(0, 3); i++) {
            Enemy enemy = new Enemy(EnemyType.DOUGH);
            enemy.sendToBack();
            enemyLayer.add(enemy);
        }

    }

    public Set<TickListener> getEnemyTickListeners() {
        return enemyTickListeners;
    }

    public Set<TickListener> getTowerTickListeners() {
        return towerTickListeners;
    }

    public Set<TickListener> getProjectileTickListeners() {
        return projectileTickListeners;
    }

    @Override
    public synchronized void registerTickListener(TickListener listener) {
        if (listener instanceof Enemy) {
            enemyTickListeners.add(listener);
        } else if (listener instanceof Tower) {
            towerTickListeners.add(listener);
        } else if (listener instanceof Projectile) {
            projectileTickListeners.add(listener);
        } else {
            throw new IllegalArgumentException("Invalid listener type");
        }
    }

    @Override
    public synchronized void unregisterTickListener(TickListener listener) {
        if (listener instanceof Enemy) {
            enemyTickListeners.remove(listener);
        } else if (listener instanceof Tower) {
            towerTickListeners.remove(listener);
        } else if (listener instanceof Projectile) {
            projectileTickListeners.remove(listener);
        } else {
            throw new IllegalArgumentException("Invalid listener type");
        }
    }

    @Override
    public void unregisterAllTickListener() {
        enemyTickListeners.clear();
        towerTickListeners.clear();
        projectileTickListeners.clear();
    }

    @Override
    public void update() {
    }

    @Override
    public void add(GObject gobj) {
        if (gobj instanceof Enemy) {
            enemyLayer.add(gobj);
        } else if (gobj instanceof Tower) {
            towerLayer.add(gobj);
        } else if (gobj instanceof Projectile) {
            projectileLayer.add(gobj);
        } else {
            super.add(gobj);
            return;
        }
    }

    @Override
    public void onTick() {
        if (RestaurantScreen.isDayStarted()) {
            screenExecutor.submit(() -> {
                addEnemy();
                enemyTickListeners.spliterator().forEachRemaining(TickListener::onTick);
            });
            screenExecutor.submit(() -> {
                towerTickListeners.spliterator().forEachRemaining(TickListener::onTick);
            });
            screenExecutor.submit(() -> {
                projectileTickListeners.spliterator().forEachRemaining(TickListener::onTick);
            });
        }
    }
}