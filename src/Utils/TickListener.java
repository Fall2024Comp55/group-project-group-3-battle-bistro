package Utils;

import java.util.LinkedHashSet;
import java.util.Set;

public interface TickListener {
    final Set<TickListener> registeredTickListeners = new LinkedHashSet<TickListener>();

    static void registerTickListener(TickListener tickListener) {
        registeredTickListeners.add(tickListener);
    }

    static void unregisterTickListener(TickListener tickListener) {
        registeredTickListeners.remove(tickListener);
    }

    static Set<TickListener> getRegisteredTickListeners() {
        return registeredTickListeners;
    }

    void onTick(GameTick tick);
}
