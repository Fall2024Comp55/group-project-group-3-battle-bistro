package Enemy;

public enum EnemyType {
    DOUGH(30, 20, 2),
    MOZZARELLA(50, 1, 3),
    PEPPERONI(70, 1, 4),
    MUSHROOM(90, 3, 5);

    private static final String BASE_PATH = "/resources/enemy/";
    private static final String EXTENSION = ".png";

    private final int health;
    private final int speed;
    private final int damage;

    EnemyType(int health, int speed, int damage) {
        this.health = health;
        this.speed = speed;
        this.damage = damage;
    }

    public String toString() {
        return switch (this) {
            case DOUGH -> "Dough";
            case MOZZARELLA -> "Mozzarella";
            case PEPPERONI -> "Pepperoni";
            case MUSHROOM -> "Mushroom";
        };
    }

    public String toPath() {
        return switch (this) {
            case DOUGH -> BASE_PATH + "dough" + EXTENSION;
            case MOZZARELLA -> BASE_PATH + "mozzarella" + EXTENSION;
            case PEPPERONI -> BASE_PATH + "pepperoni" + EXTENSION;
            case MUSHROOM -> BASE_PATH + "mushroom" + EXTENSION;
        };
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDamage() {
        return damage;
    }
}
