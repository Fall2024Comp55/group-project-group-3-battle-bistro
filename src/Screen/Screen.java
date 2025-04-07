package Screen;

import Utils.GameTick;
import Utils.TickListener;
import acm.graphics.GCompound;
import acm.graphics.GObject;

import java.util.HashSet;
import java.util.Set;

public abstract class Screen extends GCompound {
    protected Set<GObject> elements;

    Screen() {
        // Initialize the elements set
        elements = new HashSet<GObject>();
    }

    // Abstract method to be implemented by subclasses
    public abstract void initializeComponents();

    public abstract void registerTickListener(TickListener listener);

    public abstract void unregisterTickListener(TickListener listener);

    public abstract void unregisterAllTickListener();

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
            // change to registerTickListener(listener);
            GameTick.TickManager.registerTickListener(listener);
        }
    }

    // Other common methods for screens can be added here
}
