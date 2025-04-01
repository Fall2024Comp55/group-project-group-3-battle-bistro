package Utils;

/**
 * The TickListener interface should be implemented by any class that wants to receive
 * notifications about game ticks. A game tick is an event that occurs at regular intervals
 * during the game's execution.
 */
public interface TickListener {

    /**
     * This method is called whenever a game tick occurs.
     */
    void onTick();
}