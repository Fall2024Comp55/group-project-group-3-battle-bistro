package Screen;

import Enemy.Enemy;
import UI.GardenUI;
import Utils.GameTick;
import Enemy.Path;
import Utils.TickListener;
import Enemy.EnemyType;

import java.util.Set;
import java.util.random.RandomGenerator;

public class GardenScreen extends Screen {
    private volatile Set<TickListener> enemyTickListeners;
    private volatile Set<TickListener> towerTickListeners;
    private volatile Set<TickListener> projectileTickListeners;

    private static final GardenScreen GARDEN_SCREEN;

    private static Path path;

    static {
        try {
            GARDEN_SCREEN = new GardenScreen();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating GardenScreen singleton instance");
        }
    }

    private GardenScreen() {
        // Initialize the garden screen components here
        initializeComponents();
    }

    public static GardenScreen getInstance() {
        return GARDEN_SCREEN;
    }

    public static Path getPath() {
        return path;
    }

    @Override
    public void initializeComponents() {
        path = new Path(-10, 100, 100, 100, 100, 200, 200, 200, 200, 150, 300, 150, 300, 300, 150, 300);
        add(GardenUI.getInstance());
        addEnemy();
    }

    public void addEnemy() {
        // add 2-5 new enemies and register to tick manager
        for (int i = 0; i < RandomGenerator.getDefault().nextInt(2, 5); i++) {
            Enemy enemy = new Enemy(EnemyType.DOUGH);
            enemy.sendToBack();
            add(enemy);
        }
        // every 1-10 ms run this function again
        GameTick.ActionManager.addAction(RandomGenerator.getDefault().nextInt(1, 10), () -> {
            addEnemy();
        });
    }

    @Override
    public void registerTickListener(TickListener listener) {

    }

    @Override
    public void unregisterTickListener(TickListener listener) {

    }

    @Override
    public void unregisterAllTickListener() {

    }
}
