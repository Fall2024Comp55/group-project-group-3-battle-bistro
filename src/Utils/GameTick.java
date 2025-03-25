package Utils;

import Food.Food;
import acm.program.GraphicsProgram;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;


public class GameTick implements ActionListener {
    public static final int tickRate = 20;
    public static final int tickDelay = 50;
    public static final int timerDelay = 1;


    private final Timer timer;
    private final GraphicsProgram screen;
    private long lastTickTime;
    private static AtomicLong currentTick;
    private long ticksPerSecond;

    public GameTick(GraphicsProgram screen) {
        timer = new Timer(timerDelay, this);
        if (currentTick == null) {
            currentTick = new AtomicLong(0);
        }
        this.screen = screen;
    }

    public void start() {
        timer.start();
        lastTickTime = System.currentTimeMillis();
    }

    public void stop() {
        timer.stop();
    }

    // !!! FIXME
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getWhen() - lastTickTime >= tickDelay) {
            ticksPerSecond = Math.ceilDiv(1000, (e.getWhen() - lastTickTime));
            if ((currentTick.get() + 1) % 20 == 0) {
                // xxx sout ticks per second
                System.out.println("Ticks per second: " + ticksPerSecond);
            }
            tick(Math.round((float) ((e.getWhen() - lastTickTime) / tickDelay) - 1));
            lastTickTime = e.getWhen();
        }
    }

    private void tick(long missingTicks) {
        currentTick.incrementAndGet();
        // xxx sout tick and registered tick listeners
        if (currentTick.get() % 100 == 0) {
            System.out.println("Tick: " + currentTick);
            System.out.println("Registered tick listeners: " + TickManager.getRegisteredTickListeners().size());
        }

//        for (TickListener listener : registeredTickListeners) {
//            listener.onTick(this);
//        }

        TickManager.getRegisteredTickListeners().parallelStream().spliterator().forEachRemaining(listener -> {
            listener.onTick(this);
        });

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

        if (!ActionManager.getActions().isEmpty() && ActionManager.getActions().containsKey(currentTick.get())) {
            ActionManager.getActions().get(currentTick.get()).parallelStream().spliterator().forEachRemaining(action -> {
                if (action instanceof Action act) {
                    act.performAction();
                }
            });

        }

        // TODO redo missing tick. Maybe make a timer in actionPerformed that tries to reclaim missing ticks every 20 ticks since this is not reclaiming all ticks probably becuase of rounding
//        if (missingTicks > 0) {
//            System.out.println("Missing ticks: " + missingTicks);
//            Timer tickReclaimer;
//            tickReclaimer = new Timer(1, new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    for (int i = 0; i < missingTicks; i++) {
//                        tick(0);
//                    }
//                    ((Timer) e.getSource()).stop();
//                }
//            });
//            tickReclaimer.start();
//        }
        screen.repaint();
    }

    public long getTicksPerSecond() {
        return ticksPerSecond;
    }

    public static long getCurrentTick() {
        return currentTick.get();
    }

    public long getLastTickTime() {
        return lastTickTime;
    }


    public static class TickManager {
        private static final Set<TickListener> registeredTickListeners = new LinkedHashSet<TickListener>();
        private static AtomicLong currentTick;

        public static void registerTickListener(TickListener tickListener) {
            registeredTickListeners.add(tickListener);
        }

        public static void unregisterTickListener(TickListener tickListener) {
            registeredTickListeners.remove(tickListener);
        }

        public static Set<TickListener> getRegisteredTickListeners() {
            return registeredTickListeners;
        }

        public static AtomicLong getCurrentTick() {
            return currentTick;
        }

        public static long getCurrentTickValue() {
            return currentTick.get();
        }
    }

    public static class ActionManager {
        private static final Multimap<Long, Action> actions = ArrayListMultimap.create();

        public static void addAction(long tickDelay, Action action) {
            if (tickDelay < 0) {
                throw new IllegalArgumentException("Tick delay must be greater than or equal to 0");
            }
            actions.put(tickDelay + GameTick.getCurrentTick(), action);
        }

        public static void removeAction(Action action) {
            actions.values().remove(action);
        }

        public static Multimap<Long, Action> getActions() {
            return actions;
        }
    }

}
