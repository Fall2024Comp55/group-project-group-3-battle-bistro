package Food;

public enum IngredientsType {
    DOUGH,
    MOZZARELLA,
    PEPPERONI(100),
    MUSHROOM(200),
    SAUCE(50);

    private static final String INGREDIENTS_BASE_PATH = "/resources/ingredients/";
    private static final String STATION_BASE_PATH = "/resources/restaurant/stations/";
    private static final String EXTENSION = ".png";

    private boolean unlocked;
    private int price;


    IngredientsType(int price) {
        this.price = price;
        this.unlocked = false;
    }

    IngredientsType() {
        this.price = -1;
        this.unlocked = true;
    }

    public int getPrice() {
        return price;
    }

    public void setUnlocked(boolean locked) {
        this.unlocked = locked;
    }

    public boolean isLocked() {
        return unlocked;
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
}
