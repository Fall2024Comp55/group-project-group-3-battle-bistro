package Screen;

import Enemy.Enemy;
import Enemy.EnemyType;
import Enemy.Path;
import Tower.Projectile;
import Tower.Tower;
import UI.GardenUI;
import Utils.GameTick;
import Utils.TickListener;

import java.util.HashSet;
import java.util.Set;
import java.util.random.RandomGenerator;

public class GardenScreen extends Screen {
    private volatile Set<TickListener> enemyTickListeners;
    private volatile Set<TickListener> towerTickListeners;
    private volatile Set<TickListener> projectileTickListeners;

    private static final GardenScreen GARDEN_SCREEN;

    private static EnemyPath enemyPath;

    static {
        try {
            GARDEN_SCREEN = new GardenScreen();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating GardenScreen singleton instance");
        }
    }

    private GardenScreen() {
        // Initialize the garden screen components here
        enemyTickListeners = new HashSet<>();
        towerTickListeners = new HashSet<>();
        projectileTickListeners = new HashSet<>();
        initializeComponents();
    }

    public static GardenScreen getInstance() {
        return GARDEN_SCREEN;
    }

    public static EnemyPath getEnemyPath() {
        return enemyPath;
    }

    @Override
    public void initializeComponents() {
        enemyPath = new EnemyPath(-10, 100, 100, 100, 100, 200, 200, 200, 200, 150, 300, 150, 300, 300, 150, 300);
        add(GardenUI.getInstance());
        addEnemy();
        path.addPath(this);
    }

    public void addEnemy() {
        // add 2-5 new enemies and register to tick manager
        for (int i = 0; i < RandomGenerator.getDefault().nextInt(0, 3); i++) {
            Enemy enemy = new Enemy(EnemyType.DOUGH);
            enemy.sendToBack();
            add(enemy);
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
    public void unregisterTickListener(TickListener listener) {
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
    public void onTick() {
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
