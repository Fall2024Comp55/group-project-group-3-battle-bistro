package Utils;

public enum Directions {
    UP(0, -1, 0),
    DOWN(0, 1, 180),
    LEFT(-1, 0, 90),
    RIGHT(1, 0, 270),
    UP_LEFT(-1, -1, 45),
    UP_RIGHT(1, -1, 315),
    DOWN_LEFT(-1, 1, 135),
    DOWN_RIGHT(1, 1, 225),
    ;

    private final int x;
    private final int y;
    private final int theta;

    Directions(int x, int y, int theta) {
        this.x = x;
        this.y = y;
        this.theta = theta;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTheta() {
        return theta;
    }
}
