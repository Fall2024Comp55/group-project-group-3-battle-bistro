package Screen;

import Utils.TickListener;
import acm.graphics.GCompound;
import acm.graphics.GObject;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class Screen extends GCompound implements TickListener {
    protected Set<GObject> elements;
    protected ExecutorService screenExecutor;

    Screen() {
        // Initialize the elements set
        elements = new HashSet<GObject>();
        screenExecutor = Executors.newFixedThreadPool(10);
    }

    // Abstract method to be implemented by subclasses
    public abstract void initializeComponents();

    public abstract void registerTickListener(TickListener listener);

    public abstract void unregisterTickListener(TickListener listener);

    public abstract void unregisterAllTickListener();

    public Set<GObject> getElements() {
        return elements;
    }

    @Override
    public void remove(GObject obj) {
        if (obj instanceof TickListener listener) {
            unregisterTickListener(listener);
        }
        elements.remove(obj);
        super.remove(obj);
    }

    @Override
    public void removeAll() {
        elements.clear();
        unregisterAllTickListener();
        super.removeAll();
    }

    @Override
    public void add(GObject gobj) {
        super.add(gobj);
        elements.add(gobj);
        if (gobj instanceof TickListener listener) {
            registerTickListener(listener);
        }
    }

    // Other common methods for screens can be added here
}
