package Utils;

/**
 * The Directions enum represents the possible movement directions with their respective
 * x and y coordinates and the angle (theta) in degrees.
 */
public enum Directions {
    UP(0, -1, 0),
    DOWN(0, 1, 180),
    LEFT(-1, 0, 90),
    RIGHT(1, 0, 270),
    UP_LEFT(-1, -1, 45),
    UP_RIGHT(1, -1, 315),
    DOWN_LEFT(-1, 1, 135),
    DOWN_RIGHT(1, 1, 225);

            private final int x;
    private final int y;
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