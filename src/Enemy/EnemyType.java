package Enemy;

import Food.IngredientsType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EnemyType {
    DOUGH(15, 2, 1, IngredientsType.DOUGH),
    TOMATO(28, 3, 1, IngredientsType.SAUCE),
    MOZZARELLA(45, 4, 2, IngredientsType.MOZZARELLA),
    PEPPERONI(70, 5, 3, IngredientsType.PEPPERONI),
    MUSHROOM(150, 2, 5, IngredientsType.MUSHROOM);

    private static final String BASE_PATH = "/resources/enemy/";
    private static final String EXTENSION = ".png";

    private final int health;
    private final int speed;
    private final int damage;
    private final IngredientsType ingredientsType;

    EnemyType(int health, int speed, int damage, IngredientsType ingredientsType) {
        this.health = health;
        this.speed = speed;
        this.damage = damage;
        this.ingredientsType = ingredientsType;
    }

    public static List<EnemyType> getUnlockedEnemies() {
        return Arrays.stream(EnemyType.values())
                .filter(i -> i.isUnlocked())
                .collect(Collectors.toList());
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

    public String toPath() {
        return BASE_PATH + this.toString().toLowerCase() + EXTENSION;
    }

    public boolean isUnlocked() {
        return ingredientsType.isUnlocked();
    }

    public IngredientsType getIngredientsType() {
        return ingredientsType;
    }
}
