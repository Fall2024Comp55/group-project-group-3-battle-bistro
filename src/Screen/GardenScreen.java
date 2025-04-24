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
import java.util.Set;
import java.util.random.RandomGenerator;

public class GardenScreen extends Screen {
    private final Set<TickListener> enemyTickListeners;
    private final Set<TickListener> towerTickListeners;
    private final Set<TickListener> projectileTickListeners;
    private static final String FLOOR_PATH = "/resources/grass.jpg";

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
        // Initialize the garden screen components here
        enemyTickListeners = Collections.synchronizedSet(new HashSet<>());
        towerTickListeners = Collections.synchronizedSet(new HashSet<>());
        projectileTickListeners = Collections.synchronizedSet(new HashSet<>());
        enemyLayer = new LayerCompound(this);
        towerLayer = new LayerCompound(this);
        projectileLayer = new LayerCompound(this);

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

        enemyPath = new EnemyPath(-10, 100, 100, 100, 100, 200, 200, 200, 200, 150, 300, 150, 300, 300, 150, 300);
        Enemy.setPath(enemyPath);
        enemyPath.showPath();
        enemyPath.addPathHitbox(this);
        enemyPath.showPathHitbox();
        enemyPath.addPath(this);

        add(towerLayer);
        add(enemyLayer);
        add(projectileLayer);
    }

    public void addEnemy() {
        // add 2-5 new enemies and register to tick manager
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
                // remove later
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
