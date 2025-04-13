package UI;

import acm.graphics.GCompound;
import acm.graphics.GObject;

import java.util.HashSet;
import java.util.Set;

public abstract class UI extends GCompound {
    protected Set<GObject> elements;

    public UI() {
        elements = new HashSet<GObject>();
    }

    public abstract void initializeComponents();

    public abstract void update();

    public Set<GObject> getElements() {
        return elements;
    }

    @Override
    public void remove(GObject obj) {
        elements.remove(obj);
        super.remove(obj);
    }

    @Override
    public void removeAll() {
        elements.clear();
        super.removeAll();
    }

    @Override
    public void add(GObject gobj) {
        super.add(gobj);
        elements.add(gobj);
    }
}
