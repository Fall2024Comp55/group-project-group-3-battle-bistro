package Utils;

import UI.GameScreen;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


public class GameTick implements ActionListener, Runnable{
    public static final int tickRate = 20;
    public static final int tickDelay = 50;
    public static final int timerDelay = 5;
//    private final Timer timer;
    private final ScheduledExecutorService scheduler;
    private long lastTickTime;
    private long ticksPerSecond;

    public GameTick() {
//        timer = new Timer(timerDelay, this);
        scheduler = Executors.newScheduledThreadPool(2);
    }

    public void start() {
//        timer.start();
        scheduler.scheduleAtFixedRate(this, 0, timerDelay, TimeUnit.MILLISECONDS);
        lastTickTime = System.currentTimeMillis();
    }

    public void stop() {
//        timer.stop();
        scheduler.shutdown();
    }

    // !!! need to work on this and figure out what is best


    // !!! FIXME
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action performed");
        long currentTime = System.currentTimeMillis();
        ticksPerSecond = Math.ceilDiv(1000, currentTime - lastTickTime);
        if (ticksPerSecond < tickRate) {
            System.out.println("Warning: Ticks per second is less than the tick rate" + ticksPerSecond + " " + (currentTime - lastTickTime));
        }
        if (currentTime - lastTickTime >= tickDelay) {
            tick();
            lastTickTime = currentTime;
        }
        tickReclaimer();
    }

    private void tick() {
        TickManager.incrementCurrentTick();
        // xxx sout tick and registered tick listeners
        if (TickManager.getCurrentTickValue() % 100 == 0) {
            System.out.println("Tick: " + TickManager.getCurrentTickValue());
            System.out.println("Registered tick listeners: " + TickManager.getRegisteredTickListeners().size());
        }

//        for (TickListener listener : registeredTickListeners) {
//            listener.onTick(this);
//        }

        TickManager.getRegisteredTickListeners().parallelStream().spliterator().forEachRemaining(listener -> {
            listener.onTick(this);
        });

        performActions();

//        !!! This is the original code
//        registeredTickListeners.spliterator().forEachRemaining(object -> {;
//            if (object instanceof TickListener listener) {
//                listener.onTick(this);
//            }
//        });
//
//
//
//        registeredTickListeners.forEach(object -> {
//            if (object instanceof TickListener listener) {
//                listener.onTick(this);
//            }
//        });
    }

    public void performActions() {
        if (!ActionManager.getActions().isEmpty() && ActionManager.getActions().containsKey(TickManager.getCurrentTickValue())) {
            ActionManager.getActions().get(TickManager.getCurrentTickValue()).parallelStream().spliterator().forEachRemaining(action -> {
                if (action instanceof Action act) {
                    act.performAction();
                }
            });
        }
    }

    public void tickReclaimer() {
        if (System.currentTimeMillis() - lastTickTime >= tickDelay) {
            System.out.println("Tick reclaimer");
            tick();
            lastTickTime += tickDelay;
        }
    }


    public long getTicksPerSecond() {
        return ticksPerSecond;
    }

    public long getLastTickTime() {
        return lastTickTime;
    }

    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTickTime >= tickDelay) {
            long timeBetweenTicks = currentTime - lastTickTime;
//            System.out.println("Time between ticks: " + timeBetweenTicks + " ms");
            tick();
            lastTickTime = currentTime;
        }
        GameScreen.getInstance().repaint();
        tickReclaimer();
    }


    public static class TickManager {
        private static final Set<TickListener> registeredTickListeners = new LinkedHashSet<TickListener>();
        private static final AtomicLong currentTick = new AtomicLong(0);

        public static void registerTickListener(TickListener tickListener) {
            registeredTickListeners.add(tickListener);
            if (tickListener instanceof Tower) {
                // register to TowerManager
            } else if (tickListener instanceof Enemy) {
                // register to EnemyManager
            } else if (tickListener instanceof Projectile) {
                // register to ProjectileManager
            } else if (tickListener.getClass().getPackageName().equals("Restaurant")) {
                // register to RestaurantManager
            } else {
                // register to GameTick
            }
        }

        public static void unregisterTickListener(TickListener tickListener) {
            registeredTickListeners.remove(tickListener);
            if (tickListener instanceof Tower) {
                // unregister from TowerManager
            } else if (tickListener instanceof Enemy) {
                // unregister from EnemyManager
            } else if (tickListener instanceof Projectile) {
                // unregister from ProjectileManager
            } else if (tickListener.getClass().getPackageName().equals("Restaurant")) {
                // unregister from RestaurantManager
            } else {
                // unregister from GameTick
            }
        }

        public static Set<TickListener> getRegisteredTickListeners() {
            return registeredTickListeners;
        }

        public static AtomicLong getCurrentTick() {
            return currentTick;
        }

        public static void setCurrentTick(long tick) {
            currentTick.set(tick);
        }

        public static long getCurrentTickValue() {
            return currentTick.get();
        }

        public static void incrementCurrentTick() {
            currentTick.incrementAndGet();
        }


    }

    public static class ActionManager {
        private static final Multimap<Long, Action> actions = ArrayListMultimap.create();

        public static void addAction(long tickDelay, Action action) {
            if (tickDelay < 0) {
                throw new IllegalArgumentException("Tick delay must be greater than or equal to 0");
            }
            actions.put(tickDelay + TickManager.getCurrentTickValue(), action);
        }

        public static void removeAction(Action action) {
            actions.values().remove(action);
        }

        public static Multimap<Long, Action> getActions() {
            return actions;
        }
    }

}
