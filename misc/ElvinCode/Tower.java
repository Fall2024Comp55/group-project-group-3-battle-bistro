package ElvinCode;

import java.util.ArrayList;

public class Tower {
    private int x, y;
    private int damage;
    private int range;

    public Tower(int x, int y, int damage, int range) {
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.range = range;
    }

    public void setPosition(int x, int y, int[][] path) {
        if (!isOnPath(x, y, path)) {
            this.x = x;
            this.y = y;
        }
    }

    private boolean isOnPath(int x, int y, int[][] path) {
        for (int i = 0; i < path.length - 1; i++) {
            int px1 = path[i][0], py1 = path[i][1];
            int px2 = path[i + 1][0], py2 = path[i + 1][1];

            double distance = pointToSegmentDistance(x, y, px1, py1, px2, py2);
            if (distance < 30) { 
                return true;
            }
        }
        return false;
    }

    private double pointToSegmentDistance(int x, int y, int x1, int y1, int x2, int y2) {
        double px = x2 - x1;
        double py = y2 - y1;
        double temp = (px * px) + (py * py);
        double u = ((x - x1) * px + (y - y1) * py) / temp;

        if (u > 1) u = 1;
        else if (u < 0) u = 0;

        double dx = x1 + u * px - x;
        double dy = y1 + u * py - y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void attack(ArrayList<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (enemy.isAlive() && Math.hypot(enemy.getX() - x, enemy.getY() - y) < range) {
                enemy.takeDamage(damage);
                break;
            }
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
