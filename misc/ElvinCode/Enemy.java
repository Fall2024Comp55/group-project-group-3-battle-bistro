package ElvinCode;

public class Enemy {
    private int x, y;
    private int health;
    private int speed;
    private boolean isAlive;
    private int pathIndex;
    private int[][] path = {
        {0, 100}, {100, 100}, {100, 200}, {200, 200}, {200, 300}, {300, 300}, {400, 300}, {500, 250}
    };

    public Enemy(int health, int speed) {
        this.x = path[0][0];
        this.y = path[0][1];
        this.health = health;
        this.speed = speed;
        this.isAlive = true;
        this.pathIndex = 0;
    }

    public void move() {
        if (isAlive && pathIndex < path.length - 1) {
            int targetX = path[pathIndex + 1][0];
            int targetY = path[pathIndex + 1][1];

            int dx = targetX - x;
            int dy = targetY - y;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < speed) {
                x = targetX;
                y = targetY;
                pathIndex++;
            } else {
                x += (int) Math.round((dx / distance) * speed);
                y += (int) Math.round((dy / distance) * speed);
            }
        }
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            isAlive = false;
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
