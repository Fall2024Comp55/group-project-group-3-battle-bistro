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


public class GameTick implements ActionListener {
    public static final int tickRate = 20;
    public static final int tickDelay = 50;
    public static final int timerDelay = 1;


    private final Timer timer;
    private final GraphicsProgram screen;
    private final Set<TickListener> registeredTickListeners;
    private final Multimap<Long, Action> actions;
    private long lastTickTime;
    private long currentTick;
    private long ticksPerSecond;

    public GameTick(GraphicsProgram screen) {
        timer = new Timer(timerDelay, this);
        registeredTickListeners = new LinkedHashSet<TickListener>();
        actions = ArrayListMultimap.create();
        this.screen = screen;
    }

    public void start() {
        timer.start();
        lastTickTime = System.currentTimeMillis();
    }

    public void stop() {
        timer.stop();
    }

    public void registerTickListener(TickListener tickListener) {
        registeredTickListeners.add(tickListener);
    }

    public void unregisterTickerListener(TickListener tickListener) {
        registeredTickListeners.remove(tickListener);
    }

    public void addAction(long tickDelay, Action action) {
        if (tickDelay < 0) {
            throw new IllegalArgumentException("Tick delay must be greater than or equal to 0");
        }
        actions.put(tickDelay + currentTick, action);
    }

    public void removeAction(Action action) {
        actions.values().remove(action);
    }

    // !!! FIXME
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getWhen() - lastTickTime >= tickDelay) {
            ticksPerSecond = Math.ceilDiv(1000, (e.getWhen() - lastTickTime));
            if ((currentTick + 1) % 20 == 0) {
                // xxx sout ticks per second
                System.out.println("Ticks per second: " + ticksPerSecond);
            }
            tick(Math.round((float) ((e.getWhen() - lastTickTime) / tickDelay) - 1));
            lastTickTime = e.getWhen();
        }
    }

    private void tick(long missingTicks) {
        currentTick++;
        // xxx sout tick and registered tick listeners
        if (currentTick % 100 == 0) {
            System.out.println("Tick: " + currentTick);
            System.out.println("Registered tick listeners: " + registeredTickListeners.size());
        }

//        for (TickListener listener : registeredTickListeners) {
//            listener.onTick(this);
//        }

        registeredTickListeners.parallelStream().spliterator().forEachRemaining(listener -> {
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

        if (!actions.isEmpty() && actions.containsKey(currentTick)) {
            actions.get(currentTick).parallelStream().spliterator().forEachRemaining(action -> {
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

    public long getCurrentTick() {
        return currentTick;
    }

    public long getLastTickTime() {
        return lastTickTime;
    }
}
