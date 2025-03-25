package Utils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public interface Action {
    final Multimap<Long, Action> actions = ArrayListMultimap.create();
    // TODO work on Action interface. Allow for a action to be added to GameTick Action list and then performed.

    static void addAction(long tickDelay, Action action) {
        if (tickDelay < 0) {
            throw new IllegalArgumentException("Tick delay must be greater than or equal to 0");
        }
        actions.put(tickDelay + GameTick.getCurrentTick(), action);
    }

    static void removeAction(Action action) {
        actions.values().remove(action);
    }

    static Multimap<Long, Action> getActions() {
        return actions;
    }

    void performAction();
}
