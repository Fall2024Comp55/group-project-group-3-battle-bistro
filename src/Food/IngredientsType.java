package Food;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public enum IngredientsType {
    DOUGH(IngredientType.DOUGH),
    SAUCE(IngredientType.SAUCE),
    MOZZARELLA(IngredientType.CHEESE, 50),
    MUSHROOM(IngredientType.TOPPING, 100),
    PEPPERONI(IngredientType.TOPPING, 200);

    private static final String INGREDIENTS_BASE_PATH = "/resources/ingredients/";
    private static final String STATION_BASE_PATH = "/resources/restaurant/stations/";
    private static final String EXTENSION = ".png";

    private IngredientType type;
    private boolean unlocked;
    private int price;


    IngredientsType(IngredientType type, int price) {
        this.type = type;
        this.price = price;
        this.unlocked = false;
    }

    IngredientsType(IngredientType type) {
        this.type = type;
        this.price = -1;
        this.unlocked = true;
    }

    public int getPrice() {
        return price;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public static IngredientsType getRandomIngredient() {
        Random random = new Random();
        int randomIndex = random.nextInt(getUnlockedIngredients().size());
        return getUnlockedIngredients().get(randomIndex);
    }

    public static List<IngredientsType> getUnlockedIngredients() {
        return Arrays.stream(IngredientsType.values())
                .filter(i -> i.isUnlocked() && i.isTopping())
                .collect(Collectors.toList());
    }

    public String toString() {
        return switch (this) {
            case DOUGH -> "Dough";
            case MOZZARELLA -> "Mozzarella";
            case PEPPERONI -> "Pepperoni";
            case MUSHROOM -> "Mushroom";
            case SAUCE -> "Sauce";
        };
    }

    public boolean isTopping() {
        return type == IngredientType.TOPPING ? true : false;
    }

    public String toStationPath() {
        return switch (this) {
            case DOUGH -> STATION_BASE_PATH + "dough" + EXTENSION;
            case MOZZARELLA -> STATION_BASE_PATH + "mozzarella" + EXTENSION;
            case PEPPERONI -> STATION_BASE_PATH + "pepperoni" + EXTENSION;
            case MUSHROOM -> STATION_BASE_PATH + "mushroom" + EXTENSION;
            case SAUCE -> STATION_BASE_PATH + "sauce" + EXTENSION;
        };
    }

    public String toIngredientPath() {
        return switch (this) {
            case DOUGH -> INGREDIENTS_BASE_PATH + "dough" + EXTENSION;
            case MOZZARELLA -> INGREDIENTS_BASE_PATH + "mozzarella" + EXTENSION;
            case PEPPERONI -> INGREDIENTS_BASE_PATH + "pepperoni" + EXTENSION;
            case MUSHROOM -> INGREDIENTS_BASE_PATH + "mushroom" + EXTENSION;
            case SAUCE -> INGREDIENTS_BASE_PATH + "sauce" + EXTENSION;
        };
    }

    public enum IngredientType {
        DOUGH,
        SAUCE,
        CHEESE,
        TOPPING;
    }
}
