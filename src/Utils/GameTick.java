package Utils;

import Screen.*;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


public class GameTick implements Runnable {
    public static final int TICK_RATE = 20;
    public static final int TICK_DELAY = 50;
    public static final int TIMER_DELAY = 5;

    private final ScheduledExecutorService gameTick;
    private long lastTickTime;
    private long ticksPerSecond;

    public GameTick() {
        gameTick = Executors.newSingleThreadScheduledExecutor();
    }

    public void start() {
        gameTick.scheduleAtFixedRate(this, 0, TIMER_DELAY, TimeUnit.MILLISECONDS);
        lastTickTime = System.currentTimeMillis();
    }

    public void stop() {
        gameTick.shutdown();
    }

    private void tick() {
        TickManager.incrementCurrentTick();

        GardenScreen.getInstance().onTick();
        RestaurantScreen.getInstance().onTick();
        MainMenuScreen.getInstance().onTick();
        SummaryMenuScreen.getInstance().onTick();


        performActions();
    }

    public synchronized void performActions() {
        if (!ActionManager.getActions().isEmpty() && ActionManager.getActions().containsKey(TickManager.getCurrentTickValue())) {
            ActionManager.getActions().get(TickManager.getCurrentTickValue()).parallelStream().spliterator().forEachRemaining(action -> {
                if (action instanceof Action act) {
                    act.performAction();
                }
            });
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
        if (currentTime - lastTickTime >= TICK_DELAY) {
            long timeBetweenTicks = currentTime - lastTickTime;
//            System.out.println("Time between ticks: " + timeBetweenTicks + " ms");
            tick();
            lastTickTime = currentTime;
        }
        ProgramWindow.getInstance().repaint();
    }


    /**
     * The TickManager class manages the registration and tracking of tick listeners
     * and the current tick value in the game.
     */
    public static class TickManager {
        private static final AtomicLong currentTick;

        // initializing the static variables
        static {
            currentTick = new AtomicLong(0);
        }

        /**
         * @return the current tick as an AtomicLong
         */
        public static AtomicLong getCurrentTick() {
            return currentTick;
        }

        /**
         * Sets the current tick to the specified value. Should be used to reset the tick counter.
         *
         * @param tick the new tick value
         */
        public static void setCurrentTick(long tick) {
            currentTick.set(tick);
        }

        /**
         * @return the current tick value
         */
        public static long getCurrentTickValue() {
            return currentTick.get();
        }

        /**
         * Increments the current tick value by one.
         * Should not be called outside of GameTick Class.
         */
        public synchronized static void incrementCurrentTick() {
            currentTick.incrementAndGet();
        }


    }

    /**
     * The ActionManager class manages actions that are to be performed
     * at specific future tick in the game.
     */
    public static class ActionManager {
        private static final Multimap<Long, Action> actions;

        // initializing the static variables
        static {
            actions = ArrayListMultimap.create();
        }

        /**
         * Adds an action to be performed after a specified tick delay.
         *
         * @param tickDelay the delay in ticks after which the action should be performed
         * @param action    the action to be performed
         * @throws IllegalArgumentException if the tick delay is less than 0
         */
        public static void addAction(long tickDelay, Action action) {
            if (tickDelay < 0) {
                throw new IllegalArgumentException("Tick delay must be greater than or equal to 0");
            }
            actions.put(tickDelay + TickManager.getCurrentTickValue(), action);
        }

        /**
         * Removes a specified action from the scheduled actions.
         *
         * @param action the action to be removed
         */
        public static void removeAction(Action action) {
            actions.values().remove(action);
        }

        /**
         * @return the multimap of scheduled actions
         */
        public static Multimap<Long, Action> getActions() {
            return actions;
        }
    }
}
