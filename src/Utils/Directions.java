package Utils;

import java.awt.event.KeyEvent;

/**
 * The Directions enum represents the possible movement directions with their respective
 * x and y coordinates and the angle (theta) in degrees.
 */
public enum Directions {
    UP(0, -1, 0, KeyEvent.VK_W),
    DOWN(0, 1, 180, KeyEvent.VK_S),
    LEFT(-1, 0, 90, KeyEvent.VK_A),
    RIGHT(1, 0, 270, KeyEvent.VK_D),
    UP_LEFT(-1, -1, 45),
    UP_RIGHT(1, -1, 315),
    DOWN_LEFT(-1, 1, 135),
    DOWN_RIGHT(1, 1, 225);

    private final int x;
    private final int y;
    private int keyEvent;
    private final int theta;

    /**
     * Constructs a Directions enum with the specified x, y coordinates and theta.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param theta The angle in degrees.
     */
    Directions(int x, int y, int theta) {
        this.x = x;
        this.y = y;
        this.theta = theta;
    }

    /**
     * Constructs a Directions enum with the specified x, y coordinates, theta and key event.
     *
     * @param x     The x coordinate.
     * @param y     The y coordinate.
     * @param theta The angle in degrees.
     * @param key   The key event associated with the direction.
     */
    Directions(int x, int y, int theta, int key) {
        this(x, y, theta);
        this.keyEvent = key;
    }

    /**
     * @return The key event associated with the direction.
     */
    public Integer getKey() {
        return keyEvent;
    }

    /**
     * Sets the key event associated with the direction.
     *
     * @param key The key event to set.
     */
    public void setKey(int key) {
        this.keyEvent = key;
    }

    /**
     * @return The x coordinate of the direction.
     */
    public int getX() {
        return x;
    }

    /**
     * @return The y coordinate of the direction.
     */
    public int getY() {
        return y;
    }

    /**
     * @return The angle (theta) of the direction degrees.
     */
    public int getTheta() {
        return theta;
    }
}