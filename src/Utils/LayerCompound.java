package Utils;

import Screen.Screen;
import acm.graphics.GCompound;
import acm.graphics.GObject;

import java.util.HashSet;
import java.util.Set;

public class LayerCompound extends GCompound {
    private final GCompound parent;
    private Set<GObject> elements;


    public LayerCompound(GCompound parent) {
        this.parent = parent;
        elements = new HashSet<GObject>();
    }

    public Set<GObject> getElements() {
        return elements;
    }

    public void unregisterAllTickListener() {
        if (parent instanceof Screen s) {
            for (GObject element : elements) {
                if (element instanceof TickListener listener) {
                    s.unregisterTickListener(listener);
                }
            }
        }
    }

    @Override
    public void remove(GObject obj) {
        if (parent instanceof Screen s && obj instanceof TickListener listener) {
            s.unregisterTickListener(listener);
        }
        elements.remove(obj);
        super.remove(obj);
    }

    @Override
    public void removeAll() {
        unregisterAllTickListener();
        elements.clear();
        super.removeAll();
    }

    @Override
    public void add(GObject gobj) {
        super.add(gobj);
        elements.add(gobj);
        if (parent instanceof Screen s && gobj instanceof TickListener listener) {
            s.registerTickListener(listener);
        }
    }

}
